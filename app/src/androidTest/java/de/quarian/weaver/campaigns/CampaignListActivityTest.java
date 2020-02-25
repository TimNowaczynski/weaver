package de.quarian.weaver.campaigns;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import junit.framework.TestCase;

import org.greenrobot.eventbus.EventBus;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.ActionBar;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.service.CampaignService;
import de.quarian.weaver.test.EventRecorder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class CampaignListActivityTest extends TestCase {

    private static final EventRecorder eventRecorder = new EventRecorder();

    @Rule
    public ActivityTestRule<CampaignListActivity> activityTestRule = new ActivityTestRule<>(CampaignListActivity.class, false, false);

    @Mock
    public NavigationController navigationControllerMock;

    @Mock
    public SharedPreferences sharedPreferences;

    @Mock
    public SharedPreferences.Editor sharedPreferencesEditor;

    @Mock
    public CampaignService campaignService;

    @Mock
    public SharedPreferences sortOrderPreferences;

    @BeforeClass
    public static void setUpBeforeClass() {
        EventBus.getDefault().register(eventRecorder);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        EventBus.getDefault().unregister(eventRecorder);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        NavigationController.setTestInstance(navigationControllerMock);
        eventRecorder.reset();

        prepareActivity();

        final String sortOrderString = CampaignService.SortOrder.CAMPAIGN_NAME.name();
        when(sortOrderPreferences.getString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, sortOrderString)).thenReturn(sortOrderString);
        when(sortOrderPreferences.edit()).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditor);
    }

    private void prepareActivity() {
        final CampaignListActivity campaignListActivity = activityTestRule.launchActivity(null);
        campaignListActivity.campaignListOrderPreferences = sharedPreferences;
        campaignListActivity.campaignService = campaignService;

        final TestScheduler testScheduler = new TestScheduler(1L, TimeUnit.SECONDS);
        campaignListActivity.setIoScheduler(testScheduler);
    }

    @Test
    public void testSetUpToolbar() {
        final CampaignListActivity campaignListActivity = activityTestRule.getActivity();

        final ActionBar supportActionBar = campaignListActivity.getSupportActionBar();
        assertThat(supportActionBar, notNullValue());
        assertThat(supportActionBar.getTitle(), is("Weaver"));
    }

    @Test
    public void testCreateCampaignFloatingActionButton() {
        final CampaignListActivity campaignListActivity = activityTestRule.getActivity();

        final View addCampaignButton = campaignListActivity.findViewById(R.id.campaign_list_add_campaign);
        addCampaignButton.callOnClick();

        verify(navigationControllerMock, atLeastOnce()).addCampaign(campaignListActivity);
    }

    @Test
    public void testInitSortOderSpinnerWithDefault() {
        final CampaignListActivity campaignListActivity = activityTestRule.getActivity();

        final Spinner spinner = campaignListActivity.findViewById(R.id.campaign_list_sort_order_spinner);
        assertThat(spinner.getSelectedItemPosition(), is(0));
    }

    /*
     So... this is a problem. It would be best to actually test getting the sort order
     from shared preferences. But when the test rule launches the activity we already
     initialize the order before we have a chance to (for example) inject our mocked
     shared preferences. And when we launch it later on, our injected mock gets
     overwritten again.

     To separate concerns we now use event bus and instead test sending/receiving events.
     */

    @Test
    public void testRefreshCampaignsEventIsThrownOnResume() {
        activityTestRule.getActivity();

        List<Object> events = eventRecorder.pullEvents();
        assertThat(events.size(), is(1));

        events = eventRecorder.pullEvents();
        assertThat(events.size(), is(0));
    }

    @Test
    public void testRefreshCampaignsEventIsThrownOnChangingSortOrder() {
        final CampaignListActivity activity = activityTestRule.getActivity();
        activity.campaignListOrderPreferences = sortOrderPreferences;
        eventRecorder.reset();

        final Spinner spinner = activity.findViewById(R.id.campaign_list_sort_order_spinner);
        final AdapterView.OnItemSelectedListener onItemSelectedListener = spinner.getOnItemSelectedListener();
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(null, null, 3, 0L);
        }

        final List<Object> events = eventRecorder.pullEvents();
        assertThat(events.size(), is(1));
    }

    // TODO: this test is flaky, investigate
    @Test
    public void testRefreshCampaignsEventIsReceived() {
        final CampaignListActivity activity = activityTestRule.getActivity();
        activity.campaignListOrderPreferences = sortOrderPreferences;
        activity.io = AndroidSchedulers.mainThread();

        // So this is where we fake selecting some spinner option
        when(sortOrderPreferences.getString(CampaignService.CAMPAIGN_LIST_ORDER_SP_KEY, CampaignService.SortOrder.CAMPAIGN_NAME.name())).thenReturn(CampaignService.SortOrder.LAST_USED.name());
        EventBus.getDefault().post(new RefreshDisplayObjectsEvent());

        verify(campaignService, times(1)).readCampaigns(CampaignService.SortOrder.LAST_USED);
    }
}
