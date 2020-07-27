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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

        initializeConfirmButton(view);
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

    private void initializeConfirmButton(@NonNull final View view) {
        final FloatingActionButton confirmButton = view.findViewById(R.id.fragment_configure_name_sets_confirm);
        confirmButton.setOnClickListener((clickedView) -> selectNameSets());
    }

    private void selectNameSets() {
        final ConfigureNameSetsCallbacks callbacks = (ConfigureNameSetsCallbacks) getActivity();
        if (callbacks == null) {
            throw new RuntimeException("Parent activity must not be null and implement ConfigureNameSetsCallbacks");
        }

        final List<Long> selectedNameSetIds = adapter.getSelectedNameSetIds();
        // TODO: ensure we select at least one name set when trying to finish editing
        callbacks.onNameSetsSelected(selectedNameSetIds);
    }

    @Override
    public long getCampaignId() {
        return campaignId;
    }
}
