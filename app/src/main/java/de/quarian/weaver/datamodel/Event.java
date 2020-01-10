package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * 1:1
 * Is linked by {@link EventToCharacter} with a {@link Character}
 */
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Entity
public class Event {

    public static final String ID = "event_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = "event_date_millis")
    public long eventDateMillis;

    @NonNull
    @ColumnInfo(name = "headline")
    public String headline = "";

    @Nullable
    @ColumnInfo(name = "text")
    public String text;

    @Nullable
    @ColumnInfo(name = "image")
    public byte[] image;

    @Nullable
    @ColumnInfo(name = "image_type")
    public String imageType;

    @Nullable
    @ColumnInfo(name = "file")
    public byte[] file;

    @Nullable
    @ColumnInfo(name = "file_type")
    public String fileType;
}
