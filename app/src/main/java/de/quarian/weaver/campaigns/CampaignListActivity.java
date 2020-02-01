package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;
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
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.di.ApplicationContext;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;

public class CampaignListActivity extends AppCompatActivity {

    // TODO: possibly remove this later on, it's just meant as a POC
    @Inject
    @ApplicationContext
    public Context applicationContext;

    @Inject
    public WeaverDB weaverDB;

    private CampaignListAdapter campaignListAdapter = new CampaignListAdapter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);
        injectDependencies();

        setUpToolbar();
        setUpButtons();
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
            final List<CampaignListDisplayObject> displayObjects = readCampaignListDisplayObjectsFromDB();
            campaignListAdapter.setCampaignListDisplayObjects(displayObjects);
            runOnUiThread(() -> campaignListAdapter.notifyDataSetChanged());
        });
    }

    // TODO: implement order
    private List<CampaignListDisplayObject> readCampaignListDisplayObjectsFromDB() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final List<CampaignListDisplayObject> displayObjects = new LinkedList<>();
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByLastUsed();
        for (final Campaign campaign : campaigns) {
            final Theme theme = themeDAO.readThemeByID(campaign.themeId);
            displayObjects.add(CampaignListDisplayObject.createFrom(campaign, theme));
        }
        return displayObjects;
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
