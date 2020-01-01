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
import de.quarian.weaver.SettingsActivity;
import de.quarian.weaver.assets.ViewScheduledToDeleteActivity;
import de.quarian.weaver.namesets.ManageNameSetsActivity;
import de.quarian.weaver.namesets.dev.DevelopFunctionsScreen;

public class CampaignListActivity extends AppCompatActivity {

    private List<CampaignDisplayObject> campaignDisplayObjects = new ArrayList<>();

    public CampaignListActivity() {
        //TODO: replace dummy code
        campaignDisplayObjects.add(new CampaignDisplayObject(1, "Campaign 1"));
        campaignDisplayObjects.add(new CampaignDisplayObject(2, "Campaign 2"));
        campaignDisplayObjects.add(new CampaignDisplayObject(3, "Campaign 3"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);
        setUpToolbar();
        setUpFloatingActionButton();

        final RecyclerView campaignList = findViewById(R.id.campaign_list);
        final Context baseContext = getBaseContext();
        campaignList.setLayoutManager(new LinearLayoutManager(baseContext));
        campaignList.setHasFixedSize(true);

        final CampaignListAdapter campaignListAdapter = new CampaignListAdapter(this);
        campaignList.setAdapter(campaignListAdapter);
        campaignListAdapter.setCampaignDisplayObjects(campaignDisplayObjects);
    }

    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.activity_title_campaign_screen);
    }

    private void setUpFloatingActionButton() {
        final View floatingActionButton = findViewById(R.id.campaign_list_add_campaign);
        floatingActionButton.setOnClickListener((view) -> NavigationController.getInstance().addCampaign(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.DEBUG) {
            final MenuInflater menuInflater = new MenuInflater(getBaseContext());
            menuInflater.inflate(R.menu.menu_campaign_list_activit_developer, menu);
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
            case R.id.menu_item_manage_name_sets: {
                manageNameSets();
                break;
            }
            case R.id.menu_item_view_scheduled_to_delete: {
                viewScheduledToDelete();
            }
            case R.id.menu_item_developer_options: {
                if (BuildConfig.DEBUG) {
                    goToDeveloperScreen();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAppSettings() {
        final Context baseContext = getBaseContext();
        final Intent intent = new Intent(baseContext, SettingsActivity.class);
        startActivity(intent);
    }

    private void manageNameSets() {
        final Context baseContext = getBaseContext();
        final Intent intent = new Intent(baseContext, ManageNameSetsActivity.class);
        startActivity(intent);
    }

    private void viewScheduledToDelete() {
        final Context baseContext = getBaseContext();
        final Intent intent = new Intent(baseContext, ViewScheduledToDeleteActivity.class);
        startActivity(intent);
    }

    private void goToDeveloperScreen() {
        final Context baseContext = getBaseContext();
        final Intent intent = new Intent(baseContext, DevelopFunctionsScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CampaignActivity.REQUEST_CODE_MODIFY_CAMPAIGNS && resultCode == Activity.RESULT_OK) {
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
