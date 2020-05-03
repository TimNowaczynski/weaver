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

    public ErrorHandler(@NonNull final Context context) {
        this.context = context;
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
        if (result == null) {
            genericDialogBuilder.showDialog(context);
        }
        return result;
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
        if (result == null) {
            Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show();
        }
        return result;
    }

}
