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

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @NonNull
    @ColumnInfo(name = "roleplaying_system_name")
    public String roleplayingSystemName = "";

    @Nullable
    @ColumnInfo(name = "logo")
    public Byte[] logoImage;

    @Nullable
    @ColumnInfo(name = "logo_image_type")
    public String logoImageType;
}
