package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * M:N
 * Links {@link Name}s with multiple {@link NameSet}s
 *
 * When deleting an associated {@link Name} this entry will be removed as well
 * When deleting an associated {@link NameSet} this entry will be removed as well
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class NameToNameSet {

    public static final String FK_NAME_ID = "fk_name_id";
    public static final String FK_NAME_SET_ID = "fk_name_set_id";

    @PrimaryKey
    public long id;

    @ColumnInfo(name = FK_NAME_ID)
    @ForeignKey(entity = Name.class,
            parentColumns = Name.ID,
            childColumns = FK_NAME_ID,
            onDelete = ForeignKey.CASCADE)
    public long name_id;

    @ColumnInfo(name = FK_NAME_SET_ID)
    @ForeignKey(entity = NameSet.class,
            parentColumns = NameSet.ID,
            childColumns = FK_NAME_SET_ID,
            onDelete = ForeignKey.CASCADE)
    public long name_set_id;
}
