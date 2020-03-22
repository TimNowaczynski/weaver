package de.quarian.weaver.assets;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.AssetDAO;

public class AssetService {

    // TODO: move to cloud option

    @NonNull
    private final AssetDAO assetDAO;

    public AssetService(@NonNull final AssetDAO assetDAO) {
        this.assetDAO = assetDAO;
    }
}
