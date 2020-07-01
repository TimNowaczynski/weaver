package de.quarian.weaver.di;

import javax.inject.Singleton;

import dagger.Component;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.campaigns.CampaignEditorActivity;
import de.quarian.weaver.campaigns.CampaignListActivity;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.players.PlayerCharacterListActivity;
import de.quarian.weaver.theming.SetThemeActivity;
import de.quarian.weaver.theming.WeaverThemedActivity;

@Component(modules = {
        ActivityModule.class,
        SharedPreferencesModule.class
})
@Singleton
public interface ActivityComponent {

    void inject(final WeaverActivity.WeaverActivityDependencies weaverActivity);
    void inject(final WeaverThemedActivity.WeaverThemedActivityDependencies weaverThemedActivity);
    void inject(final CampaignListActivity.ActivityDependencies campaignListActivity);
    void inject(final CampaignEditorActivity.ActivityDependencies campaignEditorActivity);
    void inject(final PlayerCharacterListActivity.ActivityDependencies playerCharacterListActivity);
    void inject(final SetThemeActivity.ActivityDependencies setThemeActivity);
    void inject(final DeveloperFunctionsActivity.ActivityDependencies developerFunctionsActivity);
}
