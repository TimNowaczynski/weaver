package de.quarian.weaver.theming;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Theme;

@SuppressWarnings("WeakerAccess")
public class ThemeDisplayObject {

    public int backgroundTextColor;
    public int itemTextColor;
    public int backgroundColor;
    public int itemColor;

    // TODO: reconsider if we actually have a use for that
    public Drawable itemDrawable;

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
                themeDisplayObject = createFromTheme(theme);
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
        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.backgroundTextColor = resources.getColor(R.color.pitch_black);
        themeDisplayObject.itemTextColor = resources.getColor(R.color.white);
        themeDisplayObject.backgroundColor = resources.getColor(R.color.fantasyPrimaryColor);
        themeDisplayObject.itemColor = resources.getColor(R.color.fantasyPrimaryDarkColor);
        themeDisplayObject.itemDrawable = themeDisplayObject.createItemDrawable();
        return themeDisplayObject;
    }

    private static ThemeDisplayObject createModernThemeDisplayObject(@NonNull final Context context) {
        final Resources resources = context.getResources();
        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.backgroundTextColor = resources.getColor(R.color.white);
        themeDisplayObject.itemTextColor = resources.getColor(R.color.pitch_black);
        themeDisplayObject.backgroundColor = resources.getColor(R.color.modernSecondaryColor);
        themeDisplayObject.itemColor = resources.getColor(R.color.modernSecondaryLightColor);
        themeDisplayObject.itemDrawable = themeDisplayObject.createItemDrawable();
        return themeDisplayObject;
    }

    private static ThemeDisplayObject createFromTheme(@NonNull final Theme theme) {
        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.backgroundTextColor = Color.argb(theme.backgroundFontColorA, theme.backgroundFontColorR, theme.backgroundFontColorG, theme.backgroundFontColorB);
        themeDisplayObject.itemTextColor = Color.argb(theme.itemFontColorA, theme.itemFontColorR, theme.itemFontColorG, theme.itemFontColorB);
        themeDisplayObject.backgroundColor = Color.argb(theme.screenBackgroundColorA, theme.screenBackgroundColorR, theme.screenBackgroundColorG, theme.screenBackgroundColorB);
        themeDisplayObject.itemColor = Color.argb(theme.itemBackgroundColorA, theme.itemBackgroundColorR, theme.itemBackgroundColorG, theme.itemBackgroundColorB);
        themeDisplayObject.itemDrawable = themeDisplayObject.createItemDrawable();
        return themeDisplayObject;
    }

    private Drawable createItemDrawable() {
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(8f);
        gradientDrawable.setColor(itemColor);
        return gradientDrawable;
    }
}
