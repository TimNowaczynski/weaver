package de.quarian.weaver.dev;

import java.util.HashMap;
import java.util.Map;

public class MockRegistry {

    private final Map<Class, Object> mocks = new HashMap<>();

    public void addMock(final Class<?> mockedClass, final Object mock) {
        mocks.put(mockedClass, mock);
    }

    public <T> T getModule(final Class<T> mockedClass) {
        final Object mock = mocks.get(mockedClass);
        if (mock == null) {
            return null;
        }

        return mockedClass.cast(mock);
    }

    public void clear() {
        mocks.clear();
    }
}
