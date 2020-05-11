package de.quarian.weaver.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

// TODO: Extend with cloud logging solution
public class ErrorHandler {

    @NonNull
    private final Context context;

    @NonNull
    private final AndroidToastHandler androidToastHandler;

    public ErrorHandler(@NonNull final Context context, @NonNull final AndroidToastHandler androidToastHandler) {
        this.context = context;
        this.androidToastHandler = androidToastHandler;
    }

    /**
     * This method is used to require certain conditions and show an error dialog
     * which closes the activity if those are not met, after confirming the message.
     * @param supplier The method to be invoked which should return the result.
     * @param genericDialogBuilder A configured builder to create an error dialog
     * @param <T> The type of the expected result.
     * @return The result itself, created by the supplier, or null.
     */
    @Nullable
    public <T> T requireHard(@NonNull final Supplier<T> supplier, @NonNull final GenericDialogBuilder genericDialogBuilder) {
        final T result = supplier.get();
        final T processedResult = processBoolean(result);
        if (processedResult == null) {
            genericDialogBuilder.showDialog(context);
        }
        return processedResult;
    }

    private <T> T processBoolean(final T result) {
        T intermediateResult = result;
        if (result instanceof Boolean) {
            final Boolean bool = (Boolean) result;
            if (!bool) {
                intermediateResult = null;
            }
        }
        return intermediateResult;
    }

    /**
     * This method is used to require certain conditions and show a toast
     * to the user. The activity will continue to run though.
     * @param supplier The method to be invoked which should return the result.
     * @param <T> The type of the expected result.
     * @return The result itself, created by the supplier, or null.
     */
    @Nullable
    public <T> T requireSoft(@NonNull final Supplier<T> supplier, @StringRes final int errorMessage) {
        final T result = supplier.get();
        final T processedResult = processBoolean(result);
        if (processedResult == null) {
            androidToastHandler.showToast(errorMessage, Toast.LENGTH_LONG);
        }
        return processedResult;
    }

}
