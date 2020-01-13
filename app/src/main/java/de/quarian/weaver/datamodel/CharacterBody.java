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
 * Links a {@link CharacterBody} with a {@link Campaign}
 *
 * When deleting an associated {@link Character} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = CharacterBody.FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class CharacterBody {

    public static final String ID = "character_body_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CHARACTER_ID, index = true)
    public long characterId;

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
