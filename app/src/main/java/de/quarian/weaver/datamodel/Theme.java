package de.quarian.weaver.datamodel;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class Theme {

    public static final String ID = "theme_id";

    // This is somewhat shitty, but there seems to be no way around a string constant
    private static final String DEFAULT_PRESET = "1";

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    public long id;

    // Defaults to a constant, not a database ID
    @ColumnInfo(name = "preset_id", defaultValue = DEFAULT_PRESET, typeAffinity = ColumnInfo.INTEGER)
    public int presetId;

    // Same here: this is a constant, not a database ID
    @ColumnInfo(name = "font_id", defaultValue = DEFAULT_PRESET, typeAffinity = ColumnInfo.INTEGER)
    public int fontId;

    @Nullable
    @ColumnInfo(name = "banner_background_image")
    public byte[] banner_background_image;

    @Nullable
    @ColumnInfo(name = "banner_background_image_type")
    public String banner_background_image_type;

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
