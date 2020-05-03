package de.quarian.weaver.assets;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverActivity;

public class ViewScheduledToDeleteActivity extends WeaverActivity {

    public static final String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static final int NO_CAMPAIGN_FILTER = -1;

    // TODO: reschedule item
    // TODO: keep item forever
    // TODO: clean all scheduled items
    // TODO: clean single item
    // TODO: clean single item from cloud
    // TODO: ensure clean operations are filtered by campaignId parameter

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scheduled_to_delete);
        setTitle(R.string.activity_title_view_scheduled_to_delete);
        final ViewPager2 viewPager = setUpViewPager();
        setUpTabs(viewPager);
    }

    private ViewPager2 setUpViewPager() {
        final ViewPager2 viewPager = findViewById(R.id.scheduled_to_delete_view_pager);
        viewPager.setAdapter(new ScheduledToDeleteFragmentViewPagerAdapter(this));
        return viewPager;
    }

    private void setUpTabs(final ViewPager2 viewPager) {
        final TabLayout tabLayout = findViewById(R.id.scheduled_to_delete_tab_layout);
        final TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = createTabConfigurationStrategy();
        final TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy);
        tabLayoutMediator.attach();
    }

    private TabLayoutMediator.TabConfigurationStrategy createTabConfigurationStrategy() {
        return (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.activity_view_scheduled_to_delete_scheduled_items);
            } else {
                tab.setText(R.string.activity_view_scheduled_to_delete_unscheduled_items);
            }
        };
    }

    @Override
    public void onDependenciesInjected() {
        // Nothing so far
    }

    // TODO: remove this if not needed
    public long readCampaignIdIfPresent() {
        final Intent intent = getIntent();
        return intent.getLongExtra(EXTRA_CAMPAIGN_ID, NO_CAMPAIGN_FILTER);
    }
}
