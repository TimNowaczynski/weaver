package de.quarian.weaver.theming;

import android.graphics.Color;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Theme;

public class ThemeDisplayObject {

    // TODO: Extend

    public int backgroundColor;

    public static ThemeDisplayObject fromTheme(@NonNull Theme theme) {
        final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
        themeDisplayObject.backgroundColor = Color.argb(theme.screenBackgroundColorA, theme.screenBackgroundColorR, theme.screenBackgroundColorG, theme.screenBackgroundColorB);
        return themeDisplayObject;
    }
}
