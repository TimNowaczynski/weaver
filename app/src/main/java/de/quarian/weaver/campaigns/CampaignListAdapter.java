package de.quarian.weaver.campaigns;

import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListItemViewHolder> {

    private final WeakReference<WeaverActivity> activity;
    private final WeaverLayoutInflater weaverLayoutInflater;
    private final List<CampaignListDisplayObject> campaignListDisplayObjects = new ArrayList<>();

    public CampaignListAdapter(final WeaverActivity activity, @NonNull WeaverLayoutInflater weaverLayoutInflater) {
        this.activity = new WeakReference<>(activity);
        this.weaverLayoutInflater = weaverLayoutInflater;
    }

    public void setCampaignListDisplayObjects(List<CampaignListDisplayObject> campaignListDisplayObjects) {
        this.campaignListDisplayObjects.clear();
        this.campaignListDisplayObjects.addAll(campaignListDisplayObjects);
    }

    @NonNull
    @Override
    public CampaignListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View campaignListItemView = weaverLayoutInflater.inflate(R.layout.campaign_list_item, parent);
        return new CampaignListItemViewHolder(activity.get(), campaignListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignListItemViewHolder holder, int position) {
        final CampaignListDisplayObject campaignListDisplayObject = campaignListDisplayObjects.get(position);
        holder.setCampaign(campaignListDisplayObject);
    }

    @Override
    public int getItemCount() {
        return campaignListDisplayObjects.size();
    }
}
