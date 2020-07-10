package de.quarian.weaver.assets;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.datamodel.ddo.AssetDisplayObject;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.service.AssetService;
import de.quarian.weaver.theming.ThemeProvider;

public class ScheduledToDeleteFragment extends Fragment {

    public static class FragmentDependencies {

        @Inject
        public WeaverLayoutInflater weaverLayoutInflater;

        @Inject
        public AssetService assetService;

        @Inject
        public ThemeProvider themeProvider;

    }

    public enum Mode {
        SCHEDULED, UNSCHEDULED
    }

    public final FragmentDependencies fragmentDependencies = new FragmentDependencies();
    private final Mode mode;
    private ScheduledToDeleteFragmentViewHolder viewHolder;

    public ScheduledToDeleteFragment(@NonNull final Mode mode) {
        this.mode = mode;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        DependencyInjector.get().injectDependencies(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_scheduled_to_delete, container, true);
        viewHolder = new ScheduledToDeleteFragmentViewHolder(view, fragmentDependencies.weaverLayoutInflater, fragmentDependencies.themeProvider);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AsyncTask.execute(this::readAssets);
    }

    private void readAssets() {
        final List<AssetDisplayObject> assetDisplayObjects;
        if (mode == Mode.UNSCHEDULED) {
            assetDisplayObjects = fragmentDependencies.assetService.getAssetsWithUnlimitedLifetime();
        } else {
            assetDisplayObjects = fragmentDependencies.assetService.getAssetsWithLimitedLifetime();
        }

        final FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new IllegalStateException();
        }

        activity.runOnUiThread(() -> viewHolder.setAssetDisplayObjects(assetDisplayObjects));
    }

}
