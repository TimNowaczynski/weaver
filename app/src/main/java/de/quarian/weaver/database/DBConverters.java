package de.quarian.weaver.database;

import androidx.room.TypeConverter;

public class DBConverters {

    private static BlobConverter blobConverter;

    // Meant for testing
    public static BlobConverter getBlobConverter() {
        if (blobConverter == null) {
            blobConverter = new BlobConverter();
        }
        return blobConverter;
    }

    public static class BlobConverter {

        @TypeConverter
        public Byte[] convertPrimitiveToBytes(final byte[] bytes) {
            if (bytes == null || bytes.length == 0) {
                return null;
            }

            final Byte[] output = new Byte[bytes.length];
            int index = 0;
            for (final byte b : bytes) {
                output[index] = b;
                index++;
            }
            return output;
        }

        @TypeConverter
        public byte[] convertBytesToPrimitive(final Byte[] bytes) {
            final byte[] output = new byte[bytes.length];
            int index = 0;
            for (byte b : bytes) {
                output[index] = b;
                index++;
            }
            return output;
        }

    }
}
