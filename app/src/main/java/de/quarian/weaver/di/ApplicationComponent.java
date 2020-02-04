package de.quarian.weaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.quarian.weaver.WeaverApplication;
import de.quarian.weaver.campaigns.CampaignListActivity;
import de.quarian.weaver.characters.CharacterLibraryActivity;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.theming.SetThemeActivity;
import de.quarian.weaver.theming.WeaverThemedActivity;

@Component(modules = {
        ApplicationModule.class,
        SharedPreferencesModule.class
})
@Singleton
public interface ApplicationComponent {

    void inject(final WeaverApplication weaverApplication);
    void inject(final WeaverThemedActivity  weaverThemedActivity);
    void inject(final CampaignListActivity campaignListActivity);
    void inject(final CharacterLibraryActivity characterLibraryActivity);
    void inject(final SetThemeActivity setThemeActivity);
    void inject(final DeveloperFunctionsActivity developerFunctionsActivity);
}
