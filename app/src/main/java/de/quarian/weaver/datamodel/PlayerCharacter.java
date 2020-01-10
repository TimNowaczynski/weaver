package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Represents a player character within a campaign.
 * When deleting the {@link RoleplayingSystem}, this entry will be removed as well.
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class PlayerCharacter {

    public static final String ID = "player_character_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";
    public static final String FK_ROLEPLAYING_SYSTEM_ID = "fk_roleplaying_system_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CAMPAIGN_ID)
    @ForeignKey(entity = Campaign.class,
            parentColumns = Campaign.ID,
            childColumns = FK_CAMPAIGN_ID)
    public long campaignId;

    /**
     * This is basically just for ease of use,
     * so we can generate proper suggestions
     */
    @ColumnInfo(name = FK_ROLEPLAYING_SYSTEM_ID)
    @ForeignKey(entity = RoleplayingSystem.class,
            parentColumns = RoleplayingSystem.ID,
            childColumns = FK_CAMPAIGN_ID,
            onDelete = ForeignKey.CASCADE)
    public long roleplayingSystemId;

    @NonNull
    @ColumnInfo(name = "player_name")
    public String playerName = "";

    @NonNull
    @ColumnInfo(name = "player_character_name")
    public String playerCharacterName = "";

    @Nullable
    @ColumnInfo(name = "player_character_avatar_image_type")
    public String playerCharacterAvatarImageType;

    @Nullable
    @ColumnInfo(name = "player_character_avatar")
    public byte[] playerCharacterAvatar;

    @ColumnInfo(name = "character_highlight_color_a")
    public int characterHighlightColorA;

    @ColumnInfo(name = "character_highlight_color_r")
    public int characterHighlightColorR;

    @ColumnInfo(name = "character_highlight_color_g")
    public int characterHighlightColorG;

    @ColumnInfo(name = "character_highlight_color_b")
    public int characterHighlightColorB;
}
