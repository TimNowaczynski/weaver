package de.quarian.weaver.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.dev.DemoDataSetInjector;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.service.CampaignServiceImplementation;
import de.quarian.weaver.theming.ThemeProvider;

@Module
public class ApplicationModule {

    private final Context applicationContext;
    private final Handler globalHandler;

    public ApplicationModule(final Context applicationContext) {
        this.applicationContext = applicationContext;
        this.globalHandler = new Handler();
    }

    @ApplicationContext
    @Provides
    @Singleton
    public Context context() {
        return applicationContext;
    }

    @GlobalHandler
    @Provides
    @Singleton
    public Handler globalHandler() {
        return globalHandler;
    }

    @Provides
    @Singleton
    public WeaverDB weaverDB() {
        return Room.databaseBuilder(applicationContext, WeaverDB.class, WeaverDB.DATABASE_FILE_NAME)
                .build();
    }

    @Provides
    @Singleton
    public CampaignService campaignService(final WeaverDB weaverDB, @CampaignListOrderPreferences SharedPreferences sharedPreferences) {
        return new CampaignServiceImplementation(weaverDB, sharedPreferences);
    }

    @Provides
    @Singleton
    public ThemeProvider themeProvider() {
        return new ThemeProvider(weaverDB());
    }

    @Provides
    @Singleton
    public DemoDataSetInjector demoDataSetInjector() {
        return new DemoDataSetInjector(applicationContext);
    }

}
