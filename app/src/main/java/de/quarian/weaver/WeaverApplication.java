package de.quarian.weaver;

import android.app.Application;
import android.os.Handler;

import javax.inject.Inject;

import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;
import de.quarian.weaver.di.GlobalHandler;

public class WeaverApplication extends Application {

    @Inject
    @GlobalHandler
    public Handler globalHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
    }

    private void injectDependencies() {
        final ApplicationModule applicationModule = new ApplicationModule(this);

        DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build()
                .inject(this);
    }
}
