package de.quarian.weaver.dev;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import androidx.annotation.DrawableRes;
import de.quarian.weaver.R;
import de.quarian.weaver.database.AssetDAO;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.CharacterDAO;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Event;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.util.Utils;

public class DemoDataSetInjector {

    private final Context context;

    public DemoDataSetInjector(final Context context) {
        this.context = context;
    }

    // TODO: It might be best to develop this on the go
    public void setDemoState(final WeaverDB weaverDB) {
        AsyncTask.execute(() -> {
            Looper.prepare();
            weaverDB.clearAllTables();
            final long[] rpsIds = setUpRoleplayingSystems(weaverDB);
            final long[] themeIds = setUpThemes(weaverDB);
            final long[] campaignIds = setUpCampaigns(weaverDB, rpsIds, themeIds);

            final long[] eventIds = setUpEvents(weaverDB);
            final long[] assetIds = setUpAssets(weaverDB, eventIds);

            Toast.makeText(context, "Demo State initialized", Toast.LENGTH_SHORT).show();
        });
    }

    private long[] setUpRoleplayingSystems(final WeaverDB weaverDB) {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final long[] ids = new long[4];

        final RoleplayingSystem sr = new RoleplayingSystem();
        sr.roleplayingSystemName = "Shadowrun";
        sr.logoImage = convertDrawableResToBytes(R.drawable.shadowrun_logo);
        sr.logoImageType = "image/jpg";
        ids[0] = roleplayingSystemDAO.createRoleplayingSystem(sr);

        final RoleplayingSystem dsa = new RoleplayingSystem();
        dsa.roleplayingSystemName = "DSA 4.0";
        dsa.logoImage = convertDrawableResToBytes(R.drawable.dsa_logo);
        dsa.logoImageType = "image/jpg";
        ids[1] = roleplayingSystemDAO.createRoleplayingSystem(dsa);

        final RoleplayingSystem hunter = new RoleplayingSystem();
        hunter.roleplayingSystemName = "Hunter";
        hunter.logoImage = convertDrawableResToBytes(R.drawable.wod_hunter_logo);
        hunter.logoImageType = "image/png";
        ids[2] = roleplayingSystemDAO.createRoleplayingSystem(hunter);

        final RoleplayingSystem dnd = new RoleplayingSystem();
        dnd.roleplayingSystemName = "DnD";
        dnd.logoImage = convertDrawableResToBytes(R.drawable.dnd_logo);
        dnd.logoImageType = "image/jpg";
        ids[3] = roleplayingSystemDAO.createRoleplayingSystem(dnd);

        return ids;
    }

    private long[] setUpThemes(final WeaverDB weaverDB) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final long[] themeIDs = new long[4];

