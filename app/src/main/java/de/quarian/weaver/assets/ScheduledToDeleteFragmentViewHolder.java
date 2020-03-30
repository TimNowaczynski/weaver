package de.quarian.weaver.assets;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduledToDeleteFragmentViewHolder extends RecyclerView.ViewHolder {

    private final TextView dummyText;

    public ScheduledToDeleteFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
        dummyText = (TextView) itemView;
    }

    public void setDummyText(@NonNull final String text) {
        dummyText.setText(text);
    }

}
