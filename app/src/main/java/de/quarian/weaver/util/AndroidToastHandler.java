package de.quarian.weaver.util;

import androidx.annotation.StringRes;

/**
 * Basically a wrapper so we can test invocations here which otherwise would be static calls
 */
public interface AndroidToastHandler {

    void showToast(@StringRes final int text);

    void showToast(@StringRes final int text, final int duration);

}
