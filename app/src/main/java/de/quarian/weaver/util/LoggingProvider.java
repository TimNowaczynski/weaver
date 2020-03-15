package de.quarian.weaver.util;

import androidx.annotation.NonNull;

public interface LoggingProvider {

    @NonNull
    Logger getLogger(final Object object);

}
