package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class RoleplayingSystem {

    public static final String ID = "roleplaying_system_id";

    @PrimaryKey
    @ColumnInfo(name = ID)
    public long id;

    @NonNull
    @ColumnInfo(name = "roleplaying_system_name")
    public String roleplayingSystemName = "";

    @Nullable
    @ColumnInfo(name = "logo_image_type")
    public String logo_image_type;

    @Nullable
    @ColumnInfo(name = "logo")
    public byte[] logo;
}
