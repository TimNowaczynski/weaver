package de.quarian.weaver.service;

import java.util.List;

import de.quarian.weaver.assets.AssetDisplayObject;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Event;

public interface AssetService {

    long DEFAULT_LIFETIME = 1000L * 60L * 60L * 24L * 30L * 6L; // About six month
    long UNLIMITED_LIFETIME = 0L;

    void createAsset(final Asset asset);

    List<AssetDisplayObject> getAssetsWithLimitedLifetime();

    List<AssetDisplayObject> getAssetsWithUnlimitedLifetime();

    int getNumberOfExpiredAssets();

    AssetDisplayObject getAssetForEvent(final long eventId);

    void moveAssetToCloud(final long assetId);

    Asset extendLifetime(final long assetId, final long additionalLifetime);

    void cleanUpExpiredAssets();
}
