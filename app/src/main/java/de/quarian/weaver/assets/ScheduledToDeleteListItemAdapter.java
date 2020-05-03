package de.quarian.weaver.assets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.theming.ThemeDisplayObject;
import de.quarian.weaver.theming.ThemeProvider;

public class ScheduledToDeleteListItemAdapter extends RecyclerView.Adapter<ScheduledToDeleteListItemViewHolder> {

    @NonNull
    private final WeakReference<Context> context;

    @NonNull
    private final WeaverLayoutInflater weaverLayoutInflater;

    // Currently unused, see below
    @NonNull
    private final ThemeProvider themeProvider;

    @NonNull
    private final List<AssetDisplayObject> assetDisplayObjects = new ArrayList<>();

    public ScheduledToDeleteListItemAdapter(@NonNull final Context context,
                                            @NonNull final WeaverLayoutInflater weaverLayoutInflater,
                                            @NonNull final ThemeProvider themeProvider) {
        this.context = new WeakReference<>(context);
        this.weaverLayoutInflater = weaverLayoutInflater;
        this.themeProvider = themeProvider;
    }

    @NonNull
    @Override
    public ScheduledToDeleteListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = weaverLayoutInflater.inflate(R.layout.scheduled_to_delete_list_item, parent);
        final ScheduledToDeleteListItemViewHolder scheduledToDeleteListItemViewHolder = new ScheduledToDeleteListItemViewHolder(view);

        final Context context = this.context.get();
        // TODO: That's not nice. Show an error and log this, not just crash with an exception.
        Objects.requireNonNull(context);

        // TODO: query custom theme if applicable, therefore the activity must also be available from within a campaign
        // TODO: IF we really need this
        scheduledToDeleteListItemViewHolder.setTheme(ThemeDisplayObject.getDefault(context));
        return scheduledToDeleteListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledToDeleteListItemViewHolder holder, int position) {
        final AssetDisplayObject assetDisplayObject = assetDisplayObjects.get(position);
        holder.setAsset(assetDisplayObject);
    }

    @Override
    public int getItemCount() {
        return assetDisplayObjects.size();
    }

    public void setAssetDisplayObjects(final List<AssetDisplayObject> assetDisplayObjects) {
        this.assetDisplayObjects.clear();
        this.assetDisplayObjects.addAll(assetDisplayObjects);
        notifyDataSetChanged();
    }
}
