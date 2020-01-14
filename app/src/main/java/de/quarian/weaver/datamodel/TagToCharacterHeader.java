package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.TagToCharacterHeader.FK_CHARACTER_HEADER_ID;

/**
 * [RELATIONS]
 *
 * n:1
 * Links multiple {@link Tag}s with a {@link CharacterHeader}s
 *
 * When deleting an associated {@link Tag} this entry will be removed as well
 * When deleting an associated {@link CharacterHeader} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Tag.class,
                parentColumns = Tag.ID,
                childColumns = TagToCharacterHeader.FK_TAG_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = CharacterHeader.class,
                parentColumns = CharacterHeader.ID,
                childColumns = FK_CHARACTER_HEADER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class TagToCharacterHeader {

    public static final String FK_TAG_ID = "fk_tag_id";
    public static final String FK_CHARACTER_HEADER_ID = "fk_character_header_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_TAG_ID, index = true)
    public long tagId;

    @ColumnInfo(name = FK_CHARACTER_HEADER_ID, index = true)
    public long characterHeaderId;
}
