package de.quarian.weaver.di;

import android.app.Activity;

import java.lang.ref.WeakReference;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import de.quarian.weaver.ActivityPreconditionErrorHandler;

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
    public ActivityPreconditionErrorHandler errorHandler() {
        final Activity activity = this.activity.get();
        if (activity == null) {
            // TODO: log error
            return null;
        }
        return new ActivityPreconditionErrorHandler(activity);
    }
}
