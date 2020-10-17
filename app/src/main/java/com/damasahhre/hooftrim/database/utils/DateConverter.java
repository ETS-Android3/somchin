package com.damasahhre.hooftrim.database.utils;

import androidx.room.TypeConverter;

import com.damasahhre.hooftrim.models.MyDate;


public class DateConverter {
    @TypeConverter
    public static MyDate fromTimestamp(Long value) {
        return value == null ? null : new MyDate(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(MyDate date) {
        return date == null ? null : date.toLong();
    }
}