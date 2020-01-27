package de.quarian.weaver.service;

import java.util.List;

import androidx.annotation.NonNull;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameGender;
import de.quarian.weaver.datamodel.NamePosition;
import de.quarian.weaver.datamodel.NameSet;

public interface NameService {

    long createNameSetAndNames(@NonNull final NameSet nameSet, @NonNull final List<Name> names);

    List<NameSet> readNameSets();

    List<NameSet> readNameSetsForCampaign(long campaignId);

    Name readRandomName(final long nameSetId, @NameGender final int nameGender, @NamePosition final int namePosition);

    void updateNameSetsForCampaign(@NonNull final List<NameSet> nameSets, long campaignId);

    void deleteNameSet(@NonNull final NameSet nameSet);
}
