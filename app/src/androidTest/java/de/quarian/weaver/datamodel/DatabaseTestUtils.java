package de.quarian.weaver.datamodel;

import java.util.Date;

import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;

import static de.quarian.weaver.datamodel.DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_DSA_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_DSA_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_DSA_UNISEX;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_SHADOWRUN_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_SHADOWRUN_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_SHADOWRUN_UNISEX;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_UNISEX;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.LAST_NAME_DSA;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.LAST_NAME_SHADOWRUN;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.LAST_NAME_VAMPIRE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_AGE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_AVATAR;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_AVATAR_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_BACKGROUND;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_GENDER;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_LOOKS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_MISC;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_ROLE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_SMALL_AVATAR;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_SMALL_AVATAR_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_STATE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.NAME_SET_NAME_DSA;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.NAME_SET_NAME_VAMPIRE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_LOGO_DSA;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_LOGO_IMAGE_TYPE_DSA;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_LOGO_IMAGE_TYPE_SHADOWRUN;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_LOGO_IMAGE_TYPE_VAMPIRE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_LOGO_SHADOWRUN;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_LOGO_VAMPIRE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_NAME_DSA;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_NAME_SHADOWRUN;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.RPS_NAME_VAMPIRE;

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
        shadowrunNameSet.id = nameDAO.createNameSet(shadowrunNameSet);
        final NameSetToCampaign shadowrunNameSetMapping = new NameSetToCampaign();
        shadowrunNameSetMapping.nameSetId = shadowrunNameSet.id;
        shadowrunNameSetMapping.campaignId = shadowrunCampaignId;
        nameDAO.createNameSetToCampaignMapping(shadowrunNameSetMapping);

        final NameSet vampireNameSet = new NameSet();
        vampireNameSet.nameSetName = NAME_SET_NAME_VAMPIRE;
        vampireNameSet.id = nameDAO.createNameSet(vampireNameSet);
        final NameSetToCampaign vampireNameSetToCampaignMapping = new NameSetToCampaign();
        vampireNameSetToCampaignMapping.nameSetId = vampireNameSet.id;
        vampireNameSetToCampaignMapping.campaignId = vampireCampaignId;
        nameDAO.createNameSetToCampaignMapping(vampireNameSetToCampaignMapping);

        final NameSet dsaNameSet = new NameSet();
        dsaNameSet.nameSetName = NAME_SET_NAME_DSA;
        dsaNameSet.id = nameDAO.createNameSet(dsaNameSet);
        final NameSetToCampaign dsaNameSetToCampaignMapping = new NameSetToCampaign();
        dsaNameSetToCampaignMapping.nameSetId = dsaNameSet.id;
        dsaNameSetToCampaignMapping.campaignId = dsaCampaignId;
        nameDAO.createNameSetToCampaignMapping(dsaNameSetToCampaignMapping);
    }

    /***
     * Requires to run tbe following method upfront:
     *  - {@link DatabaseTestUtils}.setUpNameSets()
     */
    public static void setUpNames(final WeaverDB weaverDB) {
        final NameDAO nameDAO = weaverDB.nameDAO();

        // Set A: Runner
        final long nameSetIDShadowrun = nameDAO.readNameSetByName(NAME_SET_NAME_SHADOWRUN).id;

        final Name runnerLastName = new Name();
        runnerLastName.gender = Constants.NameGender.UNISEX.getValue();
        runnerLastName.position = Constants.NamePosition.LAST.getValue();
        runnerLastName.name = LAST_NAME_SHADOWRUN;
        runnerLastName.nameSetId = nameSetIDShadowrun;
        nameDAO.createName(runnerLastName);

        final Name runnerFirstNameMale = new Name();
        runnerFirstNameMale.gender = Constants.NameGender.MALE.getValue();
        runnerFirstNameMale.position = Constants.NamePosition.FIRST.getValue();
        runnerFirstNameMale.name = FIRST_NAME_SHADOWRUN_MALE;
        runnerFirstNameMale.nameSetId = nameSetIDShadowrun;
        nameDAO.createName(runnerFirstNameMale);

        final Name runnerFirstNameFemale = new Name();
        runnerFirstNameFemale.gender = Constants.NameGender.FEMALE.getValue();
        runnerFirstNameFemale.position = Constants.NamePosition.FIRST.getValue();
        runnerFirstNameFemale.name = FIRST_NAME_SHADOWRUN_FEMALE;
        runnerFirstNameFemale.nameSetId = nameSetIDShadowrun;
        nameDAO.createName(runnerFirstNameFemale);

        final Name runnerFirstNameUnisex = new Name();
        runnerFirstNameUnisex.gender = Constants.NameGender.UNISEX.getValue();
        runnerFirstNameUnisex.position = Constants.NamePosition.FIRST.getValue();
        runnerFirstNameUnisex.name = FIRST_NAME_SHADOWRUN_UNISEX;
        runnerFirstNameUnisex.nameSetId = nameSetIDShadowrun;
        nameDAO.createName(runnerFirstNameUnisex);

        // Set B: Vampire
        final long nameSetIDVampire = nameDAO.readNameSetByName(NAME_SET_NAME_VAMPIRE).id;

        final Name vampireLastName = new Name();
        vampireLastName.gender = Constants.NameGender.UNISEX.getValue();
        vampireLastName.position = Constants.NamePosition.LAST.getValue();
        vampireLastName.name = LAST_NAME_VAMPIRE;
        vampireLastName.nameSetId = nameSetIDVampire;
        nameDAO.createName(vampireLastName);

        final Name vampireFirstNameMale = new Name();
        vampireFirstNameMale.gender = Constants.NameGender.MALE.getValue();
        vampireFirstNameMale.position = Constants.NamePosition.FIRST.getValue();
        vampireFirstNameMale.name = FIRST_NAME_VAMPIRE_MALE;
        vampireFirstNameMale.nameSetId = nameSetIDVampire;
        nameDAO.createName(vampireFirstNameMale);

        final Name vampireFirstNameFemale = new Name();
        vampireFirstNameFemale.gender = Constants.NameGender.FEMALE.getValue();
        vampireFirstNameFemale.position = Constants.NamePosition.FIRST.getValue();
        vampireFirstNameFemale.name = FIRST_NAME_VAMPIRE_FEMALE;
        vampireFirstNameFemale.nameSetId = nameSetIDVampire;
        nameDAO.createName(vampireFirstNameFemale);

        final Name vampireFirstNameUnisex = new Name();
        vampireFirstNameUnisex.gender = Constants.NameGender.UNISEX.getValue();
        vampireFirstNameUnisex.position = Constants.NamePosition.FIRST.getValue();
        vampireFirstNameUnisex.name = FIRST_NAME_VAMPIRE_UNISEX;
        vampireFirstNameUnisex.nameSetId = nameSetIDVampire;
        nameDAO.createName(vampireFirstNameUnisex);

        // Set C: Fantasy
        final long nameSetIDDSA = nameDAO.readNameSetByName(NAME_SET_NAME_DSA).id;

        final Name dsaLastName = new Name();
        dsaLastName.gender = Constants.NameGender.UNISEX.getValue();
        dsaLastName.position = Constants.NamePosition.LAST.getValue();
        dsaLastName.name = LAST_NAME_DSA;
        dsaLastName.nameSetId = nameSetIDDSA;
        nameDAO.createName(dsaLastName);

        final Name dsaFirstNameMale = new Name();
        dsaFirstNameMale.gender = Constants.NameGender.MALE.getValue();
        dsaFirstNameMale.position = Constants.NamePosition.FIRST.getValue();
        dsaFirstNameMale.name = FIRST_NAME_DSA_MALE;
        dsaFirstNameMale.nameSetId = nameSetIDDSA;
        nameDAO.createName(dsaFirstNameMale);

        final Name dsaFirstNameFemale = new Name();
        dsaFirstNameFemale.gender = Constants.NameGender.FEMALE.getValue();
        dsaFirstNameFemale.position = Constants.NamePosition.FIRST.getValue();
        dsaFirstNameFemale.name = FIRST_NAME_DSA_FEMALE;
        dsaFirstNameFemale.nameSetId = nameSetIDDSA;
        nameDAO.createName(dsaFirstNameFemale);

        final Name dsaFirstNameUnisex = new Name();
        dsaFirstNameUnisex.gender = Constants.NameGender.UNISEX.getValue();
        dsaFirstNameUnisex.position = Constants.NamePosition.FIRST.getValue();
        dsaFirstNameUnisex.name = FIRST_NAME_DSA_UNISEX;
        dsaFirstNameUnisex.nameSetId = nameSetIDDSA;
        nameDAO.createName(dsaFirstNameUnisex);
    }

    /***
     * Requires to run tbe following method upfront:
     *  - {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     *  - {@link DatabaseTestUtils}.setUpThemes()
     *  - {@link DatabaseTestUtils}.setUpCampaigns()
     */
    public static void setUpCharacters(final WeaverDB weaverDB) {
        final CharacterHeader characterHeaderInput = new CharacterHeader();

        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByName(CAMPAIGN_NAME_RISING_DRAGON);
        characterHeaderInput.campaignId = campaign.id;

        final Date editDate = new Date();
        final Date creationDate = new Date(editDate.getTime() - (1000L * 60L * 30L));
        characterHeaderInput.creationDateMillis = creationDate.getTime();
        characterHeaderInput.editDateMillis = editDate.getTime();
        characterHeaderInput.firstName = MOONLIGHT_FIRST_NAME;
        characterHeaderInput.alias = MOONLIGHT_ALIAS;
        characterHeaderInput.lastName = MOONLIGHT_LAST_NAME;
        characterHeaderInput.gender = MOONLIGHT_GENDER;
        characterHeaderInput.smallAvatar = MOONLIGHT_SMALL_AVATAR;
        characterHeaderInput.smallAvatarImageType = MOONLIGHT_SMALL_AVATAR_IMAGE_TYPE;
        characterHeaderInput.role = MOONLIGHT_ROLE;
        characterHeaderInput.state = MOONLIGHT_STATE;

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final long moonlightCharacterHeaderId = characterDAO.createCharacterHeader(characterHeaderInput);

        final CharacterBody characterBodyInput = new CharacterBody();
        characterBodyInput.characterHeaderId = moonlightCharacterHeaderId;
        characterBodyInput.age = MOONLIGHT_AGE;
        characterBodyInput.looks = MOONLIGHT_LOOKS;
        characterBodyInput.background = MOONLIGHT_BACKGROUND;
        characterBodyInput.miscellaneous = MOONLIGHT_MISC;
        characterBodyInput.avatar = MOONLIGHT_AVATAR;
        characterBodyInput.avatarImageType = MOONLIGHT_AVATAR_IMAGE_TYPE;

        characterDAO.createCharacterBody(characterBodyInput);
    }
}
