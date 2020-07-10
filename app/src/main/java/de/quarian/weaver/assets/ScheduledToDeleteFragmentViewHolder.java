package de.quarian.weaver.assets;

import android.content.Context;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.datamodel.ddo.AssetDisplayObject;
import de.quarian.weaver.theming.ThemeProvider;

public class ScheduledToDeleteFragmentViewHolder extends RecyclerView.ViewHolder {

    private final ScheduledToDeleteListItemAdapter adapter;

    public ScheduledToDeleteFragmentViewHolder(@NonNull final View itemView,
                                               @NonNull final WeaverLayoutInflater weaverLayoutInflater,
                                               @NonNull final ThemeProvider themeProvider) {
        super(itemView);
        final RecyclerView recyclerView = (RecyclerView) itemView;
        final Context context = recyclerView.getContext();
        this.adapter = new ScheduledToDeleteListItemAdapter(context, weaverLayoutInflater, themeProvider);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        /*
            TODO: [Purely optional] Think about a transition from a campaign
             to this activity and filter the items accordingly
         */
    }

    public void setAssetDisplayObjects(@NonNull final List<AssetDisplayObject> displayObjects) {
        this.adapter.setAssetDisplayObjects(displayObjects);
        this.adapter.notifyDataSetChanged();
    }
}
