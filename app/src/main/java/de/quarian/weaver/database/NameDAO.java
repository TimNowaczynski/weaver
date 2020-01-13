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

    @Query("SELECT COUNT(*) FROM name " +
            "INNER JOIN nameset ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId")
    long readNumberOfNames(final long nameSetId);

    /**
     * Used for picking random names without reading the whole table into memory
     * See {@link de.quarian.weaver.datamodel.Constants.Gender} for hardcoded values 0 and 2
     * @param nameSetId ID of the {@link NameSet}
     * @param offset Should be the random roll with a index starting at 1
     * @param limit Should basically always be 1
     * @return A {@link Name}-part of a full name
     */
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS 0 " +
            "AND name.gender IN (0,2) " +
            "LIMIT :limit OFFSET :offset")
    Name readMaleFirstName(final long nameSetId, final long offset, final long limit);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT COUNT(*) FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS 0 " +
            "AND name.gender IN (0,2)")
    long readNumberOfMaleFirstNames(final long nameSetId);

    /**
     * Used for picking random names without reading the whole table into memory
     * See {@link de.quarian.weaver.datamodel.Constants.Gender} for hardcoded values 1 and 2
     * @param nameSetId ID of the {@link NameSet}
     * @param offset Should be the random roll with a index starting at 1
     * @param limit Should basically always be 1
     * @return A {@link Name}-part of a full name
     */
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS 0 " +
            "AND name.gender IN (1,2) " +
            "LIMIT :limit OFFSET :offset")
    Name readFemaleFirstName(final long nameSetId, final long offset, final long limit);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT COUNT(*) FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS 0 " +
            "AND name.gender IN (1,2)")
    long readNumberOfFemaleFirstNames(final long nameSetId);

    /**
     * Used for picking random names without reading the whole table into memory
     * See {@link de.quarian.weaver.datamodel.Constants.Gender} for hardcoded value 2
     * @param nameSetId ID of the {@link NameSet}
     * @param offset Should be the random roll with a index starting at 1
     * @param limit Should basically always be 1
     * @return A {@link Name}-part of a full name
     */
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS 0 " +
            "AND name.gender IS 2 " +
            "LIMIT :limit OFFSET :offset")
    Name readUnisexFirstName(final long nameSetId, final long offset, final long limit);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT COUNT(*) FROM nameset " +
            "INNER JOIN name ON name.fk_name_set_id = nameset.name_set_id " +
            "WHERE nameset.name_set_id IS :nameSetId " +
            "AND name.position IS 0 " +
            "AND name.gender IS 2")
    long readNumberOfUnisexFirstNames(final long nameSetId);

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
            "LEFT JOIN namesettocampaign ON campaign.campaign_id = namesettocampaign.fk_campaign_id " +
            "LEFT JOIN nameset ON namesettocampaign.fk_name_set_id = nameset.name_set_id " +
            "WHERE campaign.campaign_id = :campaignID")
    List<NameSet> readNameSetsForCampaign(final long campaignID);

    @Update
    void updateNameSet(final NameSet nameSet);

    @Delete()
    void deleteName(final Name name);

    @Delete()
    void deleteNameSet(final NameSet nameSet);

    @Insert
    void createNameSetToCampaignMapping(final NameSetToCampaign nameSetToCampaignMapping);
}
