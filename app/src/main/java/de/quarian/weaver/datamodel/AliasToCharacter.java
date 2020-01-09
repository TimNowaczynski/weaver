package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
@SuppressWarnings("WeakerAccess")
@Entity
public class AliasToCharacter {

    public static final String ALIAS_ID = "fk_alias_id";
    public static final String CHARACTER_ID = "fk_character_id";

    @ColumnInfo(name = "alias_to_character_jd")
    @PrimaryKey
    public long id;

    @ColumnInfo(name = ALIAS_ID)
    @ForeignKey(entity = Alias.class,
            parentColumns = Alias.ID,
            childColumns = ALIAS_ID,
            onDelete = ForeignKey.CASCADE)
    public long alias_id;

    @ColumnInfo(name = CHARACTER_ID)
    @ForeignKey(entity = Character.class,
            parentColumns = Character.ID,
            childColumns = CHARACTER_ID,
            onDelete = ForeignKey.CASCADE)
    public long character_id;
}
