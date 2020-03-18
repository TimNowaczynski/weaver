package de.quarian.weaver.campaigns;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.databinding.CampaignListItemBinding;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public class CampaignListItemViewHolder extends RecyclerView.ViewHolder {

    private CampaignListItemBinding itemBinding;
    private boolean disableImageConversion;

    public CampaignListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemBinding = CampaignListItemBinding.bind(itemView);
    }

    /**
     * For testing
     * @param itemView the view to bind
     * @param campaignListItemBinding the item binding
     */
    @VisibleForTesting
    protected CampaignListItemViewHolder(@NonNull View itemView, @NonNull CampaignListItemBinding campaignListItemBinding) {
        super(itemView);
        this.itemBinding = campaignListItemBinding;
        this.disableImageConversion = true;
    }

    public void setCampaign(final CampaignListDisplayObject campaignListDisplayObject) {
        campaignListDisplayObject.setDisableImageConversion(disableImageConversion);
        this.itemBinding.setCampaignDisplayObject(campaignListDisplayObject);
    }
}
