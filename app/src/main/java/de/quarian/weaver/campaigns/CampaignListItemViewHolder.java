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
    private CampaignDisplayObject campaignDisplayObject;
    private TextView campaignName;

    public CampaignListItemViewHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(itemView);
        this.activity = new WeakReference<>(activity);
        this.campaignName = itemView.findViewById(R.id.campaign_list_item_view);
        this.campaignName.setOnClickListener(this);
        this.campaignName.setOnClickListener(this);
        final TextView editCampaignButton = itemView.findViewById(R.id.campaign_list_item_edit);
        editCampaignButton.setOnClickListener(this);
    }

    public void setCampaign(final CampaignDisplayObject campaignDisplayObject) {
        this.campaignDisplayObject = campaignDisplayObject;
        this.campaignName.setText(campaignDisplayObject.getName());
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {

            case R.id.campaign_list_item_view: {
                NavigationController.getInstance().viewCampaign(activity.get(), campaignDisplayObject.getId());
                break;
            }

            case R.id.campaign_list_item_edit: {
                NavigationController.getInstance().editCampaign(activity.get(), campaignDisplayObject.getId());
                break;
            }

            default: {
                //TODO: Implement logging
            }
        }
    }
}
