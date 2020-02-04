package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.BuildConfig;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.RequestCodes;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.di.ApplicationContext;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.CampaignListOrderPreferences;
import de.quarian.weaver.di.DaggerApplicationComponent;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.service.CampaignService;

public class CampaignListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    private CampaignListAdapter campaignListAdapter = new CampaignListAdapter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);
        injectDependencies();

        setUpToolbar();
        setUpButtons();
        setUpSortOrderSpinner();
        setUpRecyclerView();
    }

    private void injectDependencies() {
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build()
                .inject(this);
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
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(applicationContext, R.array.activity_campaign_list_sort_order_spinner_options, R.layout.campaign_list_spinner);
        sortOrderSpinner.setAdapter(spinnerAdapter);

        final String preselectedOrderRaw = campaignListOrderPreferences.getString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, null);
        if (preselectedOrderRaw != null) {
            /*
                So, again I don't really like that it's hardwired. An option might be to convert
                the enum names into the displayed strings, on the other hand that might break just
                as easily.
             */
            final CampaignService.SortOrder sortOrder = CampaignService.SortOrder.valueOf(preselectedOrderRaw);
            final int position;
            switch (sortOrder) {
                case CAMPAIGN_NAME:
                default:
                    position = 0;
                    break;
                case SYSTEM_NAME:
                    position = 1;
                    break;
                case LAST_USED:
                    position = 2;
                    break;
                case LAST_EDITED:
                    position = 3;
                    break;
                case CREATED:
                    position = 4;
                    break;
            }
            sortOrderSpinner.setSelection(position);
        }

        sortOrderSpinner.setOnItemSelectedListener(this);
    }

    private void setUpRecyclerView() {
        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final Context baseContext = getBaseContext();
        campaignList.setLayoutManager(new LinearLayoutManager(baseContext));
        campaignList.setHasFixedSize(true);
        campaignList.setAdapter(campaignListAdapter);
        queryDisplayObjects();
    }

    private void queryDisplayObjects() {
        AsyncTask.execute(() -> {
            final List<CampaignListDisplayObject> displayObjects = campaignService.readCampaignsWithOrderFromPreferences();
            campaignListAdapter.setCampaignListDisplayObjects(displayObjects);
            globalHandler.post(() -> campaignListAdapter.notifyDataSetChanged());
        });
    }

    private void queryDisplayObjects(final CampaignService.SortOrder sortOrder) {
        AsyncTask.execute(() -> {
            final List<CampaignListDisplayObject> displayObjects = campaignService.readCampaigns(sortOrder);
            campaignListAdapter.setCampaignListDisplayObjects(displayObjects);
            globalHandler.post(() -> campaignListAdapter.notifyDataSetChanged());
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            campaignListAdapter.notifyDataSetChanged();
        }
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
        navigationController.viewScheduledToDelete(this);
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

        if (selectedSortOrder != null) {
            campaignListOrderPreferences.edit()
                    .putString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, selectedSortOrder.name())
                    .apply();
            queryDisplayObjects(selectedSortOrder);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // I think it's safe to ignore this for now
    }

    // Spinner selection callbacks end

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.MODIFY_CAMPAIGNS && resultCode == Activity.RESULT_OK) {
            refreshList();
        } else if (requestCode == RequestCodes.RESTART_ACTIVITY && resultCode == Activity.RESULT_OK) {
            final Intent intent = new Intent(getBaseContext(), CampaignListActivity.class);
            finish();
            startActivity(intent);
        }
    }

    private void refreshList() {
        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final RecyclerView.Adapter adapter = campaignList.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
