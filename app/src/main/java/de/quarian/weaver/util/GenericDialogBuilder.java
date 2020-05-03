package de.quarian.weaver.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import de.quarian.weaver.R;
import de.quarian.weaver.databinding.GenericDialogBinding;
import de.quarian.weaver.theming.WeaverThemedActivity;
import io.reactivex.exceptions.OnErrorNotImplementedException;

public class GenericDialogBuilder {

    public static class Factory {

        public GenericDialogBuilder getBuilder() {
            return new GenericDialogBuilder();
        }

    }

    private static final int UNDEFINED = -1;

    @StringRes
    private int title = UNDEFINED;

    @StringRes
    private int message = UNDEFINED;

    @StringRes
    private int primaryButtonText;

    @Nullable
    private View.OnClickListener primaryButtonAction;

    private boolean cancelable = false;

    private GenericDialogBuilder() {
        primaryButtonText = R.string.generic_okay;
    }

    public void showDialog(@NonNull final Context context) {
        if (message == UNDEFINED) { // title == UNDEFINED || ?
            throw new IllegalStateException("You need to define at least an error message.");
        }

        final AlertDialog alertDialog = createDialog(context);
        alertDialog.show();
    }

    @SuppressWarnings("WeakerAccess")
    @VisibleForTesting
    @NonNull
    protected AlertDialog createDialog(@NonNull final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = createView(context);
        builder.setView(dialogView);
        builder.setCancelable(cancelable);

        final AlertDialog alertDialog = builder.create();
        setPrimaryListener(alertDialog);
        setCancelListener(alertDialog);
        return alertDialog;
    }

    private View createView(@NonNull final Context context) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.generic_dialog, null);
        final GenericDialogBinding binding = DataBindingUtil.bind(view);

        if (binding == null) {
            throw new NullPointerException();
        }

        if (context instanceof WeaverThemedActivity) {
            final WeaverThemedActivity weaverThemedActivity = (WeaverThemedActivity) context;
            binding.setTheme(weaverThemedActivity.getThemeDisplayObject());
        } else {
            // TODO: think about providing a fallback theme
            throw new IllegalStateException();
        }

        binding.genericDialogText.setText(message);
        binding.genericDialogConfirmButton.setText(primaryButtonText);
        binding.genericDialogConfirmButton.setOnClickListener(primaryButtonAction);
        return view;
    }

    private void setPrimaryListener(@NonNull final AlertDialog alertDialog) {
        final View primaryDialogButtonView = alertDialog.findViewById(R.id.generic_dialog_confirm_button);
        if (primaryButtonAction == null) {
            primaryDialogButtonView.setOnClickListener((view) -> alertDialog.dismiss());
        } else {
            primaryDialogButtonView.setOnClickListener((view) -> {
                primaryButtonAction.onClick(primaryDialogButtonView);
                alertDialog.dismiss();
            });
        }
    }

    private void setCancelListener(@NonNull final AlertDialog alertDialog) {
        if (!cancelable) {
            return;
        }

        final View cancelDialogButtonView = alertDialog.findViewById(R.id.generic_dialog_cancel_button);
        cancelDialogButtonView.setOnClickListener((view) -> alertDialog.dismiss());
    }

    public void setTitle(@StringRes final int title) {
        this.title = title;
        throw new OnErrorNotImplementedException("Generic dialog title not implemented yet", new RuntimeException());
    }

    public void setMessage(@StringRes final int message) {
        this.message = message;
    }

    public void setPrimaryButtonText(@StringRes final int primaryButtonText) {
        this.primaryButtonText = primaryButtonText;
    }

    public void setPrimaryButtonAction(@NonNull final View.OnClickListener primaryButtonAction) {
        this.primaryButtonAction = primaryButtonAction;
    }

    public void setCancelable(final boolean cancelable) {
        this.cancelable = cancelable;
    }
}
