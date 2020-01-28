package de.quarian.weaver.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.EventToCharacterHeader;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Roll;
import de.quarian.weaver.datamodel.RollToCharacterHeader;
import de.quarian.weaver.datamodel.Tag;
import de.quarian.weaver.datamodel.TagToCharacterHeader;

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
    public List<CharacterHeader> readCharacterHeadersByTagId(long campaignId, long tagId) {
        // TODO: extend with sorting and filtering later on, for a first version this should suffice
        return characterDAO.readCharacterHeadersByTagId(campaignId, tagId);
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
        final List<Roll> rolls = characterDAO.readRollsForCharacterHeaderId(characterHeader.id);
        for (final Roll roll : rolls) {
            characterDAO.deleteRoll(roll);
        }

        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(characterHeader.id);
        for (final Event event : events) {
            characterDAO.deleteEvent(event);
        }

        // We could also delete tags if there are no further references to them
        // but I feel it's like overkill, also it's maybe nice to keep them
        // as suggestions

        final CharacterBody characterBody = characterDAO.readCharacterBodyByCharacterHeaderId(characterHeader.id);
        characterDAO.deleteCharacterBody(characterBody);
        characterDAO.deleteCharacterHeader(characterHeader);
    }

    @Override
    public long createRoll(@NonNull CharacterHeader characterHeader, @NonNull Roll roll) {
        final long rollId = characterDAO.createRoll(roll);
        final RollToCharacterHeader rollToCharacterHeader = new RollToCharacterHeader();
        rollToCharacterHeader.characterHeaderId = characterHeader.id;
        rollToCharacterHeader.rollId = rollId;
        characterDAO.createRollToCharacterHeader(rollToCharacterHeader);
        return rollId;
    }

    @Override
    public List<Roll> readRolls(@NonNull CharacterHeader characterHeader) {
        return characterDAO.readRollsForCharacterHeaderId(characterHeader.id);
    }

    @Override
    public void updateRoll(@NonNull Roll roll) {
        characterDAO.updateRoll(roll);
    }

    @Override
    public void deleteRoll(@NonNull Roll roll) {
        characterDAO.deleteRoll(roll);
    }

    @Override
    public long createAndAssignTag(@NonNull CharacterHeader characterHeader, @NonNull Tag tag) {
        final Tag existingTag = characterDAO.readTagByName(tag.tag);
        if (existingTag != null) {
            tag.id = existingTag.id;
        } else {
            tag.id = characterDAO.createTag(tag);
        }
        final TagToCharacterHeader tagToCharacterHeader = new TagToCharacterHeader();
        tagToCharacterHeader.tagId = tag.id;
        tagToCharacterHeader.characterHeaderId = characterHeader.id;
        characterDAO.createTagToCharacterHeader(tagToCharacterHeader);
        return tag.id;
    }

    @Override
    public List<Tag> readTags(@NonNull RoleplayingSystem roleplayingSystem) {
        return characterDAO.readTagsForRoleplayingSystemId(roleplayingSystem.id);
    }

    @Override
    public List<Tag> readTags(@NonNull CharacterHeader characterHeader) {
        return characterDAO.readTagsForCharacterHeaderId(characterHeader.id);
    }

    @Override
    public void removeTag(@NonNull CharacterHeader characterHeader, @NonNull Tag tag) {
        characterDAO.removeTagFromCharacter(characterHeader.id, tag.id);
        final long occurrences = characterDAO.countTagOccurrences(tag.id);
        if (occurrences == 0) {
            characterDAO.deleteTag(tag);
        }
    }

    @Override
    public long createEvent(@NonNull CharacterHeader characterHeader, @NonNull Event event) {
        final long eventId = characterDAO.createEvent(event);
        final EventToCharacterHeader eventToCharacterHeader = new EventToCharacterHeader();
        eventToCharacterHeader.eventId = eventId;
        eventToCharacterHeader.characterHeaderId = characterHeader.id;
        characterDAO.createEventToCharacterHeader(eventToCharacterHeader);
        return eventId;
    }

    @Override
    public Map<CharacterHeader, List<Event>> readEvents(@NonNull Campaign campaign) {
        final Map<CharacterHeader, List<Event>> results = new HashMap<>();
        final List<CharacterHeader> characterHeaders = characterDAO.readCharacterHeadersByCampaignIdSortedByFirstName(campaign.id);
        for (CharacterHeader characterHeader : characterHeaders) {
            final List<Event> events = characterDAO.readEventsForCharacterHeaderId(characterHeader.id);
            results.put(characterHeader, events);
        }
        return results;
    }

    @Override
    public List<Event> readEvents(@NonNull CharacterHeader characterHeader) {
        return characterDAO.readEventsForCharacterHeaderId(characterHeader.id);
    }

    @Override
    public void updateEvent(@NonNull Event event) {
        characterDAO.updateEvent(event);
    }

    @Override
    public void deleteEvent(@NonNull Event event) {
        characterDAO.deleteEvent(event);
    }
}
