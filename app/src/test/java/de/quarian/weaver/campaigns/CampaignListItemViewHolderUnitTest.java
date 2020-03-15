package de.quarian.weaver.campaigns;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampaignListItemViewHolderUnitTest {

    @Mock
    private WeaverActivity activity;

    @Mock
    private View view;

    @Mock
    private ImageView imageView;

    @Mock
    private TextView textView;

    private CampaignListItemViewHolder campaignListItemViewHolder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(view.findViewById(R.id.campaign_list_item_synopsis)).thenReturn(imageView);
        when(view.findViewById(R.id.campaign_list_item_edit_campaign)).thenReturn(imageView);
        when(view.findViewById(R.id.campaign_list_item_manage_player_characters)).thenReturn(textView);

        campaignListItemViewHolder = new CampaignListItemViewHolder(activity, view);
    }

    @Test
    public void testCreateViewHolder() {
        verify(view).setOnClickListener(campaignListItemViewHolder);
        /*
         Not sure if it makes sense to run additional tests here,
         after all most of this is covered through integration tests
         */
    }


}
