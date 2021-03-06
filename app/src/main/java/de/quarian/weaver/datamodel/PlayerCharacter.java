package de.quarian.weaver.datamodel;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static de.quarian.weaver.datamodel.PlayerCharacter.FK_CAMPAIGN_ID;
import static de.quarian.weaver.datamodel.PlayerCharacter.FK_ROLEPLAYING_SYSTEM_ID;

/**
 * Represents a player character within a campaign.
 * When deleting the {@link RoleplayingSystem}, this entry will be removed as well.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Campaign.class,
                parentColumns = Campaign.ID,
                childColumns = FK_CAMPAIGN_ID,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = RoleplayingSystem.class,
                parentColumns = RoleplayingSystem.ID,
                childColumns = FK_ROLEPLAYING_SYSTEM_ID,
                onDelete = ForeignKey.CASCADE)
})
public class PlayerCharacter {

    public static final String ID = "player_character_id";
    public static final String FK_CAMPAIGN_ID = "fk_campaign_id";
    public static final String FK_ROLEPLAYING_SYSTEM_ID = "fk_roleplaying_system_id";

    private static final String WHITE_ARGB = "255";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = FK_CAMPAIGN_ID, index = true)
    public long campaignId;

    /**
     * This is basically just for ease of use,
     * so we can generate proper suggestions
     */
    @ColumnInfo(name = FK_ROLEPLAYING_SYSTEM_ID, index = true)
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

    @ColumnInfo(name = "character_highlight_color_a", defaultValue = WHITE_ARGB)
    public int characterHighlightColorA;

    @ColumnInfo(name = "character_highlight_color_r", defaultValue = WHITE_ARGB)
    public int characterHighlightColorR = Color.red(Color.WHITE);

    @ColumnInfo(name = "character_highlight_color_g", defaultValue = WHITE_ARGB)
    public int characterHighlightColorG = Color.green(Color.WHITE);

    @ColumnInfo(name = "character_highlight_color_b", defaultValue = WHITE_ARGB)
    public int characterHighlightColorB = Color.blue(Color.WHITE);
}
