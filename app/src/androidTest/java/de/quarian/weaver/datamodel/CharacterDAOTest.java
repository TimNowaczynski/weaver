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

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CharacterDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(this.weaverDB);
        DatabaseTestUtils.setUpThemes(this.weaverDB);
        DatabaseTestUtils.setUpCampaigns(this.weaverDB);
        DatabaseTestUtils.setUpCharacters(this.weaverDB);
        //TODO: set up avatars
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testCreateAndReadCharacters() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE);
        final long targetCampaignId = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON).id;
        campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD);

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignId(targetCampaignId);
        assertThat(characterHeaders.size(), is(1));
        // Get the header again to test the readById method
        final long characterHeaderId = characterHeaders.get(0).id;

        final CharacterHeader characterHeader = characterDAO.readCharacterHeaderById(characterHeaderId);
        final CharacterBody characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeaderId);
        assertThat(characterHeader, notNullValue());
        assertThat(characterBody, notNullValue());

        final long now = System.currentTimeMillis();
        final long fortyFiveMinutesAgo = now - (1000L * 60L * 45L);
        assertTrue(characterHeader.creationDateMillis <= now);
        assertTrue(characterHeader.creationDateMillis > fortyFiveMinutesAgo);
        assertTrue(characterHeader.editDateMillis > now - 1000L * 5L);
        assertTrue(characterHeader.editDateMillis <= now);

        assertThat(characterHeader.campaignId, is(targetCampaignId));
        assertThat(characterHeader.firstName, is(DatabaseTestConstants.MOONLIGHT_FIRST_NAME));
        assertThat(characterHeader.alias, is(DatabaseTestConstants.MOONLIGHT_ALIAS));
        assertThat(characterHeader.lastName, is(DatabaseTestConstants.MOONLIGHT_LAST_NAME));
        assertThat(characterHeader.gender, is(DatabaseTestConstants.MOONLIGHT_GENDER));
        assertThat(characterHeader.smallAvatar, is(DatabaseTestConstants.MOONLIGHT_SMALL_AVATAR));
        assertThat(characterHeader.smallAvatarImageType, is(DatabaseTestConstants.MOONLIGHT_SMALL_AVATAR_IMAGE_TYPE));
        assertThat(characterHeader.role, is(DatabaseTestConstants.MOONLIGHT_ROLE));
        assertThat(characterHeader.state, is(DatabaseTestConstants.MOONLIGHT_STATE));

        assertThat(characterBody.characterHeaderId, is(characterHeader.id));
        assertThat(characterBody.avatarImageType, is(DatabaseTestConstants.MOONLIGHT_AVATAR_IMAGE_TYPE));
        assertThat(characterBody.avatar, is(DatabaseTestConstants.MOONLIGHT_AVATAR));
        assertThat(characterBody.age, is(DatabaseTestConstants.MOONLIGHT_AGE));
        assertThat(characterBody.looks, is(DatabaseTestConstants.MOONLIGHT_LOOKS));
        assertThat(characterBody.background, is(DatabaseTestConstants.MOONLIGHT_BACKGROUND));
        assertThat(characterBody.miscellaneous, is(DatabaseTestConstants.MOONLIGHT_MISC));
    }

    @Test
    public void updateCharacterHeaderAndBody() {

    }

    @Test
    public void deleteCharacterBodyByDeletingCharacterHeader() {

    }

    @Test
    public void deleteCharacterBodyAndHeaderByDeletingCampaign() {

    }

    @Test
    public void testCreateAndReadTags() {

    }

    @Test
    public void testCreateAndReadTagToCharacterHeaders() {

    }

    // There is no Update Operation on Tags or TagToCharacterHeaders


    @Test
    public void testDeleteTag() {

    }

    @Test
    public void testDeleteTagToCharacterHeaders() {

    }

    // TODO: have something like this in place:
    // TODO: when deleting the last reference from all characters to a tag: delete tag

    // TODO: events
    // TODO: rolls
}
