package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CampaignDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(this.weaverDB);
        DatabaseTestUtils.setUpThemes(this.weaverDB);
    }

    @Test
    public void testCreateAndReadCampaign() {
        // Fetch Roleplaying System ID
        final RoleplayingSystem shadowrunRPS = weaverDB.roleplayingSystemDAO().readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN);
        final long shadowrunRolePlayingSystemID = shadowrunRPS.id;

        final Campaign shadowrunCampaignInput = new Campaign();
        shadowrunCampaignInput.roleplayingSystemId = shadowrunRolePlayingSystemID;
        shadowrunCampaignInput.themeId = 1L;
        shadowrunCampaignInput.campaignName = DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON;
        shadowrunCampaignInput.archived = true;
        shadowrunCampaignInput.synopsis = DatabaseTestConstants.CAMPAIGN_SYNOPSIS_RISING_DRAGON;

        final long timestamp = System.currentTimeMillis();
        final long created = timestamp - 50000L;
        final long edited = timestamp - 25000L;
        final long lastUsed = timestamp;
        shadowrunCampaignInput.creationDateMillis = created;
        shadowrunCampaignInput.editDateMillis = edited;
        shadowrunCampaignInput.lastUsedDataMillis = lastUsed;

        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        campaignDAO.createCampaign(shadowrunCampaignInput);

        final Campaign shadowrunCampaignOutput = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);
        assertThat(shadowrunCampaignOutput.id, greaterThan(0L));
        assertThat(shadowrunCampaignOutput.roleplayingSystemId, is(shadowrunRolePlayingSystemID));
        assertThat(shadowrunCampaignOutput.themeId, is(1L));
        assertThat(shadowrunCampaignOutput.campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
        assertThat(shadowrunCampaignOutput.archived, is(true));
        assertThat(shadowrunCampaignOutput.synopsis, is(DatabaseTestConstants.CAMPAIGN_SYNOPSIS_RISING_DRAGON));
        assertThat(shadowrunCampaignOutput.creationDateMillis, is(created));
        assertThat(shadowrunCampaignOutput.editDateMillis, is(edited));
        assertThat(shadowrunCampaignOutput.lastUsedDataMillis, is(lastUsed));
    }

    @Test
    public void testUpdateCampaign() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Read
        Campaign shadowrunCampaignOutput = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);
        assertThat(shadowrunCampaignOutput.archived, is(false));

        // Update
        final Campaign shadowrunCampaignInput = shadowrunCampaignOutput;
        shadowrunCampaignInput.archived = true;
        campaignDAO.updateCampaign(shadowrunCampaignInput);

        // Confirm
        shadowrunCampaignOutput = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);
        assertThat(shadowrunCampaignOutput.archived, is(true));
    }

    @Test
    public void testDeleteCampaign() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Read
        List<Campaign> campaigns = campaignDAO.readCampaigns();
        assertThat(campaigns, hasSize(3));

        // Delete
        campaignDAO.deleteCampaign(campaigns.get(0));

        // Confirm
        campaigns = campaignDAO.readCampaigns();
        assertThat(campaigns, hasSize(2));
    }
}
