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
 * Is linked by {@link EventToCharacterHeader} with a {@link CharacterHeader}
 *
 * 1:1
 * May be linked to an {@link Asset}
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
}
