package de.quarian.weaver;

import android.graphics.Color;

import androidx.annotation.ColorInt;

public class Utils {

    private Utils() { }

    public static int[] toColorARGB(@ColorInt final int color) {
        final int[]colorArgb = new int[4];
        colorArgb[0] = Color.alpha(color);
        colorArgb[1] = Color.red(color);
        colorArgb[2] = Color.green(color);
        colorArgb[3] = Color.blue(color);
        return colorArgb;
    }
}
