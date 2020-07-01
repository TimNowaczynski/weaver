package de.quarian.weaver.campaigns;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class CampaignListInformationHandlerUnitTest {

    private CampaignListInformationHandler campaignListInformationHandler = new CampaignListInformationHandler(Locale.GERMAN);

    @Test
    public void testReturnDatePerDefault() {
        final String processedInformation = campaignListInformationHandler.getProcessedInformation();
        assertThat(processedInformation, containsString("."));
        assertThat(processedInformation, containsString(":"));
    }

}
