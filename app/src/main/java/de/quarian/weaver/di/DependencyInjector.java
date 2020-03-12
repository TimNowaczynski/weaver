package de.quarian.weaver.di;

import android.app.Activity;

import java.util.concurrent.Callable;

import androidx.annotation.Nullable;
import de.quarian.weaver.BuildConfig;
import de.quarian.weaver.WeaverApplication;
import de.quarian.weaver.theming.SetThemeActivity;

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

    // Weaver Application Dependencies

    public void injectDependencies(final WeaverApplication weaverApplication) {
        try {
            if (useMocks) {
                injectMockedDependencies(weaverApplication);
            } else {
                injectRealDependencies(weaverApplication);
            }
            weaverApplication.onDependenciesInjected();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void injectMockedDependencies(final WeaverApplication weaverApplication) throws Exception {
        assertMocksNotNull(applicationMockModuleProvider, sharedPreferencesMockModuleProvider);

        DaggerApplicationComponent.builder()
                .applicationModule(applicationMockModuleProvider.call())
                .sharedPreferencesModule(sharedPreferencesMockModuleProvider.call())
                .build()
                .inject(weaverApplication);
    }

    private void assertMocksNotNull(final Object... objects) {
        for (final Object object : objects) {
            if (object == null) {
                throw new IllegalStateException("You need to provide mocked Modules.");
            }
        }
    }

    private void injectRealDependencies(final WeaverApplication weaverApplication) {
        final ApplicationModule applicationModule = new ApplicationModule(weaverApplication);
        final SharedPreferencesModule sharedPreferencesModule = new SharedPreferencesModule();

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .sharedPreferencesModule(sharedPreferencesModule)
                .build()
                .inject(weaverApplication);
    }

    public void injectDependencies(final Activity activity) {
        if (activity instanceof SetThemeActivity) {
            final SetThemeActivity setThemeActivity = (SetThemeActivity) activity;
            injectDependencies(setThemeActivity);
        }
    }

    // Set Theme Activity Dependencies

    private void injectDependencies(final SetThemeActivity setThemeActivity) {
        if (useMocks) {
            injectMockedDependencies(setThemeActivity);
        } else {
            injectRealDependencies(setThemeActivity);
        }
        setThemeActivity.onDependenciesInjected();
    }

    private void injectMockedDependencies(final SetThemeActivity setThemeActivity) {
        assertMocksNotNull(activityMockModuleProvider, sharedPreferencesMockModuleProvider);

        try {
            // In this case we need to inject an activity module as well,
            // because our mocks can't use the @Module(includes = xxx) annotation
            DaggerActivityComponent.builder()
                    .applicationModule(applicationMockModuleProvider.call())
                    .activityModule(activityMockModuleProvider.call())
                    .build()
                    .inject(setThemeActivity.activityDependencies);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void injectRealDependencies(final SetThemeActivity setThemeActivity) {
        final ActivityModule activityModule = new ActivityModule(setThemeActivity);

        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(setThemeActivity.activityDependencies);
    }
}
