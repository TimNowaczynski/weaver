package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;

@Dao
public interface NameDAO {

    /*
    --------------------------
    ||  Start Name Section  ||
    --------------------------
     */

    @Insert
    void createName(final Name name);

    @Query("SELECT * FROM name WHERE name.name IS :name " +
            "AND name.fk_name_set_id IS :nameSetId")
    Name readName(final String name, final long nameSetId);

    @Query("SELECT COUNT(*) FROM name " +
            "INNER JOIN nameset ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId")
    int readNumberOfNames(final long nameSetId);

    @Query("SELECT COUNT(*) FROM name " +
            "INNER JOIN nameset ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS :namePosition " +
            "AND name.gender IN (:nameGenders) ")
    int readNumberOfNames(final long nameSetId, final int namePosition, List<Integer> nameGenders);

    /**
     * Is used to grab a random name within a set
     * @param nameSetId the Id of the Name Set
     * @param namePosition the {@link de.quarian.weaver.datamodel.Constants.NamePosition}
     * @param nameGenders the {@link de.quarian.weaver.datamodel.Constants.NameGender}
     * @param offset A random offset
     * @return The name at the current offset
     */
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS :namePosition " +
            "AND name.gender IN (:nameGenders) " +
            "LIMIT 1 OFFSET :offset")
    Name readRandomName(final long nameSetId, final int namePosition, final List<Integer> nameGenders, final long offset);

    @Update
    void updateName(final Name name);

    /*
    ------------------------------
    ||  Start Name Set Section  ||
    ------------------------------
     */

    @Insert
    long createNameSet(final NameSet nameSet);

    @Query("SELECT * FROM nameset")
    List<NameSet> readNameSets();

    @Query("SELECT * FROM nameset WHERE name_set_id IS :nameSetId")
    NameSet readNameSetByID(final long nameSetId);

    //TODO: Should be later on completely be replaced by readNameSetByID()
    @Query("SELECT * FROM nameset WHERE name_set_name IS :nameSetName")
    NameSet readNameSetByName(final String nameSetName);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM campaign " +
            "INNER JOIN namesettocampaign ON campaign.campaign_id = namesettocampaign.fk_campaign_id " +
            "INNER JOIN nameset ON namesettocampaign.fk_name_set_id = nameset.name_set_id " +
            "WHERE campaign.campaign_id IS :campaignID")
    List<NameSet> readNameSetsForCampaign(final long campaignID);

    @Update
    void updateNameSet(final NameSet nameSet);

    @Delete
    void deleteNameSet(final NameSet nameSet);

    @Query("DELETE FROM namesettocampaign WHERE namesettocampaign.fk_campaign_id IS :campaignId")
    void deleteNameSetToCampaignMappings(final long campaignId);

    @Insert
    void createNameSetToCampaignMapping(final NameSetToCampaign nameSetToCampaignMapping);
}
