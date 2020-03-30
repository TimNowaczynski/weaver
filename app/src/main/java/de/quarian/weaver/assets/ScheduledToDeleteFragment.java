package de.quarian.weaver.assets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.quarian.weaver.R;

public class ScheduledToDeleteFragment extends Fragment {

    public enum Mode {
        SCHEDULED, UNSCHEDULED
    }

    private final Mode mode;
    private ScheduledToDeleteFragmentViewHolder viewHolder;

    public ScheduledToDeleteFragment(@NonNull final Mode mode) {
        this.mode = mode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_scheduled_to_delete, container, true);
        viewHolder = new ScheduledToDeleteFragmentViewHolder(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewHolder.setDummyText(mode.name());
    }

}
