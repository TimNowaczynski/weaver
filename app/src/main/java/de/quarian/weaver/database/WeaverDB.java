package de.quarian.weaver.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import de.quarian.weaver.datamodel.Alias;
import de.quarian.weaver.datamodel.AliasToCharacter;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Character;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.EventToCharacter;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;
import de.quarian.weaver.datamodel.PlayerCharacter;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Roll;
import de.quarian.weaver.datamodel.RollToCharacter;
import de.quarian.weaver.datamodel.Tag;
import de.quarian.weaver.datamodel.TagToCharacter;
import de.quarian.weaver.datamodel.Theme;

@Database(version = 1, entities = {
        Alias.class,
        AliasToCharacter.class,
        Campaign.class,
        Character.class,
        Event.class,
        EventToCharacter.class,
        Name.class,
        NameSet.class,
        NameSetToCampaign.class,
        PlayerCharacter.class,
        RoleplayingSystem.class,
        Roll.class,
        RollToCharacter.class,
        Tag.class,
        TagToCharacter.class,
        Theme.class
})
public abstract class WeaverDB extends RoomDatabase {

    public abstract RoleplayingSystemDAO roleplayingSystemDAO();
    public abstract CampaignDAO campaignDAO();
    public abstract ThemeDAO themeDAO();
    public abstract NameDAO nameDAO();

    public abstract DebugDAO debugDAO();
}
