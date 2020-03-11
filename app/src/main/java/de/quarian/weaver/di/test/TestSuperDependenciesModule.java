package de.quarian.weaver.di.test;

import dagger.Module;
import dagger.Provides;

@Module
public class TestSuperDependenciesModule {

    @Provides
    public String provideSuper() {
        return "Super";
    }

}
