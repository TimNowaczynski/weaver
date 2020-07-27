package de.quarian.weaver.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;

// This is our place for constants, so I prefer to deactivate WeakerAccess warnings
@SuppressWarnings("WeakerAccess")
public class Utils {

    private Utils() { }

    public static final long ONE_SECOND = 1000L;
    public static final long ONE_MINUTE = ONE_SECOND * 60L;
    public static final long ONE_HOUR = ONE_MINUTE * 60L;
    public static final long ONE_DAY = ONE_HOUR * 24L;
    public static final long ONE_YEAR = ONE_DAY * 365L;

    public static final long ABOUT_A_MONTH = ONE_DAY * 30L;

    public static class ColorConverter {

        @ColorInt
        public int toColorInt(final int colorA, final int colorR, final int colorG, final int colorB) {
            return Color.argb(colorA, colorR, colorG, colorB);
        }

        public int[] toColorARGB(@ColorInt final int color) {
            final int[]colorArgb = new int[4];
            colorArgb[0] = Color.alpha(color);
            colorArgb[1] = Color.red(color);
            colorArgb[2] = Color.green(color);
            colorArgb[3] = Color.blue(color);
            return colorArgb;
        }
    }

    public static class ByteArrayConverter {

        public Byte[] fromPrimitive(final byte[] bytes) {
            final Byte[] convertedBytes = new Byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                convertedBytes[i] = bytes[i];
            }
            return convertedBytes;
        }

    }
}
