package de.quarian.weaver.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Roll;
import de.quarian.weaver.datamodel.RollToCharacterHeader;
import de.quarian.weaver.datamodel.Tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CharacterServiceImplementationUnitTest {

    @Mock
    private WeaverDB weaverDB;

    @Mock
    private CharacterDAO characterDAO;

    private CharacterService characterService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(weaverDB.characterDAO()).thenReturn(characterDAO);
        characterService = new CharacterServiceImplementation(weaverDB);
        when(characterDAO.createCharacterHeader(any(CharacterHeader.class))).thenReturn(1L);
    }

    @Test
    public void testCreateCharacter() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        final CharacterBody characterBody = mock(CharacterBody.class);
        characterService.createCharacter(characterHeader, characterBody);

        verify(characterDAO).createCharacterHeader(characterHeader);
        verify(characterDAO).createCharacterBody(characterBody);
    }

    /*
        CHARACTER_FIRST_NAME, // implies last name as 2nd order
        CHARACTER_LAST_NAME, // implies first name as 2nd order
        CHARACTER_ALIAS,
        LAST_EDITED,
        CREATED
     */
    @Test
    public void testReadCharactersByCampaignId() {
        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CHARACTER_FIRST_NAME, "", 1L);
        verify(characterDAO).readCharacterHeadersByCampaignIdSortedByFirstName(1L);

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CHARACTER_LAST_NAME, "", 1L);
        verify(characterDAO).readCharacterHeadersByCampaignIdSortedByLastName(1L);

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CHARACTER_ALIAS, "", 1L);
        verify(characterDAO).readCharacterHeadersByCampaignIdSortedByAlias(1L);

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.LAST_EDITED, "", 1L);
        verify(characterDAO).readCharacterHeadersByCampaignIdSortedByLastEdited(1L);


        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CREATED, "", 1L);
        verify(characterDAO).readCharacterHeadersByCampaignIdSortedByCreated(1L);
    }

    @Test
    public void testReadFilteredCharactersByCampaignId() {
        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CHARACTER_FIRST_NAME, "filter", 1L);
        verify(characterDAO).readFilteredCharacterHeadersByCampaignIdSortedByFirstName(1L, "filter");

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CHARACTER_LAST_NAME, "filter", 1L);
        verify(characterDAO).readFilteredCharacterHeadersByCampaignIdSortedByLastName(1L, "filter");

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CHARACTER_ALIAS, "filter", 1L);
        verify(characterDAO).readFilteredCharacterHeadersByCampaignIdSortedByAlias(1L,"filter");

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.LAST_EDITED, "filter", 1L);
        verify(characterDAO).readFilteredCharacterHeadersByCampaignIdSortedByLastEdited(1L,"filter");

        characterService.readCharacterHeadersByCampaignId(CharacterService.SortOrder.CREATED, "filter", 1L);
        verify(characterDAO).readFilteredCharacterHeadersByCampaignIdSortedByCreated(1L,"filter");
    }

    @Test
    public void testReadCharacterHeaderById() {
        characterService.readCharacterHeaderById(1L);
        verify(characterDAO).readCharacterHeaderById(1L);
    }

    @Test
    public void testReadCharacterBodyForCharacterHeader() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        characterHeader.id = 1L;
        characterService.readCharacterBodyForCharacterHeader(characterHeader);
        verify(characterDAO).readCharacterBodyByCharacterHeaderId(1L);
    }

    @Test
    public void testUpdateCharacter() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        final CharacterBody characterBody = mock(CharacterBody.class);
        characterService.updateCharacter(characterHeader, characterBody);
        verify(characterDAO).updateCharacterBody(characterBody);
        verify(characterDAO).updateCharacterHeader(characterHeader);
    }

    @Test
    public void testDeleteCharacter() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        characterHeader.id = 1L;

        final CharacterBody characterBody = mock(CharacterBody.class);
        when(characterDAO.readCharacterBodyByCharacterHeaderId(characterHeader.id)).thenReturn(characterBody);

        final List<Roll> rolls = new ArrayList<>(2);
        final Roll rollA = mock(Roll.class);
        final Roll rollB = mock(Roll.class);
        rolls.add(rollA);
        rolls.add(rollB);
        when(characterDAO.readRollsForCharacterHeaderId(characterHeader.id)).thenReturn(rolls);

        final List<Event> events = new ArrayList<>(2);
        final Event eventA = mock(Event.class);
        final Event eventB = mock(Event.class);
        events.add(eventA);
        events.add(eventB);
        when(characterDAO.readEventsForCharacterHeaderId(characterHeader.id)).thenReturn(events);

        characterService.deleteCharacter(characterHeader);

        verify(characterDAO).deleteCharacterHeader(characterHeader);
        verify(characterDAO).deleteCharacterBody(characterBody);
        verify(characterDAO).deleteRoll(rollA);
        verify(characterDAO).deleteRoll(rollB);
        verify(characterDAO).deleteEvent(eventA);
        verify(characterDAO).deleteEvent(eventB);

        // Tags are shared between characters,
        // so we it's easier to delete them in a clean-up job style
    }

    @Test
    public void testCreateRoll() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        characterHeader.id = 1L;
        when(characterDAO.createRoll(any(Roll.class))).thenReturn(20L);

        final Roll roll = mock(Roll.class);
        characterService.createRoll(characterHeader, roll);

        verify(characterDAO).createRoll(roll);

        final ArgumentCaptor<RollToCharacterHeader> captor = ArgumentCaptor.forClass(RollToCharacterHeader.class);
        verify(characterDAO).createRollToCharacterHeader(captor.capture());
        assertThat(captor.getValue().characterHeaderId, is(1L));
        assertThat(captor.getValue().rollId, is(20L));
    }

    @Test
    public void testReadRolls() {
        final Roll roll = mock(Roll.class);
        when(characterDAO.readRollsForCharacterHeaderId(1L)).thenReturn(Collections.singletonList(roll));

        final CharacterHeader characterHeader = new CharacterHeader();
        characterHeader.id = 1L;

        final List<Roll> rolls = characterService.readRolls(characterHeader);
        assertThat(rolls.size(), is(1));
        assertThat(rolls.get(0), is(roll));
    }

    @Test
    public void testUpdateRoll() {
        final Roll roll = mock(Roll.class);
        characterService.updateRoll(roll);
        verify(characterDAO).updateRoll(roll);
    }

    @Test
    public void testDeleteRoll() {
        final Roll roll = mock(Roll.class);
        characterService.deleteRoll(roll);
        verify(characterDAO).deleteRoll(roll);
    }

    @Test
    public void testCreateTagIfNotExisting() {
        final CharacterHeader characterHeader = new CharacterHeader();
        characterHeader.id = 1L;

        final Tag tag = new Tag();
        tag.tag = "TestTag";
        when(characterDAO.readTagByName(tag.tag)).thenReturn(null);

        characterService.createAndAssignTag(characterHeader, tag);
        verify(characterDAO).createTag(tag);
    }

    @Test
    public void testReadTagsForRoleplayingSystem() {
        final RoleplayingSystem roleplayingSystem = mock(RoleplayingSystem.class);
        roleplayingSystem.id = 1L;

        final Tag tag = mock(Tag.class);
        final List<Tag> tagsIn = new ArrayList<>(1);
        tagsIn.add(tag);
        when(characterDAO.readTagsForRoleplayingSystemId(1L)).thenReturn(tagsIn);

        final List<Tag> tagsOut = characterService.readTags(roleplayingSystem);
        assertThat(tagsOut, notNullValue());
        assertThat(tagsOut.size(), is(1));
        verify(characterDAO).readTagsForRoleplayingSystemId(1L);
    }

    @Test
    public void testReadTagForCharacter() {
        final Tag tag = mock(Tag.class);
        final List<Tag> characterTag = new ArrayList<>(1);
        characterTag.add(tag);

        final CharacterHeader characterHeader = new CharacterHeader();
        characterHeader.id = 1L;
        when(characterDAO.readTagsForCharacterHeaderId(characterHeader.id)).thenReturn(characterTag);

        final List<Tag> tags = characterService.readTags(characterHeader);
        assertThat(tags, notNullValue());
        assertThat(tags.size(), is(1));
    }

    @Test
    public void testAssignAndReadCharactersByTag() {
        final CharacterHeader characterHeader = new CharacterHeader();
        characterHeader.id = 1L;

        final Tag tag = mock(Tag.class);
        tag.tag = "TestTag";
        when(characterDAO.readTagByName("TestTag")).thenReturn(tag);
        when(characterDAO.readTagsForCharacterHeaderId(1L)).thenReturn(Collections.singletonList(tag));
        characterService.createAndAssignTag(characterHeader, tag);

        verify(characterDAO).readTagByName("TestTag");

        final List<CharacterHeader> dummyList = Collections.emptyList();
        when(characterDAO.readCharacterHeadersByTagId(2L, 3L)).thenReturn(dummyList);
        final List<CharacterHeader> characterHeaders = characterService.readCharacterHeadersByTagId(2L, 3L);
        assertThat(characterHeaders, is(dummyList));
    }

    @Test
    public void testDoNotDeleteTagIfFurtherReferencesExist() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        final Tag tag = new Tag();
        tag.id = 1L;

        when(characterDAO.countTagOccurrences(1L)).thenReturn(1L);
        characterService.removeTag(characterHeader, tag);
        verify(characterDAO, never()).deleteTag(tag);
    }

    @Test
    public void testDeleteTagIfNoFurtherReferencesExist() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        final Tag tag = new Tag();
        tag.id = 1L;

        when(characterDAO.countTagOccurrences(0L)).thenReturn(1L);
        characterService.removeTag(characterHeader, tag);
        verify(characterDAO).deleteTag(tag);
    }

    @Test
    public void testCreateAndReadEvent() {
        final CharacterHeader characterHeader = mock(CharacterHeader.class);
        final Event event = new Event();

        characterService.createEvent(characterHeader, event);
        verify(characterDAO).createEvent(event);
        verify(characterDAO, times(1)).createEventToCharacterHeader(any());

        when(characterDAO.readEventsForCharacterHeaderId(1L)).thenReturn(Collections.singletonList(event));
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(1L);
        assertThat(events, notNullValue());
        assertThat(events.size(), is(1));
        assertThat(events.get(0), is(event));
    }

    @Test
    public void testUpdateEvent() {
        final Event event = new Event();
        characterService.updateEvent(event);

        verify(characterDAO).updateEvent(event);
    }

    @Test
    public void testDeleteEvent() {
        final Event event = new Event();
        characterService.deleteEvent(event);

        verify(characterDAO).deleteEvent(event);
    }
}
