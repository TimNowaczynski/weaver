package de.quarian.weaver;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UtilsTest {

    @Test
    public void convertBytes() {
        final Utils.ByteArrayConverter converter = new Utils.ByteArrayConverter();
        final String stringIn = "Bytes";
        final byte[] bytesIn = stringIn.getBytes();
        final Byte[] bytesOut = converter.fromPrimitive(bytesIn);

        assertThat(bytesIn.length, is(bytesOut.length));

        for (int i = 0; i < bytesIn.length; i++) {
            final Byte byteIn = bytesIn[i];
            final Byte byteOut = bytesOut[i];
            assertThat(byteIn, is(byteOut));
        }
    }
}
