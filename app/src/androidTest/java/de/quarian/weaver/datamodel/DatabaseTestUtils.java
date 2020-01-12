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
        shadowrun.logo = RPS_LOGO_SHADOWRUN;
        shadowrun.logoImageType = RPS_LOGO_IMAGE_TYPE_SHADOWRUN;
        roleplayingSystemDAO.createRoleplayingSystem(shadowrun);

        final RoleplayingSystem dsa = new RoleplayingSystem();
        dsa.roleplayingSystemName = RPS_NAME_DSA;
        dsa.logo = RPS_LOGO_DSA;
        dsa.logoImageType = RPS_LOGO_IMAGE_TYPE_DSA;
        roleplayingSystemDAO.createRoleplayingSystem(dsa);

        final RoleplayingSystem vampire = new RoleplayingSystem();
        vampire.roleplayingSystemName = RPS_NAME_VAMPIRE;
        vampire.logo = RPS_LOGO_VAMPIRE;
        vampire.logoImageType = RPS_LOGO_IMAGE_TYPE_VAMPIRE;
        roleplayingSystemDAO.createRoleplayingSystem(vampire);
    }

    /**
     * This will insert a dummy theme with the ID of 1L. For real Theme testing see {@link ThemeDAOTest}.
     * @param weaverDB Our target database
     */
    public static void setUpThemes(final WeaverDB weaverDB) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final Theme theme = new Theme();
        themeDAO.createTheme(theme);
    }

    /***
     * Requires to run tbe following methods upfront
     * (the order is of importance)
     * 1) {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     * 2) {@link DatabaseTestUtils}.setUpThemes()
     */
    public static void setUpCampaigns(final WeaverDB weaverDB) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        final Campaign asiaCampaign = new Campaign();
        final RoleplayingSystem shadowrun = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_SHADOWRUN);
        asiaCampaign.roleplayingSystemId = shadowrun.id;
        asiaCampaign.campaignName = CAMPAIGN_NAME_RISING_DRAGON;
        asiaCampaign.themeId = 1L;
        campaignDAO.createCampaign(asiaCampaign);

        final Campaign europeCampaign = new Campaign();
        final RoleplayingSystem vampire = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_VAMPIRE);
        europeCampaign.roleplayingSystemId = vampire.id;
        europeCampaign.campaignName = CAMPAIGN_NAME_RENAISSANCE;
        europeCampaign.themeId = 1L;
        campaignDAO.createCampaign(europeCampaign);

        final Campaign aventurienCampaign = new Campaign();
        final RoleplayingSystem dsa = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_DSA);
        aventurienCampaign.roleplayingSystemId = dsa.id;
        aventurienCampaign.campaignName = CAMPAIGN_NAME_BORBARAD;
        aventurienCampaign.themeId = 1L;
        campaignDAO.createCampaign(aventurienCampaign);
    }

    /***
     * Requires to run tbe following methods upfront
     * (the order is of importance)
     * 1) {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     * 2) {@link DatabaseTestUtils}.setUpThemes()
     * 3) {@link DatabaseTestUtils}.setUpCampaigns()
     */
    public static void setUpNameSets(final WeaverDB weaverDB) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long shadowrunCampaignId = campaignDAO.readCampaignByName(CAMPAIGN_NAME_RISING_DRAGON).id;
        final long vampireCampaignId = campaignDAO.readCampaignByName(CAMPAIGN_NAME_RENAISSANCE).id;
        final long dsaCampaignId = campaignDAO.readCampaignByName(CAMPAIGN_NAME_BORBARAD).id;

        final NameDAO nameDAO = weaverDB.nameDAO();

        final NameSet shadowrunNameSet = new NameSet();
        shadowrunNameSet.nameSetName = NAME_SET_NAME_SHADOWRUN;
        final long shadowrunNameSetId = nameDAO.createNameSet(shadowrunNameSet);
        final NameSetToCampaign shadowrunNameSetMapping = new NameSetToCampaign();
        shadowrunNameSetMapping.nameSetId = shadowrunNameSetId;
        shadowrunNameSetMapping.campaignId = shadowrunCampaignId;
        nameDAO.createNameSetToCampaignMapping(shadowrunNameSetMapping);

        final NameSet vampireNameSet = new NameSet();
        vampireNameSet.nameSetName = NAME_SET_NAME_VAMPIRE;
        final long vampireNameSetId = nameDAO.createNameSet(vampireNameSet);
        final NameSetToCampaign vampireNameSetToCampaignMapping = new NameSetToCampaign();
        vampireNameSetToCampaignMapping.nameSetId = vampireNameSetId;
        vampireNameSetToCampaignMapping.campaignId = vampireCampaignId;
        nameDAO.createNameSetToCampaignMapping(vampireNameSetToCampaignMapping);

        final NameSet dsaNameSet = new NameSet();
        dsaNameSet.nameSetName = NAME_SET_NAME_DSA;
        final long dsaNameSetId = nameDAO.createNameSet(dsaNameSet);
        final NameSetToCampaign dsaNameSetToCampaignMapping = new NameSetToCampaign();
        dsaNameSetToCampaignMapping.nameSetId = dsaNameSetId;
        dsaNameSetToCampaignMapping.campaignId = dsaCampaignId;
        nameDAO.createNameSetToCampaignMapping(dsaNameSetToCampaignMapping);
    }
}
