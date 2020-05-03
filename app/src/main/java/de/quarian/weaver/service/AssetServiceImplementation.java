package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.assets.AssetDisplayObject;
import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.database.DAOProvider;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.converter.AssetConverter;

public class AssetServiceImplementation implements AssetService {

    @NonNull
    private final AssetDAO assetDAO;

    @NonNull
    private final AssetConverter assetConverter;

    public AssetServiceImplementation(@NonNull final DAOProvider daoProvider, @NonNull final AssetConverter assetConverter) {
        this.assetDAO = daoProvider.assetDAO();
        this.assetConverter = assetConverter;
    }

    @Override
    public void createAsset(final Asset asset) {
        assetDAO.createAsset(asset);
    }

    @Override
    public List<AssetDisplayObject> getAssetsWithLimitedLifetime() {
        final List<Asset> assets = assetDAO.readAllAssetsWithLimitedLifetime();
        return assetConverter.convert(assets);
    }

    @Override
    public List<AssetDisplayObject> getAssetsWithUnlimitedLifetime() {
        final List<Asset> assets = assetDAO.readAllAssetsWithUnlimitedLifetime();
        return assetConverter.convert(assets);
    }

    @Override
    public int getNumberOfExpiredAssets() {
        return assetDAO.readNumberOfExpiredAssets(System.currentTimeMillis());
    }

    @Override
    public AssetDisplayObject getAssetForEvent(final long eventId) {
        Asset asset = assetDAO.readAssetsForEvent(eventId).get(0);
        if (asset.endOfLifetimeTimestamp == UNLIMITED_LIFETIME) {
            return assetConverter.convert(asset);
        } else {
            asset = extendLifetime(asset.id, DEFAULT_LIFETIME);
            return assetConverter.convert(asset);
        }
    }

    @Override
    public void moveAssetToCloud(final long assetId) {
        // TODO: implement, we currently just set a dummy url for testing purposes
        final Asset asset = assetDAO.readAssetForId(assetId);
        asset.asset = null;
        asset.fallbackUrl = "http://fallback.url";
        asset.endOfLifetimeTimestamp = UNLIMITED_LIFETIME;
        assetDAO.updateAsset(asset);
    }

    @Override
    public Asset extendLifetime(final long assetId, final long additionalLifetime) {
        final Asset asset = assetDAO.readAssetForId(assetId);
        asset.endOfLifetimeTimestamp += additionalLifetime;
        assetDAO.updateAsset(asset);
        return asset;
    }

    @Override
    public void cleanUpExpiredAssets() {
        assetDAO.deleteAllExpiredAssets(System.currentTimeMillis());
    }
}
