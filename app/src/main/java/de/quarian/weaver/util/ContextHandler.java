package de.quarian.weaver.util;

import android.app.Activity;
import android.content.Context;

public class ContextHandler {

    public static Activity asActivity(final Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new ClassCastException("Could not cast context to activity");
    }
}
