package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.quarian.weaver.database.RoleplayingSystemDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class RoleplayingSystemDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testCreateAndReadRoleplayingSystem() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        // Create
        final RoleplayingSystem inputA = createRoleplayingSystemEntity("Shadowrun");
        final RoleplayingSystem inputB = createRoleplayingSystemEntity("DSA");
        roleplayingSystemDAO.createRoleplayingSystem(inputA);
        roleplayingSystemDAO.createRoleplayingSystem(inputB);

        // Confirm
        final List<RoleplayingSystem> roleplayingSystems = roleplayingSystemDAO.readRoleplayingSystems();
        assertThat(roleplayingSystems.size(), is(2));

        final RoleplayingSystem roleplayingSystemA = roleplayingSystems.get(0);
        assertThat(roleplayingSystemA.id, notNullValue());
        assertThat(roleplayingSystemA.roleplayingSystemName, is("Shadowrun"));
        assertThat(roleplayingSystemA.logo, is(roleplayingSystemA.roleplayingSystemName.getBytes()));
        assertThat(roleplayingSystemA.logoImageType, is("image/jpeg"));

        final RoleplayingSystem roleplayingSystemB = roleplayingSystems.get(1);
        assertThat(roleplayingSystemB.id, notNullValue());
        assertThat(roleplayingSystemB.roleplayingSystemName, is("DSA"));
        assertThat(roleplayingSystemB.logo, is(roleplayingSystemB.roleplayingSystemName.getBytes()));
        assertThat(roleplayingSystemB.logoImageType, is("image/jpeg"));
    }

    private RoleplayingSystem createRoleplayingSystemEntity(final String roleplayingSystemName) {
        final RoleplayingSystem shadowrun = new RoleplayingSystem();
        shadowrun.roleplayingSystemName = roleplayingSystemName;
        shadowrun.logo = roleplayingSystemName.getBytes();
        shadowrun.logoImageType = "image/jpeg";
        return shadowrun;
    }

    @Test
    public void testUpdateRoleplayingSystem() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        // Create
        final RoleplayingSystem input = createRoleplayingSystemEntity("Shadowrun");
        input.id = roleplayingSystemDAO.createRoleplayingSystem(input);

        // Update
        input.roleplayingSystemName = "DSA";
        roleplayingSystemDAO.updateRoleplayingSystem(input);

        // Confirm
        final RoleplayingSystem output = roleplayingSystemDAO.readRoleplayingSystems().get(0);
        assertThat(output.roleplayingSystemName, is("DSA"));
    }

    @Test
    public void testDeleteRoleplayingSystem() {
        //TODO: Test delete cascade
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        // Write
        RoleplayingSystem shadowrun = createRoleplayingSystemEntity("Shadowrun");
        roleplayingSystemDAO.createRoleplayingSystem(shadowrun);

        // Confirm
        final List<RoleplayingSystem> output = roleplayingSystemDAO.readRoleplayingSystems();
        assertThat(output, hasSize(1));
        shadowrun = output.get(0);

        // Delete
        roleplayingSystemDAO.deleteRoleplayingSystem(shadowrun);

        // Confirm
        assertThat(roleplayingSystemDAO.readRoleplayingSystems(), hasSize(0));
    }
}