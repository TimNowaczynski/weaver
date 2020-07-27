package de.quarian.weaver.datamodel.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.ddo.NameSetDisplayObject;

public class NameSetConverter {

    //TODO: test sorting

    public List<NameSetDisplayObject> convert(@NonNull final List<NameSet> allNameSets, @NonNull final List<NameSet> nameSetsForCampaign) {
        final List<Long> activeNameSetIds = new ArrayList<>(nameSetsForCampaign.size());
        for (final NameSet nameSet : nameSetsForCampaign) {
            activeNameSetIds.add(nameSet.id);
        }

        final List<NameSetDisplayObject> nameSetDisplayObjects = new ArrayList<>(allNameSets.size());
        for (final NameSet nameSet : allNameSets) {
            final NameSetDisplayObject nameSetDisplayObject = new NameSetDisplayObject(nameSet.id);
            nameSetDisplayObject.setNameSetName(nameSet.nameSetName);
            nameSetDisplayObject.setSelected(activeNameSetIds.contains(nameSet.id));
            nameSetDisplayObjects.add(nameSetDisplayObject);
        }

        Collections.sort(nameSetDisplayObjects);
        return nameSetDisplayObjects;
    }
}
