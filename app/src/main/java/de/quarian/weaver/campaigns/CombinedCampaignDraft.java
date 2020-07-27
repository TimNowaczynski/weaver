package de.quarian.weaver.campaigns;

import java.util.ArrayList;
import java.util.List;

import de.quarian.weaver.theming.ThemeColorsParcelable;

public class CombinedCampaignDraft {

    private String rolePlayingSystemName;
    private String campaignName;
    private String synopsis;
    private ThemeColorsParcelable themeColorsParcelable;
    private final List<Long> nameSetIds = new ArrayList<>();

    public String getRolePlayingSystemName() {
        return rolePlayingSystemName;
    }

    public void setRolePlayingSystemName(String rolePlayingSystemName) {
        this.rolePlayingSystemName = rolePlayingSystemName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public ThemeColorsParcelable getThemeColorsParcelable() {
        return themeColorsParcelable;
    }

    public void setThemeColorsParcelable(final ThemeColorsParcelable themeColorsParcelable) {
        this.themeColorsParcelable = themeColorsParcelable;
    }

    public void setNameSetIds(final List<Long> nameSetIds) {
        this.nameSetIds.addAll(nameSetIds);
    }

    public List<Long> getNameSetIds() {
        return nameSetIds;
    }

}
