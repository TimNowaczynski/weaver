package de.quarian.weaver.di;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ModuleProviderUnitTest {

    private static final String TEST_STRING_A = "Instance";
    private static final String TEST_STRING_B = "Woops";

    private ModuleProvider moduleProvider;

    @Before
    public void setUp() {
        moduleProvider =ModuleProvider.get();
    }

    @Test
    public void testStaticGetModuleProvider() {
        final ModuleProvider moduleProviderA = ModuleProvider.get();
        final ModuleProvider moduleProviderB = ModuleProvider.get();

        assertNotNull(moduleProviderA);
        assertNotNull(moduleProviderB);
        assertThat(moduleProviderA, is(moduleProviderB));
    }

    @Test
    public void testReturnInstanceIfMocksDisabled() {
        moduleProvider.disableMocks();

        final String module = moduleProvider.getModule(String.class, () -> TEST_STRING_A);
        assertThat(module, is(TEST_STRING_A));
    }

    @Test
    public void testEnableAndDisableMocks() {
        moduleProvider.registerMockedModule(TEST_STRING_B);
        moduleProvider.useMocks();

        String module = moduleProvider.getModule(String.class, () -> TEST_STRING_A);
        assertThat(module, is(TEST_STRING_B));

        moduleProvider.disableMocks();

        module = moduleProvider.getModule(String.class, () -> TEST_STRING_A);
        assertThat(module, is(TEST_STRING_A));
    }

    @Test(expected = RuntimeException.class)
    public void testThrowExceptionWhenRequestingANonexistentMock() {
        moduleProvider.disableMocks(); // This will clear all mocks
        moduleProvider.useMocks();

        moduleProvider.getModule(String.class, () -> TEST_STRING_A);
    }
}
