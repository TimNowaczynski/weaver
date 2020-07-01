package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.BuildConfig;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.RequestCodes;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.assets.ViewScheduledToDeleteActivity;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.di.ApplicationContext;
import de.quarian.weaver.di.CampaignListOrderPreferences;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.schedulers.IoScheduler;
import de.quarian.weaver.service.CampaignService;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

// TODO: Investigate the odd delay when querying campaigns
public class CampaignListActivity extends WeaverActivity implements AdapterView.OnItemSelectedListener {

    public static class ActivityDependencies {

        // TODO: possibly remove this later on, it's just meant as a POC
        @Inject
        @ApplicationContext
        public Context applicationContext;

        @Inject
        @GlobalHandler
        public Handler globalHandler;

        @Inject
        @CampaignListOrderPreferences
        public SharedPreferences campaignListOrderPreferences;

        @Inject
        public WeaverDB weaverDB;

        @Inject
        public CampaignService campaignService;

        @IoScheduler
        @Inject
        public Scheduler io;

        @Inject
        public WeaverLayoutInflater weaverLayoutInflater;

        @Inject
        public CampaignListInformationHandler campaignListInformationHandler;

    }

    public final ActivityDependencies activityDependencies = new ActivityDependencies();
    private CampaignService.SortOrder currentSortOrder = CampaignService.SortOrder.CAMPAIGN_NAME;
    private CampaignListAdapter campaignListAdapter;

    @VisibleForTesting
    public void setIoScheduler(final @NonNull Scheduler scheduler) {
        activityDependencies.io = scheduler;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);
        DependencyInjector.get().injectDependencies(this);
        campaignListAdapter = new CampaignListAdapter(this, activityDependencies.weaverLayoutInflater);

