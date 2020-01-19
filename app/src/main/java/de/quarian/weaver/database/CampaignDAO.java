package de.quarian.weaver.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Campaign;

@Dao
public interface CampaignDAO {

    @Insert
    long createCampaign(final Campaign campaign);

    @Query("SELECT * FROM Campaign ORDER BY Campaign.archived ASC, Campaign.campaign_name ASC")
    List<Campaign> readCampaignsOrderedByName();

    @Query("SELECT campaign_id, fk_roleplaying_system_id, fk_theme_id, creation_date_millis, edit_date_millis, last_used_date_millis, archived, campaign_name, synopsis " +
            "FROM Campaign INNER JOIN RoleplayingSystem " +
            "ON Campaign.fk_roleplaying_system_id IS RoleplayingSystem.roleplaying_system_id " +
            "ORDER BY Campaign.archived ASC, RoleplayingSystem.roleplaying_system_name ASC")
    List<Campaign> readCampaignsOrderedBySystemName();

    @Query("SELECT * FROM Campaign ORDER BY Campaign.archived ASC, Campaign.last_used_date_millis DESC")
    List<Campaign> readCampaignsOrderedByLastUsed();

    @Query("SELECT * FROM Campaign ORDER BY Campaign.archived ASC, Campaign.edit_date_millis DESC")
    List<Campaign> readCampaignsOrderedByLastEdited();

    @Query("SELECT * FROM Campaign ORDER BY Campaign.archived ASC, Campaign.creation_date_millis DESC")
    List<Campaign> readCampaignsOrderedByCreated();

    @Query("SELECT * FROM Campaign WHERE campaign_id IS :campaignId")
    LiveData<Campaign> readCampaignByID(final long campaignId);

    @Query("SELECT * FROM Campaign WHERE campaign_name LIKE :campaignName")
    Campaign readCampaignByName(final String campaignName);

    @Update
    void updateCampaign(final Campaign campaign);

    @Delete
    void deleteCampaign(final Campaign campaign);

}
