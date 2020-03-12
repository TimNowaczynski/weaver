package de.quarian.weaver;

import android.app.Application;
import android.os.Handler;

import javax.inject.Inject;

import de.quarian.weaver.di.DependencyInjectionListener;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.GlobalHandler;

public class WeaverApplication extends Application implements DependencyInjectionListener {

    @Inject
    @GlobalHandler
    public Handler globalHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
    }

    private void injectDependencies() {
        DependencyInjector.get().injectDependencies(this);
    }

    @Override
    public void onDependenciesInjected() {
        if (DependencyInjector.get().shouldUseMocks()) {
            // In Test Code we need to call this again to overwrite the real dependencies
            // with mocks if applicable
            injectDependencies();
        }
    }
}
