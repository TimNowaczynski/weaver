package de.quarian.weaver.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    private static final String CAMPAIGN_LIST_ORDER_PREFERENCES = "sharedPreferences.campaignListOrder";
    private static final String SETTINGS_PREFERENCES = "sharedPreferences.settings";

    @CampaignListOrderPreferences
    @Provides
    public SharedPreferences campaignListOrderPreferences(@ApplicationContext final Context context) {
        return context.getSharedPreferences(CAMPAIGN_LIST_ORDER_PREFERENCES, Context.MODE_PRIVATE);
    }

    @SettingsPreferences
    @Provides
    public SharedPreferences settingsPreferences(@ApplicationContext final Context context) {
        return context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }
}
