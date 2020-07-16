package de.quarian.weaver.datamodel.converter;

import android.content.res.Resources;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.RoleplayingSystem;
import de.quarian.weaver.datamodel.Theme;
import de.quarian.weaver.datamodel.ddo.CampaignDisplayObject;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.datamodel.ddo.NameSetDisplayObject;
import de.quarian.weaver.util.ResourcesProvider;

public class CampaignConverter {

    @NonNull
    private final ResourcesProvider resourcesProvider;

    @NonNull
    private final Resources resources;

    @NonNull
    private final NameSetConverter nameSetConverter;

    public CampaignConverter(@NonNull final ResourcesProvider resourcesProvider, @NonNull final NameSetConverter nameSetConverter) {
        this.resourcesProvider = resourcesProvider;
        this.resources = resourcesProvider.provide();
        this.nameSetConverter = nameSetConverter;
    }

    @NonNull
    public CampaignListDisplayObject convert(@NonNull final RoleplayingSystem roleplayingSystem, @NonNull final Campaign campaign, @NonNull final Theme theme, final long numberOfPlayerCharacters) {
        final CampaignListDisplayObject displayObject = new CampaignListDisplayObject(resourcesProvider);
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

    @NonNull
    public CampaignDisplayObject convert(@NonNull final RoleplayingSystem roleplayingSystem, @NonNull final Campaign campaign, @NonNull final List<NameSet> allNameSets, @NonNull final List<NameSet> nameSetsForCampaign) {
        final CampaignDisplayObject displayObject = new CampaignDisplayObject();
        displayObject.setCampaignId(campaign.id);
        displayObject.setCampaignName(campaign.campaignName);
        displayObject.setRoleplayingSystemName(roleplayingSystem.roleplayingSystemName);
        displayObject.setSynopsis(campaign.synopsis);
        final List<NameSetDisplayObject> nameSetDisplayObjects = nameSetConverter.convert(allNameSets, nameSetsForCampaign);
        displayObject.setNameSetDisplayObjects(nameSetDisplayObjects);
        return displayObject;
    }
}
