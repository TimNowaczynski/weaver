package de.quarian.weaver.namesets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.ddo.NameSetDisplayObject;

public class ConfigureNameSetsAdapter extends RecyclerView.Adapter<ConfigureNameSetsListItemViewHolder> {

    private final List<NameSetDisplayObject> nameSetDisplayObjects = new ArrayList<>();
    private List<Long> selectedNameSetIds;

    public void setNameSetDisplayObjects(@NonNull final List<NameSetDisplayObject> nameSetDisplayObjects) {
        this.nameSetDisplayObjects.clear();
        this.nameSetDisplayObjects.addAll(nameSetDisplayObjects);
        initSelectedIds(nameSetDisplayObjects);
        notifyDataSetChanged();
    }

    private void initSelectedIds(@NonNull final List<NameSetDisplayObject> nameSetDisplayObjects) {
        final int initialCapacity = nameSetDisplayObjects.size();
        selectedNameSetIds = new ArrayList<>(initialCapacity);
        for (final NameSetDisplayObject nameSetDisplayObject : nameSetDisplayObjects) {
            final long nameSetId = nameSetDisplayObject.getId();
            selectedNameSetIds.add(nameSetId);
        }
    }

    @NonNull
    @Override
    public ConfigureNameSetsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.fragment_configure_name_sets_list_item, parent, false);
        return new ConfigureNameSetsListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ConfigureNameSetsListItemViewHolder holder, int position) {
        final NameSetDisplayObject nameSetDisplayObject = nameSetDisplayObjects.get(position);
        final String nameSetName = nameSetDisplayObject.getNameSetName();
        if (nameSetDisplayObject.isSelected()) {
            holder.setChecked(nameSetName);
        } else {
            holder.setUnchecked(nameSetName);
        }
    }

    @Override
    public int getItemCount() {
        return nameSetDisplayObjects.size();
    }

    public List<Long> getSelectedNameSetIds() {
        return selectedNameSetIds;
    }
}
