package de.quarian.weaver.assets;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.BR;
import de.quarian.weaver.databinding.ScheduledToDeleteListItemBinding;
import de.quarian.weaver.datamodel.ddo.AssetDisplayObject;
import de.quarian.weaver.datamodel.ddo.ThemeDisplayObject;

/**
 * TODO:
 *  a) Change Text Color to White?
 *  b) Cell Padding (Distance between border / content) - also maybe the dark-greenish should be the bg color
 *  c) Delete all / move all to cloud button
 *  d) Can / should we somehow make the tab-bar distinguishable from the app bar?
 *  e) Click to open asset
 */
public class ScheduledToDeleteListItemViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final ScheduledToDeleteListItemBinding viewBinding;

    public ScheduledToDeleteListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        viewBinding = ScheduledToDeleteListItemBinding.bind(itemView);
    }

    public void setTheme(@NonNull final ThemeDisplayObject themeDisplayObject) {
        viewBinding.setTheme(themeDisplayObject);
        viewBinding.notifyPropertyChanged(BR.theme);
    }

    public void setAsset(@NonNull final AssetDisplayObject assetDisplayObject) {
        viewBinding.setAsset(assetDisplayObject);
        viewBinding.notifyPropertyChanged(BR.asset);
    }
}
