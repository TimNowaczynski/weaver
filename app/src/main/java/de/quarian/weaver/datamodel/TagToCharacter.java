package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * n:1
 * Links multiple {@link Tag}s with a {@link Character}
 *
 * When deleting an associated {@link Tag} this entry will be removed as well
 * When deleting an associated {@link Character} this entry will be removed as well
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class TagToCharacter {

    public static final String TAG_ID = "fk_tag_id";
    public static final String CHARACTER_ID = "fk_character_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_to_character_id")
    public long id;

    @ColumnInfo(name = TAG_ID)
    @ForeignKey(entity = Tag.class,
            parentColumns = Tag.ID,
            childColumns = TAG_ID,
            onDelete = ForeignKey.CASCADE)
    public long tagId;

    @ColumnInfo(name = CHARACTER_ID)
    @ForeignKey(entity = Character.class,
            parentColumns = Character.ID,
            childColumns = CHARACTER_ID,
            onDelete = ForeignKey.CASCADE)
    public long characterId;
}
