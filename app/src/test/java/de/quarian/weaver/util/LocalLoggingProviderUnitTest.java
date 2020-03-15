package de.quarian.weaver.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class LocalLoggingProviderUnitTest {

    @Test
    public void testProvideLocalLogger() {
        final LoggingProvider loggingProvider = new LocalLogger.LocalLoggingProvider();
        final Logger logger = loggingProvider.getLogger(this);
        assertThat(logger, notNullValue());
    }

}
