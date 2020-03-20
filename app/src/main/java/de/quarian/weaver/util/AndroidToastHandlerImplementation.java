package de.quarian.weaver.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;
import de.quarian.weaver.di.ApplicationContext;

public class AndroidToastHandlerImplementation implements AndroidToastHandler {

    @ApplicationContext
    private final Context applicationContext;

    public AndroidToastHandlerImplementation(@ApplicationContext final Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void showToast(@StringRes final int text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    @Override
    public void showToast(int text, int duration) {
        Toast.makeText(applicationContext, text, duration).show();
    }
}
