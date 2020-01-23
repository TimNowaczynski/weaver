package de.quarian.weaver.test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class TestUtils {

    public static class Gate {

        public void blockingWait(final Callable<Boolean> condition, final long waitPeriod) throws TimeoutException {
            int tries = 0;
            try {
                while (!condition.call() && tries < 3) {
                    Thread.sleep(waitPeriod);
                    tries++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tries == 3) {
                throw new TimeoutException();
            }
        }
    }

    public static class ValueHolder<T> {
        public T value;
    }
}
