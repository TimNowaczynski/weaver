package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.RollToCharacter.FK_CHARACTER_ID;
/**
 * [RELATIONS]
 *
 * n:1
 * Links multiple {@link Roll}s with a {@link Character}
 *
 * When deleting an associated {@link Roll} this entry will be removed as well
 * When deleting an associated {@link Character} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Roll.class,
                parentColumns = Roll.ID,
                childColumns = RollToCharacter.FK_ROLL_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class RollToCharacter {

    public static final String FK_ROLL_ID = "fk_roll_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_ROLL_ID, index = true)
    public long rollId;

    @ColumnInfo(name = FK_CHARACTER_ID, index = true)
    public long characterId;
}
