package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class NameDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(weaverDB);
        DatabaseTestUtils.setUpThemes(weaverDB);
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        DatabaseTestUtils.setUpNameSets(weaverDB);
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testReadNameSets() {
        // Confirm
        final NameDAO nameDAO = weaverDB.nameDAO();
        final List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(3));
        assertThat(allNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN));
        assertThat(allNameSets.get(1).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE));
        assertThat(allNameSets.get(2).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_DSA));
    }

    @Test
    public void testUpdateNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Read Name Set
        final NameSet shadowrun = nameDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN);

        // Update
        shadowrun.nameSetName = "Hunter";
        nameDAO.updateNameSet(shadowrun);

        // Confirm
        final NameSet updatedNameSet = nameDAO.readNameSetByName("Hunter");
        assertThat(updatedNameSet, notNullValue());
    }

    @Test
    public void testDeleteNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Confirm
        List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(3));

        // Delete
        nameDAO.deleteNameSet(allNameSets.get(0));

        // Confirm
        allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(2));
    }

    @Test
    public void testNameSetToCampaignMapping() {
        // Read campaigns
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign asiaCampaign = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);
        final Campaign europeCampaign = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE);
        final Campaign aventurienCampaign = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD);

        // Read name sets
        final NameDAO nameDAO = weaverDB.nameDAO();
        final List<NameSet> asiaNameSets = nameDAO.readNameSetsForCampaign(asiaCampaign.id);
        final List<NameSet> europeNameSets = nameDAO.readNameSetsForCampaign(europeCampaign.id);
        final List<NameSet> aventurienNameSets = nameDAO.readNameSetsForCampaign(aventurienCampaign.id);

        // Verify
        assertThat(asiaNameSets.size(), is(1));
        assertThat(asiaNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN));
        assertThat(europeNameSets.size(), is(1));
        assertThat(europeNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE));
        assertThat(aventurienNameSets.size(), is(1));
        assertThat(aventurienNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_DSA));
    }
}
