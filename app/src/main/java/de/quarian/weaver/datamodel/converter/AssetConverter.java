package de.quarian.weaver.datamodel.converter;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.ddo.AssetDisplayObject;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.util.ResourcesProvider;
import de.quarian.weaver.util.TimeConstants;

public class AssetConverter {

    private final Resources resources;

    public AssetConverter(@NonNull final ResourcesProvider resourcesProvider) {
        this.resources = resourcesProvider.provide();
    }

    @NonNull
    public AssetDisplayObject convert(@NonNull final Asset asset) {
        final AssetDisplayObject assetDisplayObject = new AssetDisplayObject();
        assetDisplayObject.setId(asset.id);
        assetDisplayObject.setName(asset.assetName);
        assetDisplayObject.setCampaignName(asset.campaignName);
        assetDisplayObject.setDescription(asset.assetDescription);
        assetDisplayObject.setRemainingTime(calculateRemainingTime(asset));
        return assetDisplayObject;
    }

    @NonNull
    protected String calculateRemainingTime(@NonNull final Asset asset) {
        final long remainingTime = asset.endOfLifetimeTimestamp - System.currentTimeMillis();

        // Expired
        if (remainingTime <= 0) {
            return resources.getString(R.string.asset_converter_time_expired);
        }

        // Seconds (and also 60sec, because we rather display 60 seconds than 1 minute(s!))
        if (remainingTime <= TimeConstants.ONE_MINUTE) {
            return resources.getString(R.string.asset_converter_time_seconds_remaining);
        }

        // Minutes (same here, rather 60min instead 1 hours...)
        if (remainingTime <= TimeConstants.ONE_HOUR) {
            final long minutes = remainingTime / TimeConstants.ONE_MINUTE;
            return resources.getString(R.string.asset_converter_time_minutes_remaining, minutes);
        }

        // Hours
        if (remainingTime <= TimeConstants.ONE_DAY) {
            final long hours = remainingTime / TimeConstants.ONE_HOUR;
            return resources.getString(R.string.asset_converter_time_hours_remaining, hours);
        }

        // About a Day (up to 47 Hours)
        if (remainingTime < (2 * TimeConstants.ONE_DAY)) {
            return resources.getString(R.string.asset_converter_time_about_a_day_remaining);
        }

        // Days
        if (remainingTime <= (TimeConstants.ONE_WEEK)) {
            final long days = remainingTime / TimeConstants.ONE_DAY;
            return resources.getString(R.string.asset_converter_time_days_remaining, days);
        }

        // Over (about) a month
        if (remainingTime <= TimeConstants.ONE_DAY * 31L) {
            return resources.getString(R.string.asset_converter_time_about_a_month_remaining);
        }

        // Multiple month
        if (remainingTime <= TimeConstants.ONE_YEAR) {
            final long month = remainingTime / (TimeConstants.ONE_DAY * 31L);
            return resources.getString(R.string.asset_converter_time_month_remaining, month);
        }

        return resources.getString(R.string.asset_converter_time_over_a_year_remaining);
    }

    @NonNull
    public List<AssetDisplayObject> convert(@NonNull final List<Asset> assets) {
        final List<AssetDisplayObject> assetDisplayObjects = new ArrayList<>(assets.size());
        for (final Asset asset : assets) {
            final AssetDisplayObject assetDisplayObject = convert(asset);
            assetDisplayObjects.add(assetDisplayObject);
        }
        return assetDisplayObjects;
    }
}
