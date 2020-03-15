package de.quarian.weaver;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.di.DependencyInjectionListener;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.util.Logger;
import de.quarian.weaver.util.LoggingProvider;

public abstract class WeaverActivity extends AppCompatActivity implements DependencyInjectionListener {

    public static class WeaverActivityDependencies {

        @Inject
        public LoggingProvider loggingProvider;

    }

    public final WeaverActivityDependencies weaverActivityDependencies = new WeaverActivityDependencies();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyInjector.get().injectDependencies(this);
    }

    public Logger getLogger(final Object object) {
        return weaverActivityDependencies.loggingProvider.getLogger(object);
    }
}
