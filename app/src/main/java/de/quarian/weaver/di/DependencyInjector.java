package de.quarian.weaver.di;

import android.app.Activity;
import android.content.Context;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import de.quarian.weaver.BuildConfig;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.WeaverApplication;
import de.quarian.weaver.assets.ScheduledToDeleteFragment;
import de.quarian.weaver.campaigns.CampaignEditorActivity;
import de.quarian.weaver.campaigns.CampaignListActivity;
import de.quarian.weaver.characters.CharacterLibraryActivity;
import de.quarian.weaver.dev.DeveloperFunctionsActivity;
import de.quarian.weaver.namesets.ConfigureNameSetsFragment;
import de.quarian.weaver.players.PlayerCharacterListActivity;
import de.quarian.weaver.theming.SetThemeActivity;
import de.quarian.weaver.theming.ThemePreviewFragment;
import de.quarian.weaver.theming.WeaverThemedActivity;

// TODO: test this
public class DependencyInjector {

    private static final DependencyInjector instance = new DependencyInjector();
    public static DependencyInjector get() {
        return instance;
    }

    private boolean useMocks;
    private Callable<ApplicationModule> applicationMockModuleProvider;
    private Callable<ActivityModule> activityMockModuleProvider;
    private Callable<SharedPreferencesModule> sharedPreferencesMockModuleProvider;

    public void setUseMocks(boolean useMocks) {
        if (!BuildConfig.DEBUG && useMocks) {
            throw new IllegalStateException("Mocks aren't allowed for non-debug builds.");
        }
        this.useMocks = useMocks;
    }

    public boolean shouldUseMocks() {
        return useMocks;
    }

    public void setApplicationMockModuleProvider(@Nullable Callable<ApplicationModule> applicationMockModuleProvider) {
        this.applicationMockModuleProvider = applicationMockModuleProvider;
    }

    public void setActivityMockModuleProvider(@Nullable Callable<ActivityModule> activityMockModuleProvider) {
        this.activityMockModuleProvider = activityMockModuleProvider;
    }

    public void setSharedPreferencesMockModuleProvider(@Nullable Callable<SharedPreferencesModule> sharedPreferencesMockModuleProvider) {
        this.sharedPreferencesMockModuleProvider = sharedPreferencesMockModuleProvider;
    }

    // Modules

