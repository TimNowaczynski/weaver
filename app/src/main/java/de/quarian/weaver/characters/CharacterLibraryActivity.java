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
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;

// TODO: extend themed activity + integration test
public class CharacterLibraryActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Inject
    public WeaverDB weaverDB;

    private CampaignDAO campaignDAO;
    private long campaignId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_library);
        injectDependencies();
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

    private void injectDependencies() {
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build()
                .inject(this);
        campaignDAO = weaverDB.campaignDAO();
    }

    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.activity_title_character_library);
    }

    private Campaign readCampaign() {
        return campaignDAO.readCampaignByID(campaignId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTask.execute(() -> {
            final Campaign campaign = readCampaign();
            campaign.lastUsedDataMillis = System.currentTimeMillis();
            campaignDAO.updateCampaign(campaign);
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
            campaignDAO.updateCampaign(campaign);
        });

        switch (itemId) {
            case R.id.menu_item_deactivate_campaign: {
                Toast.makeText(getBaseContext(), R.string.activity_character_library_campaign_deactivated, Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.menu_item_activate_campaign: {
                Toast.makeText(getBaseContext(), R.string.activity_character_library_campaign_activated, Toast.LENGTH_LONG).show();
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
