package de.quarian.weaver.theming;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.util.LoggingProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ThemeProviderUnitTest {

    @Mock
    private WeaverDB weaverDB;

    @Mock
    private LoggingProvider loggingProvider;

    @Mock
    private CampaignDAO campaignDAO;

    @Mock
    private ThemeDAO themeDAO;

    private ThemeProvider themeProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(weaverDB.campaignDAO()).thenReturn(campaignDAO);
        when(weaverDB.themeDAO()).thenReturn(themeDAO);

        final Campaign campaign = new Campaign();
        campaign.themeId = 10L;
        when(campaignDAO.readCampaignByID(1L)).thenReturn(campaign);

        themeProvider = new ThemeProvider(weaverDB, loggingProvider);
    }

    @Test
    public void testGetThemeForCampaign() {
        final long campaignID = 2L;
        final long themeID = 20L;

        final Campaign campaign = new Campaign();
        campaign.themeId = themeID;
        when(campaignDAO.readCampaignByID(campaignID)).thenReturn(campaign);

        final Theme theme = new Theme();
        theme.id = themeID;
        when(themeDAO.readThemeByID(themeID)).thenReturn(theme);

        final Theme themeForCampaign = themeProvider.getThemeForCampaign(campaignID);
        assertThat(themeForCampaign.id, is(themeID));
    }

    @Test
    public void testSetThemeForCampaignCreatesTheme() {
        final long campaignId = 5L;
        final long themeId = 10L;

        final Theme theme = new Theme();
        theme.id = themeId;

        final Campaign campaign = new Campaign();

        when(themeDAO.createTheme(theme)).thenReturn(themeId);
        when(campaignDAO.readCampaignByID(campaignId)).thenReturn(campaign);

        themeProvider.setThemeForCampaign(campaignId, theme);

        verify(themeDAO).createTheme(theme);
        verify(campaignDAO).createCampaign(campaign);
    }

    @Test
    public void testSetThemeForCampaignUpdatesTheme() {
        final long themeId = 5L;

        final Theme theme = new Theme();
        when(themeDAO.readThemeByID(themeId)).thenReturn(theme);

        theme.id = themeId;
        themeProvider.setThemeForCampaign(5L, theme);

        verify(themeDAO).updateTheme(theme);
    }
}
