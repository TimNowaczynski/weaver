package de.quarian.weaver.assets;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;

public class ViewScheduledToDeleteActivity extends WeaverActivity {

    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static final int NO_CAMPAIGN_FILTER = -1;

    // TODO: view in tabs: scheduled, unscheduled
    // TODO: reschedule item
    // TODO: keep item forever
    // TODO: clean all scheduled items
    // TODO: clean single item

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scheduled_to_delete);
        setTitle(R.string.activity_title_view_scheduled_to_delete);
        setUpViewPager();
    }

    private void setUpViewPager() {
        final ViewPager2 viewPager = findViewById(R.id.scheduled_to_delete_view_pager);
        viewPager.setAdapter(new ScheduledToDeleteFragmentViewPagerAdapter(this));
    }

    @Override
    public void onDependenciesInjected() {
        // Nothing so far
    }

    public long readCampaignIdIfPresent() {
        final Intent intent = getIntent();
        return intent.getLongExtra(EXTRA_CAMPAIGN_ID, NO_CAMPAIGN_FILTER);
    }
}
