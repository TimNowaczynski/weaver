package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.NameSetToCampaign.FK_CAMPAIGN_ID;
import static de.quarian.weaver.datamodel.NameSetToCampaign.FK_NAME_SET_ID;

/**
 * [RELATIONS]
 *
 * M:N
 * Links {@link NameSet}s with multiple {@link Campaign}s
 *
 * When deleting an associated {@link NameSet} this entry will be removed as well
 * When deleting an associated {@link Campaign} this entry will be removed as well
 */
@SuppressWarnings("WeakerAccess")
@Entity(foreignKeys = {
        @ForeignKey(entity = NameSet.class,
                parentColumns = NameSet.ID,
                childColumns = FK_NAME_SET_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Campaign.class,
                parentColumns = Campaign.ID,
                childColumns = FK_CAMPAIGN_ID,
                onDelete = ForeignKey.CASCADE)
    })
public class NameSetToCampaign {

    public static final String FK_NAME_SET_ID = "fk_name_set_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";

    @PrimaryKey(autoGenerate = true)
    public long nameSetToCampaignId;

    @ColumnInfo(name = FK_NAME_SET_ID)
    public long nameSetId;

    @ColumnInfo(name = FK_CAMPAIGN_ID)
    public long campaignId;
}
