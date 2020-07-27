package de.quarian.weaver.theming;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ThemeColorsParcelable implements Parcelable {

    public final int actionColorA;
    public final int actionColorR;
    public final int actionColorG;
    public final int actionColorB;

    public final int screenBackgroundColorA;
    public final int screenBackgroundColorR;
    public final int screenBackgroundColorG;
    public final int screenBackgroundColorB;

    public final int backgroundFontColorA;
    public final int backgroundFontColorR;
    public final int backgroundFontColorG;
    public final int backgroundFontColorB;

    public final int itemBackgroundColorA;
    public final int itemBackgroundColorR;
    public final int itemBackgroundColorG;
    public final int itemBackgroundColorB;

    public final int itemFontColorA;
    public final int itemFontColorR;
    public final int itemFontColorG;
    public final int itemFontColorB;

    // TODO: constructor for presets? or as own class?

    public ThemeColorsParcelable(final int actionColor,
                                 final int screenBackgroundColor,
                                 final int backgroundFontColor,
                                 final int itemBackgroundColor,
                                 final int itemFontColor) {
        actionColorA = Color.alpha(actionColor);
        actionColorR = Color.red(actionColor);
        actionColorG = Color.green(actionColor);
        actionColorB = Color.blue(actionColor);

        screenBackgroundColorA = Color.alpha(screenBackgroundColor);
        screenBackgroundColorR = Color.red(screenBackgroundColor);
        screenBackgroundColorG = Color.green(screenBackgroundColor);
        screenBackgroundColorB = Color.blue(screenBackgroundColor);

        backgroundFontColorA = Color.alpha(backgroundFontColor);
        backgroundFontColorR = Color.red(backgroundFontColor);
        backgroundFontColorG = Color.green(backgroundFontColor);
        backgroundFontColorB = Color.blue(backgroundFontColor);

        itemBackgroundColorA = Color.alpha(itemBackgroundColor);
        itemBackgroundColorR = Color.red(itemBackgroundColor);
        itemBackgroundColorG = Color.green(itemBackgroundColor);
        itemBackgroundColorB = Color.blue(itemBackgroundColor);

        itemFontColorA = Color.alpha(itemFontColor);
        itemFontColorR = Color.red(itemFontColor);
        itemFontColorG = Color.green(itemFontColor);
        itemFontColorB = Color.blue(itemFontColor);
    }

    private ThemeColorsParcelable(@NonNull Parcel parcel) {
        actionColorA = parcel.readInt();
        actionColorR = parcel.readInt();
        actionColorG = parcel.readInt();
        actionColorB = parcel.readInt();

        screenBackgroundColorA = parcel.readInt();
        screenBackgroundColorR = parcel.readInt();
        screenBackgroundColorG = parcel.readInt();
        screenBackgroundColorB = parcel.readInt();

        backgroundFontColorA = parcel.readInt();
        backgroundFontColorR = parcel.readInt();
        backgroundFontColorG = parcel.readInt();
        backgroundFontColorB = parcel.readInt();

        itemBackgroundColorA = parcel.readInt();
        itemBackgroundColorR = parcel.readInt();
        itemBackgroundColorG = parcel.readInt();
        itemBackgroundColorB = parcel.readInt();

        itemFontColorA = parcel.readInt();
        itemFontColorR = parcel.readInt();
        itemFontColorG = parcel.readInt();
        itemFontColorB = parcel.readInt();
    }

    public static final Creator<ThemeColorsParcelable> CREATOR = new Creator<ThemeColorsParcelable>() {

        @Override
        public ThemeColorsParcelable createFromParcel(Parcel in) {
            return new ThemeColorsParcelable(in);
        }

        @Override
        public ThemeColorsParcelable[] newArray(int size) {
            return new ThemeColorsParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actionColorA);
        dest.writeInt(actionColorR);
        dest.writeInt(actionColorG);
        dest.writeInt(actionColorB);

        dest.writeInt(screenBackgroundColorA);
        dest.writeInt(screenBackgroundColorR);
        dest.writeInt(screenBackgroundColorG);
        dest.writeInt(screenBackgroundColorB);

        dest.writeInt(backgroundFontColorA);
        dest.writeInt(backgroundFontColorR);
        dest.writeInt(backgroundFontColorG);
        dest.writeInt(backgroundFontColorB);

        dest.writeInt(itemBackgroundColorA);
        dest.writeInt(itemBackgroundColorR);
        dest.writeInt(itemBackgroundColorG);
        dest.writeInt(itemBackgroundColorB);

        dest.writeInt(itemFontColorA);
        dest.writeInt(itemFontColorR);
        dest.writeInt(itemFontColorG);
        dest.writeInt(itemFontColorB);
    }

}
