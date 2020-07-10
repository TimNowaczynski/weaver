package de.quarian.weaver.campaigns;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import de.quarian.weaver.namesets.ConfigureNameSetsFragment;

public class CampaignEditorTabAdapter extends FragmentStateAdapter {

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
}
