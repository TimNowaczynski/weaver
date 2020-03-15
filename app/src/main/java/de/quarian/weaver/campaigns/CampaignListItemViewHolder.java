package de.quarian.weaver.campaigns;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;
import de.quarian.weaver.util.LogLevel;
import de.quarian.weaver.util.Logger;

public class CampaignListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final DBConverters.ImageBlobConverter imageBlobConverter = new DBConverters.ImageBlobConverter();
    private final WeakReference<WeaverActivity> activity;
    private CampaignListDisplayObject campaignListDisplayObject;
    private TextView managePlayerCharactersButton;
    private TextView rpsName;
    private TextView campaignName;
    private WeakReference<ImageView> campaignBanner;
    private WeakReference<ImageView> rpsImageView;
    private FrameLayout deactivationOverlay;
    private boolean disableImageConversion;

    /**
     * This is used for testing only, so we can easily mock this class
     * @param itemView The dummy View
     */
    @VisibleForTesting
    protected CampaignListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        activity = new WeakReference<>(null);
        disableImageConversion = true;
        initializeViewReferences(itemView);
    }

    public CampaignListItemViewHolder(@NonNull WeaverActivity activity, @NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        this.activity = new WeakReference<>(activity);
        initializeViewReferences(itemView);
    }

    private void initializeViewReferences(@NonNull View itemView) {
        this.rpsName = itemView.findViewById(R.id.campaign_list_item_rps_name);
        this.campaignName = itemView.findViewById(R.id.campaign_list_item_name);

        final ImageView campaignSynopsis = itemView.findViewById(R.id.campaign_list_item_synopsis);
        campaignSynopsis.setOnClickListener(this);

        final ImageView editCampaignButton = itemView.findViewById(R.id.campaign_list_item_edit_campaign);
        editCampaignButton.setOnClickListener(this);

        managePlayerCharactersButton = itemView.findViewById(R.id.campaign_list_item_manage_player_characters);
        managePlayerCharactersButton.setOnClickListener(this);

        final ImageView campaignBanner = itemView.findViewById(R.id.campaign_list_item_banner);
        this.campaignBanner = new WeakReference<>(campaignBanner);

        final ImageView rpsImageView = itemView.findViewById(R.id.campaign_list_item_rps_image);
        this.rpsImageView = new WeakReference<>(rpsImageView);

        this.deactivationOverlay = itemView.findViewById(R.id.campaign_list_item_deactivation_overlay);
    }

    public void setCampaign(final CampaignListDisplayObject campaignListDisplayObject) {
        this.campaignListDisplayObject = campaignListDisplayObject;
        final long numberOfPlayerCharacters = campaignListDisplayObject.getNumberOfPlayerCharacters();
        this.managePlayerCharactersButton.setText(String.valueOf(numberOfPlayerCharacters));
        this.rpsName.setText(campaignListDisplayObject.getRoleplayingSystemName());
        this.campaignName.setText(campaignListDisplayObject.getCampaignName());

        if (!disableImageConversion) {
            convertImages(campaignListDisplayObject);
        }

        configureActiveStateOverlay(campaignListDisplayObject);

    }

    private void convertImages(CampaignListDisplayObject campaignListDisplayObject) {
        final Byte[] campaignImageBytes = campaignListDisplayObject.getCampaignImage();
        if (campaignImageBytes != null && campaignImageBytes.length > 0) {
            final byte[] campaignImageBytesPrimitive = imageBlobConverter.convertBytesToPrimitive(campaignImageBytes);
            final Bitmap campaignImage = BitmapFactory.decodeByteArray(campaignImageBytesPrimitive, 0, campaignImageBytes.length);
            final ImageView imageView = campaignBanner.get();
            if (imageView != null) {
                imageView.setImageBitmap(campaignImage);
            }
        }

        final Byte[] rpsImageBytes = campaignListDisplayObject.getRoleplayingSystemImage();
        if (rpsImageBytes != null && rpsImageBytes.length > 0) {
            final byte[] rpsImageBytesPrimitive = imageBlobConverter.convertBytesToPrimitive(rpsImageBytes);
            final Bitmap rpsImage = BitmapFactory.decodeByteArray(rpsImageBytesPrimitive, 0, rpsImageBytes.length);
            final ImageView imageView = rpsImageView.get();
            if (imageView != null) {
                imageView.setImageBitmap(rpsImage);
            }
        }
    }

    private void configureActiveStateOverlay(CampaignListDisplayObject campaignListDisplayObject) {
        if (campaignListDisplayObject.isArchived()) {
            if (activity != null) {
                final Activity activity = this.activity.get();
                final Context baseContext = activity.getBaseContext();
                final Resources resources = baseContext.getResources();
                final int overlayColor = resources.getColor(R.color.dark_fifty_percent);
                this.deactivationOverlay.setBackgroundColor(overlayColor);
            } else {
                throw new RuntimeException("Activity should not be null!");
            }
        } else {
            this.deactivationOverlay.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {

            case R.id.campaign_list_item: {
                NavigationController.getInstance().openCharacterLibrary(activity.get(), campaignListDisplayObject.getCampaignId());
                break;
            }

            case R.id.campaign_list_item_edit_campaign: {
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
                getLogger().log(LogLevel.ERROR, "Failed to implement switch case for view id: " + id);
            }
        }
    }

    private Logger getLogger() {
        final WeaverActivity activity = this.activity.get();
        return activity.getLogger(this);
    }
}
