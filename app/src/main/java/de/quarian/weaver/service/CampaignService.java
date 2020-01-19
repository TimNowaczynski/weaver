package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public interface CampaignService {

    /**
     * Regardless of the chosen option,
     * deactivated campaigns should show after the rest.
     */
    enum  SortOrder {
        CAMPAIGN_NAME,
        SYSTEM_NAME,
        LAST_USED,
        LAST_EDITED,
        CREATED
    }

    List<CampaignListDisplayObject> readCampaignListDisplayObjects(@NonNull final AppCompatActivity activity, @NonNull final SortOrder sortOrder);
}
