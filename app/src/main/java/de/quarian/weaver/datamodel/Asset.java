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
/*
    TODO: Test putting campaignId as foreign key here as well.
     And if that works, test deleting this by deleting the campaign which might fail
     because the same should happen via campaign -> event -> asset
 */
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

    @ColumnInfo(name = FK_EVENT_ID, index = true)
    public long eventId;

    /*
        For simplicity (to avoid merging more than two tables),
        we put the campaignId here as well. In Theory there would be a link like this:
        asset -> event -> campaign
     */
    // TODO: add campaignId for delete operations

    /*
       TODO: change this on the long run to either an approach using merged tables
        (aka doing it the right way), or a sequence of queries.
     */
    /*
        Note that the [Campaign] Class also contains this,
        and should be the primary source of truth. So only use
        this while displaying assets.
     */
    @NonNull
    @ColumnInfo(name = "campaign_name")
    public String campaignName = "";

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
    public Byte[] asset;

    @Nullable
    @ColumnInfo(name = "fallback_url")
    public String fallbackUrl;

}
