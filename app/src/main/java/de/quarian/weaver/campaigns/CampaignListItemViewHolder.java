package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;

public class CampaignListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final WeakReference<Activity> activity;
    private CampaignListDisplayObject campaignListDisplayObject;
    private TextView campaignName;

    public CampaignListItemViewHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(itemView);
        this.activity = new WeakReference<>(activity);
        this.campaignName = itemView.findViewById(R.id.campaign_list_item_view);
        this.campaignName.setOnClickListener(this);
        final TextView campaignSynopsis = itemView.findViewById(R.id.campaign_list_item_synopsis);
        campaignSynopsis.setOnClickListener(this);
        final TextView editCampaignButton = itemView.findViewById(R.id.campaign_list_item_edit);
        editCampaignButton.setOnClickListener(this);
        final TextView managePlayerCharactersButton = itemView.findViewById(R.id.campaign_list_item_manage_player_characters);
        managePlayerCharactersButton.setOnClickListener(this);
    }

    public void setCampaign(final CampaignListDisplayObject campaignListDisplayObject) {
        this.campaignListDisplayObject = campaignListDisplayObject;
        this.campaignName.setText(campaignListDisplayObject.getName());
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {

            case R.id.campaign_list_item_view: {
                NavigationController.getInstance().openCharacterLibrary(activity.get(), campaignListDisplayObject.getId());
                break;
            }

            case R.id.campaign_list_item_edit: {
                NavigationController.getInstance().editCampaign(activity.get(), campaignListDisplayObject.getId());
                break;
            }

            case R.id.campaign_list_item_synopsis: {
                NavigationController.getInstance().openSynopsis(activity.get(), campaignListDisplayObject.getId());
                break;
            }

            case R.id.campaign_list_item_manage_player_characters: {
                NavigationController.getInstance().managePlayerCharacters(activity.get(), campaignListDisplayObject.getId());
                break;
            }

            default: {
                //TODO: Implement logging
            }
        }
    }
}
