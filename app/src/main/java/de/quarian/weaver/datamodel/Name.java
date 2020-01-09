package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents part of a name
 */
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class Name {

    public static final String ID = "name_id";

    @PrimaryKey
    @ColumnInfo(name = ID)
    public long id;

    /**
     * For position see {@link Constants.NamePosition}.
     */
    @ColumnInfo(name = "position")
    public int position;

    @NonNull
    @ColumnInfo(name = "name")
    public String name = "";
}
