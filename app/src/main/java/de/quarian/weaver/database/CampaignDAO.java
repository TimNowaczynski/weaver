package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Campaign;

@Dao
public interface CampaignDAO {

    @Insert
    void insertCampaign(final Campaign campaign);

    @Query("SELECT * FROM Campaign")
    List<Campaign> getCampaigns();

    @Update
    void updateCampaign(final Campaign campaign);

    @Delete
    void deleteCampaign(final Campaign campaign);

}
