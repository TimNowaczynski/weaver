package de.quarian.weaver.campaigns;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.constraintlayout.widget.ConstraintLayout;
import de.quarian.weaver.databinding.CampaignListItemBinding;
import de.quarian.weaver.datamodel.ddo.CampaignListDisplayObject;

import static org.mockito.Mockito.verify;

public class CampaignListItemViewHolderUnitTest {

    @Mock
    private ConstraintLayout constraintLayout;

    @Mock
    private CampaignListItemBinding campaignListItemBinding;

    @Mock
    private CampaignListDisplayObject campaignListDisplayObject;

    private CampaignListItemViewHolder campaignListItemViewHolder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        campaignListItemViewHolder = new CampaignListItemViewHolder(constraintLayout, campaignListItemBinding);
    }

    @Test
    public void testSetCampaign() {
        campaignListItemViewHolder.setCampaign(campaignListDisplayObject);
        verify(campaignListItemBinding).setCampaignDisplayObject(campaignListDisplayObject);
    }
}
