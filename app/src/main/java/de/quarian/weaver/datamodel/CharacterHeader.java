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
 * A {@link CharacterHeader} is associated
 * a) directly with a {@link Campaign}
 *
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Campaign.class,
                parentColumns = Campaign.ID,
                childColumns = CharacterHeader.FK_CAMPAIGN_ID,
                onDelete = ForeignKey.CASCADE)
})
public class CharacterHeader {

    public static final String ID = "character_header_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CAMPAIGN_ID, index = true)
    public long campaignId;

    @ColumnInfo(name = "creation_date_millis")
    public long creationDateMillis;

    @ColumnInfo(name = "edit_date_millis")
    public long editDateMillis;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName = "";

    @NonNull
    @ColumnInfo(name = "alias")
    public String alias = "";

    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName = "";

    @NonNull
    @ColumnInfo(name = "race")
    public String race = "";

    @ColumnInfo(name = "gender")
    public int gender = Constants.CharacterGender.OTHER.getValue();

    @Nullable
    @ColumnInfo(name = "small_avatar_image_type")
    public String smallAvatarImageType;

    @ColumnInfo(name = "small_avatar")
    public byte[] smallAvatar;

    @Nullable
    @ColumnInfo(name = "role")
    public String role;

    @Nullable
    @ColumnInfo(name = "state")
    public String state;
}
