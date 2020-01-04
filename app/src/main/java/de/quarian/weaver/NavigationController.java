package de.quarian.weaver;

import android.app.Activity;
import android.content.Intent;

import de.quarian.weaver.assets.ViewScheduledToDeleteActivity;
import de.quarian.weaver.campaigns.CampaignEditorActivity;
import de.quarian.weaver.campaigns.CampaignSynopsisActivity;
import de.quarian.weaver.characters.CharacterLibraryActivity;
import de.quarian.weaver.namesets.SelectNameSetsActivity;
import de.quarian.weaver.players.PlayerCharacterListActivity;
import de.quarian.weaver.theming.SetThemeActivity;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.namesets.ManageNameSetsActivity;

public class NavigationController {

    private static NavigationController instance = new NavigationController();

    private NavigationController() { }

    public static NavigationController getInstance() {
        return instance;
    }

    public void openCharacterLibrary(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, CharacterLibraryActivity.class);
        intent.putExtra(CharacterLibraryActivity.EXTRA_CAMPAIGN_ID, campaignId);
        activity.startActivity(intent);
    }

    public void addCampaign(final Activity activity) {
        final Intent intent = new Intent(activity, CampaignEditorActivity.class);
        intent.putExtra(CampaignEditorActivity.EXTRA_MODE, CampaignEditorActivity.Mode.NEW.toString());
        activity.startActivityForResult(intent, CampaignEditorActivity.REQUEST_CODE_MODIFY_CAMPAIGNS);
    }

    public void editCampaign(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, CampaignEditorActivity.class);
        intent.putExtra(CampaignEditorActivity.EXTRA_CAMPAIGN_ID, campaignId);
        intent.putExtra(CampaignEditorActivity.EXTRA_MODE, CampaignEditorActivity.Mode.EDIT.toString());
        activity.startActivityForResult(intent, CampaignEditorActivity.REQUEST_CODE_MODIFY_CAMPAIGNS);
    }

    public void setTheme(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, SetThemeActivity.class);
        intent.putExtra(SetThemeActivity.EXTRA_CAMPAIGN_ID, campaignId);
        activity.startActivity(intent);
    }

    public void selectNameSets(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, SelectNameSetsActivity.class);
        intent.putExtra(SelectNameSetsActivity.EXTRA_CAMPAIGN_ID, campaignId);
        activity.startActivity(intent);
    }

    public void openSynopsis(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, CampaignSynopsisActivity.class);
        intent.putExtra(CampaignSynopsisActivity.EXTRA_CAMPAIGN_ID, campaignId);
        activity.startActivity(intent);
    }

    public void managePlayerCharacters(final Activity activity, final int campaignId) {
        final Intent intent = new Intent(activity, PlayerCharacterListActivity.class);
        intent.putExtra(PlayerCharacterListActivity.EXTRA_CAMPAIGN_ID, campaignId);
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
