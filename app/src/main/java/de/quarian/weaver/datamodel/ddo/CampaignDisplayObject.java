package de.quarian.weaver.datamodel.ddo;

import java.util.List;

public class CampaignDisplayObject {

    private static final long NEW_CAMPAIGN_ID = -2L;

    private long campaignId = NEW_CAMPAIGN_ID;
    private String campaignName;
    private String roleplayingSystemName;
    private String synopsis;
    private List<NameSetDisplayObject> nameSetDisplayObjects;

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getRoleplayingSystemName() {
        return roleplayingSystemName;
    }

    public void setRoleplayingSystemName(String roleplayingSystemName) {
        this.roleplayingSystemName = roleplayingSystemName;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<NameSetDisplayObject> getNameSetDisplayObjects() {
        return nameSetDisplayObjects;
    }

    public void setNameSetDisplayObjects(List<NameSetDisplayObject> nameSetDisplayObjects) {
        this.nameSetDisplayObjects = nameSetDisplayObjects;
    }
}
