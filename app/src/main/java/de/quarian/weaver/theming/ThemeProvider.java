package de.quarian.weaver.theming;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.util.LogLevel;
import de.quarian.weaver.util.LoggingProvider;

public class ThemeProvider {

    private final WeaverDB weaverDB;
    private final LoggingProvider loggingProvider;
    // TODO: add active theme

    public ThemeProvider(@NonNull final WeaverDB weaverDB, @NonNull final LoggingProvider loggingProvider) {
        this.weaverDB = weaverDB;
        this.loggingProvider = loggingProvider;
    }

    public Theme getThemeForCampaign(final long campaignID) {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        final Campaign campaign = campaignDAO.readCampaignByID(campaignID);
        if (campaign == null) {
            throw new RuntimeException("Campaign was null");
        }
        final ThemeDAO themeDAO = weaverDB.themeDAO();
        if (themeDAO == null) {
            throw new RuntimeException("themeDAO");
        }
        Theme theme = themeDAO.readThemeByID(campaign.themeId);
        if (theme == null) {
            // TODO: return some fallback theme
            loggingProvider.getLogger(this).log(LogLevel.ERROR, "Could not find theme for campaign: " + campaign.campaignName);
        }
        return theme;
    }

    public void setThemeForCampaign(final long campaignId, @NonNull final Theme theme) {
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
        campaignDAO.createCampaign(campaign);
    }
}
