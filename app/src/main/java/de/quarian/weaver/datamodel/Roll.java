package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * * [RELATIONS]
 *
 * A {@link Roll} is associated
 * a) indirectly with a {@link CharacterHeader} through a Mapping Table {@link RollToCharacterHeader}
 */
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class Roll {

    public static final String ID = "roll_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @NonNull
    @ColumnInfo(name = "roll_name")
    public String rollName = "";

    @NonNull
    @ColumnInfo(name = "roll")
    public String roll = "";
}
