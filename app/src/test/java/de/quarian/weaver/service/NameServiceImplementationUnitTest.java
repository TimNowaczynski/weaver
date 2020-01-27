package de.quarian.weaver.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.quarian.weaver.database.NameDAO;
import de.quarian.weaver.datamodel.Constants;
import de.quarian.weaver.datamodel.Name;
import de.quarian.weaver.datamodel.NameSet;
import de.quarian.weaver.datamodel.NameSetToCampaign;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NameServiceImplementationUnitTest {

    @Mock
    private NameDAO nameDAO;

    @Mock
    private Random random;

    private NameService nameService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nameService = new NameServiceImplementation(nameDAO, random);

        when(random.nextInt(anyInt())).thenReturn(0);
    }

    @Test
    public void testCreateNameSetAndNamesUsesExistingSet() {
        final NameSet existingNameSet = new NameSet();
        existingNameSet.id = 1L;
        existingNameSet.nameSetName = "NameSetName";

        when(nameDAO.readNameSetByName("NameSetName")).thenReturn(existingNameSet);
        when(nameDAO.readNumberOfNames(1L)).thenReturn(1);

        final Name nameA = new Name();
        nameA.name = "NameA";
        final List<Name> names = Collections.singletonList(nameA);
        when(nameDAO.readName("NameA", existingNameSet.id)).thenReturn(nameA);
        when(nameDAO.readName("NameB", existingNameSet.id)).thenReturn(null);

        nameService.createNameSetAndNames(existingNameSet, names);

        verify(nameDAO).readNameSetByName("NameSetName");
        verify(nameDAO, times(1)).updateName(any());
        verify(nameDAO, times(0)).createName(any());
    }

    @Test
    public void testCreateNameSetAndNamesCreatesSetAndNameIfNotExisting() {
        final NameSet newNameSet = new NameSet();
        final Name name = new Name();
        name.name = "NameA";
        final List<Name> names = Collections.singletonList(name);
        nameService.createNameSetAndNames(newNameSet, names);

        verify(nameDAO).createNameSet(newNameSet);
        verify(nameDAO).createName(name);
    }

    @Test
    public void testCreateNameSetAndNamesReplacesExistingNames() {
        final NameSet nameSet = new NameSet();
        nameSet.nameSetName = "SetA";
        nameSet.id = 1L;
        final Name name = new Name();
        name.id = 1L;
        name.name = "NameA";
        name.gender = 2;

        when(nameDAO.readNameSetByName(nameSet.nameSetName)).thenReturn(nameSet);
        when(nameDAO.readName("NameA", 1L)).thenReturn(name);
        nameService.createNameSetAndNames(nameSet, Collections.singletonList(name));

        verify(nameDAO, never()).createNameSet(nameSet);
        verify(nameDAO).updateName(name);
    }

    @Test
    public void testReadNameSets() {
        final NameSet nameSet = new NameSet();
        nameSet.nameSetName = "SetA";
        nameSet.id = 1L;

        when(nameDAO.readNameSets()).thenReturn(Collections.singletonList(nameSet));

        final List<NameSet> nameSets = nameService.readNameSets();
        verify(nameDAO, times(1)).readNameSets();
        assertThat(nameSets.size(), is(1));
        assertThat(nameSets.get(0).nameSetName, is("SetA"));
    }

    @Test
    public void testReadNameSetsForCampaign() {
        final long campaignId = 1L;
        final List<NameSet> nameSetsIn = Collections.emptyList();
        when(nameDAO.readNameSetsForCampaign(campaignId)).thenReturn(nameSetsIn);

        final List<NameSet> nameSetsOut = nameService.readNameSetsForCampaign(campaignId);
        assertThat(nameSetsOut, notNullValue());
    }

    @Test
    public void testReadRandomMaleName() {
        final long nameSetId = 1L;

        final List<Integer> nameGendersRequested = new ArrayList<>();
        nameGendersRequested.add(Constants.NameGender.MALE.getValue());
        nameGendersRequested.add(Constants.NameGender.UNISEX.getValue());

        final Name result = new Name();
        result.name = "Jordan";

        when(nameDAO.readNumberOfNames(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested)).thenReturn(2);
        when(nameDAO.readRandomName(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested, 0L)).thenReturn(result);

        final Name name = nameService.readRandomName(nameSetId, Constants.NameGender.MALE.getValue(), Constants.NamePosition.FIRST.getValue());
        assertThat(name.name, is("Jordan"));

        verify(nameDAO).readRandomName(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested, 0L);
    }

    @Test
    public void testReadRandomFemaleName() {
        final long nameSetId = 1L;

        final List<Integer> nameGendersRequested = new ArrayList<>();
        nameGendersRequested.add(Constants.NameGender.FEMALE.getValue());
        nameGendersRequested.add(Constants.NameGender.UNISEX.getValue());

        final Name result = new Name();
        result.name = "Jordan";

        when(nameDAO.readNumberOfNames(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested)).thenReturn(2);
        when(nameDAO.readRandomName(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested, 0L)).thenReturn(result);

        final Name name = nameService.readRandomName(nameSetId, Constants.NameGender.FEMALE.getValue(), Constants.NamePosition.FIRST.getValue());
        assertThat(name.name, is("Jordan"));

        verify(nameDAO).readRandomName(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested, 0L);
    }

    @Test
    public void testReadRandomUnisexName() {
        final long nameSetId = 1L;

        final List<Integer> nameGendersRequested = new ArrayList<>();
        nameGendersRequested.add(Constants.NameGender.UNISEX.getValue());

        final Name result = new Name();
        result.name = "Jordan";

        when(nameDAO.readNumberOfNames(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested)).thenReturn(2);
        when(nameDAO.readRandomName(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested, 0L)).thenReturn(result);

        final Name name = nameService.readRandomName(nameSetId, Constants.NameGender.UNISEX.getValue(), Constants.NamePosition.FIRST.getValue());
        assertThat(name.name, is("Jordan"));

        verify(nameDAO).readRandomName(nameSetId, Constants.NamePosition.FIRST.getValue(), nameGendersRequested, 0L);
    }

    @Test
    public void testUpdateNameSetsForCampaign() {
        final NameSet nameSet = new NameSet();
        nameSet.id = 1L;
        nameSet.nameSetName = "Aventurien";

        nameService.updateNameSetsForCampaign(Collections.singletonList(nameSet), 1L);

        verify(nameDAO).deleteNameSetToCampaignMappings(1L);
        verify(nameDAO, times(1)).createNameSetToCampaignMapping(any(NameSetToCampaign.class));
    }

    @Test
    public void testDeleteNameSet() {
        final NameSet nameSet = new NameSet();
        nameSet.id = 1L;
        nameSet.nameSetName = "Aventurien";

        nameService.deleteNameSet(nameSet);

        verify(nameDAO).deleteNameSet(nameSet);
    }
}
