package de.quarian.weaver.campaigns;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.util.Utils;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void testBindViewHolder() {
        final List<CampaignListDisplayObject> displayObjects = new ArrayList<>(3);
        final CampaignListDisplayObject campaignListDisplayObject = mock(CampaignListDisplayObject.class);
        when(campaignListDisplayObject.getNumberOfPlayerCharacters()).thenReturn(2L);

        final Utils.ByteArrayConverter converter = new Utils.ByteArrayConverter();

        final String rpsImageDummyString = "rpsImage";
        final Byte[] rpsImage = converter.fromPrimitive(rpsImageDummyString.getBytes());
        when(campaignListDisplayObject.getRoleplayingSystemImage()).thenReturn(rpsImage);

        final String campaignImageDummyString = "campaignImage";
        final Byte[] campaignImage = converter.fromPrimitive(campaignImageDummyString.getBytes());
        when(campaignListDisplayObject.getCampaignImage()).thenReturn(campaignImage);

        when(campaignListDisplayObject.getCampaignName()).thenReturn("Rising Dragon");
        when(campaignListDisplayObject.getRoleplayingSystemName()).thenReturn("Shadowrun");
        displayObjects.add(campaignListDisplayObject);
        adapter.setCampaignListDisplayObjects(displayObjects);

        final View itemView = mock(View.class);
        final TextView campaignName = mock(TextView.class);
        final TextView rpsName = mock(TextView.class);
        final ImageView itemSynopsisButton = mock(ImageView.class);
        final ImageView editCampaignButton = mock(ImageView.class);
        final FrameLayout deactivationOverlay = mock(FrameLayout.class);
        final ImageView campaignBanner = mock(ImageView.class);
        final ImageView rpsImageView = mock(ImageView.class);
        final TextView managePlayerCharactersButton = mock(TextView.class);

        when(itemView.findViewById(R.id.campaign_list_item_name)).thenReturn(campaignName);
        when(itemView.findViewById(R.id.campaign_list_item_rps_name)).thenReturn(rpsName);
        when(itemView.findViewById(R.id.campaign_list_item_synopsis)).thenReturn(itemSynopsisButton);
        when(itemView.findViewById(R.id.campaign_list_item_edit_campaign)).thenReturn(editCampaignButton);
        when(itemView.findViewById(R.id.campaign_list_item_deactivation_overlay)).thenReturn(deactivationOverlay);
        when(itemView.findViewById(R.id.campaign_list_item_banner)).thenReturn(campaignBanner);
        when(itemView.findViewById(R.id.campaign_list_item_rps_image)).thenReturn(rpsImageView);
        when(itemView.findViewById(R.id.campaign_list_item_manage_player_characters)).thenReturn(managePlayerCharactersButton);

        final CampaignListItemViewHolder viewHolder = new CampaignListItemViewHolder(itemView);
        adapter.bindViewHolder(viewHolder, 0);

        // Init
        verify(itemSynopsisButton).setOnClickListener(viewHolder);
        verify(editCampaignButton).setOnClickListener(viewHolder);
        verify(managePlayerCharactersButton).setOnClickListener(viewHolder);

        // Set Display Object
        verify(managePlayerCharactersButton).setText(String.valueOf(2));
        verify(rpsName).setText("Shadowrun");
        verify(campaignName).setText("Rising Dragon");
        verify(deactivationOverlay).setBackgroundColor(Color.TRANSPARENT);
    }
}
