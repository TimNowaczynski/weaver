package de.quarian.weaver.campaigns;

import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import de.quarian.weaver.namesets.ConfigureNameSetsFragment;

public class CampaignEditorTabAdapter extends FragmentStateAdapter implements TabLayout.OnTabSelectedListener {

    public CampaignEditorTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CampaignSynopsisFragment();
        } else {
            return new ConfigureNameSetsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final CollapseEditorEvent collapseEditorEvent = new CollapseEditorEvent();
        EventBus.getDefault().post(collapseEditorEvent);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }
}
