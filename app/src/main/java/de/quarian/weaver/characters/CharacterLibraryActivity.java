package de.quarian.weaver.characters;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.RequestCodes;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.util.AndroidToastHandler;

// TODO: extend themed activity + integration test
public class CharacterLibraryActivity extends AppCompatActivity {

    public static class ActivityDependencies {

        @Inject
        public CampaignDAO campaignDAO;

        @Inject
        public AndroidToastHandler androidToastHandler;

    }

    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public final ActivityDependencies activityDependencies = new ActivityDependencies();

    //private CampaignDAO campaignDAO;
    private long campaignId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_library);
        DependencyInjector.get().injectDependencies(this);
        setUpToolbar();

        campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, -1L);
        if (campaignId < 0) {
            throw new IllegalArgumentException("Campaign ID not provided in extras");
        }

        setResult(RESULT_OK);
        AsyncTask.execute(() -> {
            final Campaign campaign = readCampaign();
            runOnUiThread(() -> setTitle(campaign.campaignName));
        });
    }

    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.activity_title_character_library);
    }

    private Campaign readCampaign() {
        return activityDependencies.campaignDAO.readCampaignByID(campaignId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTask.execute(() -> {
            final Campaign campaign = readCampaign();
            campaign.lastUsedDataMillis = System.currentTimeMillis();
            activityDependencies.campaignDAO.updateCampaign(campaign);
        });
    }

    // Listeners

    public void onAddCharacterClicked(View view) {
        NavigationController.getInstance().addNewCharacter(this);
    }

    public void onViewCharacterClicked(View view) {
        NavigationController.getInstance().viewCharacter(this);
    }

    // CAB

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = new MenuInflater(getBaseContext());
        AsyncTask.execute(() -> {
            final Campaign campaign = readCampaign();
            runOnUiThread(() -> {
                if (campaign.archived) {
                    menuInflater.inflate(R.menu.menu_character_library_activity_deactivated, menu);
                } else {
                    menuInflater.inflate(R.menu.menu_character_library_activity_activated, menu);
                }
            });
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        AsyncTask.execute(() -> {
            final Campaign campaign = readCampaign();
            switch (itemId) {
                case R.id.menu_item_deactivate_campaign: {
                    campaign.archived = true;
                    break;
                }
                case R.id.menu_item_activate_campaign: {
                    campaign.archived = false;
                    break;
                }
            }
            activityDependencies.campaignDAO.updateCampaign(campaign);
        });

        switch (itemId) {
            case R.id.menu_item_deactivate_campaign: {
                activityDependencies.androidToastHandler.showToast(R.string.activity_character_library_campaign_deactivated, Toast.LENGTH_LONG);
                break;
            }
            case R.id.menu_item_activate_campaign: {
                activityDependencies.androidToastHandler.showToast(R.string.activity_character_library_campaign_activated, Toast.LENGTH_LONG);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO: Edit option for list items

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.MODIFY_CHARACTERS && resultCode == RESULT_OK) {
            //TODO: refresh content
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
