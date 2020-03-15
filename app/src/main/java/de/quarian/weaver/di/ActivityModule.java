package de.quarian.weaver.di;

import android.app.Activity;

import java.lang.ref.WeakReference;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.util.LogLevel;
import de.quarian.weaver.util.LoggingProvider;

@Module(includes = ApplicationModule.class)
public class ActivityModule {

    private final WeakReference<Activity> activity;

    public ActivityModule(@NonNull Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Provides
    @Singleton
    @Nullable
    public Activity activity() {
        return activity.get();
    }

    @Provides
    @Singleton
    @Nullable
    public ActivityPreconditionErrorHandler errorHandler(final LoggingProvider loggingProvider) {
        final Activity activity = this.activity.get();
        if (activity == null) {
            loggingProvider.getLogger(this).log(LogLevel.ERROR, "Activity Weak Reference was null");
            return null;
        }
        return new ActivityPreconditionErrorHandler(activity, loggingProvider);
    }
}
