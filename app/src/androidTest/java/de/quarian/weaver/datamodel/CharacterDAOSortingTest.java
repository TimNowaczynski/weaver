package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CharacterDAOSortingTest {

    private long campaignId;
    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(this.weaverDB);
        DatabaseTestUtils.setUpThemes(this.weaverDB);
        DatabaseTestUtils.setUpCampaigns(this.weaverDB);
        DatabaseTestUtils.setUpCharactersForSortingTests(this.weaverDB);

        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        campaignId = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON).id;
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    /*
     * Sort Order should be:
     * Alex Warner
     * James Luther
     * John Luther
     * John Mason
     * Judith Mason
     * Judith Miller
     */
    @Test
    public void testReadCharactersSortedByFirstAndLastName() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignIdSortedByFirstName(campaignId);

        assertThat(characterHeaders, hasSize(6));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(2).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(2).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(3).firstName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(3).lastName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME));

        assertThat(characterHeaders.get(4).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(4).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));

        assertThat(characterHeaders.get(5).firstName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME));
        assertThat(characterHeaders.get(5).lastName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME));

        characterHeaders = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByFirstName(campaignId, "Luther");

        assertThat(characterHeaders, hasSize(2));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));
    }

    /*
     * Sort Order should be:
     * James Luther
     * John Luther
     * John Mason
     * Judith Mason
     * Judith Miller
     * Alex Warner
     */
    @Test
    public void testReadCharactersSortedByLastAndFirstName() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignIdSortedByLastName(campaignId);

        assertThat(characterHeaders, hasSize(6));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(2).firstName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(2).lastName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME));

        assertThat(characterHeaders.get(3).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(3).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));

        assertThat(characterHeaders.get(4).firstName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME));
        assertThat(characterHeaders.get(4).lastName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME));

        assertThat(characterHeaders.get(5).firstName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_FIRST_NAME));
        assertThat(characterHeaders.get(5).lastName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_LAST_NAME));

        characterHeaders = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByFirstName(campaignId, "Luther");

        assertThat(characterHeaders, hasSize(2));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));
    }

    /*
     * Sort Order should be:
     * James "7 Lives" Luther
     * John "Judge" Mason
     * Alex "Magic" Warner
     * Judith "Phantom"  Miller
     * John "Priest" Luther
     * Judith "Reaver" Mason
     */
    @Test
    public void testReadCharactersSortedByAlias() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignIdSortedByAlias(campaignId);

        assertThat(characterHeaders, hasSize(6));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME));

        assertThat(characterHeaders.get(2).firstName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_FIRST_NAME));
        assertThat(characterHeaders.get(2).lastName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_LAST_NAME));

        assertThat(characterHeaders.get(3).firstName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME));
        assertThat(characterHeaders.get(3).lastName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME));

        assertThat(characterHeaders.get(4).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(4).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(5).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(5).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));

        characterHeaders = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByAlias(campaignId, "Judith");

        assertThat(characterHeaders, hasSize(2));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));
    }

    /*
     * Sort Order should be:
     * (higher = newer)
     * Judith "Phantom" Miller - 14d created - 2d edited
     * John "Priest" Luther - 12d created - 10d edited
     * John "Judge" Mason - 23d created - 12d edited
     * Alex "Magic" Warner - 144d created - 27 d edited
     * James "7 Lives" Luther - 120d created - 60d edited
     * Judith "Reaver" Mason - 122d created - 99d edited
     */
    @Test
    public void testReadCharactersSortedByLastEdited() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignIdSortedByLastEdited(campaignId);

        assertThat(characterHeaders, hasSize(6));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(2).firstName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(2).lastName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME));

        assertThat(characterHeaders.get(3).firstName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_FIRST_NAME));
        assertThat(characterHeaders.get(3).lastName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_LAST_NAME));

        assertThat(characterHeaders.get(4).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(4).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(5).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(5).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));

        characterHeaders = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByLastEdited(campaignId, "Luther");

        assertThat(characterHeaders, hasSize(2));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));
    }

    /*
     * Sort Order should be:
     * (higher = newer)
     * John "Priest" Luther - 12d created - 10d edited
     * Judith "Phantom" Miller - 14d created - 2d edited
     * John "Judge" Mason - 23d created - 12d edited
     * James "7 Lives" Luther - 120d created - 60d edited
     * Judith "Reaver" Mason - 122d created - 99d edited
     * Alex "Magic" Warner - 144d created - 27 d edited
     */
    @Test
    public void testReadCharactersSortedByCreated() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignIdSortedByCreated(campaignId);

        assertThat(characterHeaders, hasSize(6));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME));

        assertThat(characterHeaders.get(2).firstName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(2).lastName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME));

        assertThat(characterHeaders.get(3).firstName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME));
        assertThat(characterHeaders.get(3).lastName,is(DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME));

        assertThat(characterHeaders.get(4).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(4).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));

        assertThat(characterHeaders.get(5).firstName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_FIRST_NAME));
        assertThat(characterHeaders.get(5).lastName,is(DatabaseTestConstants.ALEX_MAGIC_WARNER_LAST_NAME));

        characterHeaders = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByCreated(campaignId, "Mason");

        assertThat(characterHeaders, hasSize(2));

        assertThat(characterHeaders.get(0).firstName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(0).lastName,is(DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME));

        assertThat(characterHeaders.get(1).firstName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME));
        assertThat(characterHeaders.get(1).lastName,is(DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME));
    }

}
