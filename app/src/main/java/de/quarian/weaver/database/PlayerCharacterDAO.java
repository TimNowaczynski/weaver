package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.PlayerCharacter;

@Dao
public interface PlayerCharacterDAO {

    @Insert
    long createPlayerCharacter(final PlayerCharacter playerCharacter);

    /**
     * Meant for generating suggestions when adding player characters to a campaign
     * @param roleplayingSystemId The associated ID of a player character's roleplaying system
     * @return A List of Player Characters used for a roleplaying system
     */
    @Query("SELECT * FROM playercharacter WHERE fk_roleplaying_system_id IS :roleplayingSystemId")
    List<PlayerCharacter> readPlayerCharactersForRoleplayingSystem(final long roleplayingSystemId);

    @Query("SELECT * FROM playercharacter WHERE fk_campaign_id is :campaignId ORDER BY Playercharacter.player_character_name ASC")
    List<PlayerCharacter> readPlayerCharactersForCampaign(final long campaignId);

    @Query("SELECT COUNT(*) FROM playercharacter WHERE fk_campaign_id is :campaignId")
    long readNumberOfPlayerCharactersForCampaign(final long campaignId);

    @Update
    void updatePlayerCharacter(final PlayerCharacter playerCharacter);

    @Delete
    void deletePlayerCharacter(final PlayerCharacter playerCharacter);
}
