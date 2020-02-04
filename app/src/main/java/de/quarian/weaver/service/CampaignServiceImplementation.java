package de.quarian.weaver.service;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.di.CampaignListOrderPreferences;

public class CampaignServiceImplementation implements CampaignService {

    private static final String SP_CURRENT_ORDER = "campaignService.currentOrder";

    private final CampaignDAO campaignDAO;
    private final RoleplayingSystemDAO roleplayingSystemDAO;
    private final ThemeDAO themeDAO;
    private final SharedPreferences orderPreferences;

    public CampaignServiceImplementation(@NonNull WeaverDB weaverDB,
                                         @NonNull @CampaignListOrderPreferences SharedPreferences orderPreferences) {
        this.campaignDAO = weaverDB.campaignDAO();
        this.roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        this.themeDAO = weaverDB.themeDAO();
        this.orderPreferences = orderPreferences;
    }

    @Override
    public List<CampaignListDisplayObject> readCampaignsWithOrderFromPreferences() {
        final String rawValue = orderPreferences.getString(SP_CURRENT_ORDER, SortOrder.CREATED.name());
        final SortOrder sortOrder = SortOrder.valueOf(rawValue);
        return readCampaigns(sortOrder);
    }

    /**
     * TODO: We could theoretically optimize here a bit and use joined tables
     *  via the @Embedded Annotation in Room.
     *  */
    @Override
    public List<CampaignListDisplayObject> readCampaigns(@NonNull final SortOrder sortOrder) {
        final List<Campaign> campaigns = readCampaignsFromDB(sortOrder);
        final List<CampaignListDisplayObject> campaignListDisplayObjects = new ArrayList<>(campaigns.size());
        for (final Campaign campaign : campaigns) {
            final RoleplayingSystem roleplayingSystem = roleplayingSystemDAO.readRoleplayingSystemsById(campaign.roleplayingSystemId);
            assert roleplayingSystem != null;

            final CampaignListDisplayObject displayObject = new CampaignListDisplayObject();
            displayObject.setCampaignId(campaign.id);
            displayObject.setRoleplayingSystemName(roleplayingSystem.roleplayingSystemName);
            displayObject.setCampaignName(campaign.campaignName);
            displayObject.setRoleplayingSystemImage(roleplayingSystem.logoImage);
            displayObject.setCreated(new Date(campaign.creationDateMillis));
            displayObject.setLastUsed(new Date(campaign.lastUsedDataMillis));
            displayObject.setLastEdited(new Date(campaign.editDateMillis));
            displayObject.setArchived(campaign.archived);

            final Theme theme = themeDAO.readThemeByID(campaign.themeId);
            displayObject.setCampaignImage(theme.bannerBackgroundImage);
            // TODO: set from theme
            //displayObject.setCampaignName(campaign.campaignName);
            campaignListDisplayObjects.add(displayObject);
        }
        return campaignListDisplayObjects;
    }

    private List<Campaign> readCampaignsFromDB(final SortOrder sortOrder) {
        switch (sortOrder) {
            case CAMPAIGN_NAME:
                return campaignDAO.readCampaignsOrderedByName();
            case SYSTEM_NAME:
                return campaignDAO.readCampaignsOrderedBySystemName();
            case LAST_USED:
                return campaignDAO.readCampaignsOrderedByLastUsed();
            case LAST_EDITED:
                return campaignDAO.readCampaignsOrderedByLastEdited();
            case CREATED:
                return campaignDAO.readCampaignsOrderedByCreated();
            default:
                throw new IllegalArgumentException("Sort Order not covered in switch statement");
        }
    }

    @Override
    public long createCampaign(@NonNull Campaign campaign) {
        return campaignDAO.createCampaign(campaign);
    }

    @Override
    public void updateCampaign(@NonNull Campaign campaign) {
        campaignDAO.updateCampaign(campaign);
    }

    @Override
    public void deleteCampaign(@NonNull Campaign campaign) {
        campaignDAO.deleteCampaign(campaign);
    }

    @Override
    public long createTheme(@NonNull Theme theme) {
        return themeDAO.createTheme(theme);
    }

    @Override
    public Theme readThemeForCampaign(@NonNull Campaign campaign) {
        return themeDAO.readThemeByID(campaign.themeId);
    }

    @Override
    public void updateTheme(@NonNull Theme theme) {
        themeDAO.updateTheme(theme);
    }

    @Override
    public void deleteTheme(@NonNull Theme theme) {
        themeDAO.deleteTheme(theme);
    }
}
