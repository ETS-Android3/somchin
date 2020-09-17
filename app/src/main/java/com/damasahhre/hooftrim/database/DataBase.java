package com.damasahhre.hooftrim.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.damasahhre.hooftrim.database.dao.MyDao;
import com.damasahhre.hooftrim.database.models.Cow;
import com.damasahhre.hooftrim.database.models.Farm;
import com.damasahhre.hooftrim.database.models.Report;
import com.damasahhre.hooftrim.database.utils.DateConverter;

@Database(entities = {Farm.class, Cow.class, Report.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class DataBase extends RoomDatabase {
    private static DataBase dataBase;
    private static final String dataBaseName = "temp";

    public abstract MyDao dao();

    public static synchronized DataBase getInstance(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(context.getApplicationContext(), DataBase.class,
                    dataBaseName).fallbackToDestructiveMigration().build();

        }
        return dataBase;
    }

}