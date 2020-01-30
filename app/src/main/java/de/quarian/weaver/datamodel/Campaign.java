package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * * [RELATIONS]
 *
 * A {@link Campaign} is associated
 * a) directly with a {@link RoleplayingSystem},
 * b) also directly with a {@link Theme} and
 * c) with a {@link PlayerCharacter}
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = RoleplayingSystem.class,
                parentColumns = RoleplayingSystem.ID,
                childColumns = Campaign.FK_ROLEPLAYING_SYSTEM_ID,
                onDelete = ForeignKey.RESTRICT),
        @ForeignKey(entity = Theme.class,
                parentColumns = Theme.ID,
                childColumns = Campaign.FK_THEME_ID,
                onDelete = ForeignKey.SET_DEFAULT)
})
public class Campaign {

    public static final String ID = "campaign_id";
    public static final String FK_ROLEPLAYING_SYSTEM_ID = "fk_roleplaying_system_id";
    public static final String FK_THEME_ID = "fk_theme_id";

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_ROLEPLAYING_SYSTEM_ID, index = true)
    public long roleplayingSystemId;

    @ColumnInfo(name = FK_THEME_ID, index = true, defaultValue = "0")
    public long themeId;

    @ColumnInfo(name = "creation_date_millis")
    public long creationDateMillis;

    @ColumnInfo(name = "edit_date_millis")
    public long editDateMillis;

    @ColumnInfo(name = "last_used_date_millis")
    public long lastUsedDataMillis;

    @ColumnInfo(name = "archived")
    public boolean archived;

    @NonNull
    @ColumnInfo(name = "campaign_name")
    public String campaignName = "";

    // TODO remove campaign image and campaign image type
    @Nullable
    @ColumnInfo(name = "campaign_image")
    public byte[] campaignImage;

    @Nullable
    @ColumnInfo(name = "campaign_image_type")
    public String campaignImageType;

    @Nullable
    @ColumnInfo(name = "synopsis")
    public String synopsis;
}
