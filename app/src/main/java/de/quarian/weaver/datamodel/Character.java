package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Campaign.class,
                parentColumns = Campaign.ID,
                childColumns = Character.FK_CAMPAIGN_ID,
                onDelete = ForeignKey.CASCADE)
})
public class Character {

    public static final String ID = "character_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CAMPAIGN_ID, index = true)
    public long campaignId;

    @ColumnInfo(name = "creation_date_millis")
    public long creationDateMillis;

    @ColumnInfo(name = "edit_date_millis")
    public long editDateMillis;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName = "";

    @NonNull
    @ColumnInfo(name = "alias")
    public String alias = "";

    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName = "";
}
