package de.quarian.weaver.service;

import java.util.List;

import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.PlayerCharacter;
import de.quarian.weaver.datamodel.RoleplayingSystem;

public interface PlayerCharacterService {

    void createPlayerCharacter(final PlayerCharacter playerCharacter);

    List<PlayerCharacter> readPlayerCharactersForCampaign(final Campaign campaign);

    // This is primary meant for suggesting characters in between campaigns of the same system
    List<PlayerCharacter> readPlayerCharactersForRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    void updatePlayerCharacter(final PlayerCharacter playerCharacter);

    void deletePlayerCharacter(final PlayerCharacter playerCharacter);
}
