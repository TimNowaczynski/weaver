package de.quarian.weaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.quarian.weaver.theming.ThemePreviewFragment;

@Component(modules = {
        FragmentModule.class
})
@Singleton
public interface FragmentComponent {

    void inject(final ThemePreviewFragment.FragmentDependencies themePreviewFragment);

}
