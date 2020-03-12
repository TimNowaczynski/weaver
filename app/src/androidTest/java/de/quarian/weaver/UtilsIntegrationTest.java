package de.quarian.weaver;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import de.quarian.weaver.util.Utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UtilsIntegrationTest {

    @Test
    public void convertColors() {
        final Utils.ColorConverter colorConverter = new Utils.ColorConverter();
        final int colorInt = colorConverter.toColorInt(100, 20, 40, 80);
        final int[] colorArgb = colorConverter.toColorARGB(colorInt);

        assertThat(colorArgb[0], is(100));
        assertThat(colorArgb[1], is(20));
        assertThat(colorArgb[2], is(40));
        assertThat(colorArgb[3], is(80));
    }
}
