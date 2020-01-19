package de.quarian.weaver.di;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;

@Module
public class SharedPreferencesModule {

    @Inject
    public Context applicationContext;

    /* I leave this here as an example for now
        @ThemePreferences
        @Provides
        public SharedPreferences themePreferences() {
            return applicationContext.getSharedPreferences(SHARED_PREFERENCES_THEME, Context.MODE_PRIVATE);
        }
     */

}
