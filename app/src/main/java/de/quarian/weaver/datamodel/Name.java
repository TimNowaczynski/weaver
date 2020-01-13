package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.Name.FK_NAME_SET_ID;

/**
 * Represents part of a full name
 */
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity(foreignKeys = @ForeignKey(entity = NameSet.class,
        parentColumns = NameSet.ID,
        childColumns = FK_NAME_SET_ID,
        onDelete = ForeignKey.CASCADE))
public class Name {

    public static final String ID = "name_id";
    public static final String FK_NAME_SET_ID = "fk_name_set_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_NAME_SET_ID, index = true)
    public long nameSetId;

    @NamePosition
    @ColumnInfo(name = "position")
    public int position;

    @NameGender
    @ColumnInfo(name = "gender")
    public int gender;

    @NonNull
    @ColumnInfo(name = "name")
    public String name = "";
}
