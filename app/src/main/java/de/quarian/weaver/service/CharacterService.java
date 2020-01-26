package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;

public interface CharacterService {

    enum  SortOrder {
        CHARACTER_FIRST_NAME, // implies last name as 2nd order
        CHARACTER_LAST_NAME, // implies first name as 2nd order
        CHARACTER_ALIAS,
        LAST_EDITED,
        CREATED
    }

    long createCharacter(@NonNull CharacterHeader characterHeader, @NonNull CharacterBody characterBody);

    List<CharacterHeader> readCharacterHeadersByCampaignId(@NonNull final SortOrder sortOrder, @NonNull String filter, long campaignId);

    CharacterHeader readCharacterHeaderById(long characterHeaderId);

    CharacterBody readCharacterBodyForCharacterHeader(final CharacterHeader characterHeader);

    void updateCharacter(@NonNull CharacterHeader characterHeader, @NonNull CharacterBody characterBody);

    void deleteCharacter(@NonNull CharacterHeader characterHeader);

}
