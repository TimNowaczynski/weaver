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
import de.quarian.weaver.database.DebugDAO;
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
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testCreateAndReadNameSets() {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Create
        final NameSet asia = createNameSetEntity("asia");
        final NameSet europe = createNameSetEntity("europe");
        nameDAO.createNameSet(asia);
        nameDAO.createNameSet(europe);

        // Confirm
        final List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(2));
        assertThat(allNameSets.get(0).nameSetName, is("asia"));
        assertThat(allNameSets.get(1).nameSetName, is("europe"));
    }

    private NameSet createNameSetEntity(final String nameSetName) {
        final NameSet nameSet = new NameSet();
        nameSet.nameSetName = nameSetName;
        return nameSet;
    }

    @Test
    public void testUpdateNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Create
        final NameSet asia = createNameSetEntity("asia");
        asia.id = nameDAO.createNameSet(asia);

        // Update
        asia.nameSetName = "europe";
        nameDAO.updateNameSet(asia);

        // Confirm
        final List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(1));
        assertThat(allNameSets.get(0).nameSetName, is("europe"));
    }

    @Test
    public void testDeleteNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Create
        final NameSet asia = createNameSetEntity("asia");
        asia.id = nameDAO.createNameSet(asia);

        // Confirm
        List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(1));

        // Delete
        nameDAO.deleteNameSet(allNameSets.get(0));

        // Confirm
        allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets.size(), is(0));
    }

    @Test
    public void testNameSetToCampaignMapping() {
        // Create
        final Campaign asiaCampaign = new Campaign();
        asiaCampaign.campaignName = "Rising Dragon";

        final Campaign europeCampaign = new Campaign();
        europeCampaign.campaignName = "Renaissance";

        final Campaign usCampaign = new Campaign();
        usCampaign.campaignName = "Liberty";

        setUpNameSetToCampaignMapping(asiaCampaign, europeCampaign, usCampaign);

        // Read
        final NameDAO nameDAO = weaverDB.nameDAO();
        final List<NameSet> asiaNameSets = nameDAO.readNameSetsForCampaign(asiaCampaign.id);
        final List<NameSet> europeNameSets = nameDAO.readNameSetsForCampaign(europeCampaign.id);
        final List<NameSet> usNameSets = nameDAO.readNameSetsForCampaign(usCampaign.id);

        // Verify
        assertThat(asiaNameSets.size(), is(1));
        assertThat(asiaNameSets.get(0).nameSetName, is("asia"));
        assertThat(europeNameSets.size(), is(1));
        assertThat(europeNameSets.get(0).nameSetName, is("europe"));
        assertThat(usNameSets.size(), is(1));
        assertThat(usNameSets.get(0).nameSetName, is("us"));
    }

    @Test
    public void testDeleteDoesNotCascadeFromCampaignToNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Create
        final Campaign asiaCampaign = new Campaign();
        asiaCampaign.campaignName = "Rising Dragon";

        final Campaign europeCampaign = new Campaign();
        europeCampaign.campaignName = "Renaissance";

        final Campaign usCampaign = new Campaign();
        usCampaign.campaignName = "Liberty";

        setUpNameSetToCampaignMapping(asiaCampaign, europeCampaign, usCampaign);

        // Delete and Verify
        campaignDAO.deleteCampaign(europeCampaign);
        assertThat(campaignDAO.readCampaigns(), hasSize(2));
        assertThat(nameDAO.readNameSets(), hasSize(3));
    }

    @Test
    public void testDeleteDoesNotCascadeFromNameSetToCampaign() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Create
        final Campaign asiaCampaign = new Campaign();
        asiaCampaign.campaignName = "Rising Dragon";

        final Campaign europeCampaign = new Campaign();
        europeCampaign.campaignName = "Renaissance";

        final Campaign usCampaign = new Campaign();
        usCampaign.campaignName = "Liberty";

        final NameSet asiaNameSet = setUpNameSetToCampaignMapping(asiaCampaign, europeCampaign, usCampaign);
        final long asiaCampaignId = campaignDAO.readCampaigns().get(0).id;
        assertThat(nameDAO.readNameSets(), hasSize(3));

        // Delete and Verify
        nameDAO.deleteNameSet(asiaNameSet);
        assertThat(campaignDAO.readCampaigns(), hasSize(3));
        assertThat(nameDAO.readNameSets(), hasSize(2));
        assertThat(nameDAO.readNameSets().get(0).nameSetName, not(equalTo("Rising Dragon")));
        assertThat(nameDAO.readNameSets().get(1).nameSetName, not(equalTo("Rising Dragon")));

        assertThat(campaignDAO.readCampaign(asiaCampaignId), notNullValue());
        assertThat(nameDAO.readNameSetsForCampaign(asiaCampaignId), hasSize(1));
    }

    private NameSet setUpNameSetToCampaignMapping(final Campaign asiaCampaign, final Campaign europeCampaign, final Campaign usCampaign) {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final DebugDAO debugDAO = weaverDB.debugDAO();

        // Create Name Sets
        final NameSet asiaNameSet = createNameSetEntity("asia");
        asiaNameSet.id = nameDAO.createNameSet(asiaNameSet);

        final NameSet europeNameSet = createNameSetEntity("europe");
        europeNameSet.id = nameDAO.createNameSet(europeNameSet);

        final NameSet usNameSet = createNameSetEntity("us");
        usNameSet.id = nameDAO.createNameSet(usNameSet);

        // Create Campaigns
        asiaCampaign.id = campaignDAO.createCampaign(asiaCampaign);
        europeCampaign.id = campaignDAO.createCampaign(europeCampaign);
        usCampaign.id = campaignDAO.createCampaign(usCampaign);

        // Create Mappings
        final NameSetToCampaign asiaMapping = new NameSetToCampaign();
        asiaMapping.nameSetId = asiaNameSet.id;
        asiaMapping.campaignId = asiaCampaign.id;
        nameDAO.createNameSetToCampaignMapping(asiaMapping);

        final NameSetToCampaign europeMapping = new NameSetToCampaign();
        europeMapping.nameSetId = europeNameSet.id;
        europeMapping.campaignId = europeCampaign.id;
        nameDAO.createNameSetToCampaignMapping(europeMapping);

        final NameSetToCampaign usMapping = new NameSetToCampaign();
        usMapping.nameSetId = usNameSet.id;
        usMapping.campaignId = usCampaign.id;
        nameDAO.createNameSetToCampaignMapping(usMapping);

        // Verity
        assertThat(campaignDAO.readCampaigns(), hasSize(3));
        assertThat(nameDAO.readNameSets(), hasSize(3));

        return asiaNameSet;
    }
}
