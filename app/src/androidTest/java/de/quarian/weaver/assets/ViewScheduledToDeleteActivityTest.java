package de.quarian.weaver.assets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ViewScheduledToDeleteActivityTest {

    @Rule
    public ActivityTestRule<ViewScheduledToDeleteActivity> activityTestRule = new ActivityTestRule<>(ViewScheduledToDeleteActivity.class);

    @Test
    public void testActivityCreation() {
        final ViewScheduledToDeleteActivity activity = activityTestRule.getActivity();
        assertThat(activity.getTitle(), is("Scheduled to Delete"));
    }
}
