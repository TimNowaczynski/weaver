package de.quarian.weaver.dev;

import org.junit.Before;
import org.junit.Test;

import de.quarian.weaver.di.ActivityModule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MockRegistryUnitTest {

    private MockRegistry mockRegistry;

    @Before
    public void setUp() {
        mockRegistry = new MockRegistry();
    }

    @Test
    public void testGetModuleReturnsNullIfNotRegistered() {
        final ActivityModule module = mockRegistry.getModule(ActivityModule.class);
        assertNull(module);
    }

    @Test
    public void testGetMockReturnsRegisteredMock() {
        final ActivityModule activityModuleMockIn = mock(ActivityModule.class);
        mockRegistry.addMock(ActivityModule.class, activityModuleMockIn);

        final ActivityModule activityModuleMockOut = mockRegistry.getModule(ActivityModule.class);
        assertThat(activityModuleMockOut, is(activityModuleMockIn));
    }
}
