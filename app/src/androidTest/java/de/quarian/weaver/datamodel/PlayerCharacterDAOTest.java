package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class PlayerCharacterDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpRoleplayingSystems(weaverDB);
        DatabaseTestUtils.setUpThemes(weaverDB);
        DatabaseTestUtils.setUpCampaigns(weaverDB);
        DatabaseTestUtils.setUpPlayerCharacters(weaverDB);
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testReadPlayerCharacters() {
        // Confirm
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final RoleplayingSystem sr = roleplayingSystemDAO.readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN);

        final PlayerCharacterDAO playerCharacterDAO = weaverDB.playerCharacterDAO();

        final List<PlayerCharacter> playerCharactersForRPS = playerCharacterDAO.readPlayerCharactersForRoleplayingSystem(sr.id);
        assertThat(playerCharactersForRPS.size(), is(1));

        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign risingDragon = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);

        final List<PlayerCharacter> playerCharactersForCampaign = playerCharacterDAO.readPlayerCharactersForCampaign(risingDragon.id);
        assertThat(playerCharactersForCampaign.size(), is(1));

        final PlayerCharacter playerCharacter = playerCharactersForCampaign.get(0);
        assertThat(playerCharacter.campaignId, is(risingDragon.id));
        assertThat(playerCharacter.roleplayingSystemId, is(sr.id));
        assertThat(playerCharacter.playerName, is("Tim"));
        assertThat(playerCharacter.playerCharacterName, is("Amanda"));
        assertThat(playerCharacter.playerCharacterAvatarImageType, is("image/jpeg"));
        assertThat(playerCharacter.playerCharacterAvatar, is("avatar".getBytes()));
        assertThat(playerCharacter.characterHighlightColorA, is(1));
        assertThat(playerCharacter.characterHighlightColorR, is(2));
        assertThat(playerCharacter.characterHighlightColorG, is(3));
        assertThat(playerCharacter.characterHighlightColorB, is(4));
    }

    @Test
    public void testReadPlayerCharactersCount() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign risingDragon = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);

        final PlayerCharacterDAO playerCharacterDAO = weaverDB.playerCharacterDAO();
        final long numberOfPlayerCharacters = playerCharacterDAO.readNumberOfPlayerCharactersForCampaign(risingDragon.id);

        assertThat(numberOfPlayerCharacters, is(1L));
    }

    @Test
    public void testUpdatePlayerCharacter() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign risingDragon = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);

        final PlayerCharacterDAO playerCharacterDAO = weaverDB.playerCharacterDAO();
        final List<PlayerCharacter> playerCharactersOutA = playerCharacterDAO.readPlayerCharactersForCampaign(risingDragon.id);
        assertThat(playerCharactersOutA.size(), is(1));

        final PlayerCharacter playerCharacterOutA = playerCharactersOutA.get(0);
        assertThat(playerCharacterOutA.playerName, is("Tim"));

        playerCharacterOutA.playerName = "Kathrin";
        playerCharacterDAO.updatePlayerCharacter(playerCharacterOutA);

        final List<PlayerCharacter> playerCharactersOutB = playerCharacterDAO.readPlayerCharactersForCampaign(risingDragon.id);
        final PlayerCharacter playerCharacterOutB = playerCharactersOutB.get(0);
        assertThat(playerCharacterOutB.playerName, is("Kathrin"));
    }

    @Test
    public void testDeletePlayerCharacter() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign risingDragon = campaignDAO.readCampaignByName(DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON);

        final PlayerCharacterDAO playerCharacterDAO = weaverDB.playerCharacterDAO();
        List<PlayerCharacter> playerCharactersOutA = playerCharacterDAO.readPlayerCharactersForCampaign(risingDragon.id);
        assertThat(playerCharactersOutA.size(), is(1));

        playerCharacterDAO.deletePlayerCharacter(playerCharactersOutA.get(0));

        playerCharactersOutA = playerCharacterDAO.readPlayerCharactersForCampaign(risingDragon.id);
        assertThat(playerCharactersOutA.size(), is(0));
    }
}
