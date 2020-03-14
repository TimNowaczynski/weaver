package de.quarian.weaver.service;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.converter.CampaignConverter;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceImplementationUnitTest {

    private CampaignService campaignService;

    @Mock
    private WeaverDB weaverDBMock;

    @Mock
    private SharedPreferences sharedPreferencesMock;

    @Mock
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Mock
    private CampaignConverter campaignConverterMock;

    @Mock
    private RoleplayingSystemDAO roleplayingSystemDAOMock;

    @Mock
    private CampaignDAO campaignDAOMock;

    @Mock
    private ThemeDAO themeDAOMock;

    @Mock
    private PlayerCharacterDAO playerCharacterDAO;

    // Return Values

    @Mock
    private RoleplayingSystem roleplayingSystem;

    @Mock
    private Campaign campaign;

    @Mock
    private CampaignListDisplayObject campaignListDisplayObject;

    @Mock
    private Theme theme;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(weaverDBMock.roleplayingSystemDAO()).thenReturn(roleplayingSystemDAOMock);
        when(weaverDBMock.themeDAO()).thenReturn(themeDAOMock);
        when(weaverDBMock.campaignDAO()).thenReturn(campaignDAOMock);
        when(weaverDBMock.playerCharacterDAO()).thenReturn(playerCharacterDAO);

        campaign.roleplayingSystemId = 1L;
        campaign.themeId = 1L;

        final List<Campaign> campaignList = new ArrayList<>();
        campaignList.add(campaign);

        when(campaignDAOMock.readCampaignsOrderedByName()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedBySystemName()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedByLastUsed()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedByLastEdited()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedByCreated()).thenReturn(campaignList);

        when(roleplayingSystemDAOMock.readRoleplayingSystemsById(1L)).thenReturn(roleplayingSystem);
        when(themeDAOMock.readThemeByID(1L)).thenReturn(theme);
        final long numberOfPlayerCharacters = 5L;
        when(playerCharacterDAO.readNumberOfPlayerCharactersForCampaign(anyLong())).thenReturn(numberOfPlayerCharacters);
        when(campaignConverterMock.convert(roleplayingSystem, campaign, theme, numberOfPlayerCharacters)).thenReturn(campaignListDisplayObject);

        when(sharedPreferencesMock.edit()).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditor);

        campaignService = new CampaignServiceImplementation(weaverDBMock, sharedPreferencesMock, campaignConverterMock);
    }

    @Test
    public void testReadCampaignsOrderedByName() {
        final List<CampaignListDisplayObject> displayObjects = campaignService.readCampaigns(CampaignService.SortOrder.CAMPAIGN_NAME);
        assertThat(displayObjects.size(), is(1));
        assertThat(displayObjects.get(0), is(campaignListDisplayObject));

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByName();
        verify(campaignConverterMock).convert(roleplayingSystem, campaign, theme, 5L);
    }

    @Test
    public void testReadCampaignsOrderedBySystemName() {
        campaignService.readCampaigns(CampaignService.SortOrder.SYSTEM_NAME);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedBySystemName();
        verify(campaignConverterMock).convert(roleplayingSystem, campaign, theme, 5L);
    }

    @Test
    public void testReadCampaignsOrderedByLastUsed() {
        campaignService.readCampaigns(CampaignService.SortOrder.LAST_USED);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByLastUsed();
        verify(campaignConverterMock).convert(roleplayingSystem, campaign, theme, 5L);
    }

    @Test
    public void testReadCampaignsOrderedByLastEdited() {
        campaignService.readCampaigns(CampaignService.SortOrder.LAST_EDITED);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByLastEdited();
        verify(campaignConverterMock).convert(roleplayingSystem, campaign, theme, 5L);
    }

    @Test
    public void testReadCampaignsOrderedByCreated() {
        campaignService.readCampaigns(CampaignService.SortOrder.CREATED);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByCreated();
        verify(campaignConverterMock).convert(roleplayingSystem, campaign, theme, 5L);
    }

    @Test
    public void testCreateCampaign() {
        final Campaign campaign = mock(Campaign.class);
        campaignService.createCampaign(campaign);

        verify(campaignDAOMock).createCampaign(campaign);
    }

    @Test
    public void testEditCampaign() {
        final Campaign campaign = mock(Campaign.class);
        campaignService.updateCampaign(campaign);

        verify(campaignDAOMock).updateCampaign(campaign);
    }

    @Test
    public void testDeleteCampaign() {
        final Campaign campaign = mock(Campaign.class);
        campaignService.deleteCampaign(campaign);

        verify(campaignDAOMock).deleteCampaign(campaign);
    }

    @Test
    public void testCreateTheme() {
        final Theme theme = mock(Theme.class);
        campaignService.createTheme(theme);

        verify(themeDAOMock).createTheme(theme);
    }

    @Test
    public void testReadTheme() {
        final Campaign campaign = new Campaign();
        campaign.themeId = 2L;

        final Theme themeMock = mock(Theme.class);
        when(themeDAOMock.readThemeByID(2L)).thenReturn(themeMock);

        final Theme themeOut = campaignService.readThemeForCampaign(campaign);
        assertThat(themeOut, is(themeMock));
    }

    @Test
    public void testUpdateTheme() {
        final Theme themeMock = mock(Theme.class);

        campaignService.updateTheme(themeMock);

        verify(themeDAOMock).updateTheme(themeMock);
    }

    @Test
    public void testDeleteTheme() {
        final Theme themeMock = mock(Theme.class);

        campaignService.deleteTheme(themeMock);

        verify(themeDAOMock).deleteTheme(themeMock);
    }
}
