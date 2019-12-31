package de.quarian.weaver;

import android.app.Activity;
import android.content.Intent;

import de.quarian.weaver.campaigns.CampaignActivity;

public class NavigationController {

    private static NavigationController instance = new NavigationController();

    private NavigationController() { }

    public static NavigationController getInstance() {
        return instance;
    }

    public void viewCampaign(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, CampaignActivity.class);
        intent.putExtra(CampaignActivity.EXTRA_CAMPAIGN_ID, campaignId);
        intent.putExtra(CampaignActivity.EXTRA_MODE, CampaignActivity.Mode.VIEW.toString());
        activity.startActivity(intent);
    }

    public void addCampaign(final Activity activity) {
        final Intent intent = new Intent(activity, CampaignActivity.class);
        intent.putExtra(CampaignActivity.EXTRA_MODE, CampaignActivity.Mode.NEW.toString());
        activity.startActivityForResult(intent, CampaignActivity.REQUEST_CODE_MODIFY_CAMPAIGNS);
    }

    public void editCampaign(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, CampaignActivity.class);
        intent.putExtra(CampaignActivity.EXTRA_CAMPAIGN_ID, campaignId);
        intent.putExtra(CampaignActivity.EXTRA_MODE, CampaignActivity.Mode.EDIT.toString());
        activity.startActivityForResult(intent, CampaignActivity.REQUEST_CODE_MODIFY_CAMPAIGNS);
    }
}
