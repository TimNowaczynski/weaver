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
        //TODO: implement demo data, currently campaign AND theme are null
        /*
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByID(campaignID).getValue();
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        //TODO: handle possible null pointer
        return themeDAO.readTheme(campaign.themeId).getValue();
         */
        final Theme theme = new Theme();
        theme.backgroundFontColorA = 255;
        theme.backgroundFontColorR = 255;
        theme.backgroundFontColorG = 0;
        theme.backgroundFontColorB = 0;
        return theme;
    }

    public void setThemeForCampaign(final long campaignId, final Theme theme) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final Theme existingTheme = themeDAO.readTheme(theme.id).getValue();
        if (existingTheme != null) {
            themeDAO.updateTheme(theme);
        } else {
            createNewThemeForCampaign(campaignId, theme);
        }
    }

    private void createNewThemeForCampaign(long campaignId, @NonNull Theme theme) {
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        final long newTheme = themeDAO.createTheme(theme);
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByID(campaignId).getValue();
        //TODO: handle possible null pointer
        campaign.themeId = newTheme;
        campaignDAO.updateCampaign(campaign);
    }
}
