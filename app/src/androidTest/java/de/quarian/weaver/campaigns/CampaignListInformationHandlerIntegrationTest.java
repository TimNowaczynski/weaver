package de.quarian.weaver.campaigns;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CampaignListInformationHandlerIntegrationTest {

    private CampaignListInformationHandler campaignListInformationHandler = new CampaignListInformationHandler(Locale.GERMAN);

    @Test
    public void testDefaultMessages() {
        addDefaultMessages();

        final String processedInformation = campaignListInformationHandler.getProcessedInformation();
        assertThat(processedInformation, containsString("Cake"));
        assertThat(processedInformation, containsString("Fishes"));
        assertThat(processedInformation, containsString("Rambo"));
    }

    private void addDefaultMessages() {
        final CampaignListInformation campaignListInformationA = new CampaignListInformation(CampaignListInformation.Priority.NORMAL, "The Cake is a Lie!");
        final CampaignListInformation campaignListInformationB = new CampaignListInformation(CampaignListInformation.Priority.NORMAL, "Fishes look funny.");
        final CampaignListInformation campaignListInformationC = new CampaignListInformation(CampaignListInformation.Priority.NORMAL, "My Name is John Rambo");
        campaignListInformationHandler.addInformation(campaignListInformationA);
        campaignListInformationHandler.addInformation(campaignListInformationB);
        campaignListInformationHandler.addInformation(campaignListInformationC);
    }

    @Test
    public void testClickableSpan() throws Exception {
        final CampaignListInformation campaignListInformation = new CampaignListInformation(CampaignListInformation.Priority.NORMAL, "Click this!");
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        atomicBoolean.set(false);

        assertThat(atomicBoolean.get(), is(false));
        campaignListInformation.setAction(() -> atomicBoolean.set(true));
        campaignListInformation.performAction();
        Thread.sleep(10L);

        assertThat(atomicBoolean.get(), is(true));
    }
}
