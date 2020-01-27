package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.DebugDAO;
import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.test.TestUtils;

import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_UNISEX;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class NameDAOTest {

    @Rule
    public ActivityTestRule<DeveloperFunctionsActivity> activityTestRule = new ActivityTestRule<>(DeveloperFunctionsActivity.class, true, true);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();

        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(weaverDB);
        DatabaseTestUtils.setUpThemes(weaverDB);
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        DatabaseTestUtils.setUpNameSets(weaverDB);
        DatabaseTestUtils.setUpNames(weaverDB);
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    // Start Name Sets Section

    @Test
    public void testReadNameSets() {
        // Confirm
        final NameDAO nameDAO = weaverDB.nameDAO();

        final List<NameSet> nameSets = nameDAO.readNameSets();
        assertThat(nameSets, hasSize(3));
        assertThat(nameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN));
        assertThat(nameSets.get(1).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE));
        assertThat(nameSets.get(2).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_DSA));
    }

    @Test
    public void testUpdateNameSet() {
        final DebugDAO debugDAO = weaverDB.debugDAO();
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Read Name Set
        final NameSet shadowrun = debugDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN);

        // Update
        shadowrun.nameSetName = "Hunter";
        nameDAO.updateNameSet(shadowrun);

        // Confirm
        final NameSet updatedNameSet = debugDAO.readNameSetByName("Hunter");
        assertThat(updatedNameSet, notNullValue());
    }

    @Test
    public void testDeleteNameSet() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final DebugDAO debugDAO = weaverDB.debugDAO();

        final TestUtils.ValueHolder<Long> idValueHolder = new TestUtils.ValueHolder<>();
        List<NameSet> nameSets = nameDAO.readNameSets();

        // Read
        assertThat(nameSets, notNullValue());
        assertThat(nameSets, hasSize(3));
        idValueHolder.value = nameSets.get(0).id;

        int numberOfNamesForNameSet = debugDAO.getNumberOfNamesForNameSetID(idValueHolder.value);
        assertThat(numberOfNamesForNameSet, is(4));

        // Delete
        final NameSet nameSetToDelete = nameDAO.readNameSetByID(idValueHolder.value);
        nameDAO.deleteNameSet(nameSetToDelete);

        nameSets = nameDAO.readNameSets();
        assertThat(nameSets, notNullValue());
        assertThat(nameSets.size(), is(2));

        numberOfNamesForNameSet = debugDAO.getNumberOfNamesForNameSetID(idValueHolder.value);
        assertThat(numberOfNamesForNameSet, is(0));
    }

    // Start NameSet to Name Relation Test

    @Test
    public void testDeletingNameSetCascadesToName() {
        // Verify initial state
        final NameDAO nameDAO = weaverDB.nameDAO();
        final NameSet nameSetToDelete = nameDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_DSA);

        assertThat(nameDAO.readNumberOfNames(nameSetToDelete.id), is(4));
        final List<NameSet> initialNameSets = nameDAO.readNameSets();
        assertThat(initialNameSets, hasSize(3));
        assertThat(nameDAO.readNumberOfNames(nameSetToDelete.id), is(4));

        // Delete
        nameDAO.deleteNameSet(nameSetToDelete);

        final List<NameSet> resultingNameSets = nameDAO.readNameSets();
        assertThat(resultingNameSets, hasSize(2));
        assertThat(nameDAO.readNumberOfNames(nameSetToDelete.id), is(0));
    }

    // Don't start Name to NameSet Relation Tests, because:
    // There is no use in deleting Names on their own

    // Start NameSet to Campaign Relation Tests

    @Test
    public void testNameSetToCampaignMapping() {
        // Read Campaigns
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
        assertThat(asiaNameSets, notNullValue());
        assertThat(asiaNameSets.size(), is(1));
        assertThat(asiaNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN));
        assertThat(europeNameSets, notNullValue());
        assertThat(europeNameSets.size(), is(1));
        assertThat(europeNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE));
        assertThat(aventurienNameSets, notNullValue());
        assertThat(aventurienNameSets.size(), is(1));
        assertThat(aventurienNameSets.get(0).nameSetName, is(DatabaseTestConstants.NAME_SET_NAME_DSA));
    }

    @Test
    public void testDeletingCampaignCascadesToMappingEntryButNotToNameSetTable() {
        // Verify initial state
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByCreated();
        assertThat(campaigns, hasSize(3));

        final NameDAO nameDAO = weaverDB.nameDAO();
        List<NameSet> nameSets = nameDAO.readNameSets();
        assertThat(nameSets, hasSize(3));
        final DebugDAO debugDAO = weaverDB.debugDAO();
        assertThat(debugDAO.getNameSetToCampaignMappings(), hasSize(3));

        // Delete
        final Campaign asiaCampaign = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);
        final long asiaCampaignId = asiaCampaign.id;
        campaignDAO.deleteCampaign(asiaCampaign);

        // Verify final state
        campaigns = campaignDAO.readCampaignsOrderedByCreated();
        assertThat(campaigns, hasSize(2));
        final List<NameSet> allNameSets = nameDAO.readNameSets();
        assertThat(allNameSets, hasSize(3));
        nameSets = nameDAO.readNameSetsForCampaign(asiaCampaignId);
        assertThat(nameSets, hasSize(0));
        assertThat(debugDAO.getNameSetToCampaignMappings(), hasSize(2));
    }

    @Test
    public void testDeletingNameSetCascadesToMappingEntryButNotToCampaignTable() {
        // Verify initial state
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final NameDAO nameDAO = weaverDB.nameDAO();
        final DebugDAO debugDAO = weaverDB.debugDAO();

        final List<Campaign> campaigns = campaignDAO.readCampaignsOrderedByCreated();
        assertThat(campaigns, hasSize(3));
        assertThat(nameDAO.readNameSets(), hasSize(3));
        assertThat(debugDAO.getNameSetToCampaignMappings(), hasSize(3));

        // Delete
        final Campaign asiaCampaign = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);
        final List<NameSet> nameSets = nameDAO.readNameSetsForCampaign(asiaCampaign.id);
        assertThat(nameSets, hasSize(1));
        nameDAO.deleteNameSet(nameSets.get(0));

        // Verify final state
        assertThat(campaignDAO.readCampaignsOrderedByCreated(), hasSize(3));
        assertThat(nameDAO.readNameSets(), hasSize(2));
        assertThat(debugDAO.getNameSetToCampaignMappings(), hasSize(2));
    }

    // CREATE AND READ NAME

    @Test
    public void testCreateAndReadName() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final NameSet shadowrunNameSet = nameDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN);
        final Name shadowrunUnisexName = nameDAO.readRandomName(shadowrunNameSet.id, Constants.NamePosition.FIRST.getValue(), Collections.singletonList(Constants.NameGender.UNISEX.getValue()), 0);
        assertThat(shadowrunUnisexName.name, is(DatabaseTestConstants.FIRST_NAME_SHADOWRUN_UNISEX));
    }

    @Test
    public void testReadRandomUnisexName() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final NameSet vampireNameSet = nameDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE);
        final List<Integer> nameGenders = Collections.singletonList(Constants.NameGender.UNISEX.getValue());
        final long bound = nameDAO.readNumberOfNames(vampireNameSet.id, Constants.NamePosition.FIRST.getValue(), nameGenders);

        int run = 0;
        while (run < 100) {
            run++;
            final long randomIndex = ThreadLocalRandom.current().nextLong(bound);
            final Name generatedName = nameDAO.readRandomName(vampireNameSet.id, Constants.NamePosition.FIRST.getValue(), nameGenders, randomIndex);
            assertThat(generatedName, notNullValue());
            assertThat(generatedName.name, is(FIRST_NAME_VAMPIRE_UNISEX));
        }
    }

    @Test
    public void testReadRandomFemaleName() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final NameSet vampireNameSet = nameDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE);
        final List<Integer> nameGenders = Collections.singletonList(Constants.NameGender.FEMALE.getValue());
        final long bound = nameDAO.readNumberOfNames(vampireNameSet.id, Constants.NamePosition.FIRST.getValue(), nameGenders);

        int run = 0;
        while (run < 100) {
            run++;
            final long randomIndex = ThreadLocalRandom.current().nextLong(bound);
            final Name generatedName = nameDAO.readRandomName(vampireNameSet.id, Constants.NamePosition.FIRST.getValue(), nameGenders, randomIndex);
            assertThat(generatedName, notNullValue());
            assertThat(generatedName.name, is(FIRST_NAME_VAMPIRE_FEMALE));
        }
    }

    @Test
    public void testReadRandomMaleName() {
        final NameDAO nameDAO = weaverDB.nameDAO();
        final NameSet vampireNameSet = nameDAO.readNameSetByName(DatabaseTestConstants.NAME_SET_NAME_VAMPIRE);
        final List<Integer> nameGenders = Collections.singletonList(Constants.NameGender.MALE.getValue());
        final long bound = nameDAO.readNumberOfNames(vampireNameSet.id, Constants.NamePosition.FIRST.getValue(), nameGenders);

        int run = 0;
        while (run < 100) {
            run++;
            final long randomIndex = ThreadLocalRandom.current().nextLong(bound);
            final Name generatedName = nameDAO.readRandomName(vampireNameSet.id, Constants.NamePosition.FIRST.getValue(), nameGenders, randomIndex);
            assertThat(generatedName, notNullValue());
            assertThat(generatedName.name, is(FIRST_NAME_VAMPIRE_MALE));
        }
    }

    // NO UPDATE NAME
    // NO DELETE NAME (IT'S DONE VIA DELETING WHOLE NAME SETS)
}
