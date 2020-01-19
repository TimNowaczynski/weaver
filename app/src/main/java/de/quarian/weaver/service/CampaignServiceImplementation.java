package de.quarian.weaver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

// TODO: Write Test
public class CampaignServiceImplementation implements CampaignService {

    private final CampaignDAO campaignDAO;
    private final RoleplayingSystemDAO roleplayingSystemDAO;
    private final ThemeDAO themeDAO;

    public CampaignServiceImplementation(@NonNull WeaverDB weaverDB) {
        campaignDAO = weaverDB.campaignDAO();
        roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();
        themeDAO = weaverDB.themeDAO();
    }

    @Override
    public List<CampaignListDisplayObject> readCampaignListDisplayObjects(@NonNull final AppCompatActivity activity, @NonNull final SortOrder sortOrder) {
        final List<Campaign> campaigns = readCampaignsFromDB(sortOrder);
        final List<CampaignListDisplayObject> campaignListDisplayObjects = new ArrayList<>(campaigns.size());
        for (final Campaign campaign : campaigns) {
            final RoleplayingSystem roleplayingSystem = roleplayingSystemDAO.readRoleplayingSystemsById(campaign.roleplayingSystemId);
            assert roleplayingSystem != null;

            final CampaignListDisplayObject displayObject = new CampaignListDisplayObject();
            displayObject.setCampaignId(campaign.id);
            displayObject.setRoleplayingSystemName(roleplayingSystem.roleplayingSystemName);
            displayObject.setCampaignName(campaign.campaignName);
            displayObject.setRoleplayingSystemImage(roleplayingSystem.logo);
            displayObject.setCreated(new Date(campaign.creationDateMillis));
            displayObject.setLastUsed(new Date(campaign.lastUsedDataMillis));
            displayObject.setLastEdited(new Date(campaign.editDateMillis));
            displayObject.setArchived(campaign.archived);

            final LiveData<Theme> themeLiveData = themeDAO.readTheme(campaign.themeId);
            final Theme theme = themeLiveData.getValue();
            displayObject.setCampaignImage(theme.bannerBackgroundImage);
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

}
