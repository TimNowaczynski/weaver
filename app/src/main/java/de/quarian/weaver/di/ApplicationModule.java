package de.quarian.weaver.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.dev.DemoDataSetInjector;
import de.quarian.weaver.schedulers.IoScheduler;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.service.CampaignServiceImplementation;
import de.quarian.weaver.theming.ThemeProvider;
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
    public WeaverLayoutInflater weaverLayoutInflater() {
        return new WeaverLayoutInflater();
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

    @Provides
    @IoScheduler
    @Singleton
    public Scheduler ioScheduler() {
        return ioScheduler;
    }

}
