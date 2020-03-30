package de.quarian.weaver.assets;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScheduledToDeleteFragmentViewPagerAdapter extends FragmentStateAdapter {

    public ScheduledToDeleteFragmentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ScheduledToDeleteFragment(ScheduledToDeleteFragment.Mode.SCHEDULED);
        } else {
            return new ScheduledToDeleteFragment(ScheduledToDeleteFragment.Mode.UNSCHEDULED);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
