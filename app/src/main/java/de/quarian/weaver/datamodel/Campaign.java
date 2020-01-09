package de.quarian.weaver.datamodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * * [RELATIONS]
 *
 * A {@link Campaign} is associated
 * a) directly with a {@link RoleplayingSystem},
 * b) also directly with a {@link Theme} and
 * c) with n {@link PlayerCharacter}s through a mapping table {@link PlayerCharacterToCampaign}
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class Campaign {

    public static final String ID = "campaign_id";
    public static final String ROLEPLAYING_SYSTEM_ID = "fk_roleplaying_system_id";
    public static final String THEME_ID = "fk_theme_id";

    @ColumnInfo(name = ID)
    @PrimaryKey
    public long id;

    @ForeignKey(entity = RoleplayingSystem.class,
            parentColumns = RoleplayingSystem.ID,
            childColumns = Campaign.ROLEPLAYING_SYSTEM_ID,
            onDelete = ForeignKey.RESTRICT)
    @ColumnInfo(name = ROLEPLAYING_SYSTEM_ID)
    public long roleplayingSystemId;

    @ForeignKey(entity = Theme.class,
            parentColumns = Theme.ID,
            childColumns = Campaign.THEME_ID,
            onDelete = ForeignKey.RESTRICT)
    @ColumnInfo(name = THEME_ID)
    public long themeId;

    @ColumnInfo(name = "archived")
    public boolean archived;

    @NonNull
    @ColumnInfo(name = "campaign_name")
    public String campaignName = "";

    @Nullable
    @ColumnInfo(name = "synopsis")
    public String synopsis;

    @ColumnInfo(name = "created")
    public long created;

    @ColumnInfo(name = "last_used")
    public long lastUsed;
}
