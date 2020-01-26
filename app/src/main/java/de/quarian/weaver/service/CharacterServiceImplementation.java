package de.quarian.weaver.service;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;

public class CharacterServiceImplementation implements CharacterService {

    private final CharacterDAO characterDAO;

    public CharacterServiceImplementation(@NonNull final WeaverDB weaverDB) {
        characterDAO = weaverDB.characterDAO();
    }

    @Override
    public long createCharacter(@NonNull final CharacterHeader characterHeader, @NonNull final CharacterBody characterBody) {
        final long characterHeaderId = characterDAO.createCharacterHeader(characterHeader);
        characterBody.characterHeaderId = characterHeaderId;
        characterDAO.createCharacterBody(characterBody);
        return characterHeaderId;
    }

    @Override
    public List<CharacterHeader> readCharacterHeadersByCampaignId(@NonNull final SortOrder sortOrder, @NonNull final String filter, long campaignId) {
        List<CharacterHeader> items = Collections.emptyList();
        switch (sortOrder) {
            case CREATED: {
                if (filter.isEmpty()) {
                    items = characterDAO.readCharacterHeadersByCampaignIdSortedByCreated(campaignId);
                } else {
                    items = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByCreated(campaignId, filter);
                }
                break;
            }
            case LAST_EDITED: {
                if (filter.isEmpty()) {
                    items = characterDAO.readCharacterHeadersByCampaignIdSortedByLastEdited(campaignId);
                } else {
                    items = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByLastEdited(campaignId, filter);
                }
                break;
            }
            case CHARACTER_FIRST_NAME: {
                if (filter.isEmpty()) {
                    items = characterDAO.readCharacterHeadersByCampaignIdSortedByFirstName(campaignId);
                } else {
                    items = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByFirstName(campaignId, filter);
                }
                break;
            }
            case CHARACTER_ALIAS: {
                if (filter.isEmpty()) {
                    items = characterDAO.readCharacterHeadersByCampaignIdSortedByAlias(campaignId);
                } else {
                    items = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByAlias(campaignId, filter);
                }
                break;
            }
            case CHARACTER_LAST_NAME: {
                if (filter.isEmpty()) {
                    items = characterDAO.readCharacterHeadersByCampaignIdSortedByLastName(campaignId);
                } else {
                    items = characterDAO.readFilteredCharacterHeadersByCampaignIdSortedByLastName(campaignId, filter);
                }
                break;
            }
        }
        return items;
    }

    @Override
    public CharacterHeader readCharacterHeaderById(final long characterHeaderId) {
        return characterDAO.readCharacterHeaderById(characterHeaderId);
    }

    @Override
    public CharacterBody readCharacterBodyForCharacterHeader(final CharacterHeader characterHeader) {
        return characterDAO.readCharacterBodyByCharacterHeaderId(characterHeader.id);
    }

    @Override
    public void updateCharacter(@NonNull final CharacterHeader characterHeader, @NonNull final CharacterBody characterBody) {
        characterDAO.updateCharacterHeader(characterHeader);
        characterDAO.updateCharacterBody(characterBody);
    }

    @Override
    public void deleteCharacter(@NonNull final CharacterHeader characterHeader) {
        final CharacterBody characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeader.id);
        characterDAO.deleteCharacterBody(characterBody);
        characterDAO.deleteCharacterHeader(characterHeader);
    }
}
