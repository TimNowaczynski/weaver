package de.quarian.weaver.di;

import android.content.Context;
import android.os.Handler;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.database.WeaverDB;
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
    public ThemeProvider themeProvider() {
        return new ThemeProvider(weaverDB());
    }

}
