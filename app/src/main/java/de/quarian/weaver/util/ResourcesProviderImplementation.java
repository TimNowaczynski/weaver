package de.quarian.weaver.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import de.quarian.weaver.di.ApplicationContext;

public class ResourcesProviderImplementation implements ResourcesProvider {

    @ApplicationContext
    private final Context applicationContext;

    public ResourcesProviderImplementation(@NonNull @ApplicationContext final Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Resources provide() {
        return applicationContext.getResources();
    }

}
