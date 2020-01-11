package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.AliasToCharacter.FK_CHARACTER_ID;

/**
 * [RELATIONS]
 *
 * N:1
 * Links multiple {@link Alias}es with a {@link Character}
 * This relation needs to be partially enforced in code (alias id must be unique)
 *
 * When deleting an associated {@link Alias} this entry will be removed as well
 * When deleting an associated {@link Character} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Alias.class,
                parentColumns = Alias.ID,
                childColumns = AliasToCharacter.FK_ALIAS_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class AliasToCharacter {

    public static final String FK_ALIAS_ID = "fk_alias_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";

    @ColumnInfo(name = "alias_to_character_jd")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_ALIAS_ID, index = true)
    public long aliasId;

    @ColumnInfo(name = FK_CHARACTER_ID, index = true)
    public long characterId;
}
