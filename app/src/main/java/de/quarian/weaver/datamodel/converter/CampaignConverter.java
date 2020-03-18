package de.quarian.weaver.datamodel.converter;

import android.content.res.Resources;

import java.util.Date;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public class CampaignConverter {

    private final Resources resources;

    public CampaignConverter(final Resources resources) {
        this.resources = resources;
    }

    public CampaignListDisplayObject convert(@NonNull final RoleplayingSystem roleplayingSystem, @NonNull final Campaign campaign, @NonNull final Theme theme, final long numberOfPlayerCharacters) {
        final CampaignListDisplayObject displayObject = new CampaignListDisplayObject(resources);
        displayObject.setCampaignId(campaign.id);
        displayObject.setRoleplayingSystemName(roleplayingSystem.roleplayingSystemName);
        displayObject.setCampaignName(campaign.campaignName);
        displayObject.setNumberOfPlayerCharacters(numberOfPlayerCharacters);
        displayObject.setRoleplayingSystemImage(roleplayingSystem.logoImage);
        displayObject.setCampaignImage(theme.bannerBackgroundImage);
        displayObject.setCreated(new Date(campaign.creationDateMillis));
        displayObject.setLastUsed(new Date(campaign.lastUsedDataMillis));
        displayObject.setLastEdited(new Date(campaign.editDateMillis));
        displayObject.setArchived(campaign.archived);
        return displayObject;
    }
}
