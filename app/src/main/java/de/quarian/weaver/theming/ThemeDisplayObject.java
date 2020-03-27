package de.quarian.weaver.theming;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;

public class ThemeDisplayObject {

    public int actionColor;
    public Drawable actionDrawable;
    public int backgroundTextColor;
    public int itemTextColor;
    public int itemTextSecondaryColor;
    public int backgroundColor;
    public int itemColor;
    public Drawable itemDrawable;

    public ThemeDisplayObject() {
    }

    public static ThemeDisplayObject fromTheme(@NonNull final Context context, @NonNull final Theme theme) {
        final ThemeDisplayObject themeDisplayObject;

        switch (theme.presetId) {

            case Theme.PRESET_ID_FANTASY: {
                themeDisplayObject = createFantasyThemeDisplayObject(context);
                break;
            }

            case Theme.PRESET_ID_MODERN: {
                themeDisplayObject = createModernThemeDisplayObject(context);
                break;
            }

            case Theme.PRESET_ID_CUSTOM: {
                themeDisplayObject = createFromTheme(context, theme);
                break;
            }

            default: {
                throw new IllegalArgumentException("Preset ID is invalid");
            }
        }
        return themeDisplayObject;
    }

    private static ThemeDisplayObject createFantasyThemeDisplayObject(@NonNull final Context context) {
        final Resources resources = context.getResources();
        final int alphaMask = resources.getColor(R.color.secondaryTextColorAlphaMask);

        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.actionColor = resources.getColor(R.color.fantasySecondaryColor);
        themeDisplayObject.actionDrawable = themeDisplayObject.createActionDrawable();
        themeDisplayObject.backgroundTextColor = resources.getColor(R.color.pitch_black);
        themeDisplayObject.itemTextColor = resources.getColor(R.color.white);
        themeDisplayObject.itemTextSecondaryColor = ColorUtils.setAlphaComponent(themeDisplayObject.itemTextColor, alphaMask);
        themeDisplayObject.backgroundColor = resources.getColor(R.color.fantasyPrimaryColor);
        themeDisplayObject.itemColor = resources.getColor(R.color.fantasyPrimaryDarkColor);
        themeDisplayObject.itemDrawable = themeDisplayObject.createItemDrawable();
        return themeDisplayObject;
    }

    private static ThemeDisplayObject createModernThemeDisplayObject(@NonNull final Context context) {
        final Resources resources = context.getResources();
        final int alphaMask = resources.getColor(R.color.secondaryTextColorAlphaMask);

        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.actionColor = resources.getColor(R.color.modernSecondaryDarkColor);
        themeDisplayObject.actionDrawable = themeDisplayObject.createActionDrawable();
        themeDisplayObject.backgroundTextColor = resources.getColor(R.color.white);
        themeDisplayObject.itemTextColor = resources.getColor(R.color.pitch_black);
        themeDisplayObject.itemTextSecondaryColor = ColorUtils.setAlphaComponent(themeDisplayObject.itemTextColor, alphaMask);
        themeDisplayObject.backgroundColor = resources.getColor(R.color.modernSecondaryColor);
        themeDisplayObject.itemColor = resources.getColor(R.color.modernSecondaryLightColor);
        themeDisplayObject.itemDrawable = themeDisplayObject.createItemDrawable();
        return themeDisplayObject;
    }

    private static ThemeDisplayObject createFromTheme(@NonNull final Context context, @NonNull final Theme theme) {
        final Resources resources = context.getResources();
        final int alphaMask = resources.getColor(R.color.secondaryTextColorAlphaMask);

        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.actionColor = Color.argb(theme.actionColorA, theme.actionColorR, theme.actionColorG, theme.actionColorB);
        themeDisplayObject.actionDrawable = themeDisplayObject.createActionDrawable();
        themeDisplayObject.backgroundTextColor = Color.argb(theme.backgroundFontColorA, theme.backgroundFontColorR, theme.backgroundFontColorG, theme.backgroundFontColorB);
        themeDisplayObject.itemTextColor = Color.argb(theme.itemFontColorA, theme.itemFontColorR, theme.itemFontColorG, theme.itemFontColorB);
        themeDisplayObject.itemTextSecondaryColor = Color.argb(alphaMask, theme.itemFontColorR, theme.itemFontColorG, theme.itemFontColorB);
        themeDisplayObject.backgroundColor = Color.argb(theme.screenBackgroundColorA, theme.screenBackgroundColorR, theme.screenBackgroundColorG, theme.screenBackgroundColorB);
        themeDisplayObject.itemColor = Color.argb(theme.itemBackgroundColorA, theme.itemBackgroundColorR, theme.itemBackgroundColorG, theme.itemBackgroundColorB);
        themeDisplayObject.itemDrawable = themeDisplayObject.createItemDrawable();
        return themeDisplayObject;
    }

    public void refresh() {
        itemDrawable = createItemDrawable();
        actionDrawable = createActionDrawable();
    }

    private Drawable createItemDrawable() {
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(8f);
        gradientDrawable.setColor(itemColor);
        return gradientDrawable;
    }

    private Drawable createActionDrawable() {
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(8f);
        gradientDrawable.setColor(actionColor);
        return gradientDrawable;
    }
}
