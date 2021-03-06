package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.EventToCharacterHeader;
import de.quarian.weaver.datamodel.Roll;
import de.quarian.weaver.datamodel.RollToCharacterHeader;
import de.quarian.weaver.datamodel.Tag;
import de.quarian.weaver.datamodel.TagToCharacterHeader;

@Dao
public interface CharacterDAO {

    //TODO: Enforce and test N:1 Constraints in Code for
    //TODO: ( ) Events (for now)
    //TODO: ( ) Rolls

    /*
    -------------------------------
    ||  Start Character Section  ||
    -------------------------------
     */

    // Character - CREATE

    @Insert
    long createCharacterHeader(final CharacterHeader characterHeader);

    @Insert
    long createCharacterBody(final CharacterBody characterBody);

    // Character - READ

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "ORDER BY first_name ASC, last_name ASC")
    List<CharacterHeader> readCharacterHeadersByCampaignIdSortedByFirstName(final long campaignId);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "AND (first_name LIKE :filter " +
            "OR last_name LIKE :filter " +
            "OR character_alias LIKE :filter) " +
            "ORDER BY first_name ASC, last_name ASC")
    List<CharacterHeader> readFilteredCharacterHeadersByCampaignIdSortedByFirstName(final long campaignId, final String filter);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "ORDER BY last_name ASC, first_name ASC")
    List<CharacterHeader> readCharacterHeadersByCampaignIdSortedByLastName(final long campaignId);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "AND (first_name LIKE :filter " +
            "OR last_name LIKE :filter " +
            "OR character_alias LIKE :filter) " +
            "ORDER BY last_name ASC, first_name ASC")
    List<CharacterHeader> readFilteredCharacterHeadersByCampaignIdSortedByLastName(final long campaignId, final String filter);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "ORDER BY character_alias ASC")
    List<CharacterHeader> readCharacterHeadersByCampaignIdSortedByAlias(final long campaignId);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "AND (first_name LIKE :filter " +
            "OR last_name LIKE :filter " +
            "OR character_alias LIKE :filter) " +
            "ORDER BY character_alias ASC")
    List<CharacterHeader> readFilteredCharacterHeadersByCampaignIdSortedByAlias(final long campaignId, final String filter);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "ORDER BY edit_date_millis DESC")
    List<CharacterHeader> readCharacterHeadersByCampaignIdSortedByLastEdited(final long campaignId);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "AND (first_name LIKE :filter " +
            "OR last_name LIKE :filter " +
            "OR character_alias LIKE :filter) " +
            "ORDER BY edit_date_millis DESC")
    List<CharacterHeader> readFilteredCharacterHeadersByCampaignIdSortedByLastEdited(final long campaignId, final String filter);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "ORDER BY creation_date_millis DESC")
    List<CharacterHeader> readCharacterHeadersByCampaignIdSortedByCreated(final long campaignId);

    @Query("SELECT * FROM CharacterHeader " +
            "WHERE fk_campaign_id IS :campaignId " +
            "AND (first_name LIKE :filter " +
            "OR last_name LIKE :filter " +
            "OR character_alias LIKE :filter) " +
            "ORDER BY creation_date_millis DESC")
    List<CharacterHeader> readFilteredCharacterHeadersByCampaignIdSortedByCreated(final long campaignId, final String filter);

    @Query("SELECT * FROM CharacterHeader WHERE character_header_id IS :characterHeaderId")
    CharacterHeader readCharacterHeaderById(final long characterHeaderId);

    // This one is for testing:
    @Query("SELECT * FROM CharacterHeader WHERE character_alias LIKE :alias")
    CharacterHeader readCharacterHeaderByAlias(final String alias);

    @Query("SELECT * FROM CharacterBody WHERE fk_character_header_id IS :characterHeaderId")
    CharacterBody readCharacterBodyByCharacterHeaderId(final long characterHeaderId);

    @Query("SELECT character_header_id, fk_campaign_id, creation_date_millis, " +
            "edit_date_millis, first_name, character_alias, last_name, race, gender, " +
            "small_avatar_image_type, small_avatar, character_role, state FROM tag " +
            "INNER JOIN tagtocharacterheader ON tag.tag_id = tagtocharacterheader.fk_tag_id " +
            "INNER JOIN characterheader ON tagtocharacterheader.fk_character_header_id = characterheader.character_header_id " +
            "WHERE tag.tag_id IS :tagId AND fk_campaign_id IS :campaignId " +
            "ORDER BY first_name ASC, last_name ASC")
    List<CharacterHeader> readCharacterHeadersByTagId(final long campaignId, final long tagId);

    // Character - UPDATE

    @Update
    void updateCharacterHeader(final CharacterHeader characterHeader);

    @Update
    void updateCharacterBody(final CharacterBody characterBody);

    // Character - DELETE

    @Delete
    void deleteCharacterHeader(final CharacterHeader characterHeader);

    @Delete
    void deleteCharacterBody(final CharacterBody characterBody);

    /*
    ---------------------------
    ||  Start Event Section  ||
    ---------------------------
     */

    // Event - CREATE

    @Insert
    long createEvent(final Event event);

    @Insert
    long createEventToCharacterHeader(final EventToCharacterHeader eventToCharacterHeader);

    // Event - READ

    @Query("SELECT event_id, event_date_millis, headline, text FROM event " +
            "INNER JOIN eventtocharacterheader ON event.event_id = eventtocharacterheader.fk_event_id " +
            "INNER JOIN characterheader ON eventtocharacterheader.fk_character_header_id = characterheader.character_header_id " +
            "WHERE characterheader.character_header_id IS :characterHeaderId")
    List<Event> readEventsForCharacterHeaderId(final long characterHeaderId);

    // Event - UPDATE

    @Update
    void updateEvent(final Event event);

    // Event - DELETE

    @Delete
    void deleteEvent(final Event event);

    /*
    --------------------------
    ||  Start Roll Section  ||
    --------------------------
     */

    // Roll - CREATE

    @Insert
    long createRoll(final Roll roll);

    @Insert
    long createRollToCharacterHeader(final RollToCharacterHeader rollToCharacterHeader);

    // Roll - READ

    @Query("SELECT roll_id, roll_name, roll_roll FROM roll " +
            "INNER JOIN rolltocharacterheader ON roll.roll_id = rolltocharacterheader.fk_roll_id " +
            "INNER JOIN characterheader ON rolltocharacterheader.fk_character_header_id = characterheader.character_header_id " +
            "WHERE characterheader.character_header_id IS :characterHeaderId")
    List<Roll> readRollsForCharacterHeaderId(final long characterHeaderId);

    // Roll - Update

    @Update
    void updateRoll(final Roll roll);

    // Roll - DELETE

    @Delete
    void deleteRoll(final Roll roll);

    /*
    -------------------------
    ||  Start Tag Section  ||
    -------------------------
     */

    // Tag - CREATE

    @Insert
    long createTag(final Tag tag);

    @Insert
    long createTagToCharacterHeader(final TagToCharacterHeader tagToCharacterHeader);

    // Tag - READ

    @Query("SELECT COUNT(*) FROM tagtocharacterheader WHERE fk_tag_id IS :tagId")
    long countTagOccurrences(final long tagId);

    @Query("SELECT tag_id, fk_roleplaying_system_id, tag FROM tag " +
            "WHERE fk_roleplaying_system_id IS :roleplayingSystemId")
    List<Tag> readTagsForRoleplayingSystemId(final long roleplayingSystemId);

    @Query("SELECT tag_id, fk_roleplaying_system_id, tag FROM tag " +
            "INNER JOIN tagtocharacterheader ON tag.tag_id = tagtocharacterheader.fk_tag_id " +
            "INNER JOIN characterheader ON tagtocharacterheader.fk_character_header_id = characterheader.character_header_id " +
            "WHERE characterheader.character_header_id IS :characterHeaderId")
    List<Tag> readTagsForCharacterHeaderId(final long characterHeaderId);

    @Query("SELECT tag_id, fk_roleplaying_system_id, tag FROM tag " +
            "WHERE tag LIKE :tagName")
    Tag readTagByName(final String tagName);

    // Tag - DELETE

    @Query("DELETE FROM tagtocharacterheader WHERE " +
            "fk_character_header_id IS :characterId AND " +
            "fk_tag_id IS :tagId")
    void removeTagFromCharacter(final long characterId, final long tagId);

    @Delete
    void deleteTag(final Tag tag);
}
