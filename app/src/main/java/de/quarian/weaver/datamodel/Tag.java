package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.Tag.FK_ROLEPLAYING_SYSTEM_ID;

/**
 * [RELATIONS]
 *
 * 1:1
 * Links a {@link Tag} with a {@link RoleplayingSystem}
 *
 * When deleting an associated {@link RoleplayingSystem} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = RoleplayingSystem.class,
                parentColumns = RoleplayingSystem.ID,
                childColumns = FK_ROLEPLAYING_SYSTEM_ID,
                onDelete = ForeignKey.CASCADE)
})
public class Tag {

    public static final String ID = "tag_id";
    public static final String FK_ROLEPLAYING_SYSTEM_ID = "fk_roleplaying_system_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_ROLEPLAYING_SYSTEM_ID, index = true)
    public long roleplayingSystemId;

    @NonNull
    @ColumnInfo(name = "tag")
    public String tag = "";
}
