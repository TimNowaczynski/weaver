package de.quarian.weaver.theming;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Theme;

public class ThemeProvider {

    public ThemeProvider(WeaverDB weaverDB) {
        this.weaverDB = weaverDB;
    }

    private final WeaverDB weaverDB;

    public Theme getThemeForCampaign(final long campaignID) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByID(campaignID);
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        //TODO: handle possible null pointer
        return themeDAO.readThemeByID(campaign.themeId);
    }

    public void setThemeForCampaign(final long campaignId, final Theme theme) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final Theme existingTheme = themeDAO.readThemeByID(theme.id);
        if (existingTheme != null) {
            themeDAO.updateTheme(theme);
        } else {
            createNewThemeForCampaign(campaignId, theme);
        }
    }

    private void createNewThemeForCampaign(long campaignId, @NonNull Theme theme) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final long newThemeId = themeDAO.createTheme(theme);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByID(campaignId);
        campaign.themeId = newThemeId;
        campaignDAO.updateCampaign(campaign);
    }
}
