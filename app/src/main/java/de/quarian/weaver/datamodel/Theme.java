package de.quarian.weaver.datamodel;

import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class Theme {

    //TODO: put default values for colors for on delete operations?

    public static final String ID = "theme_id";

    public static final int PRESET_ID_CUSTOM = 1;
    public static final int PRESET_ID_FANTASY = 2;
    public static final int PRESET_ID_MODERN = 3;
    // This is somewhat shitty, but there seems to be no way around a string constant
    private static final String DEFAULT_PRESET = "2";

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    public long id;

    // Defaults to a constant, not a database ID
    @ColumnInfo(name = "preset_id", defaultValue = DEFAULT_PRESET, typeAffinity = ColumnInfo.INTEGER)
    public int presetId;

    // Same here: this is a constant, not a database ID
    // TODO: later on we could optionally enable custom fonts by adding an additional table
    @ColumnInfo(name = "font_id", defaultValue = DEFAULT_PRESET, typeAffinity = ColumnInfo.INTEGER)
    public int fontId;

    @Nullable
    @ColumnInfo(name = "banner_background_image")
    public Byte[] bannerBackgroundImage;

    @Nullable
    @ColumnInfo(name = "banner_background_image_type")
    public String bannerBackgroundImageType;

    @ColumnInfo(name = "action_color_a")
    public int actionColorA = 255;

    @ColumnInfo(name = "action_color_r")
    public int actionColorR = Color.red(Color.BLACK);

    @ColumnInfo(name = "action_color_g")
    public int actionColorG = Color.green(Color.BLACK);

    @ColumnInfo(name = "action_color_b")
    public int actionColorB = Color.blue(Color.BLACK);

    @ColumnInfo(name = "screen_background_color_a")
    public int screenBackgroundColorA = 255;

    @ColumnInfo(name = "screen_background_color_r")
    public int screenBackgroundColorR = Color.red(Color.BLACK);

    @ColumnInfo(name = "screen_background_color_g")
    public int screenBackgroundColorG = Color.green(Color.BLACK);

    @ColumnInfo(name = "screen_background_color_b")
    public int screenBackgroundColorB = Color.blue(Color.BLACK);

    @ColumnInfo(name = "item_background_color_a")
    public int itemBackgroundColorA = 255;

    @ColumnInfo(name = "item_background_color_r")
    public int itemBackgroundColorR = Color.red(Color.BLACK);

    @ColumnInfo(name = "item_background_color_g")
    public int itemBackgroundColorG = Color.green(Color.BLACK);

    @ColumnInfo(name = "item_background_color_b")
    public int itemBackgroundColorB = Color.blue(Color.BLACK);

    @ColumnInfo(name = "background_font_color_a")
    public int backgroundFontColorA = 255;

    @ColumnInfo(name = "background_font_color_r")
    public int backgroundFontColorR = Color.red(Color.BLACK);

    @ColumnInfo(name = "background_font_color_g")
    public int backgroundFontColorG = Color.green(Color.BLACK);

    @ColumnInfo(name = "background_font_color_b")
    public int backgroundFontColorB = Color.blue(Color.BLACK);

    @ColumnInfo(name = "item_font_color_a")
    public int itemFontColorA = 255;

    @ColumnInfo(name = "item_font_color_r")
    public int itemFontColorR = Color.red(Color.BLACK);

    @ColumnInfo(name = "item_font_color_g")
    public int itemFontColorG = Color.green(Color.BLACK);

    @ColumnInfo(name = "item_font_color_b")
    public int itemFontColorB = Color.blue(Color.BLACK);

}
