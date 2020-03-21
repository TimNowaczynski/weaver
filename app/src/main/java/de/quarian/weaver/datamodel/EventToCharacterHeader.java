package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * 1:N
 * Links n {@link Event}s with a single {@link CharacterHeader}
 *
 * In Theory this could also be M:N but it's not supported by the application code right now
 *
 * When deleting an associated {@link Event} this entry will be removed as well
 * When deleting an associated {@link CharacterHeader} this entry will be removed as well
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Event.class,
                parentColumns = Event.ID,
                childColumns = EventToCharacterHeader.FK_EVENT_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = CharacterHeader.class,
                parentColumns = CharacterHeader.ID,
                childColumns = EventToCharacterHeader.FK_CHARACTER_HEADER_ID,
                onDelete = ForeignKey.CASCADE)
})
public class EventToCharacterHeader {

    public static final String FK_EVENT_ID = "fk_event_id";
    public static final String FK_CHARACTER_HEADER_ID = "fk_character_header_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = FK_EVENT_ID, index = true)
    public long eventId;

    @ColumnInfo(name = FK_CHARACTER_HEADER_ID, index = true)
    public long characterHeaderId;
}
