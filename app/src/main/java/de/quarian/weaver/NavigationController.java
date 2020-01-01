package de.quarian.weaver;

import android.app.Activity;
import android.content.Intent;

import de.quarian.weaver.assets.ViewScheduledToDeleteActivity;
import de.quarian.weaver.campaigns.CampaignActivity;
import de.quarian.weaver.campaigns.CampaignSynopsisActivity;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.namesets.ManageNameSetsActivity;

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

    public void openSynopsis(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, CampaignSynopsisActivity.class);
        intent.putExtra(CampaignSynopsisActivity.EXTRA_CAMPAIGN_ID, campaignId);
        activity.startActivity(intent);
    }

    public void manageSettings(final Activity activity) {
        final Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    public void manageNameSets(final Activity activity) {
        final Intent intent = new Intent(activity, ManageNameSetsActivity.class);
        activity.startActivity(intent);
    }

    public void viewScheduledToDelete(final Activity activity) {
        final Intent intent = new Intent(activity, ViewScheduledToDeleteActivity.class);
        activity.startActivity(intent);
    }

    public void openDeveloperOptions(final Activity activity) {
        final Intent intent = new Intent(activity, DeveloperFunctionsActivity.class);
        activity.startActivity(intent);
    }
}
