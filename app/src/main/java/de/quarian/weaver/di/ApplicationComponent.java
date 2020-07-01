package de.quarian.weaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.quarian.weaver.WeaverApplication;
import de.quarian.weaver.campaigns.CampaignListActivity;
import de.quarian.weaver.characters.CharacterLibraryActivity;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.players.PlayerCharacterListActivity;
import de.quarian.weaver.theming.SetThemeActivity;
import de.quarian.weaver.theming.WeaverThemedActivity;

@Component(modules = {
        ApplicationModule.class,
        SharedPreferencesModule.class
})
@Singleton
public interface ApplicationComponent {

    // TODO: find a nice solution for the following:
    // Commented out lines which are using the Activity Component

    void inject(final WeaverApplication weaverApplication);
    //void inject(final WeaverThemedActivity.ActivityDependencies  weaverThemedActivity);
    //void inject(final CampaignListActivity.ActivityDependencies campaignListActivity);
    //void inject(final PlayerCharacterListActivity.ActivityDependencies playerCharacterListActivity);
    void inject(final CharacterLibraryActivity.ActivityDependencies characterLibraryActivity);
    void inject(final SetThemeActivity.ActivityDependencies setThemeActivity);
    void inject(final DeveloperFunctionsActivity.ActivityDependencies developerFunctionsActivity);
}
