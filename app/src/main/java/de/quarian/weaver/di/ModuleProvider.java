package de.quarian.weaver.di;

import java.util.concurrent.Callable;

import de.quarian.weaver.dev.MockRegistry;

public class ModuleProvider {

    private static ModuleProvider instance;
    private final MockRegistry mockRegistry = new MockRegistry();
    private boolean useMocks;

    private ModuleProvider() { }

    public static ModuleProvider get() {
        if (instance == null) {
            instance = new ModuleProvider();
        }
        return instance;
    }

    public void useMocks() {
        useMocks = true;
    }

    public void disableMocks() {
        useMocks = false;
        mockRegistry.clear();
    }

    public void registerMockedModule(final Object mock) {
        final Class mockClass = mock.getClass();
        mockRegistry.addMock(mockClass, mock);
    }

    public <T> T getModule(final Class<T> moduleClass, final Callable<T> moduleCreationCallback) {
        try {
            if (!useMocks) {
                return moduleCreationCallback.call();
            } else {
                final T module = mockRegistry.getModule(moduleClass);

                if (module == null) {
                    final String className = moduleClass.getName();
                    final String message = String.format("Requested Mock was never registered (Class: %S)", className);
                    throw new IllegalStateException(message);
                }

                return module;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
