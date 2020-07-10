package de.quarian.weaver.namesets;

import android.view.View;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConfigureNameSetsListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CheckedTextView checkedTextView;

    public ConfigureNameSetsListItemViewHolder(@NonNull final View view) {
        super(view);
        checkedTextView = (CheckedTextView) view;
        checkedTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO: backend stuff :)
        if (checkedTextView.isChecked()) {
            checkedTextView.setChecked(false);
        } else {
            checkedTextView.setChecked(true);
        }
    }

    public void setChecked(@NonNull final String item) {
        checkedTextView.setChecked(true);
        checkedTextView.setText(item);
    }

    public void setUnchecked(@NonNull final String item) {
        checkedTextView.setChecked(false);
        checkedTextView.setText(item);
    }
}
