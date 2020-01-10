package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;

@Dao
public interface NameDAO {

    @Insert
    long createNameSet(final NameSet nameSet);

    @Insert
    void createNameSetToCampaignMapping(final NameSetToCampaign nameSetToCampaignMapping);

    @Query("SELECT * FROM nameset")
    List<NameSet> readNameSets();

    @Query("SELECT name_set_id, name_set_name FROM campaign " +
            "LEFT JOIN namesettocampaign ON campaign_id = fk_campaign_id " +
            "LEFT JOIN nameset ON fk_name_set_id = name_set_id " +
            "WHERE campaign_id = :campaignID")
    List<NameSet> readNameSetsForCampaign(final long campaignID);

    @Update
    void updateNameSet(final NameSet nameSet);

    @Delete
    void deleteNameSet(final NameSet nameSet);
}
