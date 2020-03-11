package de.quarian.weaver.di.test;

import dagger.Module;
import dagger.Provides;

@Module(includes = TestSuperDependenciesModule.class)
public class TestDependenciesModule {

    @Provides
    public Integer provideChild() {
        return 42;
    }

}
