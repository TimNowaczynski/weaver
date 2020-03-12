package de.quarian.weaver.theming;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.BR;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ActivityModule;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerActivityComponent;
import de.quarian.weaver.di.DependencyInjectionListener;

public abstract class WeaverThemedActivity extends WeaverActivity implements DependencyInjectionListener {

    public static class ActivityDependencies {

        @Inject
        @Nullable
        public ActivityPreconditionErrorHandler errorHandler;

        @Inject
        public ThemeProvider themeProvider;

    }

    private static final long INVALID_CAMPAIGN_ID = -2;
    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    public final ActivityDependencies weaverThemedActivityDependencies = new ActivityDependencies();
    public long campaignId;

    private ViewDataBinding viewDataBinding;

    public abstract int getContentViewId();

    public abstract Activity getTargetActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectBaseDependencies(this.weaverThemedActivityDependencies);

        final boolean requirementsMet = requireCampaignId();
        if (requirementsMet) {
            viewDataBinding = DataBindingUtil.setContentView(getTargetActivity(), getContentViewId());
            applyTheme();
        }
    }

    protected void injectBaseDependencies(final ActivityDependencies activityDependencies) {
        final Context applicationContext = getApplicationContext();
        final ApplicationModule applicationModule = new ApplicationModule(applicationContext);
        final ActivityModule activityModule = new ActivityModule(this);

        DaggerActivityComponent.builder()
                .applicationModule(applicationModule)
                .activityModule(activityModule)
                .build()
                .inject(activityDependencies);
    }

    private boolean requireCampaignId() {
        if (weaverThemedActivityDependencies.errorHandler != null) {
            campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
            return weaverThemedActivityDependencies.errorHandler.requireOrFinish(() -> campaignId != INVALID_CAMPAIGN_ID, R.string.activity_player_character_list_invalid_id_error_title, R.string.activity_player_character_list_invalid_id_error_text);
        } else {
            finish(); // Should basically never happen
            return false;
        }
    }

    /**
     * Fantasy and Modern Themes are hardcoded
     */
    private void applyTheme() {
        AsyncTask.execute(() -> {
            final Theme themeForCampaign = weaverThemedActivityDependencies.themeProvider.getThemeForCampaign(campaignId);
            if (themeForCampaign.presetId == Theme.PRESET_ID_FANTASY) {
                applyFantasyTheme();
            } else if(themeForCampaign.presetId == Theme.PRESET_ID_MODERN) {
                applyModernTheme();
            } else {
                applyTheme(themeForCampaign);
            }
        });
    }

    // We could hand over the class as parameter, but that's just to complicated I think
    @SuppressWarnings("unchecked")
    public <T extends ViewDataBinding> T getViewDataBinding() {
        return (T) viewDataBinding;
    }

    // TODO: move those hardcoded values into theme or themeDisplayObject class
    private void applyFantasyTheme() {
        final Resources resources = getResources();
        final Theme fantasyTheme = new Theme();
        fantasyTheme.presetId = Theme.PRESET_ID_FANTASY;
        // TODO: provide a bundled font and point to it somehow

        // Banner Background Image should always be customised
        // + Image Type

        fantasyTheme.screenBackgroundColorA = Color.alpha(resources.getColor(R.color.modernSecondaryColor));
        fantasyTheme.screenBackgroundColorR = Color.red(resources.getColor(R.color.modernSecondaryColor));
        fantasyTheme.screenBackgroundColorG = Color.green(resources.getColor(R.color.modernSecondaryColor));
        fantasyTheme.screenBackgroundColorB = Color.blue(resources.getColor(R.color.modernSecondaryColor));

        fantasyTheme.itemBackgroundColorA = Color.alpha(resources.getColor(R.color.modernSecondaryLightColor));
        fantasyTheme.itemBackgroundColorR = Color.red(resources.getColor(R.color.modernSecondaryLightColor));
        fantasyTheme.itemBackgroundColorG = Color.green(resources.getColor(R.color.modernSecondaryLightColor));
        fantasyTheme.itemBackgroundColorB = Color.blue(resources.getColor(R.color.modernSecondaryLightColor));

        fantasyTheme.backgroundFontColorA = Color.alpha(resources.getColor(R.color.white));
        fantasyTheme.backgroundFontColorR = Color.red(resources.getColor(R.color.white));
        fantasyTheme.backgroundFontColorG = Color.green(resources.getColor(R.color.white));
        fantasyTheme.backgroundFontColorB = Color.blue(resources.getColor(R.color.white));

        fantasyTheme.itemFontColorA = Color.alpha(resources.getColor(R.color.pitch_black));
        fantasyTheme.itemFontColorR = Color.red(resources.getColor(R.color.pitch_black));
        fantasyTheme.itemFontColorG = Color.green(resources.getColor(R.color.pitch_black));
        fantasyTheme.itemFontColorB = Color.blue(resources.getColor(R.color.pitch_black));

        applyTheme(fantasyTheme);
    }

    private void applyModernTheme() {
        final Resources resources = getResources();
        final Theme modernTheme = new Theme();
        modernTheme.presetId = Theme.PRESET_ID_MODERN;
        // TODO: provide a bundled font and point to it somehow

        // Banner Background Image should always be customised
        // + Image Type

        modernTheme.screenBackgroundColorA = Color.alpha(resources.getColor(R.color.modernSecondaryColor));
        modernTheme.screenBackgroundColorR = Color.red(resources.getColor(R.color.modernSecondaryColor));
        modernTheme.screenBackgroundColorG = Color.green(resources.getColor(R.color.modernSecondaryColor));
        modernTheme.screenBackgroundColorB = Color.blue(resources.getColor(R.color.modernSecondaryColor));

        modernTheme.itemBackgroundColorA = Color.alpha(resources.getColor(R.color.modernSecondaryLightColor));
        modernTheme.itemBackgroundColorR = Color.red(resources.getColor(R.color.modernSecondaryLightColor));
        modernTheme.itemBackgroundColorG = Color.green(resources.getColor(R.color.modernSecondaryLightColor));
        modernTheme.itemBackgroundColorB = Color.blue(resources.getColor(R.color.modernSecondaryLightColor));

        modernTheme.backgroundFontColorA = Color.alpha(resources.getColor(R.color.white));
        modernTheme.backgroundFontColorR = Color.red(resources.getColor(R.color.white));
        modernTheme.backgroundFontColorG = Color.green(resources.getColor(R.color.white));
        modernTheme.backgroundFontColorB = Color.blue(resources.getColor(R.color.white));

        modernTheme.itemFontColorA = Color.alpha(resources.getColor(R.color.pitch_black));
        modernTheme.itemFontColorR = Color.red(resources.getColor(R.color.pitch_black));
        modernTheme.itemFontColorG = Color.green(resources.getColor(R.color.pitch_black));
        modernTheme.itemFontColorB = Color.blue(resources.getColor(R.color.pitch_black));

        applyTheme(modernTheme);
    }

    private void applyTheme(@NonNull Theme themeForCampaign) {
        final ViewDataBinding viewDataBinding = getViewDataBinding();
        final Context baseContext = getBaseContext();
        final ThemeDisplayObject themeDisplayObject = ThemeDisplayObject.fromTheme(baseContext, themeForCampaign);
        viewDataBinding.setVariable(BR.appliedThemeDisplayObject, themeDisplayObject);
    }
}
