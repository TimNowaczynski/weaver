package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.dev.DemoDataSetInjector;

import static org.hamcrest.Matchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

public class DataImportIntegrationTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        final DemoDataSetInjector demoDataSetInjector = new DemoDataSetInjector(applicationContext);
        demoDataSetInjector.setDemoState(this.weaverDB);
    }

    @Test
    public void testDemoDataSetImport() throws InterruptedException {
        final CampaignDAO campaignDAO = this.weaverDB.campaignDAO();
        Thread.sleep(2000L);
        final int campaignSize = campaignDAO.readCampaignsOrderedByCreated().size();
        assertThat(campaignSize, is(4));
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }
}
