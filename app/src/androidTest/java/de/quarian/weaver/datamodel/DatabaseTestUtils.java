package de.quarian.weaver.datamodel;

import java.util.Date;
import java.util.List;

import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;

import static de.quarian.weaver.datamodel.DatabaseTestConstants.ALEX_MAGIC_WARNER_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ALEX_MAGIC_WARNER_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ALEX_MAGIC_WARNER_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_DESCRIPTION;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_FALLBACK_URL;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.CAMPAIGN_NAME_BORBARAD;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.CAMPAIGN_NAME_RENAISSANCE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.CAMPAIGN_NAME_RISING_DRAGON;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_AGE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_AVATAR;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_AVATAR_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_BACKGROUND;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_GENDER;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_LOOKS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_MISC;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_RACE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_ROLE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_SMALL_AVATAR;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_SMALL_AVATAR_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.DEV_NULL_STATE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.EVENT_DATE_MILLIS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.EVENT_HEADLINE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_IMAGE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ASSET_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.EVENT_TEXT;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_DSA_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_DSA_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_DSA_UNISEX;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_SHADOWRUN_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_SHADOWRUN_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_SHADOWRUN_UNISEX;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_FEMALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_MALE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.FIRST_NAME_VAMPIRE_UNISEX;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JAMES_7_LIVES_LUTHER_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JAMES_7_LIVES_LUTHER_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JAMES_7_LIVES_LUTHER_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JOHN_JUDGE_MASON_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JOHN_JUDGE_MASON_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JOHN_JUDGE_MASON_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JOHN_PRIEST_LUTHER_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JOHN_PRIEST_LUTHER_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JOHN_PRIEST_LUTHER_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JUDITH_PHANTOM_MILLER_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JUDITH_PHANTOM_MILLER_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JUDITH_PHANTOM_MILLER_LAST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JUDITH_REAVER_MASON_ALIAS;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JUDITH_REAVER_MASON_FIRST_NAME;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.JUDITH_REAVER_MASON_LAST_NAME;
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
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_RACE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_ROLE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_SMALL_AVATAR;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_SMALL_AVATAR_IMAGE_TYPE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.MOONLIGHT_STATE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.NAME_SET_NAME_DSA;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.NAME_SET_NAME_SHADOWRUN;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.NAME_SET_NAME_VAMPIRE;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ROLL;
import static de.quarian.weaver.datamodel.DatabaseTestConstants.ROLL_NAME;
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

    private static final long now = System.currentTimeMillis();
    private static final long oneDay = 1000L * 60L * 60L * 24L;

    private static long xDaysAgo(int x) {
        return now - (x * oneDay);
    }

    //TODO: fix this (what did I mean by "this"?)
    public static void setUpRoleplayingSystems(final WeaverDB weaverDB) {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        final RoleplayingSystem shadowrun = new RoleplayingSystem();
        shadowrun.roleplayingSystemName = RPS_NAME_SHADOWRUN;
        shadowrun.logoImage = RPS_LOGO_SHADOWRUN;
        shadowrun.logoImageType = RPS_LOGO_IMAGE_TYPE_SHADOWRUN;
        roleplayingSystemDAO.createRoleplayingSystem(shadowrun);

        final RoleplayingSystem dsa = new RoleplayingSystem();
        dsa.roleplayingSystemName = RPS_NAME_DSA;
        dsa.logoImage = RPS_LOGO_DSA;
        dsa.logoImageType = RPS_LOGO_IMAGE_TYPE_DSA;
        roleplayingSystemDAO.createRoleplayingSystem(dsa);

        final RoleplayingSystem vampire = new RoleplayingSystem();
        vampire.roleplayingSystemName = RPS_NAME_VAMPIRE;
        vampire.logoImage = RPS_LOGO_VAMPIRE;
        vampire.logoImageType = RPS_LOGO_IMAGE_TYPE_VAMPIRE;
        roleplayingSystemDAO.createRoleplayingSystem(vampire);
    }

    /**
     * This will insert dummy themes. For real Theme a bit more intense testing see {@link ThemeDAOTest}.
     * @param weaverDB Our target database
     */
    public static void setUpThemes(final WeaverDB weaverDB) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final DBConverters.BlobConverter blobConverter = new DBConverters.BlobConverter();

        final Theme modernTheme = new Theme();
        modernTheme.presetId = Theme.PRESET_ID_MODERN;
        modernTheme.fontId = Theme.PRESET_ID_MODERN;
        modernTheme.bannerBackgroundImage = blobConverter.convertPrimitiveToBytes("modern".getBytes());
        modernTheme.bannerBackgroundImageType = "image/jpeg";

        final Theme fantasyTheme = new Theme();
        fantasyTheme.presetId = Theme.PRESET_ID_FANTASY;
        fantasyTheme.fontId = Theme.PRESET_ID_FANTASY;
        fantasyTheme.bannerBackgroundImage = blobConverter.convertPrimitiveToBytes("fantasy".getBytes());
        fantasyTheme.bannerBackgroundImageType = "image/png";

        final Theme customTheme = new Theme();
        customTheme.presetId = Theme.PRESET_ID_CUSTOM;
        customTheme.fontId = Theme.PRESET_ID_CUSTOM;
        customTheme.bannerBackgroundImage = blobConverter.convertPrimitiveToBytes("custom".getBytes());
        customTheme.bannerBackgroundImageType = "image/jpg";

        customTheme.actionColorA = 10000;
        customTheme.actionColorR = 20000;
        customTheme.actionColorG = 30000;
        customTheme.actionColorB = 40000;

        customTheme.screenBackgroundColorA = 1;
        customTheme.screenBackgroundColorR = 2;
        customTheme.screenBackgroundColorG = 3;
        customTheme.screenBackgroundColorB = 4;

        customTheme.itemBackgroundColorA = 10;
        customTheme.itemBackgroundColorR = 20;
        customTheme.itemBackgroundColorG = 30;
        customTheme.itemBackgroundColorB = 40;

        customTheme.backgroundFontColorA = 100;
        customTheme.backgroundFontColorR = 200;
        customTheme.backgroundFontColorG = 300;
        customTheme.backgroundFontColorB = 400;

        customTheme.itemFontColorA = 1000;
        customTheme.itemFontColorR = 2000;
        customTheme.itemFontColorG = 3000;
        customTheme.itemFontColorB = 4000;

        themeDAO.createTheme(modernTheme);
        themeDAO.createTheme(fantasyTheme);
        themeDAO.createTheme(customTheme);
    }

    /***
     * Requires to run tbe following methods upfront
     * (the order is of importance)
     * 1) {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     * 2) {@link DatabaseTestUtils}.setUpThemes()
     *
     * Themes are currently assigned a bit random (it's test data after all)
     */
    public static void setUpCampaigns(final WeaverDB weaverDB) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        final long now = System.currentTimeMillis();
        final long oneDay = 1000L * 60L * 60L * 24L;

        final Campaign asiaCampaign = new Campaign();
        final RoleplayingSystem shadowrun = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_SHADOWRUN);
        final Theme shadowrunTheme = themeDAO.readThemeByID(1L);
        asiaCampaign.roleplayingSystemId = shadowrun.id;
        asiaCampaign.themeId = shadowrunTheme.id;
        asiaCampaign.campaignName = CAMPAIGN_NAME_RISING_DRAGON;
        asiaCampaign.creationDateMillis = now - (13L * oneDay);
        asiaCampaign.editDateMillis = now - (6L * oneDay);
        asiaCampaign.lastUsedDataMillis = now - (4L * oneDay);
        campaignDAO.createCampaign(asiaCampaign);

        final Campaign europeCampaign = new Campaign();
        final RoleplayingSystem vampire = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_VAMPIRE);
        final Theme vampireTheme = themeDAO.readThemeByID(3L);
        europeCampaign.roleplayingSystemId = vampire.id;
        europeCampaign.themeId = vampireTheme.id;
        europeCampaign.campaignName = CAMPAIGN_NAME_RENAISSANCE;
        europeCampaign.creationDateMillis = now - (11L * oneDay);
        europeCampaign.editDateMillis = now - (7L * oneDay);
        europeCampaign.lastUsedDataMillis = now - (3L * oneDay);
        campaignDAO.createCampaign(europeCampaign);

        final Campaign aventurienCampaign = new Campaign();
        final RoleplayingSystem dsa = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_DSA);
        final Theme dsaTheme = themeDAO.readThemeByID(2L);
        aventurienCampaign.roleplayingSystemId = dsa.id;
        aventurienCampaign.themeId = dsaTheme.id;
        aventurienCampaign.campaignName = CAMPAIGN_NAME_BORBARAD;
        aventurienCampaign.creationDateMillis = now - (12L * oneDay);
        aventurienCampaign.editDateMillis = now - (8L * oneDay);
        aventurienCampaign.lastUsedDataMillis = now - (2L * oneDay);
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
        final CharacterHeader moonlightCharacterHeaderInput = new CharacterHeader();

        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByName(CAMPAIGN_NAME_RISING_DRAGON);

        moonlightCharacterHeaderInput.campaignId = campaign.id;
        final Date moonlightEditDate = new Date();
        final Date moonlightCreationDate = new Date(moonlightEditDate.getTime() - (1000L * 60L * 30L));
        moonlightCharacterHeaderInput.creationDateMillis = moonlightCreationDate.getTime();
        moonlightCharacterHeaderInput.editDateMillis = moonlightEditDate.getTime();
        moonlightCharacterHeaderInput.firstName = MOONLIGHT_FIRST_NAME;
        moonlightCharacterHeaderInput.character_alias = MOONLIGHT_ALIAS;
        moonlightCharacterHeaderInput.lastName = MOONLIGHT_LAST_NAME;
        moonlightCharacterHeaderInput.race = MOONLIGHT_RACE;
        moonlightCharacterHeaderInput.gender = MOONLIGHT_GENDER;
        moonlightCharacterHeaderInput.smallAvatar = MOONLIGHT_SMALL_AVATAR;
        moonlightCharacterHeaderInput.smallAvatarImageType = MOONLIGHT_SMALL_AVATAR_IMAGE_TYPE;
        moonlightCharacterHeaderInput.character_role = MOONLIGHT_ROLE;
        moonlightCharacterHeaderInput.state = MOONLIGHT_STATE;

        final CharacterHeader devNullCharacterHeaderInput = new CharacterHeader();
        final Date devNullEditDate = new Date();
        final Date devNullCreationDate = new Date(devNullEditDate.getTime() - (1000L * 60L * 60L * 2L));
        devNullCharacterHeaderInput.campaignId = campaign.id;
        devNullCharacterHeaderInput.creationDateMillis = devNullCreationDate.getTime();
        devNullCharacterHeaderInput.editDateMillis = devNullEditDate.getTime();
        devNullCharacterHeaderInput.firstName = DEV_NULL_FIRST_NAME;
        devNullCharacterHeaderInput.character_alias = DEV_NULL_ALIAS;
        devNullCharacterHeaderInput.lastName = DEV_NULL_LAST_NAME;
        devNullCharacterHeaderInput.race = DEV_NULL_RACE;
        devNullCharacterHeaderInput.gender = DEV_NULL_GENDER;
        devNullCharacterHeaderInput.smallAvatar = DEV_NULL_SMALL_AVATAR;
        devNullCharacterHeaderInput.smallAvatarImageType = DEV_NULL_SMALL_AVATAR_IMAGE_TYPE;
        devNullCharacterHeaderInput.character_role = DEV_NULL_ROLE;
        devNullCharacterHeaderInput.state = DEV_NULL_STATE;

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final long moonlightCharacterHeaderId = characterDAO.createCharacterHeader(moonlightCharacterHeaderInput);
        final long devNullCharacterHeaderId = characterDAO.createCharacterHeader(devNullCharacterHeaderInput);

        final CharacterBody moonlightCharacterBodyInput = new CharacterBody();
        moonlightCharacterBodyInput.characterHeaderId = moonlightCharacterHeaderId;
        moonlightCharacterBodyInput.age = MOONLIGHT_AGE;
        moonlightCharacterBodyInput.looks = MOONLIGHT_LOOKS;
        moonlightCharacterBodyInput.background = MOONLIGHT_BACKGROUND;
        moonlightCharacterBodyInput.miscellaneous = MOONLIGHT_MISC;
        moonlightCharacterBodyInput.avatar = MOONLIGHT_AVATAR;
        moonlightCharacterBodyInput.avatarImageType = MOONLIGHT_AVATAR_IMAGE_TYPE;

        final CharacterBody devNullCharacterBodyInput = new CharacterBody();
        devNullCharacterBodyInput.characterHeaderId = devNullCharacterHeaderId;
        devNullCharacterBodyInput.age = DEV_NULL_AGE;
        devNullCharacterBodyInput.looks = DEV_NULL_LOOKS;
        devNullCharacterBodyInput.background = DEV_NULL_BACKGROUND;
        devNullCharacterBodyInput.miscellaneous = DEV_NULL_MISC;
        devNullCharacterBodyInput.avatar = DEV_NULL_AVATAR;
        devNullCharacterBodyInput.avatarImageType = DEV_NULL_AVATAR_IMAGE_TYPE;

        characterDAO.createCharacterBody(moonlightCharacterBodyInput);
        characterDAO.createCharacterBody(devNullCharacterBodyInput);
    }

    /***
     * Requires to run tbe following method upfront:
     *  - {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     *  - {@link DatabaseTestUtils}.setUpThemes()
     *  - {@link DatabaseTestUtils}.setUpCampaigns()
     *
     *  Here we use a bunch of irrational dummy data, we just need
     *  the names, alias and date sections to test sorting.
     */
    public static void setUpCharactersForSortingTests(final WeaverDB weaverDB) {
        // We are only interested in sorting by names and dates, so we leave the rest as it is

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long campaignId = campaignDAO.readCampaignByName(CAMPAIGN_NAME_RISING_DRAGON).id;

        final CharacterHeader alexWarner = new CharacterHeader();
        alexWarner.firstName = ALEX_MAGIC_WARNER_FIRST_NAME;
        alexWarner.character_alias = ALEX_MAGIC_WARNER_ALIAS;
        alexWarner.lastName = ALEX_MAGIC_WARNER_LAST_NAME;
        alexWarner.creationDateMillis = xDaysAgo(144);
        alexWarner.editDateMillis = xDaysAgo(27);
        alexWarner.campaignId = campaignId;
        final long alexHunterCharacterHeaderId = characterDAO.createCharacterHeader(alexWarner);

        final CharacterBody alexHunterCharacterBody = new CharacterBody();
        alexHunterCharacterBody.characterHeaderId = alexHunterCharacterHeaderId;
        characterDAO.createCharacterBody(alexHunterCharacterBody);

        final CharacterHeader jamesLuther = new CharacterHeader();
        jamesLuther.firstName = JAMES_7_LIVES_LUTHER_FIRST_NAME;
        jamesLuther.character_alias = JAMES_7_LIVES_LUTHER_ALIAS;
        jamesLuther.lastName = JAMES_7_LIVES_LUTHER_LAST_NAME;
        jamesLuther.creationDateMillis = xDaysAgo(120);
        jamesLuther.editDateMillis = xDaysAgo(60);
        jamesLuther.campaignId = campaignId;
        final long jamesLutherCharacterHeaderId = characterDAO.createCharacterHeader(jamesLuther);

        final CharacterBody jamesLutherCharacterBody = new CharacterBody();
        jamesLutherCharacterBody.characterHeaderId = jamesLutherCharacterHeaderId;
        characterDAO.createCharacterBody(jamesLutherCharacterBody);

        final CharacterHeader johnLuther = new CharacterHeader();
        johnLuther.firstName = JOHN_PRIEST_LUTHER_FIRST_NAME;
        johnLuther.character_alias = JOHN_PRIEST_LUTHER_ALIAS;
        johnLuther.lastName = JOHN_PRIEST_LUTHER_LAST_NAME;
        johnLuther.creationDateMillis = xDaysAgo(12);
        johnLuther.editDateMillis = xDaysAgo(10);
        johnLuther.campaignId = campaignId;
        final long johnLutherCharacterHeaderId = characterDAO.createCharacterHeader(johnLuther);

        final CharacterBody johnLutherCharacterBody = new CharacterBody();
        johnLutherCharacterBody.characterHeaderId = johnLutherCharacterHeaderId;
        characterDAO.createCharacterBody(johnLutherCharacterBody);

        final CharacterHeader johnMason = new CharacterHeader();
        johnMason.firstName = JOHN_JUDGE_MASON_FIRST_NAME;
        johnMason.character_alias = JOHN_JUDGE_MASON_ALIAS;
        johnMason.lastName = JOHN_JUDGE_MASON_LAST_NAME;
        johnMason.creationDateMillis = xDaysAgo(23);
        johnMason.editDateMillis = xDaysAgo(12);
        johnMason.campaignId = campaignId;
        final long johnMasonCharacterHeaderId = characterDAO.createCharacterHeader(johnMason);

        final CharacterBody johnMasonCharacterBody = new CharacterBody();
        johnMasonCharacterBody.characterHeaderId = johnMasonCharacterHeaderId;
        characterDAO.createCharacterBody(johnMasonCharacterBody);

        final CharacterHeader judithMason = new CharacterHeader();
        judithMason.firstName = JUDITH_REAVER_MASON_FIRST_NAME;
        judithMason.character_alias = JUDITH_REAVER_MASON_ALIAS;
        judithMason.lastName = JUDITH_REAVER_MASON_LAST_NAME;
        judithMason.creationDateMillis = xDaysAgo(122);
        judithMason.editDateMillis = xDaysAgo(99);
        judithMason.campaignId = campaignId;
        final long judithMasonCharacterHeaderId = characterDAO.createCharacterHeader(judithMason);

        final CharacterBody judithMasonCharacterBody = new CharacterBody();
        judithMasonCharacterBody.characterHeaderId = judithMasonCharacterHeaderId;
        characterDAO.createCharacterBody(judithMasonCharacterBody);

        final CharacterHeader judithMiller = new CharacterHeader();
        judithMiller.firstName = JUDITH_PHANTOM_MILLER_FIRST_NAME;
        judithMiller.character_alias = JUDITH_PHANTOM_MILLER_ALIAS;
        judithMiller.lastName = JUDITH_PHANTOM_MILLER_LAST_NAME;
        judithMiller.campaignId = campaignId;
        judithMiller.creationDateMillis = xDaysAgo(14);
        judithMiller.editDateMillis = xDaysAgo(2);
        final long judithMillerCharacterBodyId = characterDAO.createCharacterHeader(judithMiller);

        final CharacterBody judithMillerCharacterBody = new CharacterBody();
        judithMillerCharacterBody.characterHeaderId = judithMillerCharacterBodyId;
        characterDAO.createCharacterBody(judithMillerCharacterBody);
    }

    /***
     * Requires to run tbe follow                                                                                                                                                                                                            ing method upfront:
     *  - {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     *  - {@link DatabaseTestUtils}.setUpThemes()
     *  - {@link DatabaseTestUtils}.setUpCampaigns()
     *  - {@link DatabaseTestUtils}.setUpCharacters()
     */
    public static void setUpTags(final WeaverDB weaverDB) {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final Tag ucasTag = new Tag();
        ucasTag.tag = "UCAS";
        ucasTag.roleplayingSystemId = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_SHADOWRUN).id;

        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final long ucasTagID = characterDAO.createTag(ucasTag);
        final long characterHeaderID = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS).id;

        final TagToCharacterHeader moonlightTagToCharacterHeader = new TagToCharacterHeader();
        moonlightTagToCharacterHeader.tagId = ucasTagID;
        moonlightTagToCharacterHeader.characterHeaderId = characterHeaderID;
        characterDAO.createTagToCharacterHeader(moonlightTagToCharacterHeader);
    }

    /***
     * Requires to run tbe follow                                                                                                                                                                                                            ing method upfront:
     *  - {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     *  - {@link DatabaseTestUtils}.setUpThemes()
     *  - {@link DatabaseTestUtils}.setUpCampaigns()
     *  - {@link DatabaseTestUtils}.setUpCharacters()
     */
    public static void setUpEvents(final WeaverDB weaverDB) {
        final CharacterDAO characterDAO = weaverDB.characterDAO();

        final Event event = new Event();
        event.eventDateMillis = EVENT_DATE_MILLIS;
        event.headline = EVENT_HEADLINE;
        event.text = EVENT_TEXT;

        final EventToCharacterHeader eventToCharacterHeader = new EventToCharacterHeader();
        final CharacterHeader moonlightCharacterHeader = weaverDB.characterDAO().readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        eventToCharacterHeader.characterHeaderId = moonlightCharacterHeader.id;
        eventToCharacterHeader.eventId = characterDAO.createEvent(event);
        characterDAO.createEventToCharacterHeader(eventToCharacterHeader);
    }

    /***
     * Requires to run tbe follow                                                                                                                                                                                                            ing method upfront:
     *  - {@link DatabaseTestUtils}.setUpRoleplayingSystems()
     *  - {@link DatabaseTestUtils}.setUpThemes()
     *  - {@link DatabaseTestUtils}.setUpCampaigns()
     *  - {@link DatabaseTestUtils}.setUpCharacters()
     *  - {@link DatabaseTestUtils}.setUpEvents()
     */
    public static void setUpAssets(final WeaverDB weaverDB) {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(MOONLIGHT_ALIAS);
        final List<Event> events = characterDAO.readEventsForCharacterHeaderId(moonlight.id);
        final Event event = events.get(0);

        final AssetDAO assetDAO = weaverDB.assetDAO();

        final Asset imageAsset = new Asset();
        imageAsset.eventId = event.id;
        final long oneDay = 1000L * 60L * 60L * 24L;
        imageAsset.endOfLifetimeTimestamp = System.currentTimeMillis() + oneDay;
        imageAsset.contentLocallyPresent = true;
        imageAsset.assetName = ASSET_NAME;
        imageAsset.assetDescription = ASSET_DESCRIPTION;
        imageAsset.asset = ASSET_IMAGE;
        imageAsset.assetType = ASSET_IMAGE_TYPE;
        imageAsset.fallbackUrl = ASSET_FALLBACK_URL;
        imageAsset.campaignName = CAMPAIGN_NAME_RISING_DRAGON;
        imageAsset.id = assetDAO.createAsset(imageAsset);
    }

    /***
     * Requires to run tbe follow                                                                                                                                                                                                            ing method upfront:
     *  - {@link DatabaseTestUtils}.setUpCharacters()
     */
    public static void setUpRolls(final WeaverDB weaverDB) {
        final CharacterDAO characterDAO = weaverDB.characterDAO();

        final Roll roll = new Roll();
        roll.roll = ROLL;
        roll.rollName = ROLL_NAME;

        final long rollId = characterDAO.createRoll(roll);

        final CharacterHeader moonlight = characterDAO.readCharacterHeaderByAlias(DatabaseTestConstants.MOONLIGHT_ALIAS);
        final RollToCharacterHeader rollToCharacterHeader = new RollToCharacterHeader();
        rollToCharacterHeader.rollId = rollId;
        rollToCharacterHeader.characterHeaderId = moonlight.id;
        characterDAO.createRollToCharacterHeader(rollToCharacterHeader);
    }

    public static void setUpPlayerCharacters(final WeaverDB weaverDB) {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final RoleplayingSystem shadowrun = roleplayingSystemDAO.readRoleplayingSystemsByName(RPS_NAME_SHADOWRUN);

        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long shadowrunCampaignId = campaignDAO.readCampaignByName(CAMPAIGN_NAME_RISING_DRAGON).id;

        final PlayerCharacter amanda = new PlayerCharacter();
        amanda.campaignId = shadowrunCampaignId;
        amanda.roleplayingSystemId = shadowrun.id;
        amanda.playerName = "Tim";
        amanda.playerCharacterName = "Amanda";
        amanda.playerCharacterAvatarImageType = "image/jpeg";
        amanda.playerCharacterAvatar = "avatar".getBytes();
        amanda.characterHighlightColorA = 1;
        amanda.characterHighlightColorR = 2;
        amanda.characterHighlightColorG = 3;
        amanda.characterHighlightColorB = 4;

        final PlayerCharacterDAO playerCharacterDAO = weaverDB.playerCharacterDAO();
        playerCharacterDAO.createPlayerCharacter(amanda);
    }
}