    private ApplicationModule getApplicationModule(@NonNull final Context context) {
        try {
            if (useMocks) {
                assertMocksNotNull(applicationMockModuleProvider);
                return applicationMockModuleProvider.call();
            } else {
                return new ApplicationModule(context);
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assertMocksNotNull(final Object... objects) {
        for (final Object object : objects) {
            if (object == null) {
                throw new IllegalStateException("You need to provide mocked Modules.");
            }
        }
    }

    private ApplicationModule getApplicationModule(@NonNull final Fragment fragment) {
        final FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            throw new NullPointerException();
        }
        return getApplicationModule(activity);
    }

    private ActivityModule getActivityModule(@NonNull final Activity activity) {
        try {
            if (useMocks) {
                assertMocksNotNull(activityMockModuleProvider);
                return activityMockModuleProvider.call();
            } else {
                return new ActivityModule(activity);
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ActivityModule getActivityModule(@NonNull final Fragment fragment) {
        final FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            throw new NullPointerException();
        }
        return getActivityModule(activity);
    }

    private SharedPreferencesModule getSharedPreferencesModule() {
        try {
            if (useMocks) {
                assertMocksNotNull(sharedPreferencesMockModuleProvider);
                return sharedPreferencesMockModuleProvider.call();
            } else {
                return new SharedPreferencesModule();
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Weaver Application Dependencies

    public void injectDependencies(final WeaverApplication weaverApplication) {
        DaggerApplicationComponent.builder()
                .applicationModule(getApplicationModule(weaverApplication))
                .sharedPreferencesModule(getSharedPreferencesModule())
                .build()
                .inject(weaverApplication);
    }

    // Activity Dependencies

    public void injectDependencies(final WeaverActivity weaverActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(weaverActivity))
                .activityModule(getActivityModule(weaverActivity))
                .build()
                .inject(weaverActivity.weaverActivityDependencies);
    }

    public void injectDependencies(final WeaverThemedActivity weaverThemedActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(weaverThemedActivity))
                .activityModule(getActivityModule(weaverThemedActivity))
                .build()
                .inject(weaverThemedActivity.weaverThemedActivityDependencies);
    }

    public void injectDependencies(final CampaignListActivity campaignListActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(campaignListActivity))
                .activityModule(getActivityModule(campaignListActivity))
                .build()
                .inject(campaignListActivity.activityDependencies);
        callOnDependenciesInjected(campaignListActivity);
    }

    private void callOnDependenciesInjected(final Object listener) {
        if (listener instanceof DependencyInjectionListener) {
            final DependencyInjectionListener dependencyInjectionListener = (DependencyInjectionListener) listener;
            dependencyInjectionListener.onDependenciesInjected();
        }
    }

    public void injectDependencies(final CharacterLibraryActivity characterLibraryActivity) {
        DaggerApplicationComponent.builder()
                .applicationModule(getApplicationModule(characterLibraryActivity))
                .build()
                .inject(characterLibraryActivity.activityDependencies);
        callOnDependenciesInjected(characterLibraryActivity);
    }

    public void injectDependencies(final PlayerCharacterListActivity playerCharacterListActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(playerCharacterListActivity))
                .activityModule(getActivityModule(playerCharacterListActivity))
                .build()
                .inject(playerCharacterListActivity.activityDependencies);
        callOnDependenciesInjected(playerCharacterListActivity);
    }

    public void injectDependencies(final CampaignEditorActivity campaignEditorActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(campaignEditorActivity))
                .activityModule(getActivityModule(campaignEditorActivity))
                .sharedPreferencesModule(getSharedPreferencesModule())
                .build()
                .inject(campaignEditorActivity.activityDependencies);
        callOnDependenciesInjected(campaignEditorActivity);
    }

    public void injectDependencies(final SetThemeActivity setThemeActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(setThemeActivity))
                .activityModule(getActivityModule(setThemeActivity))
                .build()
                .inject(setThemeActivity.activityDependencies);
        callOnDependenciesInjected(setThemeActivity);
    }

    public void injectDependencies(final DeveloperFunctionsActivity developerFunctionsActivity) {
        DaggerActivityComponent.builder()
                .applicationModule(getApplicationModule(developerFunctionsActivity))
                .activityModule(getActivityModule(developerFunctionsActivity))
                .build()
                .inject(developerFunctionsActivity.activityDependencies);
        callOnDependenciesInjected(developerFunctionsActivity);
    }

    // Fragment Dependencies

    public void injectDependencies(final ThemePreviewFragment themePreviewFragment) {
        DaggerFragmentComponent.builder()
                .applicationModule(getApplicationModule(themePreviewFragment))
                .fragmentModule(new FragmentModule())
                .build()
                .inject(themePreviewFragment.fragmentDependencies);
        callOnDependenciesInjected(themePreviewFragment);
    }

    public void injectDependencies(final ScheduledToDeleteFragment scheduledToDeleteFragment) {
        DaggerFragmentComponent.builder()
                .applicationModule(getApplicationModule(scheduledToDeleteFragment))
                .fragmentModule(new FragmentModule())
                .build()
                .inject(scheduledToDeleteFragment.fragmentDependencies);
        callOnDependenciesInjected(scheduledToDeleteFragment);
    }

    public void injectDependencies(final ConfigureNameSetsFragment configureNameSetsFragment) {
        DaggerFragmentComponent.builder()
                .applicationModule(getApplicationModule(configureNameSetsFragment))
                .fragmentModule(new FragmentModule())
                .build()
                .inject(configureNameSetsFragment.fragmentDependencies);
        callOnDependenciesInjected(configureNameSetsFragment);
    }
}
