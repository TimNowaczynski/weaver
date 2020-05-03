package de.quarian.weaver.assets;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.BR;
import de.quarian.weaver.databinding.ScheduledToDeleteListItemBinding;
import de.quarian.weaver.theming.ThemeDisplayObject;

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
