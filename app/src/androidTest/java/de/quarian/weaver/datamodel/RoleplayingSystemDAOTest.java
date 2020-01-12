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
        DatabaseTestUtils.setUpRoleplayingSystems(weaverDB);
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testReadRoleplayingSystem() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        final List<RoleplayingSystem> roleplayingSystems = roleplayingSystemDAO.readRoleplayingSystems();
        assertThat(roleplayingSystems.size(), is(3));

        final RoleplayingSystem roleplayingSystemA = roleplayingSystems.get(0);
        assertThat(roleplayingSystemA.id, notNullValue());
        assertThat(roleplayingSystemA.roleplayingSystemName, is(DatabaseTestConstants.RPS_NAME_SHADOWRUN));
        assertThat(roleplayingSystemA.logo, is(DatabaseTestConstants.RPS_LOGO_SHADOWRUN));
        assertThat(roleplayingSystemA.logoImageType, is(DatabaseTestConstants.RPS_LOGO_IMAGE_TYPE_SHADOWRUN));

        final RoleplayingSystem roleplayingSystemB = roleplayingSystems.get(1);
        assertThat(roleplayingSystemB.id, notNullValue());
        assertThat(roleplayingSystemB.roleplayingSystemName, is(DatabaseTestConstants.RPS_NAME_DSA));
        assertThat(roleplayingSystemB.logo, is(DatabaseTestConstants.RPS_LOGO_DSA));
        assertThat(roleplayingSystemB.logoImageType, is(DatabaseTestConstants.RPS_LOGO_IMAGE_TYPE_DSA));

        final RoleplayingSystem roleplayingSystemC = roleplayingSystems.get(2);
        assertThat(roleplayingSystemC.id, notNullValue());
        assertThat(roleplayingSystemC.roleplayingSystemName, is(DatabaseTestConstants.RPS_NAME_VAMPIRE));
        assertThat(roleplayingSystemC.logo, is(DatabaseTestConstants.RPS_LOGO_VAMPIRE));
        assertThat(roleplayingSystemC.logoImageType, is(DatabaseTestConstants.RPS_LOGO_IMAGE_TYPE_VAMPIRE));
    }

    @Test
    public void testUpdateRoleplayingSystem() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        // Query
        final RoleplayingSystem outputA = roleplayingSystemDAO.readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN);

        // Update
        outputA.roleplayingSystemName = "Hunter";
        roleplayingSystemDAO.updateRoleplayingSystem(outputA);

        // Confirm
        final RoleplayingSystem outputB = roleplayingSystemDAO.readRoleplayingSystems().get(0);
        assertThat(outputB.roleplayingSystemName, is("Hunter"));
    }

    @Test
    public void testDeleteRoleplayingSystem() {
        final RoleplayingSystemDAO roleplayingSystemDAO = weaverDB.roleplayingSystemDAO();

        // Confirm
        final List<RoleplayingSystem> output = roleplayingSystemDAO.readRoleplayingSystems();
        assertThat(output, hasSize(3));

        // Delete
        final RoleplayingSystem shadowrun = roleplayingSystemDAO.readRoleplayingSystemsByName(DatabaseTestConstants.RPS_NAME_SHADOWRUN);
        roleplayingSystemDAO.deleteRoleplayingSystem(shadowrun);

        // Confirm
        assertThat(roleplayingSystemDAO.readRoleplayingSystems(), hasSize(2));
    }
}
