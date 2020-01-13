package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.TagToCharacter.FK_CHARACTER_ID;

/**
 * [RELATIONS]
 *
 * n:1
 * Links multiple {@link Tag}s with a {@link Character}
 *
 * When deleting an associated {@link Tag} this entry will be removed as well
 * When deleting an associated {@link Character} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Tag.class,
                parentColumns = Tag.ID,
                childColumns = TagToCharacter.FK_TAG_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class TagToCharacter {

    public static final String FK_TAG_ID = "fk_tag_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_TAG_ID, index = true)
    public long tagId;

    @ColumnInfo(name = FK_CHARACTER_ID, index = true)
    public long characterId;
}
