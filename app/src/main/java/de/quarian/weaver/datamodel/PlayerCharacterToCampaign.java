package de.quarian.weaver.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * [RELATIONS]
 *
 * 1:N
 * Links {@link PlayerCharacter}s with a {@link Campaign}
 * A {@link Campaign} must be unique, this relation needs to be enforced in code
 *
 * When deleting an associated {@link Campaign} this entry will be removed as well
 * When deleting an associated {@link PlayerCharacter} this entry will be removed as well
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class PlayerCharacterToCampaign {

    public static final String PLAYER_CHARACTER_ID = "fk_player_character_id";
    public static final String CAMPAIGN_ID = "fk_campaign_id";

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = PLAYER_CHARACTER_ID)
    @ForeignKey(entity = PlayerCharacter.class,
            parentColumns = PlayerCharacter.ID,
            childColumns = PLAYER_CHARACTER_ID,
            onDelete = ForeignKey.CASCADE)
    public long player_character_id;

    @ColumnInfo(name = CAMPAIGN_ID)
    @ForeignKey(entity = Campaign.class,
            parentColumns = Campaign.ID,
            childColumns = CAMPAIGN_ID,
            onDelete = ForeignKey.CASCADE)
    public long campaign_id;
}
