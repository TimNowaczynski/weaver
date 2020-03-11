package de.quarian.weaver.di;

import org.junit.Test;

import de.quarian.weaver.di.test.DaggerTestDependencyComponent;
import de.quarian.weaver.di.test.TestDependenciesModule;
import de.quarian.weaver.di.test.TestDependencyHolder;
import de.quarian.weaver.di.test.TestSuperDependenciesModule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DependencyInheritanceLearningTest {

    private final TestDependencyHolder testDependencyHolder = new TestDependencyHolder();

    @Test
    public void testInheritance() {
        DaggerTestDependencyComponent.builder()
                .testDependenciesModule(new TestDependenciesModule())
                .build()
                .inject(testDependencyHolder);

        assertThat(testDependencyHolder.superValue, is("Super"));
        assertThat(testDependencyHolder.child, is(42));
    }
}
