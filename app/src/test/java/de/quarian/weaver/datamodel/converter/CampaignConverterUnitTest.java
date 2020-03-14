package de.quarian.weaver.datamodel.converter;

import org.junit.Test;

import java.util.Date;

import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.testing.TimeStop;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CampaignConverterUnitTest {

    private CampaignConverter campaignConverter = new CampaignConverter();
    private DBConverters.ImageBlobConverter imageBlobConverter = new DBConverters.ImageBlobConverter();

    @Test
    public void testConvertDBObjectsIntoCampaignDisplayObject() {
        final RoleplayingSystem roleplayingSystem = new RoleplayingSystem();
        final String rpsName = "Shadowrun";
        roleplayingSystem.roleplayingSystemName = rpsName;
        final byte[] rpsLogoBytes = "image".getBytes();
        final Byte[] rpsLogoImage = imageBlobConverter.convertPrimitiveToBytes(rpsLogoBytes);
        roleplayingSystem.logoImage = rpsLogoImage;

        final Campaign campaign = new Campaign();
        final long campaignId = 1L;
        campaign.id = campaignId;
        final String campaignName = "Rising Dragon";
        campaign.campaignName = campaignName;

        final TimeStop timeStop = TimeStop.acquire();

        final long creationDateTimstamp = timeStop
                .hoursAgo(4L)
                .getPointInTime();
        campaign.creationDateMillis = creationDateTimstamp;

        final long editDateTimestamp = timeStop
                .minutesAgo(12L)
                .getPointInTime();
        campaign.editDateMillis = editDateTimestamp;

        final long lastUsedDateTimestamp = timeStop
                .secondsAgo(32L)
                .getPointInTime();
        campaign.lastUsedDataMillis = lastUsedDateTimestamp;

        campaign.archived = true;

        final Theme theme = new Theme();
        final byte[] campaignImageBytes = "campaignImage".getBytes();
        final Byte[] campaignImage = imageBlobConverter.convertPrimitiveToBytes(campaignImageBytes);
        theme.bannerBackgroundImage = campaignImage;

        final long numberOfPlayerCharacters = 4L;

        final CampaignListDisplayObject displayObject = campaignConverter.convert(roleplayingSystem, campaign, theme, numberOfPlayerCharacters);
        assertThat(displayObject.getCampaignId(), is(campaignId));
        assertThat(displayObject.getRoleplayingSystemName(), is(rpsName));
        assertThat(displayObject.getCampaignName(), is(campaignName));
        assertThat(displayObject.getNumberOfPlayerCharacters(), is(numberOfPlayerCharacters));
        assertThat(displayObject.getRoleplayingSystemImage(), is(rpsLogoImage));
        assertThat(displayObject.getCampaignImage(), is(campaignImage));
        assertThat(displayObject.getCreated(), is(new Date(creationDateTimstamp)));
        assertThat(displayObject.getLastEdited(), is(new Date(editDateTimestamp)));
        assertThat(displayObject.getLastUsed(), is(new Date(lastUsedDateTimestamp)));
        assertThat(displayObject.isArchived(), is(true));
    }
}
