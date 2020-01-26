package de.quarian.weaver.service;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.PlayerCharacter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerCharacterServiceImplementationUnitTest extends TestCase {

    @Mock
    private WeaverDB weaverDB;

    @Mock
    private PlayerCharacterDAO playerCharacterDAO;

    private PlayerCharacterService playerCharacterService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(weaverDB.playerCharacterDAO()).thenReturn(playerCharacterDAO);
        playerCharacterService = new PlayerCharacterServiceImplementation(weaverDB);
    }

    public void testCreatePlayerCharacter() {
        final PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
        playerCharacterService.createPlayerCharacter(playerCharacter);
        verify(playerCharacterDAO).createPlayerCharacter(playerCharacter);
    }

    public void testReadPlayerCharactersForCampaign() {
        final PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
        final List<PlayerCharacter> playerCharacters = Collections.singletonList(playerCharacter);
        when(playerCharacterDAO.readPlayerCharactersForCampaign(1L)).thenReturn(playerCharacters);

        final Campaign campaign = mock(Campaign.class);
        campaign.id = 1L;
        final List<PlayerCharacter> playerCharactersOut = playerCharacterService.readPlayerCharactersForCampaign(campaign);
        assertThat(playerCharactersOut.size(), is(1));
        assertThat(playerCharactersOut.get(0), is(playerCharacter));
    }

    public void testUpdatePlayerCharacter() {
        final PlayerCharacter playerCharacterToUpdate = mock(PlayerCharacter.class);
        playerCharacterService.updatePlayerCharacter(playerCharacterToUpdate);
        verify(playerCharacterDAO).updatePlayerCharacter(playerCharacterToUpdate);
    }

    public void testDeletePlayerCharacter() {
        final PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
        playerCharacterService.deletePlayerCharacter(playerCharacter);
        verify(playerCharacterDAO).deletePlayerCharacter(playerCharacter);
    }
}
