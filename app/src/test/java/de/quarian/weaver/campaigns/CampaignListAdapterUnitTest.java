package de.quarian.weaver.campaigns;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class CampaignListAdapterUnitTest {

    @Mock
    public WeaverActivity activity;

    @Mock
    public WeaverLayoutInflater weaverLayoutInflater;

    private CampaignListAdapter adapter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adapter = new CampaignListAdapter(activity, weaverLayoutInflater);
    }

    @Test
    public void testSetCampaignListDisplayObjects() {

        final List<CampaignListDisplayObject> displayObjectsA = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            displayObjectsA.add(mock(CampaignListDisplayObject.class));
        }

        final List<CampaignListDisplayObject> displayObjectsB = new ArrayList<>(3);
        for (int i = 0; i < 5; i++) {
            displayObjectsB.add(mock(CampaignListDisplayObject.class));
        }

        adapter.setCampaignListDisplayObjects(displayObjectsA);
        assertThat(adapter.getItemCount(), is(3));

        adapter.setCampaignListDisplayObjects(displayObjectsB);
        assertThat(adapter.getItemCount(), is(5));
    }

    // removed test to bind the views into a view holder because we now use data-binding
}
