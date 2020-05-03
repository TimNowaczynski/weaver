package de.quarian.weaver.database;

/**
 * Used to enhance testing capabilities. We are not able to mock the WeaverDB class itself.
 */
public interface DAOProvider {

    RoleplayingSystemDAO roleplayingSystemDAO();
    CampaignDAO campaignDAO();
    ThemeDAO themeDAO();
    NameDAO nameDAO();
    CharacterDAO characterDAO();
    AssetDAO assetDAO();
    PlayerCharacterDAO playerCharacterDAO();

    DebugDAO debugDAO();

}
