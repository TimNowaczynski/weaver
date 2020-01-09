package de.quarian.weaver.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import de.quarian.weaver.datamodel.Alias;
import de.quarian.weaver.datamodel.AliasToCharacter;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.PlayerCharacterToCampaign;
import de.quarian.weaver.datamodel.Character;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.EventToCharacter;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;
import de.quarian.weaver.datamodel.PlayerCharacter;
import de.quarian.weaver.datamodel.RoleplayingSystem;
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
        PlayerCharacterToCampaign.class,
        RoleplayingSystem.class,
        Tag.class,
        TagToCharacter.class,
        Theme.class
})
public abstract class WeaverDB extends RoomDatabase {

    public abstract CampaignDAO campaignDAO();
}
