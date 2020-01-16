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
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.WeaverDB;

import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_ALIAS;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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
        DatabaseTestUtils.setUpTags(this.weaverDB);
        DatabaseTestUtils.setUpEvents(this.weaverDB);
        DatabaseTestUtils.setUpRolls(this.weaverDB);
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
        assertThat(characterHeaders, hasSize(2));
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
        assertThat(characterHeader.playerName, is(DatabaseTestConstants.MOONLIGHT_PLAYER_NAME));
        assertThat(characterHeader.race, is(DatabaseTestConstants.MOONLIGHT_RACE));
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
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long targetCampaignId = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON).id;

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignId(targetCampaignId);
        final long characterHeaderId = characterHeaders.get(0).id;

        CharacterHeader characterHeader = characterDAO.readCharacterHeaderById(characterHeaderId);
        CharacterBody characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeaderId);

        characterHeader.state = "amused";
        characterDAO.updateCharacterHeader(characterHeader);
        characterBody.age = "16";
        characterDAO.updateCharacterBody(characterBody);

        characterHeader = characterDAO.readCharacterHeaderById(characterHeaderId);
        characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeaderId);
        assertThat(characterHeader.state, is("amused"));
        assertThat(characterBody.age, is("16"));
    }

    @Test
    public void deleteCharacterBodyByDeletingCharacterHeader() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long targetCampaignId = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON).id;

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignId(targetCampaignId);
        final long characterHeaderId = characterHeaders.get(0).id;

        CharacterHeader characterHeader = characterDAO.readCharacterHeaderById(characterHeaderId);

        characterDAO.deleteCharacterHeader(characterHeader);

        characterHeader = characterDAO.readCharacterHeaderById(characterHeaderId);
        final CharacterBody characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeaderId);
        assertThat(characterHeader, nullValue());
        assertThat(characterBody, nullValue());
    }

    @Test
    public void deleteCharacterBodyAndHeaderByDeletingCampaign() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final CharacterDAO characterDAO = weaverDB.characterDAO();

        final long targetCampaignId = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON).id;
        final List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignId(targetCampaignId);
        final long characterHeaderId = characterHeaders.get(0).id;

        Campaign targetCampaign = campaignDAO.readCampaignByID(targetCampaignId);
        campaignDAO.deleteCampaign(targetCampaign);

        targetCampaign = campaignDAO.readCampaignByID(targetCampaignId);
        assertThat(targetCampaign, nullValue());
        final CharacterHeader characterHeader = characterDAO.readCharacterHeaderById(characterHeaderId);
        assertThat(characterHeader, nullValue());
        final CharacterBody characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeaderId);
        assertThat(characterBody, nullValue());
    }

    @Test
    public void testCreateAndReadTags() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlightCharacterHeader = characterDAO.readCharacterHeaderByAlias(DatabaseTestConstants.MOONLIGHT_ALIAS);
        final List<Tag> tags = characterDAO.readTagsForCharacterHeaderId(moonlightCharacterHeader.id);
        assertThat(tags, hasSize(1));
        assertThat(tags.get(0).tag, is("UCAS"));
    }

    @Test
    public void testReadTagsForRoleplayingSystemId() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final long shadowrunId = roleplayingSystemDAO.readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN).id;
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final List<Tag> tags = characterDAO.readTagsForRoleplayingSystemId(shadowrunId);
        assertThat(tags, hasSize(1));
        assertThat(tags.get(0).tag, is("UCAS"));
    }

    // There is no Update Operation on Tags or TagToCharacterHeaders

    @Test
    public void testDeleteTag() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final long shadowrunId = roleplayingSystemDAO.readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN).id;
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        List<Tag> tags = characterDAO.readTagsForRoleplayingSystemId(shadowrunId);
        assertThat(tags, hasSize(1));
        characterDAO.deleteTag(tags.get(0));

        tags = characterDAO.readTagsForRoleplayingSystemId(shadowrunId);
        assertThat(tags, hasSize(0));
    }

    @Test
    public void testDeleteTagByDeletingLastAssociatedCharacterHeader() {
        /*
            TODO:
            Again, this will need a service to check if there are no further references,
            and if so: delete the tag as well.
            In the current state we remove the mapping entry due to constraints,
            but not the tag itself.

            This should apply to: (I) Rolls, (II) Tags and (III) Events
         */
        assertTrue(true);
    }

    @Test
    public void testTagShouldNotBeDeletedWhenAssociatedCharacterHeaderExists() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final CharacterDAO characterDAO = weaverDB.characterDAO();

        final CharacterHeader moonlightCharacterHeader = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Tag> moonlightTags = characterDAO.readTagsForCharacterHeaderId(moonlightCharacterHeader.id);
        final long moonlightTagId = moonlightTags.get(0).id;

        final TagToCharacterHeader tagToCharacterHeader = new TagToCharacterHeader();
        final CharacterHeader devNullCharacterHeader = weaverDB.characterDAO().readCharacterHeaderByAlias(DEV_NULL_ALIAS);
        tagToCharacterHeader.characterHeaderId = devNullCharacterHeader.id;
        tagToCharacterHeader.tagId = moonlightTagId;
        characterDAO.createTagToCharacterHeader(tagToCharacterHeader);

        final long shadowrunId = roleplayingSystemDAO.readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN).id;
        List<Tag> tags = characterDAO.readTagsForRoleplayingSystemId(shadowrunId);
        assertThat(tags, hasSize(1));

        characterDAO.deleteCharacterHeader(moonlightCharacterHeader);
        tags = characterDAO.readTagsForRoleplayingSystemId(shadowrunId);
        assertThat(tags, hasSize(1));
    }

    @Test
    public void testCreateAndReadEvent() {
        final long now = System.currentTimeMillis();
        final long oneMinuteAgo = now - (1000L);

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(DatabaseTestConstants.MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeader(moonlight.id);

        assertThat(events, hasSize(1));
        final Event event = events.get(0);
        assertThat(event.eventDateMillis, lessThanOrEqualTo(now));
        assertThat(event.eventDateMillis, greaterThan(oneMinuteAgo));
        assertThat(event.headline, is(DatabaseTestConstants.EVENT_HEADLINE));
        assertThat(event.text, is(DatabaseTestConstants.EVENT_TEXT));
        assertThat(event.image, is(DatabaseTestConstants.EVENT_IMAGE));
        assertThat(event.imageType, is(DatabaseTestConstants.EVENT_IMAGE_TYPE));
        assertThat(event.file, is(DatabaseTestConstants.EVENT_FILE));
        assertThat(event.fileType, is(DatabaseTestConstants.EVENT_FILE_TYPE));
    }

    @Test
    public void testUpdateEvent() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(DatabaseTestConstants.MOONLIGHT_ALIAS);
        List<Event> events = characterDAO.readEventsForCharacterHeader(moonlight.id);

        final String updatedHeadline = "Updated Event";
        assertThat(events, hasSize(1));
        final Event eventToUpdate = events.get(0);
        eventToUpdate.headline = updatedHeadline;
        characterDAO.updateEvent(eventToUpdate);

        events = characterDAO.readEventsForCharacterHeader(moonlight.id);
        final Event updatedEvent = events.get(0);
        assertThat(updatedEvent.headline, is(updatedHeadline));
    }

    @Test
    public void testDeleteEvent() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(DatabaseTestConstants.MOONLIGHT_ALIAS);
        List<Event> events = characterDAO.readEventsForCharacterHeader(moonlight.id);

        assertThat(events, hasSize(1));
        final Event eventToDelete = events.get(0);
        characterDAO.deleteEvent(eventToDelete);

        events = characterDAO.readEventsForCharacterHeader(moonlight.id);
        assertThat(events, hasSize(0));
    }

    @Test
    public void testDeleteEventByDeletingCharacterHeader() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(DatabaseTestConstants.MOONLIGHT_ALIAS);
        List<Event> events = characterDAO.readEventsForCharacterHeader(moonlight.id);

        assertThat(events, hasSize(1));
        characterDAO.deleteCharacterHeader(moonlight);

        events = characterDAO.readEventsForCharacterHeader(moonlight.id);
        assertThat(events, hasSize(0));

        /*
            TODO:
            Again, this will need a service to check if there are no further references,
            and if so: delete the event as well.
            In the current state we remove the mapping entry due to constraints,
            but not the event itself.

            This should apply to: (I) Rolls, (II) Tags and (III) Events
         */
        /*
         * This should evaluate to true:
         * final DebugDAO debugDAO = weaverDB.debugDAO();
         *         assertThat(debugDAO.getNumberOfTotalEvents(), is(0));
         */
    }

    @Test
    public void testCreateRollAndReadRollsForCharacterId() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Roll> rolls = characterDAO.readRollsForCharacterHeader(moonlight.id);
        assertThat(rolls, hasSize(1));
        final Roll rollFromDatabase = rolls.get(0);
        assertThat(rollFromDatabase.roll, is("12/14/10 (12)"));
        assertThat(rollFromDatabase.rollName, is("Perception"));
    }

    @Test
    public void testUpdateRoll() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);

        List<Roll> rolls = characterDAO.readRollsForCharacterHeader(moonlight.id);
        final Roll roll = rolls.get(0);
        roll.rollName = "Persuade";
        roll.roll = "13/40/10 + 12";
        characterDAO.updateRoll(roll);

        rolls = characterDAO.readRollsForCharacterHeader(moonlight.id);
        final Roll updatedRoll = rolls.get(0);
        assertThat(updatedRoll.rollName, is("Persuade"));
        assertThat(updatedRoll.roll, is("13/40/10 + 12"));
    }

    @Test
    public void testDeleteRollForCharacterId() {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);

        List<Roll> rolls = characterDAO.readRollsForCharacterHeader(moonlight.id);
        final Roll roll = rolls.get(0);
        characterDAO.deleteRoll(roll);

        rolls = characterDAO.readRollsForCharacterHeader(moonlight.id);
        assertThat(rolls, hasSize(0));
    }

    /*
            TODO:
            Again, this will need a service to check if there are no further references,
            and if so: delete the roll as well.
            In the current state we remove the mapping entry due to constraints,
            but not the roll itself.

            This should apply to: (I) Rolls, (II) Tags and (III) Events
     */
    @Test
    public void testDeletingLastAssociatedCharacterHeaderDeletesRollAsWell() {
        assertTrue(true);
    }
}
