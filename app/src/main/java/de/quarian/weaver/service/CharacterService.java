package de.quarian.weaver.service;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Roll;
import de.quarian.weaver.datamodel.Tag;

public interface CharacterService {

    enum  SortOrder {
        CHARACTER_FIRST_NAME, // implies last name as 2nd order
        CHARACTER_LAST_NAME, // implies first name as 2nd order
        CHARACTER_ALIAS,
        LAST_EDITED,
        CREATED
    }

    long createCharacter(@NonNull final CharacterHeader characterHeader, @NonNull final CharacterBody characterBody);

    List<CharacterHeader> readCharacterHeadersByCampaignId(@NonNull final SortOrder sortOrder, @NonNull final String filter, final long campaignId);

    List<CharacterHeader> readCharacterHeadersByTagId(long campaignId, long tagId);

    CharacterHeader readCharacterHeaderById(final long characterHeaderId);

    CharacterBody readCharacterBodyForCharacterHeader(final CharacterHeader characterHeader);

    void updateCharacter(@NonNull final CharacterHeader characterHeader, @NonNull final CharacterBody characterBody);

    void deleteCharacter(@NonNull final CharacterHeader characterHeader);

    // Rolls

    long createRoll(@NonNull CharacterHeader characterHeader, @NonNull Roll roll);

    List<Roll> readRolls(@NonNull CharacterHeader characterHeader);

    void updateRoll(@NonNull Roll roll);

    void deleteRoll(@NonNull Roll roll);

    // Tags

    long createAndAssignTag(@NonNull CharacterHeader characterHeader, @NonNull Tag tag);

    List<Tag> readTags(@NonNull RoleplayingSystem roleplayingSystem);

    List<Tag> readTags(@NonNull CharacterHeader characterHeader);

    void removeTag(@NonNull CharacterHeader characterHeader, @NonNull Tag tag);

    // Events

    long createEvent(@NonNull CharacterHeader characterHeader, @NonNull Event event);

    Map<CharacterHeader, List<Event>> readEvents(@NonNull final Campaign campaign);

    List<Event> readEvents(@NonNull final CharacterHeader characterHeader);

    void updateEvent(@NonNull final Event event);

    void deleteEvent(@NonNull final Event event);
}
