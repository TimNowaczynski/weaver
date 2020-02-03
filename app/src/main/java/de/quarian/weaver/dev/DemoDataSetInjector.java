package de.quarian.weaver.dev;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import androidx.annotation.DrawableRes;
import de.quarian.weaver.R;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;

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

            Toast.makeText(context, "Demo State initialized", Toast.LENGTH_SHORT).show();
        });
    }

    private long[] setUpRoleplayingSystems(final WeaverDB weaverDB) {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        final long[] ids = new long[3];

        final RoleplayingSystem sr = new RoleplayingSystem();
        sr.roleplayingSystemName = "Shadowrun";
        sr.logoImage = convertDrawableResToBytes(R.drawable.shadowrun_logo);
        sr.logoImageType = "image/jpeg";
        ids[0] = roleplayingSystemDAO.createRoleplayingSystem(sr);

        final RoleplayingSystem dsa = new RoleplayingSystem();
        dsa.roleplayingSystemName = "DSA 4.0";
        dsa.logoImage = convertDrawableResToBytes(R.drawable.dsa_logo);
        dsa.logoImageType = "image/jpeg";
        ids[1] = roleplayingSystemDAO.createRoleplayingSystem(dsa);

        return ids;
    }

    private long[] setUpThemes(final WeaverDB weaverDB) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final long[] themeIDs = new long[3];

        final Theme srTheme = new Theme();
        srTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.shadowrun_banner);
        srTheme.bannerBackgroundImageType = "image/jpeg";

        final Theme dsaTheme = new Theme();
        dsaTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.g7_banner);
        dsaTheme.bannerBackgroundImageType = "image/jpeg";

        themeIDs[0] = themeDAO.createTheme(srTheme);
        themeIDs[1] = themeDAO.createTheme(dsaTheme);
        //TODO: define theme values
        return themeIDs;
    }

    private long[] setUpCampaigns(final WeaverDB weaverDB, final long[] roleplayingSystemIDs, final long[] themeIds) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final long[] campaignIds = new long[3];
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

        final Campaign borbarad = new Campaign();
        borbarad.archived = false;
        borbarad.roleplayingSystemId = roleplayingSystemIDs[1];
        borbarad.themeId = themeIds[1];
        borbarad.creationDateMillis = fourteenMonthAgo.getTime();
        borbarad.editDateMillis = twelveDaysAgo.getTime();
        borbarad.lastUsedDataMillis = aWeekAgo.getTime();
        borbarad.campaignName = "Die 7 Gezeichneten";
        borbarad.synopsis = "Things are usually rather relaxed during the festive days in early " +
                "summer. But there are powers struggling to bring back an ancient sorcerer who " +
                "is hungry for power and won't stop to sacrifice anything in his way. So the " +
                "burden of stopping him falls upon an unlucky group of adventurers, chosen by " +
                "manifestations of certain marks of old times which are neither magical or holy " +
                "by nature but will grant them otherworldly powers so far unknown.";
        campaignIds[1] = campaignDAO.createCampaign(borbarad);
        return campaignIds;
    }

    private Byte[] convertDrawableResToBytes(@DrawableRes final int drawableRes) {
        final Drawable srBanner = context.getResources().getDrawable(drawableRes, null);
        final Bitmap srBannerBitmap = ((BitmapDrawable) srBanner).getBitmap();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        srBannerBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final DBConverters.ImageBlobConverter converter = new DBConverters.ImageBlobConverter();
        return converter.convertPrimitiveToBytes(outputStream.toByteArray());
    }
}
