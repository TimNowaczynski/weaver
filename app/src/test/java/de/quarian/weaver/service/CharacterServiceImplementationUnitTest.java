package de.quarian.weaver.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
        when(characterDAO.readCharacterBodyByCharacterHeaderId(1L)).thenReturn(characterBody);

        characterService.deleteCharacter(characterHeader);

        verify(characterDAO).deleteCharacterHeader(characterHeader);
        verify(characterDAO).deleteCharacterBody(characterBody);
    }
}
