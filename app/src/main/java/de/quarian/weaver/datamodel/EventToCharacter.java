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
@Entity(foreignKeys = {
        @ForeignKey(entity = Event.class,
                parentColumns = Event.ID,
                childColumns = EventToCharacter.FK_EVENT_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Character.class,
                parentColumns = Character.ID,
                childColumns = EventToCharacter.FK_CHARACTER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class EventToCharacter {

    public static final String FK_EVENT_ID = "fk_event_id";
    public static final String FK_CHARACTER_ID = "fk_character_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_EVENT_ID, index = true)
    public long eventId;

    @ColumnInfo(name = FK_CHARACTER_ID, index = true)
    public long characterId;
}
