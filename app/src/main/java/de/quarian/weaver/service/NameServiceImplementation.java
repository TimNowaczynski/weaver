package de.quarian.weaver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.datamodel.Constants;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameGender;
import de.quarian.weaver.datamodel.NamePosition;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;

public class NameServiceImplementation implements NameService {

    @NonNull
    private final NameDAO nameDAO;

    @NonNull
    private final Random random;

    public NameServiceImplementation(final @NonNull NameDAO nameDAO, final @NonNull Random random) {
        this.nameDAO = nameDAO;
        this.random = random;
    }

    @Override
    public long createNameSetAndNames(@NonNull NameSet nameSet, @NonNull List<Name> names) {
        final long nameSetId = createOrGetNameSet(nameSet);
        createOrUpdateNames(names, nameSetId);
        return nameSetId;
    }

    private long createOrGetNameSet(@NonNull final NameSet nameSet) {
        final long nameSetId;
        NameSet targetSet = nameDAO.readNameSetByName(nameSet.nameSetName);
        if (targetSet == null) {
            nameSetId = nameDAO.createNameSet(nameSet);
        } else {
            nameSetId = targetSet.id;
        }
        return nameSetId;
    }

    private void createOrUpdateNames(@NonNull List<Name> names, long nameSetId) {
        for (final Name name : names) {
            final Name existingName = nameDAO.readName(name.name, nameSetId);
            if (existingName != null && existingName.id == name.id) {
                nameDAO.updateName(name);
            } else {
                nameDAO.createName(name);
            }
        }
    }

    @Override
    public List<NameSet> readNameSets() {
        return nameDAO.readNameSets();
    }

    @Override
    public List<NameSet> readNameSetsForCampaign(long campaignId) {
        return nameDAO.readNameSetsForCampaign(campaignId);
    }

    @Override
    public Name readRandomName(long nameSetId, @NameGender int nameGender, @NamePosition int namePosition) {
        final List<Integer> nameGenders = new ArrayList<>();
        nameGenders.add(nameGender);

        // Add unisex names to pool if not explicitly going for only such
        final int unisex = Constants.NameGender.UNISEX.getValue();
        if (nameGender != unisex) {
            nameGenders.add(unisex);
        }

        final int numberOfNames = nameDAO.readNumberOfNames(nameSetId, namePosition, nameGenders);
        final int randomIndex = random.nextInt(numberOfNames);
        return nameDAO.readRandomName(nameSetId, namePosition, nameGenders, randomIndex);
    }

    @Override
    public void updateNameSetsForCampaign(@NonNull List<NameSet> nameSets, long campaignId) {
        nameDAO.deleteNameSetToCampaignMappings(campaignId);
        for (final NameSet nameSet : nameSets) {
            final NameSetToCampaign nameSetToCampaignMapping = new NameSetToCampaign();
            nameSetToCampaignMapping.campaignId = campaignId;
            nameSetToCampaignMapping.nameSetId = nameSet.id;
            nameDAO.createNameSetToCampaignMapping(nameSetToCampaignMapping);
        }
    }

    @Override
    public void deleteNameSet(@NonNull NameSet nameSet) {
        nameDAO.deleteNameSet(nameSet);
    }
}
