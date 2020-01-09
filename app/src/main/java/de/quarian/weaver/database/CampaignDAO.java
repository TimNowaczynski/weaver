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

@Dao
public interface CampaignDAO {

    @Query("SELECT * FROM RoleplayingSystem")
    List<RoleplayingSystem> getRoleplayingSystems();

    @Query("SELECT * FROM RoleplayingSystem WHERE roleplaying_system_id IS :id")
    RoleplayingSystem getRoleplayingSystem(long id);

    @Insert
    void insertRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    @Delete
    void deleteRoleplayingSystem(final RoleplayingSystem roleplayingSystem);

    @Query("SELECT * FROM Theme WHERE theme_id IS :id")
    Theme getTheme(final long id);

    @Insert
    long insertTheme(final Theme theme);

    @Update
    void updateTheme(final Theme theme);

    @Delete
    void deleteTheme(final Theme theme);

    @Query("SELECT * FROM Campaign")
    List<Campaign> getCampaigns();

    @Query("SELECT * FROM Campaign where campaign_id is :id")
    Campaign getCampaign(final long id);

    @Insert
    void insertCampaign(final Campaign campaign);

    @Update
    void updateCampaign(final Campaign campaign);

    @Delete
    void deleteCampaign(final Campaign campaign);
}
