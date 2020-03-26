package de.quarian.weaver.theming;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.quarian.weaver.R;
import de.quarian.weaver.databinding.FragmentThemePreviewBinding;
import de.quarian.weaver.di.DependencyInjector;

public class ThemePreviewFragment extends Fragment {

    public static class FragmentDependencies {

        @Inject
        public ThemeProvider themeProvider;

    }

    public ThemePreviewFragment() {
        super(R.layout.fragment_theme_preview);
    }

    public final FragmentDependencies fragmentDependencies = new FragmentDependencies();
    private ThemeDisplayObject themeDisplayObject;
    private FragmentThemePreviewBinding viewBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeDisplayObject = new ThemeDisplayObject(Objects.requireNonNull(getContext()));

        DependencyInjector.get().injectDependencies(this);
        themeDisplayObject.refresh();
    }

    public void setActionColor(int actionColor) {
        themeDisplayObject.actionColor = actionColor;
        themeDisplayObject.refresh();
        refreshContent();
    }

    public void setBackgroundColor(int backgroundColor) {
        themeDisplayObject.backgroundColor = backgroundColor;
        refreshContent();
    }

    public void setBackgroundTextColor(int backgroundTextColor) {
        themeDisplayObject.backgroundTextColor = backgroundTextColor;
        refreshContent();
    }

    public void setItemColor(int itemColor) {
        themeDisplayObject.itemColor = itemColor;
        themeDisplayObject.refresh();
        refreshContent();
    }

    public void setItemTextColor(int itemTextColor) {
        themeDisplayObject.itemTextColor = itemTextColor;
        refreshContent();
    }

    public ThemeDisplayObject getThemeDisplayObject() {
        return themeDisplayObject;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewBinding == null) {
            viewBinding = FragmentThemePreviewBinding.bind(view);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContent();
    }

    protected void refreshContent() {
        AsyncTask.execute(() -> {
            themeDisplayObject.refresh();
            viewBinding.setDraftedThemeDisplayObject(themeDisplayObject);
            viewBinding.notifyChange();
        });
    }
}
