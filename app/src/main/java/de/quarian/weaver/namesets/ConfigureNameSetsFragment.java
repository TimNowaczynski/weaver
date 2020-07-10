package de.quarian.weaver.namesets;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.campaigns.CampaignContext;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.converter.NameSetConverter;
import de.quarian.weaver.datamodel.ddo.NameSetDisplayObject;
import de.quarian.weaver.di.DependencyInjector;
import de.quarian.weaver.di.GlobalHandler;
import de.quarian.weaver.schedulers.IoScheduler;
import de.quarian.weaver.service.NameService;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

// TODO: use this fragment in the ConfigureNameSetsActivity as well
public class ConfigureNameSetsFragment extends Fragment implements CampaignContext {

    public static class FragmentDependencies {

        @IoScheduler
        @Inject
        public Scheduler io;

        @Inject
        @GlobalHandler
        public Handler globalHandler;

        @Inject
        public NameService nameService;

        @Inject
        public NameSetConverter nameSetConverter;

    }

    public final FragmentDependencies fragmentDependencies = new FragmentDependencies();

    private long campaignId;

    private ConfigureNameSetsAdapter adapter;

    public ConfigureNameSetsFragment() {
        super(R.layout.fragment_configure_name_sets);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyInjector.get().injectDependencies(this);

        final CampaignContext activityCampaignContext = (CampaignContext) getActivity();
        if (activityCampaignContext == null) {
            throw new RuntimeException("Campaign context shouldn't be null.");
        }

        campaignId = activityCampaignContext.getCampaignId();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeNameSetList(view);
    }

    private void initializeNameSetList(@NonNull final View view) {
        final RecyclerView nameSetList = (RecyclerView) view.findViewById(R.id.fragment_configure_name_sets_list);
        if (nameSetList == null) {
            throw new IllegalStateException("NameSetList should not be null.");
        }

        final Context context = getContext();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        nameSetList.setLayoutManager(layoutManager);

        adapter = new ConfigureNameSetsAdapter();
        nameSetList.setAdapter(adapter);
        readListOfAllNameSets();

        initializeAddNameSetButton(view);
    }

    private void readListOfAllNameSets() {
        final Disposable disposable = Observable.just(fragmentDependencies.nameService)
                .subscribeOn(fragmentDependencies.io)
                .subscribe(nameService -> {
                    final List<NameSet> nameSets = nameService.readNameSets();
                    readListOfNameSetsForCampaign(nameSets);
                });
        fragmentDependencies.globalHandler.postDelayed(disposable::dispose, 500L);
    }

    private void readListOfNameSetsForCampaign(@NonNull final List<NameSet> nameSets) {
        if (campaignId == CampaignContext.NEW_CAMPAIGN_CONTEXT) {
            return;
        }

        final Disposable disposable = Observable.just(fragmentDependencies.nameService)
                .subscribeOn(fragmentDependencies.io)
                .subscribe(nameService -> {
                    final long campaignId = getCampaignId();
                    final List<NameSet> nameSetsForCampaign = nameService.readNameSetsForCampaign(campaignId);
                    setDisplayItems(nameSets, nameSetsForCampaign);
                });
        fragmentDependencies.globalHandler.postDelayed(disposable::dispose, 500L);
    }

    private void setDisplayItems(@NonNull final List<NameSet> allNameSets, @NonNull final List<NameSet> nameSetsForCampaign) {
        final List<NameSetDisplayObject> nameSetDisplayObjects = fragmentDependencies.nameSetConverter.convert(allNameSets, nameSetsForCampaign);
        adapter.setNameSetDisplayObjects(nameSetDisplayObjects);
    }

    private void initializeAddNameSetButton(@NonNull final View view) {
        final FloatingActionButton addNameSetButton = view.findViewById(R.id.fragment_configure_name_sets_confirm);
        addNameSetButton.setOnClickListener((clickedView) -> {
            final FragmentActivity activity = getActivity();
            if (activity != null) {
                NavigationController.getInstance().addNameSetDummy(activity);
            }
        });
    }

    @Override
    public long getCampaignId() {
        return campaignId;
    }
}
