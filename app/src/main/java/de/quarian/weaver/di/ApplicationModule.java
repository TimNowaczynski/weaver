package de.quarian.weaver.di;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.theming.ThemeProvider;

@Module
public class ApplicationModule {

    private final Context applicationContext;

    public ApplicationModule(final Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @ApplicationContext
    @Provides
    @Singleton
    public Context context() {
        return applicationContext;
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
