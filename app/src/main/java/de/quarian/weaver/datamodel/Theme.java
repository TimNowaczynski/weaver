package de.quarian.weaver.datamodel;

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
    public int actionColorA;

    @ColumnInfo(name = "action_color_r")
    public int actionColorR;

    @ColumnInfo(name = "action_color_g")
    public int actionColorG;

    @ColumnInfo(name = "action_color_b")
    public int actionColorB;

    @ColumnInfo(name = "screen_background_color_a")
    public int screenBackgroundColorA;

    @ColumnInfo(name = "screen_background_color_r")
    public int screenBackgroundColorR;

    @ColumnInfo(name = "screen_background_color_g")
    public int screenBackgroundColorG;

    @ColumnInfo(name = "screen_background_color_b")
    public int screenBackgroundColorB;

    @ColumnInfo(name = "item_background_color_a")
    public int itemBackgroundColorA;

    @ColumnInfo(name = "item_background_color_r")
    public int itemBackgroundColorR;

    @ColumnInfo(name = "item_background_color_g")
    public int itemBackgroundColorG;

    @ColumnInfo(name = "item_background_color_b")
    public int itemBackgroundColorB;

    @ColumnInfo(name = "background_font_color_a")
    public int backgroundFontColorA;

    @ColumnInfo(name = "background_font_color_r")
    public int backgroundFontColorR;

    @ColumnInfo(name = "background_font_color_g")
    public int backgroundFontColorG;

    @ColumnInfo(name = "background_font_color_b")
    public int backgroundFontColorB;

    @ColumnInfo(name = "item_font_color_a")
    public int itemFontColorA;

    @ColumnInfo(name = "item_font_color_r")
    public int itemFontColorR;

    @ColumnInfo(name = "item_font_color_g")
    public int itemFontColorG;

    @ColumnInfo(name = "item_font_color_b")
    public int itemFontColorB;

}
