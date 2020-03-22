package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// TODO: Think about putting campaign and rps images, as well as avatars here
/**
 * [RELATIONS]
 *
 * Is linked N:1 to {@link Event}s
 */
@SuppressWarnings("NullableProblems")
@Entity(foreignKeys = {
    @ForeignKey(entity = Event.class,
            parentColumns = Event.ID,
            childColumns = Asset.FK_EVENT_ID,
            onDelete = ForeignKey.CASCADE
    )
})
public class Asset {

    public static final String ID = "asset_id";
    public static final String FK_EVENT_ID = "fk_event_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_EVENT_ID)
    public long eventId;

    @NonNull
    @ColumnInfo(name = "end_of_lifetime_timestamp")
    public long endOfLifetimeTimestamp;

    @NonNull
    @ColumnInfo(name = "content_locally_present")
    public boolean contentLocallyPresent;

    @NonNull
    @ColumnInfo(name = "asset_name")
    public String assetName;

    @Nullable
    @ColumnInfo(name = "asset_description")
    public String assetDescription;

    @NonNull
    @ColumnInfo(name = "asset_type")
    public String assetType;

    @Nullable
    @ColumnInfo(name = "asset")
    public byte[] asset;

    @Nullable
    @ColumnInfo(name = "fallback_url")
    public String fallbackUrl;

}
