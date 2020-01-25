package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public interface CampaignService {

    /**
     * Regardless of the chosen sort option,
     * deactivated campaigns should show after the rest.
     */
    enum  SortOrder {
        CAMPAIGN_NAME,
        SYSTEM_NAME,
        LAST_USED,
        LAST_EDITED,
        CREATED
    }

    long createCampaign(@NonNull Campaign campaign);

    List<CampaignListDisplayObject> readCampaigns(@NonNull final SortOrder sortOrder);

    void updateCampaign(@NonNull Campaign campaign);

    void deleteCampaign(@NonNull Campaign campaign);

}
