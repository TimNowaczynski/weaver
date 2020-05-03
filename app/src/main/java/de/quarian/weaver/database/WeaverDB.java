package de.quarian.weaver.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.CharacterBody;
import de.quarian.weaver.datamodel.CharacterHeader;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.EventToCharacterHeader;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;
import de.quarian.weaver.datamodel.PlayerCharacter;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Roll;
import de.quarian.weaver.datamodel.RollToCharacterHeader;
import de.quarian.weaver.datamodel.Tag;
import de.quarian.weaver.datamodel.TagToCharacterHeader;
import de.quarian.weaver.datamodel.Theme;

@Database(version = 1, entities = {
        Asset.class,
        Campaign.class,
        CharacterHeader.class,
        CharacterBody.class,
        Event.class,
        EventToCharacterHeader.class,
        Name.class,
        NameSet.class,
        NameSetToCampaign.class,
        PlayerCharacter.class,
        RoleplayingSystem.class,
        Roll.class,
        RollToCharacterHeader.class,
        Tag.class,
        TagToCharacterHeader.class,
        Theme.class
})
@TypeConverters(DBConverters.BlobConverter.class)
public abstract class WeaverDB extends RoomDatabase implements DAOProvider {

    public static final String DATABASE_FILE_NAME = "weaver.db";

    public abstract RoleplayingSystemDAO roleplayingSystemDAO();
    public abstract CampaignDAO campaignDAO();
    public abstract ThemeDAO themeDAO();
    public abstract NameDAO nameDAO();
    public abstract CharacterDAO characterDAO();
    public abstract AssetDAO assetDAO();
    public abstract PlayerCharacterDAO playerCharacterDAO();

    public abstract DebugDAO debugDAO();
}
