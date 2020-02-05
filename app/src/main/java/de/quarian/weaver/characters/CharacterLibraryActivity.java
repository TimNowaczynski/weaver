package de.quarian.weaver.characters;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.RequestCodes;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;

public class CharacterLibraryActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Inject
    public WeaverDB weaverDB;

    private long campaignId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_library);
        setTitle(R.string.activity_title_character_library);
        injectDependencies();

        campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, -1L);
        if (campaignId < 0) {
            throw new IllegalArgumentException("Campaign ID not provided in extras");
        }

        setResult(RESULT_OK);
        AsyncTask.execute(() -> {
            final CampaignDAO campaignDAO = weaverDB.campaignDAO();
            final Campaign campaign = campaignDAO.readCampaignByID(campaignId);
            runOnUiThread(() -> setTitle(campaign.campaignName));
        });
    }

    private void injectDependencies() {
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTask.execute(() -> {
            final CampaignDAO campaignDAO = weaverDB.campaignDAO();
            final Campaign campaign = campaignDAO.readCampaignByID(campaignId);
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

    //TODO: Edit option for list items

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.MODIFY_CHARACTERS && resultCode == RESULT_OK) {
            //TODO: refresh content
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
