package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.NameToNameSet.FK_NAME_SET_ID;

/**
 * [RELATIONS]
 *
 * M:N
 * Links {@link Name}s with multiple {@link NameSet}s
 *
 * When deleting an associated {@link Name} this entry will be removed as well
 * When deleting an associated {@link NameSet} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Name.class,
                parentColumns = Name.ID,
                childColumns = NameToNameSet.FK_NAME_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = NameSet.class,
                parentColumns = NameSet.ID,
                childColumns = FK_NAME_SET_ID,
                onDelete = ForeignKey.CASCADE)
})
public class NameToNameSet {

    public static final String FK_NAME_ID = "fk_name_id";
    public static final String FK_NAME_SET_ID = "fk_name_set_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_NAME_ID)
    public long nameId;

    @ColumnInfo(name = FK_NAME_SET_ID)
    public long nameSetId;
}
