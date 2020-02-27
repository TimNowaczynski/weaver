package de.quarian.weaver.theming;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.quarian.weaver.R;
import de.quarian.weaver.databinding.FragmentThemePreviewBinding;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerFragmentComponent;
import de.quarian.weaver.di.FragmentModule;

public class ThemePreviewFragment extends Fragment {

    public static class FragmentDependencies {

        @Inject
        public ThemeProvider themeProvider;

    }

    public ThemePreviewFragment() {
        super(R.layout.fragment_theme_preview);
    }

    private final FragmentDependencies fragmentDependencies = new FragmentDependencies();
    private final ThemeDisplayObject themeDisplayObject = new ThemeDisplayObject();
    private FragmentThemePreviewBinding viewBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getContextOrThrow();
        injectDependencies(context);
    }

    private Context getContextOrThrow() {
        final Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Context was null");
        }
        return context;
    }

    private void injectDependencies(Context context) {
        DaggerFragmentComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .fragmentModule(new FragmentModule())
                .build()
                .inject(fragmentDependencies);
    }

    public void setActionColor(int actionColor) {
        themeDisplayObject.actionColor = actionColor;
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
        refreshContent();
    }

    public void setItemTextColor(int itemTextColor) {
        themeDisplayObject.itemTextColor = itemTextColor;
        refreshContent();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding = FragmentThemePreviewBinding.bind(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContent();
    }

    protected void refreshContent() {
        AsyncTask.execute(() -> {
            viewBinding.setDraftedThemeDisplayObject(themeDisplayObject);
            viewBinding.notifyChange();
        });
    }
}
