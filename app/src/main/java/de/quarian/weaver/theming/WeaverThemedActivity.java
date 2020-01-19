package de.quarian.weaver.theming;

import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.quarian.weaver.BR;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;

public abstract class WeaverThemedActivity extends AppCompatActivity {

    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    public long campaignId;

    @Inject
    public ThemeProvider themeProvider;

    private ViewDataBinding viewDataBinding;

    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, -1L);
        if (campaignId < 0) {
            // TODO: log error
            finish();
        }

        injectDependencies();
        viewDataBinding = DataBindingUtil.setContentView(this, getContentViewId());
        applyTheme();

        super.onCreate(savedInstanceState);
    }

    private void injectDependencies() {
        final Context applicationContext = getApplicationContext();
        final ApplicationModule applicationModule = new ApplicationModule(applicationContext);

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build()
                .inject(this);
    }

    private void applyTheme() {
        final Theme themeForCampaign = themeProvider.getThemeForCampaign(campaignId);
        if (themeForCampaign.presetId == Theme.PRESET_ID_FANTASY) {
            applyClassicTheme();
        } else if(themeForCampaign.presetId == Theme.PRESET_ID_MODERN) {
            applyModernTheme();
        } else {
            applyCustomTheme(themeForCampaign);
        }
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

    private void applyCustomTheme(@NonNull Theme themeForCampaign) {
        final ViewDataBinding viewDataBinding = getViewDataBinding();
        final ThemeDisplayObject themeDisplayObject = ThemeDisplayObject.fromTheme(themeForCampaign);
        viewDataBinding.setVariable(BR.themeDisplayObject, themeDisplayObject);
        // TODO: this
    }
}
