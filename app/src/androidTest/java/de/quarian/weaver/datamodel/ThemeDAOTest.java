package de.quarian.weaver.datamodel;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.quarian.weaver.database.ThemeDAO;
import de.quarian.weaver.database.WeaverDB;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ThemeDAOTest {

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
    public void testCreateAndReadThemeEntity() {
        final ThemeDAO themeDAO = weaverDB.themeDAO();

        //Write
        final Theme input = createThemeEntity();
        final long themeId = themeDAO.insertTheme(input);

        //Read
        final Theme theme = themeDAO.getTheme(themeId);
        assertThat(theme.id, notNullValue());
        assertThat(theme.fontId, is(50));
        assertThat(theme.banner_background_image, is("bannerBackgroundImage".getBytes()));
        assertThat(theme.screenBackgroundColorA, is(1));
        assertThat(theme.screenBackgroundColorR, is(2));
        assertThat(theme.screenBackgroundColorG, is(3));
        assertThat(theme.screenBackgroundColorB, is(4));
        assertThat(theme.itemBackgroundColorA, is(5));
        assertThat(theme.itemBackgroundColorR, is(6));
        assertThat(theme.itemBackgroundColorG, is(7));
        assertThat(theme.itemBackgroundColorB, is(8));
        assertThat(theme.backgroundFontColorA, is(9));
        assertThat(theme.backgroundFontColorR, is(10));
        assertThat(theme.backgroundFontColorG, is(11));
        assertThat(theme.backgroundFontColorB, is(12));
        assertThat(theme.itemFontColorA, is(13));
        assertThat(theme.itemFontColorR, is(14));
        assertThat(theme.itemFontColorG, is(15));
        assertThat(theme.itemFontColorB, is(16));
    }

    private Theme createThemeEntity() {
        final Theme theme = new Theme();
        theme.fontId = 50;
        theme.banner_background_image = "bannerBackgroundImage".getBytes();
        theme.screenBackgroundColorA = 1;
        theme.screenBackgroundColorR = 2;
        theme.screenBackgroundColorG = 3;
        theme.screenBackgroundColorB = 4;
        theme.itemBackgroundColorA = 5;
        theme.itemBackgroundColorR = 6;
        theme.itemBackgroundColorG = 7;
        theme.itemBackgroundColorB = 8;
        theme.backgroundFontColorA = 9;
        theme.backgroundFontColorR = 10;
        theme.backgroundFontColorG = 11;
        theme.backgroundFontColorB = 12;
        theme.itemFontColorA = 13;
        theme.itemFontColorR = 14;
        theme.itemFontColorG = 15;
        theme.itemFontColorB = 16;
        return theme;
    }

    //TODO: Test Update and Delete Theme

}
