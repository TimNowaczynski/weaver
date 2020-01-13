package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CharacterName {

    public static final String ID = "character_name_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName = "";

    @NonNull
    @ColumnInfo(name = "alias")
    public String alias = "";

    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName = "";
}
