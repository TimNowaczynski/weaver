package de.quarian.weaver.theming;

import android.content.Context;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.quarian.weaver.util.Utils;
import de.quarian.weaver.datamodel.Theme;

public class ThemeDisplayObjectUnitTest {

    @Mock
    private Context context;

    private Utils.ByteArrayConverter byteArrayConverter = new Utils.ByteArrayConverter();

    private Theme theme;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        theme = new Theme();
        theme.presetId = Theme.PRESET_ID_CUSTOM;
        // TODO: Test font ID as soon we replace the dummy implementation
        theme.bannerBackgroundImage = byteArrayConverter.fromPrimitive("image".getBytes());
        theme.bannerBackgroundImageType = "image/jpg";

        theme.actionColorA = 1;
        theme.actionColorR = 2;
        theme.actionColorG = 3;
        theme.actionColorB = 4;

        theme.screenBackgroundColorA = 5;
        theme.screenBackgroundColorR = 6;
        theme.screenBackgroundColorG = 7;
        theme.screenBackgroundColorB = 8;

        theme.itemBackgroundColorA = 9;
        theme.itemBackgroundColorR = 10;
        theme.itemBackgroundColorG = 11;
        theme.itemBackgroundColorB = 12;

        theme.backgroundFontColorA = 13;
        theme.backgroundFontColorR = 14;
        theme.backgroundFontColorG = 15;
        theme.backgroundFontColorB = 16;

        theme.itemFontColorA = 17;
        theme.itemFontColorR = 18;
        theme.itemFontColorG = 19;
        theme.itemFontColorB = 20;
    }

    /*
     TODO: since we use static methods to convert colors from argb -> int,
        we can't really mock this right now. So best course of action is
        possibly to extract color conversion into a designated class
     */
    @Ignore("ThemeDisplayObjectUnitTest: ignored test because we need some refactoring")
    @Test
    public void testFromTheme() {
        final ThemeDisplayObject themeDisplayObject = ThemeDisplayObject.fromTheme(context, theme);
    }
}
