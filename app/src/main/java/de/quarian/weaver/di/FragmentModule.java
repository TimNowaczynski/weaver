package de.quarian.weaver.di;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.converter.AssetConverter;
import de.quarian.weaver.service.AssetService;
import de.quarian.weaver.service.AssetServiceImplementation;
import de.quarian.weaver.util.ResourcesProvider;

@Module(includes = ApplicationModule.class)
public class FragmentModule {

    // TODO: we might need to move this into either application or activity module
    @Provides
    @Singleton
    public AssetConverter assetConverter(@NonNull final ResourcesProvider resourcesProvider) {
        return new AssetConverter(resourcesProvider);
    }

    // TODO: same here
    @Provides
    @Singleton
    public AssetService assetService(@NonNull final WeaverDB weaverDB, @NonNull AssetConverter assetConverter) {
        return new AssetServiceImplementation(weaverDB, assetConverter);
    }

}
