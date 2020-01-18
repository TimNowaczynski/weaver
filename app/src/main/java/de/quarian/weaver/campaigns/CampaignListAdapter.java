package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public class CampaignListAdapter extends RecyclerView.Adapter {

    private WeakReference<Activity> activity;
    private List<CampaignListDisplayObject> campaignListDisplayObjects = new ArrayList<>();

    public CampaignListAdapter(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    public void setCampaignListDisplayObjects(List<CampaignListDisplayObject> campaignListDisplayObjects) {
        this.campaignListDisplayObjects.addAll(campaignListDisplayObjects);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View campaignListItemView = layoutInflater.inflate(R.layout.campaign_list_item, parent, false);
        return new CampaignListItemViewHolder(activity.get(), campaignListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CampaignListItemViewHolder viewHolder = (CampaignListItemViewHolder) holder;
        final CampaignListDisplayObject campaignListDisplayObject = campaignListDisplayObjects.get(position);
        viewHolder.setCampaign(campaignListDisplayObject);
    }

    @Override
    public int getItemCount() {
        return campaignListDisplayObjects.size();
    }
}
