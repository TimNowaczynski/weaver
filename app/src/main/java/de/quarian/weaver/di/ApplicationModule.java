package de.quarian.weaver.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.BuildConfig;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.converter.CampaignConverter;
import de.quarian.weaver.dev.DemoDataSetInjector;
import de.quarian.weaver.schedulers.IoScheduler;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.service.CampaignServiceImplementation;
import de.quarian.weaver.theming.ThemeProvider;
import de.quarian.weaver.util.LocalLogger;
import de.quarian.weaver.util.LoggingProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {

    @NonNull
    private final Context applicationContext;

    @NonNull
    private final Handler globalHandler;

    @NonNull
    private final Scheduler ioScheduler;

    public ApplicationModule(@NonNull final Context applicationContext) {
        this.applicationContext = applicationContext;
        this.globalHandler = new Handler();
        this.ioScheduler = Schedulers.io();
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
    public CampaignDAO campaignDAO() {
        return weaverDB().campaignDAO();
    }

    @Provides
    @Singleton
    public WeaverLayoutInflater weaverLayoutInflater() {
        return new WeaverLayoutInflater();
    }

    @Provides
    @Singleton
    public CampaignConverter campaignConverter() {
        return new CampaignConverter();
    }

    @Provides
    @Singleton
    public CampaignService campaignService(final WeaverDB weaverDB, @CampaignListOrderPreferences SharedPreferences sharedPreferences, final CampaignConverter campaignConverter) {
        return new CampaignServiceImplementation(weaverDB, sharedPreferences, campaignConverter);
    }

    @Provides
    @Singleton
    public ThemeProvider themeProvider(final WeaverDB weaverDB, final LoggingProvider loggingProvider) {
        return new ThemeProvider(weaverDB, loggingProvider);
    }

    @Provides
    @Singleton
    public DemoDataSetInjector demoDataSetInjector() {
        return new DemoDataSetInjector(applicationContext);
    }

    @Provides
    @IoScheduler
    @Singleton
    public Scheduler ioScheduler() {
        return ioScheduler;
    }

    @Provides
    @Singleton
    public LoggingProvider loggingProvider() {
        if (BuildConfig.DEBUG) {
            return new LocalLogger.LocalLoggingProvider();
        } else {
            // TODO: replace with remote logger
            return new LocalLogger.LocalLoggingProvider();
        }
    }

}
