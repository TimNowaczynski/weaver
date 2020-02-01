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
        sr.logoImage = convertDrawableResToBytes(R.drawable.sr_dice);
        sr.logoImageType = "image/jpeg";
        ids[0] = roleplayingSystemDAO.createRoleplayingSystem(sr);

        return ids;
    }

    private long[] setUpThemes(final WeaverDB weaverDB) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final long[] themeIDs = new long[3];

        final Theme srTheme = new Theme();

        srTheme.bannerBackgroundImage = convertDrawableResToBytes(R.drawable.shadowrun_banner);
        srTheme.bannerBackgroundImageType = "image/jpeg";

        themeIDs[0] = themeDAO.createTheme(srTheme);
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
