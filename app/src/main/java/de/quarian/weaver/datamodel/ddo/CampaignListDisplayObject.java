package de.quarian.weaver.datamodel.ddo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import java.util.Date;

import androidx.databinding.BindingAdapter;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.util.ContextHandler;

/**
 * Used to display Campaigns. Sorting in a List is done via SQL query.
 */
public class CampaignListDisplayObject {

    private boolean disableImageConversion;

    private int deactivationOverlayColor;
    private long campaignId;
    private String roleplayingSystemName;
    private String campaignName;
    private long numberOfPlayerCharacters;
    private Byte[] roleplayingSystemImage;
    private Byte[] campaignImage;
    private Date created;
    private Date lastUsed;
    private Date lastEdited;
    private boolean archived;

    public CampaignListDisplayObject(final Resources resources) {
        this.deactivationOverlayColor = resources.getColor(R.color.dark_fifty_percent);
    }

    /*
        TODO: Put Colors here as well.
         Surround Campaign List items with a border to make
         sure they can be distinguished in any case.
     */

    public void setDisableImageConversion(boolean disableImageConversion) {
        this.disableImageConversion = disableImageConversion;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public String getRoleplayingSystemName() {
        return roleplayingSystemName;
    }

    public void setRoleplayingSystemName(String roleplayingSystemName) {
        this.roleplayingSystemName = roleplayingSystemName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getNumberOfPlayerCharacters() {
        return String.valueOf(numberOfPlayerCharacters);
    }

    public void setNumberOfPlayerCharacters(long numberOfPlayerCharacters) {
        this.numberOfPlayerCharacters = numberOfPlayerCharacters;
    }

    public Byte[] getRoleplayingSystemImage() {
        if (disableImageConversion) {
            return null;
        }
        return roleplayingSystemImage;
    }

    public void setRoleplayingSystemImage(Byte[] roleplayingSystemImage) {
        this.roleplayingSystemImage = roleplayingSystemImage;
    }

    public Byte[] getCampaignImage() {
        if (disableImageConversion) {
            return null;
        }
        return campaignImage;
    }

    public void setCampaignImage(Byte[] campaignImage) {
        this.campaignImage = campaignImage;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    // Data-binding

    @BindingAdapter("bytes")
    public static void setImageBytes(final View view, final Byte[] bytes) {
        if (!(view instanceof ImageView) || bytes == null) {
            return;
        }

        final ImageView imageView = (ImageView) view;
        final DBConverters.ImageBlobConverter imageBlobConverter = new DBConverters.ImageBlobConverter();
        final byte[] imageBytesPrimitive = imageBlobConverter.convertBytesToPrimitive(bytes);
        final Bitmap image = BitmapFactory.decodeByteArray(imageBytesPrimitive, 0, bytes.length);
        imageView.setImageBitmap(image);
    }

    public int overlayColor() {
        return archived ? deactivationOverlayColor : Color.TRANSPARENT;
    }

    // OnClick

    public void openCharacterLibrary(final View view) {
        final Activity activity = ContextHandler.asActivity(view.getContext());
        NavigationController.getInstance().openCharacterLibrary(activity, campaignId);
    }

    public void editCampaign(final View view) {
        final Activity activity = ContextHandler.asActivity(view.getContext());
        NavigationController.getInstance().editCampaign(activity, campaignId);
    }

    public void openSynopsis(final View view) {
        final Activity activity = ContextHandler.asActivity(view.getContext());
        NavigationController.getInstance().openSynopsis(activity, campaignId);
    }

    public void managePlayerCharacters(final View view) {
        final Activity activity = ContextHandler.asActivity(view.getContext());
        NavigationController.getInstance().managePlayerCharacters(activity, campaignId);
    }
}
