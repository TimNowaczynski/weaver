package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Event;

public class AssetServiceImplementation implements AssetService {

    @NonNull
    private final AssetDAO assetDAO;

    public AssetServiceImplementation(@NonNull final AssetDAO assetDAO) {
        this.assetDAO = assetDAO;
    }

    @Override
    public void createAsset(final Asset asset) {
        assetDAO.createAsset(asset);
    }

    @Override
    public List<Asset> getAssetsWithLimitedLifetime() {
        return assetDAO.readAllAssetsWithLimitedLifetime();
    }

    @Override
    public List<Asset> getAssetsWithUnlimitedLifetime() {
        return assetDAO.readAllAssetsWithUnlimitedLifetime();
    }

    @Override
    public Asset getAssetForEvent(final Event event) {
        Asset asset = assetDAO.readAssetsForEvent(event.id).get(0);
        if (asset.endOfLifetimeTimestamp == UNLIMITED_LIFETIME) {
            return asset;
        } else {
            asset = extendLifetime(asset, DEFAULT_LIFETIME);
            return asset;
        }
    }

    @Override
    public void moveAssetToCloud(final Asset asset) {
        // TODO: implement, we currently just set a dummy url for testing purposes
        asset.asset = null;
        asset.fallbackUrl = "http://fallback.url";
        asset.endOfLifetimeTimestamp = UNLIMITED_LIFETIME;
        assetDAO.updateAsset(asset);
    }

    @Override
    public Asset extendLifetime(final Asset asset, final long additionalLifetime) {
        asset.endOfLifetimeTimestamp += additionalLifetime;
        assetDAO.updateAsset(asset);
        return asset;
    }

    @Override
    public void cleanUpExpiredAssets() {
        assetDAO.deleteAllExpiredAssets(System.currentTimeMillis());
    }
}
