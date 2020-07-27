package de.quarian.weaver.campaigns;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignDisplayObject;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.namesets.ConfigureNameSetsCallbacks;
import de.quarian.weaver.schedulers.IoScheduler;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.theming.SetThemeActivity;
import de.quarian.weaver.theming.ThemeColorsParcelable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import static de.quarian.weaver.theming.SetThemeActivity.EXTRA_COLORS;

// TODO: Test Class
public class CampaignEditorActivity extends AppCompatActivity implements CampaignContext, ConfigureNameSetsCallbacks {

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

        @Inject
        public CampaignEditorTabAdapter campaignEditorTabAdapter;

    }

    private static final long INVALID_CAMPAIGN_ID = -2;
    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static final String EXTRA_MODE = "extra.mode";

    public final ActivityDependencies activityDependencies = new ActivityDependencies();
    private Theme theme;
    private Mode mode;
    private long campaignId = INVALID_CAMPAIGN_ID;
    private TabLayout tabLayout;
    private boolean editorExpanded;

    // TODO: init values
    private CombinedCampaignDraft combinedCampaignDraft = new CombinedCampaignDraft();

    public enum Mode {
        EDIT, NEW
    }

    @Override
    public long getCampaignId() {
        return CampaignContext.NEW_CAMPAIGN_CONTEXT;
    }

    // In our app flow this also marks when we finished editing
    @Override
    public void onNameSetsSelected(final List<Long> nameSetIds) {
        combinedCampaignDraft.setNameSetIds(nameSetIds);
        // TODO: store campaign and a) close activity and b) open campaign
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyInjector.get().injectDependencies(this);
        determineMode();
        setSupportActionBar();
        setUpListeners();

        if (mode == Mode.EDIT) {
            queryCampaignAndTheme();
        } else {
            theme = new Theme();
            theme.presetId = Theme.PRESET_ID_MODERN;
        }
    }

    private void determineMode() {
        final String modeString = getIntent().getStringExtra(EXTRA_MODE);
        mode = Mode.valueOf(modeString);
        if(mode == Mode.NEW) {
            // TODO: init with values
            //final ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_campaign);
            //viewDataBinding.setVariable(, );
        } else {
            requireId();
        }
        setContentView(R.layout.activity_edit_campaign);
        setUpTabs();
    }

    private void setSupportActionBar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void requireId() {
        campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
        if (activityDependencies.errorHandler != null) {
            activityDependencies.errorHandler.requireOrFinish(() -> campaignId != INVALID_CAMPAIGN_ID, R.string.activity_edit_campaign_invalid_id_error_title, R.string.activity_edit_campaign_invalid_id_error_text);
        } else {
            finish(); // Should basically never happen
        }
    }

    private void setUpTabs() {
        tabLayout = findViewById(R.id.edit_campaign_tab_layout);
        final ViewPager2 viewPager = findViewById(R.id.edit_campaign_view_pager);
        final TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.activity_edit_campaign_synopsis_tab);
            } else {
                tab.setText(R.string.activity_edit_campaign_name_sets_tab);
            }
        });

        viewPager.setAdapter(activityDependencies.campaignEditorTabAdapter);
        tabLayout.addOnTabSelectedListener(activityDependencies.campaignEditorTabAdapter);
        tabLayoutMediator.attach();
    }

    private void setUpListeners() {
        setUpSetThemeButton();
    }

    private void setUpSetThemeButton() {
        final View setThemeButton = findViewById(R.id.edit_campaign_screen_set_theme_button);
        if (mode == Mode.NEW) {
            setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().setTheme(this, SetThemeActivity.NEW_CAMPAIGN_ID));
        } else {
            setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().setTheme(this, this.campaignId));
        }
    }

    private void queryCampaignAndTheme() {
        final Disposable disposable = Observable.just(activityDependencies.campaignService)
                .subscribeOn(activityDependencies.io)
                .subscribe((campaignService) -> {
                    final CampaignDisplayObject campaignDisplayObject = campaignService.readCampaign(campaignId);
                    final long campaignId = campaignDisplayObject.getCampaignId();
                    theme = campaignService.readThemeForCampaign(campaignId);
                    // TODO: implement (post delayed?), data-binding, refactor dependencies into subclasses
                });
        activityDependencies.globalHandler.postDelayed(disposable::dispose, 500L);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SetThemeActivity.REQUEST_CODE_EDIT_THEME && data != null) {
            final ThemeColorsParcelable themeColorsParcelable = data.getParcelableExtra(EXTRA_COLORS);
            combinedCampaignDraft.setThemeColorsParcelable(themeColorsParcelable);
        }
    }

    public Mode getMode() {
        return mode;
    }

    public void onSynopsisFABClicked(final View view) {
        if (editorExpanded) {
            collapseEditor();
        } else {
            selectNameTab();
        }
    }

    private void selectNameTab() {
        final TabLayout.Tab nameSelectionTab = tabLayout.getTabAt(1);
        tabLayout.selectTab(nameSelectionTab);
    }

    @Override
    public void onBackPressed() {
        if (tabLayout.getSelectedTabPosition() == 1) {
            final TabLayout.Tab synopsisTab = tabLayout.getTabAt(0);
            tabLayout.selectTab(synopsisTab);
        } else {
            if (editorExpanded) {
                collapseEditor();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void expandEditor() {
        final ExpandEditorEvent expandEditorEvent = new ExpandEditorEvent();
        EventBus.getDefault().post(expandEditorEvent);
        editorExpanded = true;
    }

    public void collapseEditor() {
        final CollapseEditorEvent collapseEditorEvent = new CollapseEditorEvent();
        EventBus.getDefault().post(collapseEditorEvent);
        editorExpanded = false;
    }
}
