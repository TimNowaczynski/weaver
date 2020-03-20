package de.quarian.weaver.util;

import android.widget.Toast;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import de.quarian.weaver.R;
import de.quarian.weaver.test.DummyActivity;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AndroidToastHandlerImplementationIntegrationTest {

    @Rule
    public ActivityTestRule<DummyActivity> activityTestRule = new ActivityTestRule<>(DummyActivity.class);

    @Test
    public void testShowToast() throws Exception {
        // Best we can do is to invoke these methods and see if they don't crash for some reason

        final DummyActivity activity = activityTestRule.getActivity();
        final AndroidToastHandler androidToastHandler = new AndroidToastHandlerImplementation(activity);

        activity.runOnUiThread(() -> {
            androidToastHandler.showToast(R.string.generic_error_unknown);
            androidToastHandler.showToast(R.string.generic_error_unknown, Toast.LENGTH_LONG);
        });

        Thread.sleep(1000L);
        assertTrue(true);
    }
}
