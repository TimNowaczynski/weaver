package de.quarian.weaver.util;

import android.util.Log;

import androidx.annotation.NonNull;

public class LocalLogger implements Logger {

    private final String prefix;

    private LocalLogger(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void log(final LogLevel level, final String message) {
        switch (level) {
            case DEBUG:
                Log.d(prefix, message);
                break;
            case INFO:
                Log.i(prefix, message);
                break;
            case ERROR:
                Log.e(prefix, message);
                break;
        }
    }

    public static class LocalLoggingProvider implements LoggingProvider {

        @NonNull
        @Override
        public Logger getLogger(final Object object) {
            return new LocalLogger(object.getClass().getSimpleName());
        }
    }
}
