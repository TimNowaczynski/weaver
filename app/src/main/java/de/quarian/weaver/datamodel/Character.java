package de.quarian.weaver.datamodel;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * 1:1
 * Links a {@link Character} with a {@link Campaign}
 *
 * When deleting an associated {@link Campaign} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Campaign.class,
                parentColumns = Campaign.ID,
                childColumns = Character.FK_CAMPAIGN_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = CharacterName.class,
                parentColumns = CharacterName.ID,
                childColumns = Character.FK_CHARACTER_NAME_ID,
                onDelete = ForeignKey.CASCADE)
})
public class Character {

    public static final String ID = "character_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";
    public static final String FK_CHARACTER_NAME_ID = "fk_character_name_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CAMPAIGN_ID, index = true)
    public long campaignId;

    @ColumnInfo(name = FK_CHARACTER_NAME_ID, index = true)
    public long characterNameId;

    @ColumnInfo(name = "creation_date_millis")
    public long creationDateMillis;

    @ColumnInfo(name = "edit_date_millis")
    public long editDateMillis;

    @Nullable
    @ColumnInfo(name = "avatar_image_type")
    public String avatarImageType;

    @ColumnInfo(name = "avatar")
    public byte[] avatar;

    @Nullable
    @ColumnInfo(name = "role")
    public String role;

    @Nullable
    @ColumnInfo(name = "state")
    public String state;

    @Nullable
    @ColumnInfo(name = "age")
    public String age;

    @Nullable
    @ColumnInfo(name = "looks")
    public String looks;

    @Nullable
    @ColumnInfo(name = "background")
    public String background;

    @Nullable
    @ColumnInfo(name = "miscellaneous")
    public String miscellaneous;
}
