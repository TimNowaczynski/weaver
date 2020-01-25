package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
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

    @After
    public void closeDatabase() {
        this.weaverDB.close();
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
        shadowrunCampaignInput.campaignImage = "image".getBytes();
        shadowrunCampaignInput.campaignImageType = "image/gif";

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
        assertThat(shadowrunCampaignOutput.campaignImage, is("image".getBytes()));
        assertThat(shadowrunCampaignOutput.campaignImageType, is("image/gif"));
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

    /**
     * Order by name should be Die Borbarad Kampagne, Renaissance, Rising Dragon
     */
    @Test
    public void testOrderByCampaignName() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Confirm
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByName();
        assertThat(campaigns.get(0).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD));
        assertThat(campaigns.get(1).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE));
        assertThat(campaigns.get(2).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
    }

    /**
     * Order by name should be Die Borbarad Kampagne, Renaissance, Rising Dragon
     */
    @Test
    public void testOrderBySystemName() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Confirm
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedBySystemName();
        assertThat(campaigns.get(0).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD));
        assertThat(campaigns.get(1).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
        assertThat(campaigns.get(2).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE));
    }

    /**
     * Order by created should be Renaissance, Die Borbarad Kampagne, Rising Dragon
     */
    @Test
    public void testOrderByCreated() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Confirm
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByCreated();
        assertThat(campaigns.get(0).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE));
        assertThat(campaigns.get(1).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD));
        assertThat(campaigns.get(2).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
    }

    /**
     * Order by last edited date should be Rising Dragon, Renaissance, Die Borbarad Kampagne
     */
    @Test
    public void testOrderByLastEdited() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Confirm
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByLastEdited();
        assertThat(campaigns.get(0).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
        assertThat(campaigns.get(1).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE));
        assertThat(campaigns.get(2).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD));
    }

    /**
     * Order by last used date should be Die Borbarad Kampagne, Renaissance, Rising Dragon
     */
    @Test
    public void testOrderByLastUsed() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Confirm
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByLastUsed();
        assertThat(campaigns.get(0).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD));
        assertThat(campaigns.get(1).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE));
        assertThat(campaigns.get(2).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
    }

    /**
     * Order by name would be Die Borbarad Kampagne, Renaissance, Rising Dragon
     * Expected is Renaissance, Rising Dragon, Die Borbarad Kampagne
     */
    @Test
    public void testArchiveCampaign() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Read
        final Campaign dsaCampaignOutput = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD);
        assertThat(dsaCampaignOutput.archived, is(false));

        // Update
        final Campaign dsaCampaignInput = dsaCampaignOutput;
        dsaCampaignInput.archived = true;
        campaignDAO.updateCampaign(dsaCampaignInput);

        // Confirm
        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByName();
        assertThat(campaigns.get(0).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE));
        assertThat(campaigns.get(0).archived, is(false));
        assertThat(campaigns.get(1).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON));
        assertThat(campaigns.get(1).archived, is(false));
        assertThat(campaigns.get(2).campaignName, is(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD));
        assertThat(campaigns.get(2).archived, is(true));
    }

    @Test
    public void testDeleteCampaign() {
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Read
        List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByCreated();
        assertThat(campaigns, hasSize(3));

        // Delete
        campaignDAO.deleteCampaign(campaigns.get(0));

        // Confirm
        campaigns = campaignDAO.readCampaignsOrderedByCreated();
        assertThat(campaigns, hasSize(2));
    }
}
