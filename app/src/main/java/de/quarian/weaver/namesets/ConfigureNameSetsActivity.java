package de.quarian.weaver.namesets;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;
import de.quarian.weaver.campaigns.CampaignContext;

public class ConfigureNameSetsActivity extends WeaverActivity implements CampaignContext {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, -1L);
        if (campaignId == 0) {
            throw new IllegalStateException("No campaign ID was provided.");
        }

        setContentView(R.layout.activity_configure_name_sets);
        setUpToolbar();
    }

    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.activity_title_configure_name_sets);
        //TODO: Override title with campaign name
    }

    @Override
    public void onDependenciesInjected() {

    }

    @Override
    public long getCampaignId() {
        return campaignId;
    }
}
