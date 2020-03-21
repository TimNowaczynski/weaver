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
import de.quarian.weaver.database.DBConverters;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ThemeDAOTest {

    private WeaverDB weaverDB;

    @Before
    public void setUpDatabase() {
        final Context applicationContext = ApplicationProvider.getApplicationContext();
        this.weaverDB = Room.inMemoryDatabaseBuilder(applicationContext, WeaverDB.class).build();
        DatabaseTestUtils.setUpThemes(weaverDB);
    }

    @After
    public void closeDatabase() {
        this.weaverDB.close();
    }

    @Test
    public void testCreateAndReadThemeEntity() {
        final ThemeDAO themeDAO = weaverDB.themeDAO();

        //Write
        final Theme input = createThemeEntity();
        final long themeId = themeDAO.createTheme(input);
        assertThat(themeId, greaterThan(0L));

        final List<Theme> allThemes = themeDAO.readThemes();
        assertThat(allThemes.size(), is(4)); // 3 from test data + 1 new

        for (Theme theme : allThemes) {
            assertThat(theme, notNullValue());
            assertThat(theme.id, notNullValue());
            assertThat(theme.presetId, anyOf(is(Theme.PRESET_ID_CUSTOM), is(Theme.PRESET_ID_FANTASY), is(Theme.PRESET_ID_MODERN)));

            if (theme.presetId == Theme.PRESET_ID_CUSTOM) {
                assertThat(theme.fontId, is(Theme.PRESET_ID_CUSTOM));
                assertThat(theme.bannerBackgroundImage, is("custom".getBytes()));
                assertThat(theme.bannerBackgroundImageType, is("image/jpg"));

                assertThat(theme.fontId, anyOf(is(Theme.PRESET_ID_CUSTOM), is(Theme.PRESET_ID_FANTASY), is(Theme.PRESET_ID_MODERN)));
                assertThat(theme.screenBackgroundColorA, is(1));
                assertThat(theme.screenBackgroundColorR, is(2));
                assertThat(theme.screenBackgroundColorG, is(3));
                assertThat(theme.screenBackgroundColorB, is(4));
                assertThat(theme.itemBackgroundColorA, is(10));
                assertThat(theme.itemBackgroundColorR, is(20));
                assertThat(theme.itemBackgroundColorG, is(30));
                assertThat(theme.itemBackgroundColorB, is(40));
                assertThat(theme.backgroundFontColorA, is(100));
                assertThat(theme.backgroundFontColorR, is(200));
                assertThat(theme.backgroundFontColorG, is(300));
                assertThat(theme.backgroundFontColorB, is(400));
                assertThat(theme.itemFontColorA, is(1000));
                assertThat(theme.itemFontColorR, is(2000));
                assertThat(theme.itemFontColorG, is(3000));
                assertThat(theme.itemFontColorB, is(4000));
                assertThat(theme.actionColorA, is(10000));
                assertThat(theme.actionColorR, is(20000));
                assertThat(theme.actionColorG, is(30000));
                assertThat(theme.actionColorB, is(40000));
            }

            if (theme.presetId == Theme.PRESET_ID_FANTASY) {
                assertThat(theme.fontId, is(Theme.PRESET_ID_FANTASY));
                assertThat(theme.bannerBackgroundImage, is("fantasy".getBytes()));
                assertThat(theme.bannerBackgroundImageType, is("image/png"));
            }

            if (theme.presetId == Theme.PRESET_ID_MODERN) {
                assertThat(theme.fontId, is(Theme.PRESET_ID_MODERN));
                assertThat(theme.bannerBackgroundImage, is("modern".getBytes()));
                assertThat(theme.bannerBackgroundImageType, is("image/jpeg"));
            }
        }
    }

    // It's a bit shitty because I wanted to re-use some test code
    private Theme createThemeEntity() {
        final Theme theme = new Theme();
        final DBConverters.BlobConverter blobConverter = new DBConverters.BlobConverter();
        theme.bannerBackgroundImage = blobConverter.convertPrimitiveToBytes("modern".getBytes());
        theme.bannerBackgroundImageType = "image/jpeg";
        theme.presetId = Theme.PRESET_ID_MODERN;
        theme.fontId = Theme.PRESET_ID_MODERN;
        return theme;
    }

    @Test
    public void updateThemeEntity() {
        final ThemeDAO themeDAO = weaverDB.themeDAO();

        // Write
        final Theme input = createThemeEntity();
        final long themeId = themeDAO.createTheme(input);

        // Confirm

        final Theme outputA = themeDAO.readThemeByID(themeId);
        assertThat(outputA, notNullValue());
        assertThat(outputA.fontId, is(Theme.PRESET_ID_MODERN));

        // Update
        outputA.fontId = 100;
        themeDAO.updateTheme(outputA);

        // Confirm
        final Theme outputB = themeDAO.readThemeByID(themeId);
        assertThat(outputB.fontId, is(100));
    }

    // Delete Theme is done via CASCADE from Campaigns
}
