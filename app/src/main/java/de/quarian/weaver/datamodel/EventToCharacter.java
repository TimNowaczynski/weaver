package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * 1:N
 * Links n {@link Event}s with a {@link Character}s
 *
 * When deleting an associated {@link Event} this entry will be removed as well
 * When deleting an associated {@link Character} this entry will be removed as well
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class EventToCharacter {

    public static final String COLUMN_EVENT_ID = "fk_event_id";
    public static final String COLUMN_CHARACTER_ID = "fk_character_id";

    @PrimaryKey
    public long id;

    @ColumnInfo(name = COLUMN_EVENT_ID)
    @ForeignKey(entity = Event.class,
            parentColumns = Event.ID,
            childColumns = COLUMN_EVENT_ID,
            onDelete = ForeignKey.CASCADE)
    public long event_id;

    @ColumnInfo(name = COLUMN_CHARACTER_ID)
    @ForeignKey(entity = Character.class,
            parentColumns = Character.ID,
            childColumns = COLUMN_CHARACTER_ID,
            onDelete = ForeignKey.CASCADE)
    public long character_id;
}
