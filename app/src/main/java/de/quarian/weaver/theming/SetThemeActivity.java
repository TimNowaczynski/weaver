package de.quarian.weaver.theming;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import javax.inject.Inject;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.util.GenericDialogBuilder;

//TODO: Somehow hand back theme values for new campaigns
public class SetThemeActivity extends WeaverActivity {

    public static final int REQUEST_CODE_EDIT_THEME = 1;
    public static final long NEW_CAMPAIGN_ID = -2L;

    public static final String EXTRA_ACTION_COLOR = "extra.actionColor";
    public static final String EXTRA_BACKGROUND_COLOR = "extra.backgroundColor";
    public static final String EXTRA_BACKGROUND_TEXT_COLOR = "extra.backgroundTextColor";
    public static final String EXTRA_ITEM_COLOR = "extra.itemColor";
    public static final String EXTRA_ITEM_TEXT_COLOR = "extra.itemTextColor";

    private static final long INVALID_CAMPAIGN_ID = -1L;

    public static class ActivityDependencies {

        @GlobalHandler
        @Inject
        public Handler handler;

        @Inject
        public ThemeProvider themeProvider;

        @Inject
        public GenericDialogBuilder.Factory dialogBuilderFactory;

    }

    public final ActivityDependencies activityDependencies = new ActivityDependencies();

    @Nullable
    private Theme theme;

    @Nullable
    private ColorPicker actionColorPicker;

    @Nullable
    private ColorPicker screenBackgroundColorPicker;

    @Nullable
    private ColorPicker backgroundTextColorPicker;

    @Nullable
    private ColorPicker itemBackgroundColorPicker;

    @Nullable
    private ColorPicker itemTextColorPicker;

    private ThemeEditorValues themeEditorValues;

