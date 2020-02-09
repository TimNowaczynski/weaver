package de.quarian.weaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class ActivityPreconditionErrorHandler {

    @NonNull
    private WeakReference<Activity> activityWeakReference;

    public ActivityPreconditionErrorHandler(@NonNull final Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    public boolean requireOrFinish(@NonNull final Callable<Boolean> requirement,
                                @StringRes final int errorTitle,
                                @StringRes final int errorMessage) {
        final Activity activity = activityWeakReference.get();

        Boolean conditionMet = false;
        try {
            conditionMet = requirement.call();
        } catch (Exception e) {
            // TODO: log exception
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
