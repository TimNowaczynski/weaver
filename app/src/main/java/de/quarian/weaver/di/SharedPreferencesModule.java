package de.quarian.weaver.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    private static final String CAMPAIGN_LIST_ORDER_PREFERENCES = "sharedPreferences.campaignListOrder";

    @CampaignListOrderPreferences
    @Provides
    public SharedPreferences campaignListOrderPreferences(@ApplicationContext final Context context) {
        return context.getSharedPreferences(CAMPAIGN_LIST_ORDER_PREFERENCES, Context.MODE_PRIVATE);
    }

}
