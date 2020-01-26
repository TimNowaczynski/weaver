package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.PlayerCharacter;
import de.quarian.weaver.datamodel.RoleplayingSystem;

public class PlayerCharacterServiceImplementation implements PlayerCharacterService {

    private final PlayerCharacterDAO playerCharacterDAO;

    public PlayerCharacterServiceImplementation(@NonNull WeaverDB weaverDB) {
        playerCharacterDAO = weaverDB.playerCharacterDAO();
    }

    @Override
    public void createPlayerCharacter(PlayerCharacter playerCharacter) {
        playerCharacterDAO.createPlayerCharacter(playerCharacter);
    }

    @Override
    public List<PlayerCharacter> readPlayerCharactersForRoleplayingSystem(final RoleplayingSystem roleplayingSystem) {
        return playerCharacterDAO.readPlayerCharactersForRoleplayingSystem(roleplayingSystem.id);
    }

    @Override
    public List<PlayerCharacter> readPlayerCharactersForCampaign(final Campaign campaign) {
        return playerCharacterDAO.readPlayerCharactersForCampaign(campaign.id);
    }

    @Override
    public void updatePlayerCharacter(PlayerCharacter playerCharacter) {
        playerCharacterDAO.updatePlayerCharacter(playerCharacter);
    }

    @Override
    public void deletePlayerCharacter(PlayerCharacter playerCharacter) {
        playerCharacterDAO.deletePlayerCharacter(playerCharacter);
    }
}
