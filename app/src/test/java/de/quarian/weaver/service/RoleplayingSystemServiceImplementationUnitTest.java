package de.quarian.weaver.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.RoleplayingSystem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleplayingSystemServiceImplementationUnitTest {

    @Mock
    private WeaverDB weaverDB;

    @Mock
    private RoleplayingSystemDAO roleplayingSystemDAO;

    private RoleplayingSystemService roleplayingSystemService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(weaverDB.roleplayingSystemDAO()).thenReturn(roleplayingSystemDAO);
        roleplayingSystemService = new RoleplayingSystemServiceImplementation(weaverDB);
    }

    @Test
    public void testCreateRoleplayingSystem() {
        final RoleplayingSystem roleplayingSystem = new RoleplayingSystem();
        roleplayingSystemService.createRoleplayingSystem(roleplayingSystem);

        verify(roleplayingSystemDAO).createRoleplayingSystem(roleplayingSystem);
    }

    @Test
    public void testReadRoleplayingSystems() {
        final RoleplayingSystem roleplayingSystem = new RoleplayingSystem();
        when(roleplayingSystemService.readRoleplayingSystems()).thenReturn(Collections.singletonList(roleplayingSystem));

        final List<RoleplayingSystem> roleplayingSystems = roleplayingSystemService.readRoleplayingSystems();
        assertThat(roleplayingSystems.size(), is(1));
        assertThat(roleplayingSystems.get(0), is(roleplayingSystem));
    }

    @Test
    public void testReadRoleplayingSystemById() {
        final RoleplayingSystem roleplayingSystemIn = new RoleplayingSystem();
        when(roleplayingSystemService.readRoleplayingSystemById(1L)).thenReturn(roleplayingSystemIn);

        final RoleplayingSystem roleplayingSystemOut = roleplayingSystemService.readRoleplayingSystemById(1L);
        assertThat(roleplayingSystemIn, is(roleplayingSystemOut));
    }

    @Test
    public void testUpdateRoleplayingSystem() {
        final RoleplayingSystem roleplayingSystem = new RoleplayingSystem();
        roleplayingSystemService.updateRoleplayingSystem(roleplayingSystem);

        verify(roleplayingSystemDAO).updateRoleplayingSystem(roleplayingSystem);
    }

    @Test
    public void testDeleteRoleplayingSystem() {
        final RoleplayingSystem roleplayingSystem = new RoleplayingSystem();
        roleplayingSystemService.deleteRoleplayingSystem(roleplayingSystem);

        verify(roleplayingSystemDAO).deleteRoleplayingSystem(roleplayingSystem);
    }
}