        setUpToolbar();
        setUpButtons();
        setUpSortOrderSpinner();
        setUpRecyclerView();
    }

    @Override
    public void onDependenciesInjected() {
        // Nothing so far
    }

    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.activity_title_campaign_screen);
    }

    private void setUpButtons() {
        setUpFloatingActionButton();
    }

    private void setUpFloatingActionButton() {
        final View floatingActionButton = findViewById(R.id.campaign_list_add_campaign);
        floatingActionButton.setOnClickListener((view) -> NavigationController.getInstance().addCampaign(this));
    }

    private void setUpSortOrderSpinner() {
        final Spinner sortOrderSpinner = findViewById(R.id.campaign_list_sort_order_spinner);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(activityDependencies.applicationContext, R.array.activity_campaign_list_sort_order_spinner_options, R.layout.campaign_list_spinner);
        sortOrderSpinner.setAdapter(spinnerAdapter);

        final String preselectedOrderRaw = activityDependencies.campaignListOrderPreferences.getString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, null);
        if (preselectedOrderRaw != null) {
            final CampaignService.SortOrder sortOrder = CampaignService.SortOrder.valueOf(preselectedOrderRaw);
            currentSortOrder = sortOrder;
            sortOrderSpinner.setSelection(sortOrder.getPosition());
        }

        sortOrderSpinner.setOnItemSelectedListener(this);
    }

    private void setUpRecyclerView() {
        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final Context baseContext = getBaseContext();
        campaignList.setLayoutManager(new LinearLayoutManager(baseContext));
        campaignList.setHasFixedSize(true);
        campaignList.setAdapter(campaignListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpInfoSection();
        final RefreshDisplayObjectsEvent event = new RefreshDisplayObjectsEvent();
        EventBus.getDefault().post(event);
    }

    private void setUpInfoSection() {
        final TextView ticker = findViewById(R.id.campaign_list_ticker);
        ticker.setSelected(true);
        final String information = activityDependencies.campaignListInformationHandler.getProcessedInformation();
        ticker.setText(information);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.DEBUG) {
            final MenuInflater menuInflater = new MenuInflater(getBaseContext());
            menuInflater.inflate(R.menu.menu_campaign_list_activity_developer, menu);
            return true;
        } else {
            final MenuInflater menuInflater = new MenuInflater(getBaseContext());
            menuInflater.inflate(R.menu.menu_campaign_list_activity, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_item_app_settings: {
                openAppSettings();
                break;
            }
            case R.id.menu_item_view_scheduled_to_delete: {
                viewScheduledToDelete();
                break;
            }
            case R.id.menu_item_developer_options: {
                if (BuildConfig.DEBUG) {
                    goToDeveloperScreen();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAppSettings() {
        final NavigationController navigationController = NavigationController.getInstance();
        navigationController.manageSettings(this);
    }

    private void viewScheduledToDelete() {
        final NavigationController navigationController = NavigationController.getInstance();
        navigationController.viewScheduledToDelete(this, ViewScheduledToDeleteActivity.NO_CAMPAIGN_FILTER);
    }

    private void goToDeveloperScreen() {
        final NavigationController navigationController = NavigationController.getInstance();
        navigationController.openDeveloperOptions(this);
    }

    // Spinner selection callbacks start

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // So I'd like a save option instead of wiring positions, but right now I don't have a clue if there's something available
        CampaignService.SortOrder selectedSortOrder = null;
        switch (position) {
            case 0:
                selectedSortOrder = CampaignService.SortOrder.CAMPAIGN_NAME;
                break;
            case 1:
                selectedSortOrder = CampaignService.SortOrder.SYSTEM_NAME;
                break;
            case 2:
                selectedSortOrder = CampaignService.SortOrder.LAST_USED;
                break;
            case 3:
                selectedSortOrder = CampaignService.SortOrder.LAST_EDITED;
                break;
            case 4:
                selectedSortOrder = CampaignService.SortOrder.CREATED;
                break;
        }

        if (selectedSortOrder != null && selectedSortOrder != currentSortOrder) {
            storeSortOrder(selectedSortOrder);
            final RefreshDisplayObjectsEvent event = new RefreshDisplayObjectsEvent();
            EventBus.getDefault().post(event);
        }
    }

    private void storeSortOrder(CampaignService.SortOrder selectedSortOrder) {
        activityDependencies.campaignListOrderPreferences.edit()
                .putString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, selectedSortOrder.name())
                .apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // I think it's safe to ignore this for now
    }

    // Spinner selection callbacks end

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == RequestCodes.MODIFY_CAMPAIGNS || requestCode == RequestCodes.MODIFY_PLAYER_CHARACTERS)
                && resultCode == Activity.RESULT_OK) {
            final RefreshDisplayObjectsEvent event = new RefreshDisplayObjectsEvent();
            EventBus.getDefault().post(event);
        } else if (requestCode == RequestCodes.RESTART_ACTIVITY && resultCode == Activity.RESULT_OK) {
            final Intent intent = new Intent(getBaseContext(), CampaignListActivity.class);
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(final RefreshDisplayObjectsEvent event) {
        final CampaignService.SortOrder sortOrder = getSortOrder();
        queryDisplayObjects(sortOrder);
    }

    private CampaignService.SortOrder getSortOrder() {
        final String sortOrderName = activityDependencies.campaignListOrderPreferences.getString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, CampaignService.SortOrder.CAMPAIGN_NAME.name());
        return CampaignService.SortOrder.valueOf(sortOrderName);
    }

    private void queryDisplayObjects(final CampaignService.SortOrder sortOrder) {
        final Disposable disposable = Observable.just(activityDependencies.campaignService)
                .subscribeOn(activityDependencies.io)
                .subscribe((campaignService) -> {
                    currentSortOrder = sortOrder;
                    final List<CampaignListDisplayObject> displayObjects = campaignService.readCampaigns(sortOrder);
                    campaignListAdapter.setCampaignListDisplayObjects(displayObjects);
                    activityDependencies.globalHandler.post(campaignListAdapter::notifyDataSetChanged);
                });
        activityDependencies.globalHandler.postDelayed(disposable::dispose, 500L);
    }
}
