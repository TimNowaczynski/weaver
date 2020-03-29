package de.quarian.weaver.datamodel.converter;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.Asset;
import de.quarian.weaver.util.TimeConstants;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AssetConverterUnitTest {

    @Mock
    private Resources resources;

    private long currentTime;
    private AssetConverter assetConverter;
    private Asset asset;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currentTime = System.currentTimeMillis();
        assetConverter = new AssetConverter(resources);
        asset = new Asset();

        when(resources.getString(anyInt())).thenReturn("success");
        when(resources.getString(anyInt(), anyLong())).thenReturn("success");
    }

    @Test
    public void testConvertAssetIsExpired() {
        asset.endOfLifetimeTimestamp = currentTime - 3 * TimeConstants.ONE_SECOND;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_expired);
    }

    @Test
    public void testConvertAssetWithSecondsRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + 3 * TimeConstants.ONE_SECOND;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_seconds_remaining);
    }

    @Test
    public void testConvertAssetWithMinutesRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + 64 * TimeConstants.ONE_SECOND;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_minutes_remaining, 1L);
    }

    @Test
    public void testConvertAssetWithHoursRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + (2 * TimeConstants.ONE_HOUR) + (3 * TimeConstants.ONE_MINUTE);
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_hours_remaining, 2L);
    }

    @Test
    public void testConvertAssetWithAboutADayRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + TimeConstants.ONE_DAY + 2 * TimeConstants.ONE_HOUR;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_about_a_day_remaining);
    }

    @Test
    public void testConvertAssetWithMultipleDaysRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + 4 * TimeConstants.ONE_DAY + TimeConstants.ONE_HOUR;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_days_remaining, 4L);
    }

    @Test
    public void testConvertAssetWithAboutAMonthRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + 31 * TimeConstants.ONE_DAY;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_about_a_month_remaining);
    }

    @Test
    public void testConvertAssetWithMultipleMonthRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + 5 * (TimeConstants.ONE_DAY * 31L) + TimeConstants.ONE_DAY;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_month_remaining, 5L);
    }

    @Test
    public void testConvertAssetWithOverAYearRemaining() {
        asset.endOfLifetimeTimestamp = currentTime + TimeConstants.ONE_WEEK + TimeConstants.ONE_YEAR;
        final String remainingTime = assetConverter.calculateRemainingTime(asset);
        assertThat(remainingTime, is("success"));

        verify(resources, times(1)).getString(R.string.asset_converter_time_over_a_year_remaining);
    }
}
