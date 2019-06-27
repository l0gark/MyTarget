package com.develop.loginov.mytarget.database;

import androidx.room.TypeConverter;

public class LongConverter {
    @TypeConverter
    public String fromLong(long x) {
        return Long.toString(x);
    }

    @TypeConverter
    public long toLong(String s) {
        return Long.valueOf(s);
    }
}
