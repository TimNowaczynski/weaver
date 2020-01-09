package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;

/**
 * Associated tables:
 * Roleplaying System {@link de.quarian.weaver.datamodel.RoleplayingSystem}
 *  - get a list of all systems (for suggestions)
 *  - get a specific system
 */
@Dao
public interface CharacterDAO {

    @Query("SELECT * FROM RoleplayingSystem")
    List<RoleplayingSystem> getRoleplayingSystems();

    @Query("SELECT * FROM RoleplayingSystem WHERE roleplaying_system_id IS :id")
    RoleplayingSystem getRoleplayingSystem(long id);

    @Insert
    RoleplayingSystem insertRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    @Delete
    RoleplayingSystem deleteRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    @Query("SELECT * FROM Theme WHERE theme_id IS :id")
    Theme getTheme(final long id);

    @Insert
    Theme insertTheme(final Theme theme);

    @Update
    Theme updateTheme(final Theme theme);

    @Delete
    Theme deleteTheme(final Theme theme);

    @Query("SELECT * FROM Campaign")
    List<Campaign> getCampaigns();

    @Query("SELECT * FROM Campaign where campaign_id is :id")
    Campaign getCampaign(final long id);

    @Insert
    Campaign insertCampaign(final Campaign campaign);

    @Update
    Campaign updateCampaign(final Campaign campaign);

    @Delete
    Campaign deleteCampaign(final Campaign campaign);
}
