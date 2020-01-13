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
 * a) directly with a {@link Character}
 *
 * This entry will be delete when removing such a {@link Character}
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = CharacterHeader.FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class CharacterHeader {

    public static final String ID = "character_header_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

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
