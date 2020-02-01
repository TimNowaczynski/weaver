package de.quarian.weaver.datamodel.ddo;

import java.util.Date;

/**
 * Used to display Campaigns. Sorting in a List is done via SQL query.
 */
public class CampaignListDisplayObject {

    private long campaignId;
    private String roleplayingSystemName;
    private String campaignName;
    private Byte[] roleplayingSystemImage;
    private Byte[] campaignImage;
    private Date created;
    private Date lastUsed;
    private Date lastEdited;
    private boolean archived;

    /*
        TODO: Put Colors here as well.
         Surround Campaign List items with a border to make
         sure they can be distinguished in any case.
     */

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public String getRoleplayingSystemName() {
        return roleplayingSystemName;
    }

    public void setRoleplayingSystemName(String roleplayingSystemName) {
        this.roleplayingSystemName = roleplayingSystemName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Byte[] getRoleplayingSystemImage() {
        return roleplayingSystemImage;
    }

    public void setRoleplayingSystemImage(Byte[] roleplayingSystemImage) {
        this.roleplayingSystemImage = roleplayingSystemImage;
    }

    public Byte[] getCampaignImage() {
        return campaignImage;
    }

    public void setCampaignImage(Byte[] campaignImage) {
        this.campaignImage = campaignImage;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
