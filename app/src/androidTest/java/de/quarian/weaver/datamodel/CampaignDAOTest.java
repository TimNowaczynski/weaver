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
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class CampaignDAOTest {

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
    public void testEntityWriteAndReadList() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Write
        final RoleplayingSystem inputA = createEntity("Shadowrun");
        final RoleplayingSystem inputB = createEntity("DSA");
        campaignDAO.insertRoleplayingSystem(inputA);
        campaignDAO.insertRoleplayingSystem(inputB);

        // Read
        final List<RoleplayingSystem> roleplayingSystems = campaignDAO.getRoleplayingSystems();
        assertThat(roleplayingSystems.size(), is(2));

        final RoleplayingSystem roleplayingSystemA = roleplayingSystems.get(0);
        assertThat(roleplayingSystemA.id, notNullValue());
        assertThat(roleplayingSystemA.roleplayingSystemName, is("Shadowrun"));
        assertThat(roleplayingSystemA.logo, is(roleplayingSystemA.roleplayingSystemName.getBytes()));

        final RoleplayingSystem roleplayingSystemB = roleplayingSystems.get(1);
        assertThat(roleplayingSystemB.id, notNullValue());
        assertThat(roleplayingSystemB.roleplayingSystemName, is("DSA"));
        assertThat(roleplayingSystemB.logo, is(roleplayingSystemB.roleplayingSystemName.getBytes()));
    }

    private RoleplayingSystem createEntity(final String roleplayingSystemName) {
        final RoleplayingSystem shadowrun = new RoleplayingSystem();
        shadowrun.roleplayingSystemName = roleplayingSystemName;
        shadowrun.logo = roleplayingSystemName.getBytes();
        return shadowrun;
    }
}
