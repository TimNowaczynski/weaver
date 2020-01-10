package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * 1:1
 * Links a {@link Tag} with a {@link RoleplayingSystem}
 *
 * When deleting an associated {@link RoleplayingSystem} this entry will be removed as well
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class Tag {

    public static final String ID = "id";
    public static final String ROLEPLAYING_SYSTEM_ID = "fk_roleplaying_system_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = ROLEPLAYING_SYSTEM_ID)
    @ForeignKey(entity = Tag.class,
            parentColumns = RoleplayingSystem.ID,
            childColumns = ROLEPLAYING_SYSTEM_ID,
            onDelete = ForeignKey.CASCADE)
    public long roleplayingSystemId;

    @NonNull
    @ColumnInfo(name = "tag")
    public String tag = "";
}
