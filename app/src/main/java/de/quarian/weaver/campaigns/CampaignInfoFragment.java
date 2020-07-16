package de.quarian.weaver.campaigns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import de.quarian.weaver.R;

public class CampaignInfoFragment extends Fragment {

    private CampaignEditorActivity.Mode mode;
    private TextInputLayout campaignNameTextInputLayout;
    private TextInputLayout systemNameTextInputLayout;
    private ImageButton themeButton;
    private ImageButton selectCampaignButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CampaignEditorActivity campaignEditorActivity = (CampaignEditorActivity) getActivity();
        if (campaignEditorActivity == null) {
            throw new IllegalStateException();
        }

        mode = campaignEditorActivity.getMode();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_campaign_info, container, true);
        campaignNameTextInputLayout = view.findViewById(R.id.edit_campaign_screen_campaign_name_text_input_layout);
        systemNameTextInputLayout = view.findViewById(R.id.edit_campaign_screen_system_name_text_input_layout);
        themeButton = view.findViewById(R.id.edit_campaign_screen_set_theme_button);
        selectCampaignButton = view.findViewById(R.id.edit_campaign_screen_select_campaign_image);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        if(mode == CampaignEditorActivity.Mode.NEW) {
            toolbar.setTitle(R.string.activity_title_add_campaign_screen);
            toolbar.setSubtitle("");
        } else {
            //TODO: init with values (use the subtitle of the Toolbar for the campaign name)
            toolbar.setTitle(R.string.activity_title_edit_campaign_screen);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void showInputElements(final CollapseEditorEvent collapseEditorEvent) {
        campaignNameTextInputLayout.setVisibility(View.VISIBLE);
        systemNameTextInputLayout.setVisibility(View.VISIBLE);
        themeButton.setVisibility(View.VISIBLE);
        selectCampaignButton.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void hideInputElements(final ExpandEditorEvent expandEditorEvent) {
        campaignNameTextInputLayout.setVisibility(View.GONE);
        systemNameTextInputLayout.setVisibility(View.GONE);
        themeButton.setVisibility(View.GONE);
        selectCampaignButton.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