    // TODO: Figure out what I meant by this:
    //  Handle Management of Roleplaying Systems (how complex should it be?)
    //  Is this comment in the right place?

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_theme);
        configureSupportActionBar();
        DependencyInjector.get().injectDependencies(this);
    }

    private void configureSupportActionBar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final View confirmSetThemeButton = toolbar.findViewById(R.id.activity_set_theme_action_bar_confirm_button);
        confirmSetThemeButton.setOnClickListener((view) -> {
            final Intent colors = retrieveColors();
            setResult(RESULT_OK, colors);
            finish();
        });
    }

    @NonNull
    private Intent retrieveColors() {
        final Intent colors = new Intent();
        colors.putExtra(EXTRA_ACTION_COLOR, themeEditorValues.getActionColor());
        colors.putExtra(EXTRA_BACKGROUND_COLOR, themeEditorValues.getScreenBackgroundColor());
        colors.putExtra(EXTRA_BACKGROUND_TEXT_COLOR, themeEditorValues.getBackgroundTextColor());
        colors.putExtra(EXTRA_ITEM_COLOR, themeEditorValues.getItemBackgroundColor());
        colors.putExtra(EXTRA_ITEM_TEXT_COLOR, themeEditorValues.getItemTextColor());
        return colors;
    }

    @Override
    public void onDependenciesInjected() {
        AsyncTask.execute(() -> {
            final long campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
            if (campaignId == INVALID_CAMPAIGN_ID) {
                throw new IllegalArgumentException("Campaign ID was invalid");
            }

            if (campaignId != NEW_CAMPAIGN_ID) {
                theme = activityDependencies.themeProvider.getThemeForCampaign(campaignId);
            }
            activityDependencies.handler.post(this::initializeCurrentTheme);
        });
    }

    private void initializeCurrentTheme() {
        themeEditorValues = new ThemeEditorValues(theme);

        initializeActionColorPicker(theme);
        initializeScreenBackgroundColorPicker(theme);
        initializeBackgroundTextColorPicker(theme);
        initializeItemBackgroundColorPicker(theme);
        initializeItemTextColorPicker(theme);
    }

    private int getAlpha(int alphaFromDb) {
        // This is a quick work-around, it makes no sense to define an invisible color
        // and therefore it's safe to assume it's a newly initialized theme
        if (alphaFromDb == 0) {
            alphaFromDb = 255;
        }
        return alphaFromDb;
    }

    private void initializeActionColorPicker(@Nullable Theme theme) {

        if (theme == null) {
            actionColorPicker = prepareColorPicker();
        } else {
            final int alpha = getAlpha(theme.actionColorA);
            actionColorPicker = prepareColorPicker(alpha, theme.actionColorR, theme.actionColorG, theme.actionColorB);
        }

        actionColorPicker.setCallback((@ColorInt int colorInt) -> {
            setActionColorPreview(colorInt);
            themeEditorValues.setActionColor(colorInt);
            notifyChildFragmentChanges(ThemeColorCategory.actionColor, colorInt);
        });
    }

    private void setActionColorPreview(@ColorInt final int colorInt) {
        final FrameLayout actionColorFrame = findViewById(R.id.activity_set_theme_action_color_preview);
        final View view = actionColorFrame.getChildAt(0);
        view.setBackgroundColor(colorInt);
    }

    private void notifyChildFragmentChanges(final ThemeColorCategory themeColorCategory, @ColorInt final int colorInt) {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final ThemePreviewFragment themePreviewFragment = (ThemePreviewFragment) supportFragmentManager.findFragmentById(R.id.activity_set_theme_integrated_preview_fragment);
        if (themePreviewFragment != null) {
            switch (themeColorCategory) {
                case actionColor: {
                    themePreviewFragment.setActionColor(colorInt);
                    break;
                }
                case backgroundColor: {
                    themePreviewFragment.setBackgroundColor(colorInt);
                    break;
                }
                case backgroundTextColor: {
                    themePreviewFragment.setBackgroundTextColor(colorInt);
                    break;
                }
                case itemColor: {
                    themePreviewFragment.setItemColor(colorInt);
                    break;
                }
                case itemTextColor: {
                    themePreviewFragment.setItemTextColor(colorInt);
                    break;
                }
            }
            themePreviewFragment.refreshContent();
        }
    }

    private ColorPicker prepareColorPicker() {
        return prepareColorPicker(255, 0, 0, 0);
    }

    private ColorPicker prepareColorPicker(final int a, final int r, final int g, final int b) {
        final ColorPicker colorPicker = new ColorPicker(this, a, r, g, b);
        colorPicker.enableAutoClose();
        return colorPicker;
    }

    private void initializeScreenBackgroundColorPicker(@Nullable Theme theme) {

        if (theme == null) {
            screenBackgroundColorPicker = prepareColorPicker();
        } else {
            final int alpha = getAlpha(theme.screenBackgroundColorA);
            screenBackgroundColorPicker = prepareColorPicker(alpha, theme.screenBackgroundColorR, theme.screenBackgroundColorG, theme.screenBackgroundColorB);
        }

        screenBackgroundColorPicker.setCallback((@ColorInt int colorInt) -> {
            this.themeEditorValues.setScreenBackgroundColor(colorInt);

            final FrameLayout backgroundColorFrame = findViewById(R.id.activity_set_theme_background_color_preview);
            final View preview = backgroundColorFrame.getChildAt(0);
            preview.setBackgroundColor(colorInt);

            final FrameLayout backgroundTextColorFrame = findViewById(R.id.activity_set_theme_background_text_color_preview);
            final TextView textView = (TextView) backgroundTextColorFrame.getChildAt(0);
            textView.setBackgroundColor(colorInt);

            notifyChildFragmentChanges(ThemeColorCategory.backgroundColor, colorInt);
        });
    }

    private void initializeBackgroundTextColorPicker(@Nullable Theme theme) {

        if (theme == null) {
            backgroundTextColorPicker = prepareColorPicker();
        } else {
            final int alpha = getAlpha(theme.backgroundFontColorA);
            backgroundTextColorPicker = prepareColorPicker(alpha, theme.backgroundFontColorR, theme.backgroundFontColorG, theme.backgroundFontColorB);
        }

        backgroundTextColorPicker.setCallback((@ColorInt int colorInt) -> {
            this.themeEditorValues.setBackgroundTextColor(colorInt);

            final FrameLayout frameLayout = findViewById(R.id.activity_set_theme_background_text_color_preview);
            final TextView preview = (TextView) frameLayout.getChildAt(0);
            preview.setBackgroundColor(this.themeEditorValues.getScreenBackgroundColor());
            preview.setTextColor(colorInt);

            notifyChildFragmentChanges(ThemeColorCategory.backgroundTextColor, colorInt);
        });
    }

    private void initializeItemBackgroundColorPicker(@Nullable Theme theme) {

        if (theme == null) {
            itemBackgroundColorPicker = prepareColorPicker();
        } else {
            final int alpha = getAlpha(theme.itemBackgroundColorA);
            itemBackgroundColorPicker = prepareColorPicker(alpha, theme.itemBackgroundColorR, theme.itemBackgroundColorG, theme.itemBackgroundColorB);
        }

        itemBackgroundColorPicker.setCallback((@ColorInt int colorInt) -> {
            this.themeEditorValues.setItemBackgroundColor(colorInt);

            final FrameLayout frameLayout = findViewById(R.id.activity_set_theme_item_background_color_preview);
            final View preview = frameLayout.getChildAt(0);
            preview.setBackgroundColor(colorInt);

            final FrameLayout itemTextColorFrame = findViewById(R.id.activity_set_theme_item_text_color_preview);
            final TextView textView = (TextView) itemTextColorFrame.getChildAt(0);
            textView.setBackgroundColor(colorInt);
            notifyChildFragmentChanges(ThemeColorCategory.itemColor, colorInt);
        });
    }

    private void initializeItemTextColorPicker(@Nullable Theme theme) {

        if (theme == null) {
            itemTextColorPicker = prepareColorPicker();
        } else {
            final int alpha = getAlpha(theme.itemFontColorA);
            itemTextColorPicker = prepareColorPicker(alpha, theme.itemFontColorR, theme.itemFontColorG, theme.itemFontColorB);
        }

        itemTextColorPicker.setCallback((@ColorInt int colorInt) -> {
            this.themeEditorValues.setItemTextColor(colorInt);

            final FrameLayout frameLayout = findViewById(R.id.activity_set_theme_item_text_color_preview);
            final TextView preview = (TextView) frameLayout.getChildAt(0);
            preview.setBackgroundColor(themeEditorValues.getItemBackgroundColor());
            preview.setTextColor(colorInt);

            notifyChildFragmentChanges(ThemeColorCategory.itemTextColor, colorInt);
        });
    }

    public void pickActionColor(final View view) {
        if (this.actionColorPicker != null) {
            this.actionColorPicker.show();
        }
    }

    public void pickBackgroundColor(final View view) {
        if (this.screenBackgroundColorPicker != null) {
            this.screenBackgroundColorPicker.show();
        }
    }

    public void pickBackgroundTextColor(final View view) {
        if (this.backgroundTextColorPicker != null) {
            this.backgroundTextColorPicker.show();
        }
    }

    public void pickItemBackgroundColor(final View view) {
        if (this.itemBackgroundColorPicker != null) {
            this.itemBackgroundColorPicker.show();
        }
    }

    public void pickItemTextColor(final View view) {
        if (this.itemTextColorPicker != null) {
            this.itemTextColorPicker.show();
        }
    }

    public void applyFantasyPreset(final View view) {

    }

    public void applyModernPreset(final View view) {

    }

    // TODO: presets

    @Override
    public void onBackPressed() {
        if (themeEditorValues.hasPendingChanges()) {
            final GenericDialogBuilder builder = this.activityDependencies.dialogBuilderFactory.getBuilder();
            builder.setMessage(R.string.activity_set_theme_discard_changes)
                    .setPrimaryButtonText(R.string.activity_set_theme_discard_changes_confirm)
                    .setPrimaryButtonAction((view) -> super.onBackPressed())
                    .setCancelable(true)
                    .showDialog(this);
        } else {
            super.onBackPressed();
        }
    }

    // TODO: IN CAMPAIGN EDITOR: change FAB action from confirm to change theme if no theme is defined
}
