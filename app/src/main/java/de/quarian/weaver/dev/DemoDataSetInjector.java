package de.quarian.weaver.dev;

import android.content.Context;

public class DemoDataSetInjector {

    private static DemoDataSetInjector demoDataSetInjector = null;

    private DemoDataSetInjector(final Context context) {

    }

    public static DemoDataSetInjector get(final Context context) {
        if (demoDataSetInjector == null) {
            demoDataSetInjector = new DemoDataSetInjector(context);
        }
        return demoDataSetInjector;
    }

    public void resetDatabaseToStateA() {

    }

    // BEGIN STATE A
    // END STATE A
}
