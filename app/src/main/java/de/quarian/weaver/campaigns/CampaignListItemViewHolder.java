package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

public class CampaignListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final DBConverters.ImageBlobConverter imageBlobConverter = new DBConverters.ImageBlobConverter();
    private final WeakReference<Activity> activity;
    private CampaignListDisplayObject campaignListDisplayObject;
    private TextView campaignName;
    private WeakReference<ImageView> campaignBanner;

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
        final ImageView campaignBanner = itemView.findViewById(R.id.campaign_list_item_banner);
        this.campaignBanner = new WeakReference<>(campaignBanner);
    }

    public void setCampaign(final CampaignListDisplayObject campaignListDisplayObject) {
        this.campaignListDisplayObject = campaignListDisplayObject;
        this.campaignName.setText(campaignListDisplayObject.getCampaignName());

        final Byte[] campaignImageBytes = campaignListDisplayObject.getCampaignImage();
        if (campaignImageBytes != null && campaignImageBytes.length > 0) {
            final byte[] campaignImageBytesPrimitive = imageBlobConverter.convertBytesToPrimitive(campaignImageBytes);
            final Bitmap campaignImage = BitmapFactory.decodeByteArray(campaignImageBytesPrimitive, 0, campaignImageBytes.length);
            final ImageView imageView = campaignBanner.get();
            if (imageView != null) {
                imageView.setImageBitmap(campaignImage);
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {

            case R.id.campaign_list_item_view: {
                NavigationController.getInstance().openCharacterLibrary(activity.get(), campaignListDisplayObject.getCampaignId());
                break;
            }

            case R.id.campaign_list_item_edit: {
                NavigationController.getInstance().editCampaign(activity.get(), campaignListDisplayObject.getCampaignId());
                break;
            }

            case R.id.campaign_list_item_synopsis: {
                NavigationController.getInstance().openSynopsis(activity.get(), campaignListDisplayObject.getCampaignId());
                break;
            }

            case R.id.campaign_list_item_manage_player_characters: {
                NavigationController.getInstance().managePlayerCharacters(activity.get(), campaignListDisplayObject.getCampaignId());
                break;
            }

            default: {
                //TODO: Implement logging
            }
        }
    }
}
