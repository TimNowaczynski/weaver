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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class NameDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        this.weaverDB.clearAllTables();
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
        assertThat(allNameSets.get(0).nameSetName, is("Shadowrun"));
        assertThat(allNameSets.get(1).nameSetName, is("Vampire"));
        assertThat(allNameSets.get(2).nameSetName, is("DSA"));
    }

    @Test
    public void testUpdateNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Read Name Set
        final NameSet shadowrun = nameDAO.readNameSetByName("Shadowrun");

        // Update
        shadowrun.nameSetName = "Hunter";
        nameDAO.updateNameSet(shadowrun);

        // Confirm
        final List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(3));
        assertThat(allNameSets.get(0).nameSetName, is("Hunter"));
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
        final Campaign asiaCampaign = campaignDAO.readCampaignByName("Rising Dragon");
        final Campaign europeCampaign = campaignDAO.readCampaignByName("Renaissance");
        final Campaign aventurienCampaign = campaignDAO.readCampaignByName("Die Borbarad Kampagne");

        // Read name sets
        final NameDAO nameDAO = weaverDB.nameDAO();
        final List<NameSet> asiaNameSets = nameDAO.readNameSetsForCampaign(asiaCampaign.id);
        final List<NameSet> europeNameSets = nameDAO.readNameSetsForCampaign(europeCampaign.id);
        final List<NameSet> aventurienNameSets = nameDAO.readNameSetsForCampaign(aventurienCampaign.id);

        // Verify
        assertThat(asiaNameSets.size(), is(1));
        assertThat(asiaNameSets.get(0).nameSetName, is("Shadowrun"));
        assertThat(europeNameSets.size(), is(1));
        assertThat(europeNameSets.get(0).nameSetName, is("Vampire"));
        assertThat(aventurienNameSets.size(), is(1));
        assertThat(aventurienNameSets.get(0).nameSetName, is("DSA"));
    }

    @Test
    public void testDeleteDoesNotCascadeFromCampaignToNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Delete and Verify
        final Campaign risingDragon = campaignDAO.readCampaignByName("Rising Dragon");
        campaignDAO.deleteCampaign(risingDragon);
        assertThat(campaignDAO.readCampaigns(), hasSize(2));
        assertThat(nameDAO.readNameSets(), hasSize(3));
    }

    @Test
    public void testDeleteDoesNotCascadeFromNameSetToCampaign() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        final long asiaCampaignId = campaignDAO.readCampaigns().get(0).id;
        assertThat(nameDAO.readNameSets(), hasSize(3));

        // Delete and Verify
        final NameSet shadowrunNameSet = nameDAO.readNameSetsForCampaign(asiaCampaignId).get(0);
        nameDAO.deleteNameSet(shadowrunNameSet);
        assertThat(campaignDAO.readCampaigns(), hasSize(3));
        assertThat(nameDAO.readNameSets(), hasSize(2));
        assertThat(nameDAO.readNameSets().get(0).nameSetName, not(equalTo("Shadowrun")));
        assertThat(nameDAO.readNameSets().get(1).nameSetName, not(equalTo("Shadowrun")));

        assertThat(campaignDAO.readCampaignByID(asiaCampaignId), notNullValue());
        assertThat(nameDAO.readNameSetsForCampaign(asiaCampaignId), hasSize(1));
    }
}
