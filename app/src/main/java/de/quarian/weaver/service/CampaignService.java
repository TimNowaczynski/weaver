package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public interface CampaignService {

    String CAMPAIGN_LIST_ORDER_SP_KEY = "order";

    /**
     * Regardless of the chosen sort option,
     * deactivated campaigns should show after the rest.
     */
    enum  SortOrder {

        CAMPAIGN_NAME(0),
        SYSTEM_NAME(1),
        LAST_USED(2),
        LAST_EDITED(3),
        CREATED(4);

        private final int position;

        SortOrder(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    long createCampaign(@NonNull Campaign campaign);

    CampaignListDisplayObject readCampaign(final long campaignId);

    List<CampaignListDisplayObject> readCampaigns(@NonNull final SortOrder sortOrder);

    void updateCampaign(@NonNull Campaign campaign);

    void deleteCampaign(@NonNull Campaign campaign);

    long createTheme(@NonNull Theme theme);

    Theme readThemeForCampaign(@NonNull Campaign campaign);

    void updateTheme(@NonNull Theme theme);

    void deleteTheme(@NonNull Theme theme);

}
