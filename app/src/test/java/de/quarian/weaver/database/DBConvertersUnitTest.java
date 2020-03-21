package de.quarian.weaver.database;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DBConvertersUnitTest {

    private final Byte[] objectBytes = new Byte[1];
    private byte[] primitiveBytes = new byte[1];

    @Before
    public void setUp() {
        objectBytes[0] = Byte.valueOf("1");
        primitiveBytes[0] = Byte.parseByte("1");
    }

    @Test
    public void testConvertingObjectIntoPrimitive() {
        final DBConverters.BlobConverter blobConverter = DBConverters.getBlobConverter();

        final byte[] bytes = blobConverter.convertBytesToPrimitive(objectBytes);
        assertThat(bytes[0], is(primitiveBytes[0]));
    }

    @Test
    public void testConvertingPrimitiveIntoObject() {
        final DBConverters.BlobConverter blobConverter = DBConverters.getBlobConverter();

        final Byte[] bytes = blobConverter.convertPrimitiveToBytes(primitiveBytes);
        assertThat(bytes[0], is(objectBytes[0]));
    }
}
