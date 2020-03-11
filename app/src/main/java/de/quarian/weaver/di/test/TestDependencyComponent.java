package de.quarian.weaver.di.test;

import dagger.Component;

@Component(modules = TestDependenciesModule.class)
public interface TestDependencyComponent {

    void inject(final TestDependencyHolder testDependencyHolder);

}
