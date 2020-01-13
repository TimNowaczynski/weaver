package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * * [RELATIONS]
 *
 * A {@link CharacterTeaser} is associated
 * a) directly with a {@link CharacterName} and
 * b) directly with a {@link Character} and
 * c) also directly with a {@link Campaign}
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = CharacterName.class,
                parentColumns = CharacterName.ID,
                childColumns = CharacterTeaser.FK_CHARACTER_NAME_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = CharacterTeaser.FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Campaign.class,
                parentColumns = Campaign.ID,
                childColumns = CharacterTeaser.FK_CAMPAIGN_ID,
                onDelete = ForeignKey.CASCADE)
})
//TODO: Test out if character teaser can extend the full character class
public class CharacterTeaser {

    public static final String FK_CHARACTER_NAME_ID = "fk_character_name_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";

    @ColumnInfo(name = FK_CAMPAIGN_ID, index = true)
    public long campaignId;

    @ColumnInfo(name = FK_CHARACTER_NAME_ID, index = true)
    public long characterNameId;

    @ColumnInfo(name = FK_CHARACTER_ID, index = true)
    public long characterId;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName = "";

    @NonNull
    @ColumnInfo(name = "alias")
    public String alias = "";

    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName = "";

    @Nullable
    @ColumnInfo(name = "small_avatar_image_type")
    public String smallAvatarImageType;

    @ColumnInfo(name = "small_avatar")
    public byte[] smallAvatar;
}
