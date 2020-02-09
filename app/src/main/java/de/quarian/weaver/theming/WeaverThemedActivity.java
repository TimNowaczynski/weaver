package de.quarian.weaver.theming;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.BR;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ActivityModule;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerActivityComponent;
import de.quarian.weaver.di.DaggerApplicationComponent;

public abstract class WeaverThemedActivity extends AppCompatActivity {

    public static class ActivityDependencies {

        @Inject
        @Nullable
        public ActivityPreconditionErrorHandler errorHandler;

    }

    private static final long INVALID_CAMPAIGN_ID = -2;
    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    private final ActivityDependencies activityDependencies = new ActivityDependencies();
    public long campaignId;

    @Inject
    public ThemeProvider themeProvider;

    private ViewDataBinding viewDataBinding;

    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        final boolean requirementsMet = requireCampaignId();
        if (requirementsMet) {
            viewDataBinding = DataBindingUtil.setContentView(this, getContentViewId());
            applyTheme();
        }
        super.onCreate(savedInstanceState);
    }

    private void injectDependencies() {
        final Context applicationContext = getApplicationContext();
        final ApplicationModule applicationModule = new ApplicationModule(applicationContext);

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build()
                .inject(this);

        final ActivityModule activityModule = new ActivityModule(this);

        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this.activityDependencies);
    }

    private boolean requireCampaignId() {
        if (activityDependencies.errorHandler != null) {
            campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
            return activityDependencies.errorHandler.requireOrFinish(() -> campaignId != INVALID_CAMPAIGN_ID, R.string.activity_player_character_list_invalid_id_error_title, R.string.activity_player_character_list_invalid_id_error_text);
        } else {
            finish(); // Should basically never happen
            return false;
        }
    }

    private void applyTheme() {
        AsyncTask.execute(() -> {
            final Theme themeForCampaign = themeProvider.getThemeForCampaign(campaignId);
            if (themeForCampaign.presetId == Theme.PRESET_ID_FANTASY) {
                applyClassicTheme();
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

    private void applyClassicTheme() {
        // TODO: and this

    }

    private void applyModernTheme() {
        // TODO: and that

    }

    private void applyTheme(@NonNull Theme themeForCampaign) {
        final ViewDataBinding viewDataBinding = getViewDataBinding();
        final ThemeDisplayObject themeDisplayObject = ThemeDisplayObject.fromTheme(themeForCampaign);
        viewDataBinding.setVariable(BR.themeDisplayObject, themeDisplayObject);
    }
}
