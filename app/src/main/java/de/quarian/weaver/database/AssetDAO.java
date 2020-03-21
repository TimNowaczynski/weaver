package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import de.quarian.weaver.datamodel.Asset;

@Dao
public interface AssetDAO {

    // Create

    @Insert
    long createAsset(final Asset asset);

    // Read

    /**
     * @param eventId The event we want to retrieve the asset for
     * @return A list with currently always 0-1 items contained
     */
    @Query("SELECT asset_id, fk_event_id, end_of_lifetime_timestamp, asset_name, asset_description, asset_type, asset  FROM asset " +
            "WHERE fk_event_id IS :eventId")
    List<Asset> readAssetsForEvent(final long eventId);

    // No Update Operation

    // Delete

    @Delete
    void deleteAsset(final Asset asset);
}
