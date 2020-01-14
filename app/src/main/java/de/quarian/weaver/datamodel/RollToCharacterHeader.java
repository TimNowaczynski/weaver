package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.RollToCharacterHeader.FK_CHARACTER_HEADER_ID;
/**
 * [RELATIONS]
 *
 * n:1
 * Links multiple {@link Roll}s with a {@link CharacterHeader}s
 *
 * When deleting an associated {@link Roll} this entry will be removed as well
 * When deleting an associated {@link CharacterHeader} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Roll.class,
                parentColumns = Roll.ID,
                childColumns = RollToCharacterHeader.FK_ROLL_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = CharacterHeader.class,
                parentColumns = CharacterHeader.ID,
                childColumns = FK_CHARACTER_HEADER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class RollToCharacterHeader {

    public static final String FK_ROLL_ID = "fk_roll_id";
    public static final String FK_CHARACTER_HEADER_ID = "fk_character_header_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_ROLL_ID, index = true)
    public long rollId;

    @ColumnInfo(name = FK_CHARACTER_HEADER_ID, index = true)
    public long characterHeaderId;
}
