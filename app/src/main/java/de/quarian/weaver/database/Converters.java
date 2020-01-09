package de.quarian.weaver.database;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

public class Converters {

    @Nullable
    @TypeConverter
    public static Date fromTimestamp(@Nullable Long value) {
        return value == null ? null : new Date(value);
    }

    @Nullable
    @TypeConverter
    public static Long toTimestamp(@Nullable Date date) {
        return date == null ? null : date.getTime();
    }
}
