package de.quarian.weaver.datamodel;

import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;

import static de.quarian.weaver.datamodel.DatabaseTestConstants.*;

public final class DatabaseTestUtils {

    private DatabaseTestUtils() { }

    public static void setUpRoleplayingSystems(final WeaverDB weaverDB) {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        final RoleplayingSystem shadowrun = new RoleplayingSystem();
        shadowrun.roleplayingSystemName = RPS_NAME_SHADOWRUN;
        shadowrun.logo = "shadowrun.jpg".getBytes();
        shadowrun.logoImageType = "image/jpg";
        roleplayingSystemDAO.createRoleplayingSystem(shadowrun);

        final RoleplayingSystem dsa = new RoleplayingSystem();
        dsa.roleplayingSystemName = RPS_NAME_DSA;
        dsa.logo = "dsa.png".getBytes();
        dsa.logoImageType = "image/png";
        roleplayingSystemDAO.createRoleplayingSystem(dsa);

        final RoleplayingSystem vampire = new RoleplayingSystem();
        vampire.roleplayingSystemName = RPS_NAME_VAMPIRE;
        vampire.logo = "vampire.png".getBytes();
        vampire.logoImageType = "image/png";
        roleplayingSystemDAO.createRoleplayingSystem(vampire);
    }

    public static void setUpThemes(final WeaverDB weaverDB) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final Theme theme = new Theme();
        themeDAO.createTheme(theme);
    }

    public static void setUpCampaigns(final WeaverDB weaverDB) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        final Campaign asiaCampaign = new Campaign();
        final RoleplayingSystem shadowrun = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_SHADOWRUN);
        asiaCampaign.roleplayingSystemId = shadowrun.id;
        asiaCampaign.campaignName = "Rising Dragon";
        asiaCampaign.themeId = 1L;
        campaignDAO.createCampaign(asiaCampaign);

        final Campaign europeCampaign = new Campaign();
        final RoleplayingSystem vampire = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_VAMPIRE);
        europeCampaign.roleplayingSystemId = vampire.id;
        europeCampaign.campaignName = "Renaissance";
        europeCampaign.themeId = 1L;
        campaignDAO.createCampaign(europeCampaign);

        final Campaign aventurienCampaign = new Campaign();
        final RoleplayingSystem dsa = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_DSA);
        aventurienCampaign.roleplayingSystemId = dsa.id;
        aventurienCampaign.campaignName = "Die Borbarad Kampagne";
        aventurienCampaign.themeId = 1L;
        campaignDAO.createCampaign(aventurienCampaign);
    }

    /***
     * Reqiures to run tbe following methods upfront
     * (the order is of importance)
     * 1) {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     * 2) {@link DatabaseTestUtils}.setUpThemes()
     * 3) {@link DatabaseTestUtils}.setUpCampaigns()
     */
    public static void setUpNameSets(final WeaverDB weaverDB) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long shadowrunCampaignId = campaignDAO.readCampaignByName("Rising Dragon").id;
        final long vampireCampaignId = campaignDAO.readCampaignByName("Renaissance").id;
        final long dsaCampaignId = campaignDAO.readCampaignByName("Die Borbarad Kampagne").id;

        final NameDAO nameDAO = weaverDB.nameDAO();

        final NameSet shadowrunNameSet = new NameSet();
        shadowrunNameSet.nameSetName = "Shadowrun";
        final long shadowrunNameSetId = nameDAO.createNameSet(shadowrunNameSet);
        final NameSetToCampaign shadowrunNameSetMapping = new NameSetToCampaign();
        shadowrunNameSetMapping.nameSetId = shadowrunNameSetId;
        shadowrunNameSetMapping.campaignId = shadowrunCampaignId;
        nameDAO.createNameSetToCampaignMapping(shadowrunNameSetMapping);

        final NameSet vampireNameSet = new NameSet();
        vampireNameSet.nameSetName = "Vampire";
        final long vampireNameSetId = nameDAO.createNameSet(vampireNameSet);
        final NameSetToCampaign vampireNameSetToCampaignMapping = new NameSetToCampaign();
        vampireNameSetToCampaignMapping.nameSetId = vampireNameSetId;
        vampireNameSetToCampaignMapping.campaignId = vampireCampaignId;
        nameDAO.createNameSetToCampaignMapping(vampireNameSetToCampaignMapping);

        final NameSet dsaNameSet = new NameSet();
        dsaNameSet.nameSetName = "DSA";
        final long dsaNameSetId = nameDAO.createNameSet(dsaNameSet);
        final NameSetToCampaign dsaNameSetToCampaignMapping = new NameSetToCampaign();
        dsaNameSetToCampaignMapping.nameSetId = dsaNameSetId;
        dsaNameSetToCampaignMapping.campaignId = dsaCampaignId;
        nameDAO.createNameSetToCampaignMapping(dsaNameSetToCampaignMapping);
    }
}
