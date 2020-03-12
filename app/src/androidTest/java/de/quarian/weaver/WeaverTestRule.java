package de.quarian.weaver;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.intercepting.SingleActivityFactory;
import de.quarian.weaver.di.DependencyInjector;

public class WeaverTestRule<T extends Activity> extends ActivityTestRule<T> {

    private boolean useMocks = false;

    public WeaverTestRule(final Class<T> activityClass) {
        super(activityClass);
    }

    public WeaverTestRule(final Class<T> activityClass, final boolean useMocks) {
        super(activityClass);
        this.useMocks = useMocks;
        DependencyInjector.get().setUseMocks(useMocks);
    }

    public WeaverTestRule(final Class<T> activityClass, final boolean useMocks, final boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
        this.useMocks = useMocks;
        DependencyInjector.get().setUseMocks(useMocks);
    }

    public WeaverTestRule(final Class<T> activityClass, final boolean useMocks, final boolean initialTouchMode, final boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
        this.useMocks = useMocks;
        DependencyInjector.get().setUseMocks(useMocks);
    }

    public WeaverTestRule(final SingleActivityFactory<T> activityFactory, final boolean useMocks, final boolean initialTouchMode, final boolean launchActivity) {
        super(activityFactory, initialTouchMode, launchActivity);
        this.useMocks = useMocks;
        DependencyInjector.get().setUseMocks(useMocks);
    }

    public WeaverTestRule(final Class<T> activityClass, final boolean useMocks, final @NonNull String targetPackage, final int launchFlags, final boolean initialTouchMode, final boolean launchActivity) {
        super(activityClass, targetPackage, launchFlags, initialTouchMode, launchActivity);
        DependencyInjector.get().setUseMocks(useMocks);
        this.useMocks = useMocks;
    }
}
