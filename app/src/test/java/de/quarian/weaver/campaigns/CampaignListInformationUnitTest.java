package de.quarian.weaver.campaigns;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CampaignListInformationUnitTest {

    private CampaignListInformation campaignListInformation;

    @Before
    public void setUp() {
        campaignListInformation = new CampaignListInformation(CampaignListInformation.Priority.URGENT, "I like trains!");
    }

    @Test
    public void testRetrieveValues() {
        final String message = campaignListInformation.getMessage();
        assertThat(message, is("I like trains!"));

        final CampaignListInformation.Priority priority = campaignListInformation.getPriority();
        assertThat(priority, is(CampaignListInformation.Priority.URGENT));
    }

    @Test
    public void testNoActionPresent() {
        campaignListInformation.performAction();
        assertTrue(true);
    }

    @Test
    public void testRunActionIfPresent() {
        final AtomicBoolean test = new AtomicBoolean(false);
        campaignListInformation.setAction(() -> test.set(true));
        campaignListInformation.performAction();
        assertThat(test.get(), is(true));
    }
}
