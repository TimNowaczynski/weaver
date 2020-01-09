package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class Alias {

    public static final String ID = "alias_id";

    @PrimaryKey
    @ColumnInfo(name = ID)
    public long id;

    @NonNull
    @ColumnInfo(name = "alias")
    public String alias = "";
}
