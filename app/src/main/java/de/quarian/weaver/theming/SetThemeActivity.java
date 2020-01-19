package de.quarian.weaver.theming;

import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;

public class SetThemeActivity extends WeaverThemedActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Inject
    public ThemeProvider themeProvider;

    public Theme theme;

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_theme;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        readThemeForCampaign();
    }

    private void injectDependencies() {
        final Context applicationContext = getApplicationContext();
        final ApplicationModule applicationModule = new ApplicationModule(applicationContext);

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build()
                .inject(this);
    }

    private void readThemeForCampaign() {
        final long campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, -1L);
        if (campaignId > 0) {
            theme = themeProvider.getThemeForCampaign(campaignId);
            setTitle(R.string.activity_title_set_theme);
        } else {
            //TODO: log error
            finish();
        }
    }
}
