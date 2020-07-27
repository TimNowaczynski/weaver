package de.quarian.weaver.campaigns;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import de.quarian.weaver.R;

public class CampaignSynopsisFragment extends Fragment implements CampaignSynopsisEditText.Callbacks {

    private enum FABMode {
        NEXT, CLOSE_EDITOR
    }

    private FABMode fabMode = FABMode.NEXT;
    private WeakReference<FloatingActionButton> floatingActionButton = new WeakReference<>(null);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_synopsis, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FloatingActionButton nextButton = view.findViewById(R.id.fragment_synopsis_next);
        floatingActionButton = new WeakReference<>(nextButton);

        final CampaignEditorActivity activity = (CampaignEditorActivity) getActivity();
        if (activity != null) {
            nextButton.setOnClickListener(activity::onSynopsisFABClicked);
        }

        final CampaignSynopsisEditText campaignSynopsisEditText = view.findViewById(R.id.fragment_synopsis_text);
        campaignSynopsisEditText.setCallbacks(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onFocusChanged(boolean focused) {
        final CampaignEditorActivity campaignEditorActivity = (CampaignEditorActivity) getActivity();
        if (campaignEditorActivity == null) {
            return;
        }

        if (focused) {
            fabMode = FABMode.CLOSE_EDITOR;
            campaignEditorActivity.expandEditor();
            changeFABIconToDownArrow();
        } else {
            fabMode = FABMode.NEXT;
            campaignEditorActivity.collapseEditor();
            changeFABIconToRightArrow();
        }
    }

    private void changeFABIconToDownArrow() {
        final Context context = getContext();
        final FloatingActionButton floatingActionButton = this.floatingActionButton.get();
        if (context == null || floatingActionButton == null) {
            return;
        }

        final Drawable downDrawable = ContextCompat.getDrawable(context, R.drawable.ic_down);
        floatingActionButton.setImageDrawable(downDrawable);
    }

    private void changeFABIconToRightArrow() {
        final Context context = getContext();
        final FloatingActionButton floatingActionButton = this.floatingActionButton.get();
        if (context == null || floatingActionButton == null) {
            return;
        }

        final Drawable nextDrawable = ContextCompat.getDrawable(context, R.drawable.ic_next);
        floatingActionButton.setImageDrawable(nextDrawable);
    }

    @Subscribe
    public void notifyEditorClosed(final CollapseEditorEvent collapseEditorEvent) {
        if (fabMode == FABMode.CLOSE_EDITOR) {
            /*
            final CampaignEditorActivity campaignEditorActivity = (CampaignEditorActivity) getActivity();
            if (campaignEditorActivity == null) {
                return;
            }
            */
            fabMode = FABMode.NEXT;
            //campaignEditorActivity.collapseEditor(); what?
            changeFABIconToRightArrow();
        }
    }
}
