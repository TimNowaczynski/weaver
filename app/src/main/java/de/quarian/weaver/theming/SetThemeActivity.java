package de.quarian.weaver.theming;

import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ActivityModule;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerActivityComponent;
import de.quarian.weaver.di.DaggerApplicationComponent;

public class SetThemeActivity extends WeaverThemedActivity {

    public static class ActivityDependencies {

        @Inject
        @Nullable
        public ActivityPreconditionErrorHandler errorHandler;

    }

    private static final long INVALID_CAMPAIGN_ID = -2;
    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    private final ActivityDependencies activityDependencies = new ActivityDependencies();

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
    }

    private void injectDependencies() {
        final Context applicationContext = getApplicationContext();
        final ApplicationModule applicationModule = new ApplicationModule(applicationContext);

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build()
                .inject(this);

        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this.activityDependencies);
    }
}
