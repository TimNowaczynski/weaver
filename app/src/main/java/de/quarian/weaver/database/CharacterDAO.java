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

    //TODO: Enforce N:1 Constraints in Code for
    //TODO: ( ) Event
    //TODO: ( ) Roll
    //TODO: ( ) Tag

    // Character - CREATE

    @Insert
    long createCharacterHeader(final CharacterHeader characterHeader);

    @Insert
    long createCharacterBody(final CharacterBody characterBody);

    // Character - READ

    @Query("SELECT * FROM CharacterHeader WHERE fk_campaign_id IS :campaignId")
    List<CharacterHeader> readCharacterHeadersByCampaignId(final long campaignId);

    @Query("SELECT * FROM CharacterHeader WHERE character_header_id IS :characterHeaderId")
    CharacterHeader readCharacterHeaderById(final long characterHeaderId);

    @Query("SELECT * FROM CharacterBody WHERE fk_character_header_id IS :characterHeaderId")
    CharacterBody readCharacterBodyByCharacterHeaderId(final long characterHeaderId);

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

    // Event - CREATE

    @Insert
    long createEvent(final Event event);

    @Insert
    long createEventToCharacter(final EventToCharacterHeader eventToCharacterHeader);

    // Event - READ

    @Query("SELECT event_id, event_date_millis, headline, text, image, image_type, file, file_type FROM event " +
            "INNER JOIN eventtocharacterheader ON event.event_id = eventtocharacterheader.fk_event_id " +
            "INNER JOIN characterheader ON eventtocharacterheader.fk_character_header_id = characterheader.character_header_id " +
            "WHERE characterheader.character_header_id IS :characterHeaderId")
    List<Event> readEventsForCharacterHeader(final long characterHeaderId);

    // Event - UPDATE

    @Update
    void updateEvent(final Event event);

    // Event - DELETE

    @Delete
    void deleteEvent(final Event event);

    @Delete
    void deleteEventToCharacter(final EventToCharacterHeader eventToCharacterHeader);

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
    List<Roll> readRollsForCharacterHeader(final long characterHeaderId);

    // Roll - DELETE

    @Delete
    void deleteRoll(final Roll roll);

    @Delete
    void deleteRollToCharacterHeader(final RollToCharacterHeader rollToCharacterHeader);

    // Tag - CREATE

    @Insert
    long createTag(final Tag tag);

    @Insert
    long createTagToCharacterHeader(final TagToCharacterHeader tagToCharacterHeader);

    // Tag - READ

    @Query("SELECT tag_id, fk_roleplaying_system_id, tag FROM tag " +
            "INNER JOIN tagtocharacterheader ON tag.tag_id = tagtocharacterheader.fk_tag_id " +
            "INNER JOIN characterheader ON tagtocharacterheader.fk_character_header_id = characterheader.character_header_id " +
            "WHERE characterheader.character_header_id IS :characterHeaderId")
    List<Tag> readTagsForCharacterHeader(final long characterHeaderId);

    // Tag - DELETE

    @Delete
    void deleteTag(final Tag tag);

    @Delete
    void deleteTagToCharacterHeader(final TagToCharacterHeader tagToCharacterHeader);
}
