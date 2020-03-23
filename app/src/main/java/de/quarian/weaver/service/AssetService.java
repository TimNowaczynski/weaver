package de.quarian.weaver.service;

import java.util.List;

import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Event;

public interface AssetService {

    long DEFAULT_LIFETIME = 1000L * 60L * 60L * 24L * 30L * 6L; // About six month
    long UNLIMITED_LIFETIME = 0L;

    void createAsset(final Asset asset);

    List<Asset> getAssetsWithLimitedLifetime();

    List<Asset> getAssetsWithUnlimitedLifetime();

    Asset getAssetForEvent(final Event event);

    void moveAssetToCloud(final Asset asset);

    Asset extendLifetime(final Asset asset, final long additionalLifetime);

    void cleanUpExpiredAssets();
}
