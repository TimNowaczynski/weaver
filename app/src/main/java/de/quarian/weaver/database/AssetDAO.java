package de.quarian.weaver.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
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
    @Query("SELECT asset_id, fk_event_id, campaign_name, end_of_lifetime_timestamp, content_locally_present, asset_name, asset_description, asset_type, asset, fallback_url  FROM asset " +
            "WHERE fk_event_id IS :eventId")
    List<Asset> readAssetsForEvent(final long eventId);

    /**
     * @return A list with all the assets which can't expire
     */
    @Query("SELECT asset_id, fk_event_id, campaign_name, end_of_lifetime_timestamp, content_locally_present, asset_name, asset_description, asset_type, asset, fallback_url  FROM asset " +
            "WHERE end_of_lifetime_timestamp IS 0 " +
            "ORDER BY asset_name ASC")
    List<Asset> readAllAssetsWithUnlimitedLifetime();

    /**
     * @return A list with all the assets which have a limited lifetime
     */
    @Query("SELECT asset_id, fk_event_id, campaign_name, end_of_lifetime_timestamp, content_locally_present, asset_name, asset_description, asset_type, asset, fallback_url  FROM asset " +
            "WHERE end_of_lifetime_timestamp != 0 " +
            "AND end_of_lifetime_timestamp > 0 " +
            "ORDER BY end_of_lifetime_timestamp ASC")
    List<Asset> readAllAssetsWithLimitedLifetime();

    // Update

    @Update
    void updateAsset(final Asset asset);

    // Delete

    @Delete
    void deleteAsset(final Asset asset);

    @Query("DELETE FROM asset WHERE end_of_lifetime_timestamp <= :timestamp AND end_of_lifetime_timestamp > 0")
    void deleteAllExpiredAssets(final long timestamp);
}
