package de.quarian.weaver.theming;

import android.app.Activity;
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
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.di.ActivityModule;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerActivityComponent;
import de.quarian.weaver.di.GlobalHandler;

// TODO: presets, set preview, apply
public class SetThemeActivity extends WeaverThemedActivity {

    public static class ActivityDependencies {

        @GlobalHandler
        @Inject
        public Handler handler;

        @Inject
        public ThemeProvider themeProvider;

    }

    private final ActivityDependencies activityDependencies = new ActivityDependencies();

    @Nullable
    private Theme theme;

    @Nullable
    private ColorPicker actionColorPicker;

    @Nullable
    private ColorPicker backgroundColorPicker;

    @Nullable
    private ColorPicker backgroundTextColorPicker;

    @Nullable
    private ColorPicker itemBackgroundColorPicker;

    @Nullable
    private ColorPicker itemTextColorPicker;

    private int actionColor;
    private int screenBackgroundColor;
    private int screenBackgroundTextColor;
    private int itemBackgroundColor;
    private int itemTextColor;

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_theme;
    }

    @Override
    public Activity getTargetActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();

        AsyncTask.execute(() -> {
            theme = activityDependencies.themeProvider.getThemeForCampaign(campaignId);
            activityDependencies.handler.post(() -> {
                initializeColorPickers();
                setUpListeners();
            });
        });
    }

    private void injectDependencies() {
        DaggerActivityComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this.activityDependencies);
    }

    private void initializeColorPickers() {
        if (theme != null) {
            initializeActionColorPicker(theme);
            initializeScreenBackgroundColorPicker(theme);
            initializeBackgroundTextColorPicker(theme);
            initializeItemBackgroundColorPicker(theme);
            initializeItemTextColorPicker(theme);
        } else {
            throw new IllegalStateException("Theme should never be null");
        }
    }

    private int getAlpha(int alphaFromDb) {
        // This is a quick work-around, it makes no sense to define an invisible color
        // and therefore it's safe to assume it's a newly initialized theme
        if (alphaFromDb == 0) {
            alphaFromDb = 255;
        }
        return alphaFromDb;
    }

    private void initializeActionColorPicker(@NonNull Theme theme) {
        final int alpha = getAlpha(theme.actionColorA);
        actionColorPicker = prepareColorPicker(alpha, theme.actionColorR, theme.actionColorG, theme.actionColorB);
        actionColorPicker.setCallback((@ColorInt int colorInt) -> {
            final FrameLayout actionColorFrame = findViewById(R.id.activity_set_theme_action_color_preview);
            final View preview = actionColorFrame.getChildAt(0);
            preview.setBackgroundColor(colorInt);
            actionColor = colorInt;
        });
    }

    private ColorPicker prepareColorPicker(final int a, final int r, final int g, final int b) {
        final ColorPicker colorPicker = new ColorPicker(this, a, r, g, b);
        colorPicker.enableAutoClose();
        return colorPicker;
    }

    private void initializeScreenBackgroundColorPicker(@NonNull Theme theme) {
        final int alpha = getAlpha(theme.screenBackgroundColorA);
        backgroundColorPicker = prepareColorPicker(alpha, theme.screenBackgroundColorR, theme.screenBackgroundColorG, theme.screenBackgroundColorB);
        backgroundColorPicker.setCallback((@ColorInt int colorInt) -> {
            final FrameLayout backgroundColorFrame = findViewById(R.id.activity_set_theme_background_color_preview);
            final View preview = backgroundColorFrame.getChildAt(0);
            preview.setBackgroundColor(colorInt);
            screenBackgroundColor = colorInt;

            final FrameLayout backgroundTextColorFrame = findViewById(R.id.activity_set_theme_background_text_color_preview);
            final TextView textView = (TextView) backgroundTextColorFrame.getChildAt(0);
            textView.setBackgroundColor(colorInt);
        });
    }

    private void initializeBackgroundTextColorPicker(@NonNull Theme theme) {
        final int alpha = getAlpha(theme.backgroundFontColorA);
        backgroundTextColorPicker = prepareColorPicker(alpha, theme.backgroundFontColorR, theme.backgroundFontColorG, theme.backgroundFontColorB);
        backgroundTextColorPicker.setCallback((@ColorInt int colorInt) -> {
            final FrameLayout frameLayout = findViewById(R.id.activity_set_theme_background_text_color_preview);
            final TextView preview = (TextView) frameLayout.getChildAt(0);
            preview.setBackgroundColor(screenBackgroundColor);
            preview.setTextColor(colorInt);
            screenBackgroundTextColor = colorInt;
        });
    }

    private void initializeItemBackgroundColorPicker(@NonNull Theme theme) {
        final int alpha = getAlpha(theme.itemBackgroundColorA);
        itemBackgroundColorPicker = prepareColorPicker(alpha, theme.itemBackgroundColorR, theme.itemBackgroundColorG, theme.itemBackgroundColorB);
        itemBackgroundColorPicker.setCallback((@ColorInt int colorInt) -> {
            final FrameLayout frameLayout = findViewById(R.id.activity_set_theme_item_background_color_preview);
            final View preview = frameLayout.getChildAt(0);
            preview.setBackgroundColor(colorInt);
            itemBackgroundColor = colorInt;

            final FrameLayout itemTextColorFrame = findViewById(R.id.activity_set_theme_item_text_color_preview);
            final TextView textView = (TextView) itemTextColorFrame.getChildAt(0);
            textView.setBackgroundColor(colorInt);
        });
    }

    private void initializeItemTextColorPicker(@NonNull Theme theme) {
        final int alpha = getAlpha(theme.itemFontColorA);
        itemTextColorPicker = prepareColorPicker(alpha, theme.itemFontColorR, theme.itemFontColorG, theme.itemFontColorB);
        itemTextColorPicker.setCallback((@ColorInt int colorInt) -> {
            final FrameLayout frameLayout = findViewById(R.id.activity_set_theme_item_text_color_preview);
            final TextView preview = (TextView) frameLayout.getChildAt(0);
            preview.setBackgroundColor(itemBackgroundColor);
            preview.setTextColor(colorInt);
            itemTextColor = colorInt;
        });
    }

    private void setUpListeners() {
        final View actionColorPicker = findViewById(R.id.activity_set_theme_action_color_preview);
        actionColorPicker.setOnClickListener((view) -> {
            if (this.actionColorPicker != null) {
                this.actionColorPicker.show();
            }
        });

        final View backgroundColorPicker = findViewById(R.id.activity_set_theme_background_color_preview);
        backgroundColorPicker.setOnClickListener((view) -> {
            if (this.backgroundColorPicker != null) {
                this.backgroundColorPicker.show();
            }
        });

        final View backgroundTextColorPicker = findViewById(R.id.activity_set_theme_background_text_color_preview);
        backgroundTextColorPicker.setOnClickListener((view) -> {
            if (this.backgroundTextColorPicker != null) {
                this.backgroundTextColorPicker.show();
            }
        });

        final View itemBackgroundColorPicker = findViewById(R.id.activity_set_theme_item_background_color_preview);
        itemBackgroundColorPicker.setOnClickListener((view) -> {
            if (this.itemBackgroundColorPicker != null) {
                this.itemBackgroundColorPicker.show();
            }
        });

        final View itemTextColorPicker = findViewById(R.id.activity_set_theme_item_text_color_preview);
        itemTextColorPicker.setOnClickListener((view) -> {
            if (this.itemTextColorPicker != null) {
                this.itemTextColorPicker.show();
            }
        });

        // TODO: presets
        // TODO: preview
        // TODO: confirm action/button
    }
}
