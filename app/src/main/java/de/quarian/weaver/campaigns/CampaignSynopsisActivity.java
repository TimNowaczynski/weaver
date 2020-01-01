package de.quarian.weaver.campaigns;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class CampaignSynopsisActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_synopsis);
        setTitle(R.string.activity_title_campaign_synopsis);
    }
}
