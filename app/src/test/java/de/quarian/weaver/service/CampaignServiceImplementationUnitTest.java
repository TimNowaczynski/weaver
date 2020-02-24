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
    private SharedPreferences sharedPreferences;

    @Mock
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Mock
    private RoleplayingSystemDAO roleplayingSystemDAOMock;

    @Mock
    private CampaignDAO campaignDAOMock;

    @Mock
    private ThemeDAO themeDAOMock;

    @Mock
    private PlayerCharacterDAO playerCharacterDAO;

    // TODO: I think this should fail now, because we moved shared preferences into
    //   this class and they need to be mocked (and tested!)
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        campaignService = new CampaignServiceImplementation(weaverDBMock, sharedPreferences);
        when(weaverDBMock.roleplayingSystemDAO()).thenReturn(roleplayingSystemDAOMock);
        when(weaverDBMock.themeDAO()).thenReturn(themeDAOMock);
        when(weaverDBMock.campaignDAO()).thenReturn(campaignDAOMock);
        when(weaverDBMock.playerCharacterDAO()).thenReturn(playerCharacterDAO);

        final Campaign campaign = mock(Campaign.class);
        campaign.roleplayingSystemId = 1L;
        campaign.themeId = 1L;

        final List<Campaign> campaignList = new ArrayList<>();
        campaignList.add(campaign);
        when(campaignDAOMock.readCampaignsOrderedByName()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedBySystemName()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedByLastUsed()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedByLastEdited()).thenReturn(campaignList);
        when(campaignDAOMock.readCampaignsOrderedByCreated()).thenReturn(campaignList);

        final RoleplayingSystem roleplayingSystem = mock(RoleplayingSystem.class);
        when(roleplayingSystemDAOMock.readRoleplayingSystemsById(1L)).thenReturn(roleplayingSystem);

        final Theme theme = mock(Theme.class);
        when(themeDAOMock.readThemeByID(1L)).thenReturn(theme);

        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditor);

        when(playerCharacterDAO.readNumberOfPlayerCharactersForCampaign(anyLong())).thenReturn(5L);

        campaignService = new CampaignServiceImplementation(weaverDBMock, sharedPreferences);
    }

    @Test
    public void testReadCampaignsOrderedByName() {
        campaignService.readCampaigns(CampaignService.SortOrder.CAMPAIGN_NAME);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByName();
    }

    @Test
    public void testReadCampaignsOrderedBySystemName() {
        campaignService.readCampaigns(CampaignService.SortOrder.SYSTEM_NAME);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedBySystemName();
    }

    @Test
    public void testReadCampaignsOrderedByLastUsed() {
        campaignService.readCampaigns(CampaignService.SortOrder.LAST_USED);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByLastUsed();
    }

    @Test
    public void testReadCampaignsOrderedByLastEdited() {
        campaignService.readCampaigns(CampaignService.SortOrder.LAST_EDITED);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByLastEdited();
    }

    @Test
    public void testReadCampaignsOrderedByCreated() {
        campaignService.readCampaigns(CampaignService.SortOrder.CREATED);

        verify(roleplayingSystemDAOMock).readRoleplayingSystemsById(1L);
        verify(themeDAOMock).readThemeByID(1L);
        verify(campaignDAOMock).readCampaignsOrderedByCreated();
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
