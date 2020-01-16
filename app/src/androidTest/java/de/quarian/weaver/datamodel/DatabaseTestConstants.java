package de.quarian.weaver.datamodel;

public final class DatabaseTestConstants {

    public static final String RPS_NAME_SHADOWRUN = "Shadowrun";
    public static final byte[] RPS_LOGO_SHADOWRUN = "shadowrun.jpg".getBytes();
    public static final String RPS_LOGO_IMAGE_TYPE_SHADOWRUN = "image/jpg";

    public static final String RPS_NAME_DSA = "DSA";
    public static final byte[] RPS_LOGO_DSA = "dsa.png".getBytes();
    public static final String RPS_LOGO_IMAGE_TYPE_DSA = "image/png";

    public static final String RPS_NAME_VAMPIRE = "Vampire the Requiem";
    public static final byte[] RPS_LOGO_VAMPIRE = "vampire.png".getBytes();
    public static final String RPS_LOGO_IMAGE_TYPE_VAMPIRE = "image/png";

    public static final String CAMPAIGN_NAME_RISING_DRAGON = "Rising Dragon";
    public static final String CAMPAIGN_NAME_RENAISSANCE = "Renaissance";
    public static final String CAMPAIGN_NAME_BORBARAD = "Die Borbarad Kampagne";

    public static final String CAMPAIGN_SYNOPSIS_RISING_DRAGON = "After the Fall of the...";
    public static final String CAMPAIGN_SYNOPSIS_RENAISSANCE = "The Medici, a family of great Power, strives to...";
    public static final String CAMPAIGN_SYNOPSIS_BORBARAD = "In the beginning there were only...";

    public static final String NAME_SET_NAME_SHADOWRUN = "Shadowrun Name Set";
    public static final String NAME_SET_NAME_DSA = "DSA Name Set";
    public static final String NAME_SET_NAME_VAMPIRE = "Vampire the Requiem Name Set";

    public static final String LAST_NAME_SHADOWRUN = " > null";
    public static final String LAST_NAME_VAMPIRE = "Smith";
    public static final String LAST_NAME_DSA = "Larinow";

    public static final String FIRST_NAME_SHADOWRUN_MALE = "Spike";
    public static final String FIRST_NAME_SHADOWRUN_FEMALE = "Crash Burn";
    public static final String FIRST_NAME_SHADOWRUN_UNISEX = "Dev";

    public static final String FIRST_NAME_VAMPIRE_MALE = "Jakob";
    public static final String FIRST_NAME_VAMPIRE_FEMALE = "Miranda";
    public static final String FIRST_NAME_VAMPIRE_UNISEX = "Alex";

    public static final String FIRST_NAME_DSA_MALE = "Travidan";
    public static final String FIRST_NAME_DSA_FEMALE = "Alina";
    public static final String FIRST_NAME_DSA_UNISEX = "Drew";

    // Character Header (Moonlight)
    public static final String MOONLIGHT_FIRST_NAME = "Amy";
    public static final String MOONLIGHT_ALIAS = "Moonlight";
    public static final String MOONLIGHT_LAST_NAME = "Jameson";
    public static final String MOONLIGHT_RACE = "Elv";
    public static final int MOONLIGHT_GENDER = Constants.CharacterGender.FEMALE.getValue();
    public static final byte[] MOONLIGHT_SMALL_AVATAR = "smallAvatar".getBytes();
    public static final String MOONLIGHT_SMALL_AVATAR_IMAGE_TYPE = "image/png";
    public static final String MOONLIGHT_ROLE = "Technomancer";
    public static final String MOONLIGHT_STATE = "Happy";

    // Character Body (Moonlight)
    public static final String MOONLIGHT_AGE = "Pretty Young";
    public static final String MOONLIGHT_LOOKS = "Good";
    public static final String MOONLIGHT_BACKGROUND = "She learned Stuff...";
    public static final String MOONLIGHT_MISC = "...and likes trains";
    public static final byte[] MOONLIGHT_AVATAR = "avatar".getBytes();
    public static final String MOONLIGHT_AVATAR_IMAGE_TYPE = "image/gif";

    // Character Header (Dev > null)
    public static final String DEV_NULL_FIRST_NAME = "Jake";
    public static final String DEV_NULL_ALIAS = "Dev > Null";
    public static final String DEV_NULL_LAST_NAME = "Henderson";
    public static final String DEV_NULL_RACE = "Human";
    public static final int DEV_NULL_GENDER = Constants.CharacterGender.MALE.getValue();
    public static final byte[] DEV_NULL_SMALL_AVATAR = "smallRoundAvatar".getBytes();
    public static final String DEV_NULL_SMALL_AVATAR_IMAGE_TYPE = "image/jpeg";
    public static final String DEV_NULL_ROLE = "Hacker";
    public static final String DEV_NULL_STATE = "Hunted";

    // Character Body (Dev > null)
    public static final String DEV_NULL_AGE = "Middle Age";
    public static final String DEV_NULL_LOOKS = "Bored";
    public static final String DEV_NULL_BACKGROUND = "He hacks Stuff...";
    public static final String DEV_NULL_MISC = "...and loves trains even more";
    public static final byte[] DEV_NULL_AVATAR = "devNullAvatar".getBytes();
    public static final String DEV_NULL_AVATAR_IMAGE_TYPE = "image/png";

    public static final long EVENT_DATE_MILLIS = System.currentTimeMillis();
    public static final String EVENT_HEADLINE = "Event Headline";
    public static final String EVENT_TEXT = "Event Text";
    public static final byte[] EVENT_IMAGE = "Event Image".getBytes();
    public static final String EVENT_IMAGE_TYPE = "image/jpeg";
    public static final byte[] EVENT_FILE = "Event Image".getBytes();
    public static final String EVENT_FILE_TYPE = "doc/pdf";

    public static final String ROLL = "12/14/10 (12)";
    public static final String ROLL_NAME = "Perception";
}
