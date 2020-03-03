package de.quarian.weaver;

import android.app.Application;
import android.os.Handler;

import javax.inject.Inject;

import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.di.ModuleProvider;
import de.quarian.weaver.di.SharedPreferencesModule;

public class WeaverApplication extends Application {

    @Inject
    @GlobalHandler
    public Handler globalHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        ModuleProvider.get().disableMocks();
        injectDependencies();
    }

    private void injectDependencies() {
        final ApplicationModule applicationModule = new ApplicationModule(this);
        final SharedPreferencesModule sharedPreferencesModule = new SharedPreferencesModule();

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .sharedPreferencesModule(sharedPreferencesModule)
                .build()
                .inject(this);
    }
}
