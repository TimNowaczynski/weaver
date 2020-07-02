package de.quarian.weaver.campaigns;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.schedulers.IoScheduler;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.theming.SetThemeActivity;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

// TODO: Test Class
public class CampaignEditorActivity extends AppCompatActivity {

    public static class ActivityDependencies {

        @Inject
        @Nullable
        public ActivityPreconditionErrorHandler errorHandler;

        @Inject
        public CampaignService campaignService;

        @IoScheduler
        @Inject
        public Scheduler io;

        @Inject
        @GlobalHandler
        public Handler globalHandler;

    }

    private static final long INVALID_CAMPAIGN_ID = -2;
    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static final String EXTRA_MODE = "extra.mode";

    public final ActivityDependencies activityDependencies = new ActivityDependencies();
    private Mode mode;
    private long campaignId = INVALID_CAMPAIGN_ID;

    public enum Mode {
        EDIT, NEW
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyInjector.get().injectDependencies(this);
        determineMode();
        setUpListeners();

        if (mode == Mode.EDIT) {
            queryCampaign();
        }
    }

    private void determineMode() {
        final String modeString = getIntent().getStringExtra(EXTRA_MODE);
        mode = Mode.valueOf(modeString);
        if(mode == Mode.NEW) {
            setTitle(R.string.activity_title_add_campaign_screen);
            //final ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_campaign);
            //viewDataBinding.setVariable(, );
            setContentView(R.layout.activity_edit_campaign);
        } else {
            //TODO: init with values (don't forget the title here as well, use the subtitle of the Toolbar)
            requireId();
            setTitle(R.string.activity_title_edit_campaign_screen);
            setContentView(R.layout.activity_edit_campaign);
        }
    }

    private void requireId() {
        campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
        if (activityDependencies.errorHandler != null) {
            activityDependencies.errorHandler.requireOrFinish(() -> campaignId != INVALID_CAMPAIGN_ID, R.string.activity_edit_campaign_invalid_id_error_title, R.string.activity_edit_campaign_invalid_id_error_text);
        } else {
            finish(); // Should basically never happen
        }
    }

    private void setUpListeners() {
        setUpSetThemeButton();
        setUpConfigureNameSetsButton();
    }

    private void setUpSetThemeButton() {
        final View setThemeButton = findViewById(R.id.set_theme_button_dummy);
        if (mode == Mode.NEW) {
            setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().setTheme(this, SetThemeActivity.NEW_CAMPAIGN_ID));
        } else {
            setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().setTheme(this, this.campaignId));
        }
    }

    private void setUpConfigureNameSetsButton() {
        final View setThemeButton = findViewById(R.id.configure_name_sets_button_dummy);
        setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().configureNameSets(this, this.campaignId));
    }

    private void queryCampaign() {
        final Disposable disposable = Observable.just(activityDependencies.campaignService)
                .subscribeOn(activityDependencies.io)
                .subscribe((campaignService) -> {
                    final CampaignListDisplayObject campaignListDisplayObject = campaignService.readCampaign(campaignId);
                    // TODO: implement (post delayed?), data-binding, refactor dependencies into subclasses
                });
        activityDependencies.globalHandler.postDelayed(disposable::dispose, 500L);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        TODO: In case of new campaigns:
        TODO: Set Theme needs to call setResult(code, intent); with intent = colors
            and then we need to grab those values here to insert both theme and
            campaign into database
         */
    }
}
