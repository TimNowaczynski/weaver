package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.BuildConfig;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.RequestCodes;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public class CampaignListActivity extends AppCompatActivity {

    private List<CampaignListDisplayObject> campaignListDisplayObjects = new ArrayList<>();

    public CampaignListActivity() {
        //TODO: replace dummy code
        final CampaignListDisplayObject campaignA = new CampaignListDisplayObject();
        campaignA.setCampaignId(1L);
        campaignA.setCampaignName("Campaign 1");
        final CampaignListDisplayObject campaignB = new CampaignListDisplayObject();
        campaignB.setCampaignName("Campaign 2");
        campaignB.setCampaignId(2L);
        final CampaignListDisplayObject campaignC = new CampaignListDisplayObject();
        campaignC.setCampaignName("Campaign 3");
        campaignC.setCampaignId(3L);
        campaignListDisplayObjects.add(campaignA);
        campaignListDisplayObjects.add(campaignB);
        campaignListDisplayObjects.add(campaignC);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);
        setUpToolbar();
        setUpButtons();

        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final Context baseContext = getBaseContext();
        campaignList.setLayoutManager(new LinearLayoutManager(baseContext));
        campaignList.setHasFixedSize(true);

        final CampaignListAdapter campaignListAdapter = new CampaignListAdapter(this);
        campaignList.setAdapter(campaignListAdapter);
        campaignListAdapter.setCampaignListDisplayObjects(campaignListDisplayObjects);
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
