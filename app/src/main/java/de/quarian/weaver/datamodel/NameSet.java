package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Is used to bundle {@link Name}s
 */
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class NameSet {

    public static final String ID = "name_set_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @NonNull
    @ColumnInfo(name = "name_set_name")
    public String nameSetName = "";
}
