package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;

@Dao
public interface DebugDAO {

    @Query("SELECT * FROM namesettocampaign")
    List<NameSetToCampaign> getNameSetToCampaignMappings();

    @Query("SELECT COUNT(*) FROM name WHERE fk_name_set_id IS :nameSetId")
    int getNumberOfNamesForNameSetID(final long nameSetId);

    @Query("SELECT * FROM nameset WHERE name_set_name IS :nameSetName")
    NameSet readNameSetByName(final String nameSetName);

}