        // TODO: define themes
        final Theme srTheme = new Theme();
        srTheme.presetId = Theme.PRESET_ID_MODERN;
        srTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.shadowrun_banner);
        srTheme.bannerBackgroundImageType = "image/jpeg";

        // Black
        srTheme.screenBackgroundColorA = 255;
        srTheme.screenBackgroundColorR = 0;
        srTheme.screenBackgroundColorG = 0;
        srTheme.screenBackgroundColorB = 0;

        // Red, dunno why
        srTheme.backgroundFontColorA = 255;
        srTheme.backgroundFontColorR = 255;
        srTheme.backgroundFontColorG = 0;
        srTheme.backgroundFontColorB = 0;

        final Theme dsaTheme = new Theme();
        dsaTheme.presetId = Theme.PRESET_ID_FANTASY;
        dsaTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.g7_banner);
        dsaTheme.bannerBackgroundImageType = "image/png";

        // Black
        dsaTheme.screenBackgroundColorA = 255;
        dsaTheme.screenBackgroundColorR = 0;
        dsaTheme.screenBackgroundColorG = 0;
        dsaTheme.screenBackgroundColorB = 0;

        // Red, dunno why
        dsaTheme.backgroundFontColorA = 255;
        dsaTheme.backgroundFontColorR = 255;
        dsaTheme.backgroundFontColorG = 0;
        dsaTheme.backgroundFontColorB = 0;

        final Theme hunterTheme = new Theme();
        hunterTheme.presetId = Theme.PRESET_ID_CUSTOM;
        hunterTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.wod_hunter_banner);
        hunterTheme.bannerBackgroundImageType = "image/jpg";
        hunterTheme.bannerBackgroundImageType = "image/png";

        // Black
        hunterTheme.screenBackgroundColorA = 255;
        hunterTheme.screenBackgroundColorR = 0;
        hunterTheme.screenBackgroundColorG = 0;
        hunterTheme.screenBackgroundColorB = 0;

        // Red, dunno why
        hunterTheme.backgroundFontColorA = 255;
        hunterTheme.backgroundFontColorR = 255;
        hunterTheme.backgroundFontColorG = 0;
        hunterTheme.backgroundFontColorB = 0;

        final Theme dnDTheme = new Theme();
        dnDTheme.presetId = Theme.PRESET_ID_FANTASY;
        dnDTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.dnd_banner);
        dnDTheme.bannerBackgroundImageType = "image/jpeg";
        dnDTheme.bannerBackgroundImageType = "image/png";

        // Black
        dnDTheme.screenBackgroundColorA = 255;
        dnDTheme.screenBackgroundColorR = 0;
        dnDTheme.screenBackgroundColorG = 0;
        dnDTheme.screenBackgroundColorB = 0;

        // Red, dunno why
        dnDTheme.backgroundFontColorA = 255;
        dnDTheme.backgroundFontColorR = 255;
        dnDTheme.backgroundFontColorG = 0;
        dnDTheme.backgroundFontColorB = 0;

        themeIDs[0] = themeDAO.createTheme(srTheme);
        themeIDs[1] = themeDAO.createTheme(dsaTheme);
        themeIDs[2] = themeDAO.createTheme(hunterTheme);
        themeIDs[3] = themeDAO.createTheme(dnDTheme);
        return themeIDs;
    }

    private long[] setUpCampaigns(final WeaverDB weaverDB, final long[] roleplayingSystemIDs, final long[] themeIds) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long[] campaignIds = new long[4];
        final Date today = new Date();

        final Date fiveMinutesAgo = new Date(today.getTime() - 1000L * 60L * 5L);
        final Date oneHourAgo = new Date(today.getTime() - 1000L * 60L * 60L);
        final Date yesterday = new Date(today.getTime() - 1000L * 60L * 60L * 24L);

        final Campaign risingDragon = new Campaign();
        risingDragon.archived = false;
        risingDragon.roleplayingSystemId = roleplayingSystemIDs[0];
        risingDragon.themeId = themeIds[0];
        risingDragon.creationDateMillis = yesterday.getTime();
        risingDragon.editDateMillis = oneHourAgo.getTime();
        risingDragon.lastUsedDataMillis = fiveMinutesAgo.getTime();
        risingDragon.campaignName = "Rising Dragon";
        risingDragon.synopsis = "For Years now the power has been split between the usual big " +
                "players, but there are rumors in the shadows that an ancient one woke up again " +
                "after taking a long rest. The question is now, which side to take, to somehow " +
                "avoid getting struck down in the emerging power struggle.";
        campaignIds[0] = campaignDAO.createCampaign(risingDragon);

        final Date fourteenMonthAgo = new Date(today.getTime() - 1000L * 60L * 60L * 24L * 30L * 14L + 1000L * 60L * 60L * 24L * 5L);
        final Date twelveDaysAgo = new Date(today.getTime() - 1000L * 60L * 60L * 12L);
        final Date aWeekAgo = new Date(today.getTime() - 1000L * 60L * 60L * 7L);

        final Campaign g7Campaign = new Campaign();
        g7Campaign.archived = true;
        g7Campaign.roleplayingSystemId = roleplayingSystemIDs[1];
        g7Campaign.themeId = themeIds[1];
        g7Campaign.creationDateMillis = fourteenMonthAgo.getTime();
        g7Campaign.editDateMillis = twelveDaysAgo.getTime();
        g7Campaign.lastUsedDataMillis = aWeekAgo.getTime();
        g7Campaign.campaignName = "Die 7 Gezeichneten";
        g7Campaign.synopsis = "Things are usually rather relaxed during the festive days in early " +
                "summer. But there are powers struggling to bring back an ancient sorcerer who " +
                "is hungry for power and won't stop to sacrifice anything in his way. So the " +
                "burden of stopping him falls upon an unlucky group of adventurers, chosen by " +
                "manifestations of certain marks of old times which are neither magical or holy " +
                "by nature but will grant them otherworldly powers so far unknown.";
        campaignIds[1] = campaignDAO.createCampaign(g7Campaign);

        final Date fiftyDaysAgo = new Date(today.getTime() - 1000L * 60L * 60L * 24L * 50L);
        final Date fourteenDaysAgo = new Date(today.getTime() - 1000L * 60L * 60L * 24L * 14L);
        final Date seventyTwoHoursAgo = new Date(today.getTime() - 1000L * 60L * 60L * 72L);

        final Campaign hunterCampaign = new Campaign();
        hunterCampaign.archived = false;
        hunterCampaign.roleplayingSystemId = roleplayingSystemIDs[2];
        hunterCampaign.themeId = themeIds[2];
        hunterCampaign.creationDateMillis = fiftyDaysAgo.getTime();
        hunterCampaign.editDateMillis = fourteenDaysAgo.getTime();
        hunterCampaign.lastUsedDataMillis = seventyTwoHoursAgo.getTime();
        hunterCampaign.campaignName = "Night Train";
        hunterCampaign.synopsis = "About three month ago you moved into the city, it was time " +
                "for a change. After things went nuclear with your partner, there hasn't been " +
                "anything holding you back. And you have never been one to be picky about jobs " +
                "so you applied for doing the night shift in a video store. After hours of " +
                "handing out questionable porn movies to a bunch of creeps and splatter stuff " +
                "to slightly nervous teens you finally close the store. Only a few hours left " +
                "till dawn. You board the train, basically all by yourself if hadn't been for " +
                "the guy sleeping on the bench. Then suddenly you saw... something... move " +
                "in the corner of your eye. Your brain kinda blacked out, all you remember is " +
                "hearing people approaching screaming at you: \"Duck down, dummy\". That was the " +
                "night when the world stopped being what you thought it has been.";
        campaignIds[2] = campaignDAO.createCampaign(hunterCampaign);

        final Date aMonthAgo = new Date(today.getTime() - 1000L * 60L * 60L * 24L * 30L);
        final Date eightDaysAgo = new Date(today.getTime() - 1000L * 60L * 60L * 24L * 8L);
        final Date twelveMinutesAgo = new Date(today.getTime() - 1000L * 60L * 12L);

        final Campaign dnDCampaign = new Campaign();
        dnDCampaign.archived = false;
        dnDCampaign.roleplayingSystemId = roleplayingSystemIDs[3];
        dnDCampaign.themeId = themeIds[3];
        dnDCampaign.creationDateMillis = aMonthAgo.getTime();
        dnDCampaign.editDateMillis = eightDaysAgo.getTime();
        dnDCampaign.lastUsedDataMillis = twelveMinutesAgo.getTime();
        dnDCampaign.campaignName = "Bad Blood";
        dnDCampaign.synopsis = "Your families property is not huge, but nonetheless you need " +
                "a few days to cross it all the way down along the coastline. In the past " +
                "there was always a bit trouble with bandits, but during your childhood " +
                "things were quite calm. You enjoyed the life of a relatively wealthy offspring " +
                "as one of the local rulers. But over the years trouble starts to erupt " +
                "and you were trained to deal with trouble - in whatever way seems to fit " +
                "the circumstances. So you gather a few people to investigate the situation " +
                "and start to travel down south. But instead of finding a bunch of rebels you " +
                "stumble upon something which will challenge your loyalty to the family.";
        campaignIds[3] = campaignDAO.createCampaign(dnDCampaign);

        return campaignIds;
    }

    private Byte[] convertDrawableResToBytes(@DrawableRes final int drawableRes) {
        final Drawable srBanner = context.getResources().getDrawable(drawableRes, null);
        final Bitmap srBannerBitmap = ((BitmapDrawable) srBanner).getBitmap();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        srBannerBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final DBConverters.BlobConverter converter = new DBConverters.BlobConverter();
        return converter.convertPrimitiveToBytes(outputStream.toByteArray());
    }

    private long[] setUpEvents(final WeaverDB weaverDB) {
        final CharacterDAO characterDAO = weaverDB.characterDAO();
        final long[] eventIds = new long[4];

        final Event eventA = new Event();
        eventA.eventDateMillis = 0L;
        eventA.headline = "Arrival in Berlin";
        eventA.text = "Today, after month of planning, the players finally arrive in Berlin.";
        eventIds[0] = characterDAO.createEvent(eventA);

        final Event eventB = new Event();
        eventB.eventDateMillis = 0L;
        eventB.headline = "Sightseeing";
        eventB.text = "So of course, one of the first things which were up: sightseeing";
        eventIds[1] = characterDAO.createEvent(eventB);

        final Event eventC = new Event();
        eventC.eventDateMillis = 0L;
        eventC.headline = "Weired Poetry";
        eventC.text = "Suddenly it started happening. This was just the first of many " +
                "letters which, under mysterious circumstances, found the way into the " +
                "groups possession.";
        eventIds[2] = characterDAO.createEvent(eventC);

        final Event eventD = new Event();
        eventD.eventDateMillis = 0L;
        eventD.headline = "Navigating the City";
        eventD.text = "Finding your way through the city might be trouble for those not " +
                "familiar with the city. So the characters grabbed an up to date plan " +
                "for the public transport system.";
        eventIds[3] = characterDAO.createEvent(eventD);
        return eventIds;
    }

    private long[] setUpAssets(final WeaverDB weaverDB, final long[] eventIds) {
        final long[] assetIds = new long[4];

        // A permanent local asset
        final Asset berlin_bear = new Asset();
        berlin_bear.eventId = eventIds[0];
        berlin_bear.campaignName = "Night Train";
        berlin_bear.endOfLifetimeTimestamp = 0L;
        berlin_bear.contentLocallyPresent = true;
        berlin_bear.assetName = "Berliner Bear";
        berlin_bear.assetDescription = "Logo for the city of Berlin.";
        berlin_bear.assetType = "image/png";
        berlin_bear.asset = convertDrawableResToBytes(R.drawable.demo_image_berlin_bear);
        berlin_bear.fallbackUrl = "";

        // An asset upcoming for deletion
        final Asset berlin_gate = new Asset();
        berlin_gate.eventId = eventIds[1];
        berlin_gate.campaignName = "Night Train";
        berlin_gate.endOfLifetimeTimestamp = System.currentTimeMillis() + Utils.ABOUT_A_MONTH;
        berlin_gate.contentLocallyPresent = true;
        berlin_gate.assetName = "Brandenburg Gate";
        berlin_gate.assetDescription = "Image of the \"Brandenburger Tor\" in Berlin";
        berlin_gate.assetType = "";
        berlin_gate.asset = convertDrawableResToBytes(R.drawable.demo_image_brandenburg_gate);
        berlin_gate.fallbackUrl = "";

        // An asset without fallback url which is already up for deletion

        final InputStream inputStream = context.getResources().openRawResource(R.raw.poem);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String rawPoem = "";
        try {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rawPoem = rawPoem.concat(line).concat("\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        final Utils.ByteArrayConverter byteArrayConverter = new Utils.ByteArrayConverter();

        final Asset poem = new Asset();
        poem.eventId = eventIds[2];
        poem.campaignName = "Night Train";
        poem.endOfLifetimeTimestamp = System.currentTimeMillis() - Utils.ONE_DAY;
        poem.contentLocallyPresent = true;
        poem.assetName = "All My Friends Are Finding New Beliefs";
        poem.assetDescription = "Poem by Christian Wiman, January 2020";
        poem.assetType = "text/plain";
        poem.asset = byteArrayConverter.fromPrimitive(rawPoem.getBytes());
        poem.fallbackUrl = "";

        /*
         Should not happen = An asset with fallback url which is up for deletion.
         Because we only assign such an url when local content gets deleted.
         */

        // A deleted asset with fallback url
        final Asset berlin_bvg_plan = new Asset();
        berlin_bvg_plan.eventId = eventIds[3];
        berlin_bvg_plan.campaignName = "Night Train";
        berlin_bvg_plan.endOfLifetimeTimestamp = 1588505035523L; // 3rd of May 2020
        berlin_bvg_plan.contentLocallyPresent = false;
        berlin_bvg_plan.assetName = "BVG Plan";
        berlin_bvg_plan.assetDescription = "Plan for the Berlin public transport system.";
        berlin_bvg_plan.assetType = "application/pdf";
        berlin_bvg_plan.asset = null;
        berlin_bvg_plan.fallbackUrl = "https://github.com/TimNowaczynski/weaver/raw/master/raw/bvg_plan.pdf";

        final AssetDAO assetDAO = weaverDB.assetDAO();
        assetIds[0] = assetDAO.createAsset(berlin_bear);
        assetIds[1] = assetDAO.createAsset(berlin_gate);
        assetIds[2] = assetDAO.createAsset(poem);
        assetIds[3] = assetDAO.createAsset(berlin_bvg_plan);
        return assetIds;
    }
}
