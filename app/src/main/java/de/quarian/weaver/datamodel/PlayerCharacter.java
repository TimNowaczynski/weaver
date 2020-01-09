package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Represents a player character within a campaign.
 * When deleting the {@link Campaign}, this entry will be removed as well.
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class PlayerCharacter {

    public static final String ID = "player_character_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CAMPAIGN_ID)
    @ForeignKey(entity = Campaign.class,
            parentColumns = Campaign.ID,
            childColumns = FK_CAMPAIGN_ID,
            onDelete = ForeignKey.CASCADE)
    public long campaign_id;

    @NonNull
    @ColumnInfo(name = "player_character_name")
    public String player_character_name = "";

    @NonNull
    @ColumnInfo(name = "player_name")
    public String player_name = "";
}
