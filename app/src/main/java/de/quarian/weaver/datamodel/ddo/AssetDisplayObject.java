package de.quarian.weaver.datamodel.ddo;

import android.view.View;

public class AssetDisplayObject {

    private long id;
    private String name;
    private String campaignName;
    private String description;
    private String remainingTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getAssetTitleText() {
        return String.format("%s - %s", campaignName, name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void open(final View view) {

    }

    @SuppressWarnings("unused")
    public void moveToCloud(final View view) {
        // TODO: to which cloud actually? pretty sure google drive would be best
        // TODO: implement later
    }

    public void delete(final View view) {

    }
}
