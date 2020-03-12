package de.quarian.weaver;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import de.quarian.weaver.test.DummyActivity;

@Ignore
@RunWith(AndroidJUnit4.class)
@SmallTest
public class WeaverApplicationTest extends TestCase {

    @Rule
    public ActivityTestRule<DummyActivity> activityTestRule = new ActivityTestRule<>(DummyActivity.class, false, false);

    // TODO: check if there is something to test here later on
}
