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
    public void testEntityWriteAndRead() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();

        // Write
        final RoleplayingSystem input = createEntity();
        campaignDAO.insertRoleplayingSystem(input);

        // Read
        final List<RoleplayingSystem> roleplayingSystems = campaignDAO.getRoleplayingSystems();
        assertThat(roleplayingSystems.size(), is(1));

        final RoleplayingSystem roleplayingSystem = roleplayingSystems.get(0);
        assertThat(roleplayingSystem.id, notNullValue());
        assertThat(roleplayingSystem.roleplayingSystemName, is("Shadowrun"));
        assertThat(roleplayingSystem.logo, is("logo".getBytes()));
    }

    private RoleplayingSystem createEntity() {
        final RoleplayingSystem shadowrun = new RoleplayingSystem();
        shadowrun.roleplayingSystemName = "Shadowrun";
        shadowrun.logo = "logo".getBytes();
        return shadowrun;
    }
}
