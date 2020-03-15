package de.quarian.weaver;

import android.app.Activity;
import android.app.AlertDialog;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import de.quarian.weaver.util.LogLevel;
import de.quarian.weaver.util.LoggingProvider;

// TODO: Not sure if it's worth refactoring to be able to test this
public class ActivityPreconditionErrorHandler {

    @NonNull
    private WeakReference<Activity> activityWeakReference;

    @NonNull
    private LoggingProvider loggingProvider;

    public ActivityPreconditionErrorHandler(@NonNull final Activity activity, @NonNull final LoggingProvider loggingProvider) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.loggingProvider = loggingProvider;
    }

    public boolean requireOrFinish(@NonNull final Callable<Boolean> requirement,
                                @StringRes final int errorTitle,
                                @StringRes final int errorMessage) {
        final Activity activity = activityWeakReference.get();

        Boolean conditionMet = false;
        try {
            conditionMet = requirement.call();
        } catch (Exception e) {
            loggingProvider.getLogger(this).log(LogLevel.ERROR, e.getMessage());
            activity.finish();
        }

        if (!conditionMet && activity != null) {
            // TODO: style res?
            final AlertDialog errorDialog = new AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setTitle(errorTitle)
                    .setMessage(errorMessage)
                    .setPositiveButton(R.string.generic_okay, (dialog, which) -> activity.finish())
                    .setOnDismissListener((dialog) -> activity.finish())
                    .create();
            errorDialog.show();
        }

        return conditionMet;
    }
}
