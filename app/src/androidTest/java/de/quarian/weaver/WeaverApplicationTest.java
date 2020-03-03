package de.quarian.weaver;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Looper;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import de.quarian.weaver.di.ModuleProvider;
import de.quarian.weaver.test.DummyActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WeaverApplicationTest extends TestCase {

    @Rule
    public ActivityTestRule<DummyActivity> activityTestRule = new ActivityTestRule<>(DummyActivity.class, false, false);

    @Test
    public void testDisableMockedModulesOnApplicationStart() throws Exception {
        final ModuleProvider moduleProvider = ModuleProvider.get();
        moduleProvider.useMocks();
        moduleProvider.registerMockedModule("Mocked Module");

        String moduleOut = moduleProvider.getModule(String.class, () -> "Real Module");
        assertThat(moduleOut, is("Mocked Module"));

        // Schedule onCreate() of application
        final DummyActivity dummyActivity = activityTestRule.launchActivity(null);
        final Application application = dummyActivity.getApplication();
        AsyncTask.execute(() -> {
            Looper.prepare();
            application.onCreate();
        });

        // Wait for AsyncTask preparing the Looper
        Thread.sleep(50L);

        moduleOut = moduleProvider.getModule(String.class, () -> "Real Module");
        assertThat(moduleOut, is("Real Module"));

        final Looper looper = Looper.myLooper();
        if (looper != null) {
            looper.quit();
        }
    }
}
