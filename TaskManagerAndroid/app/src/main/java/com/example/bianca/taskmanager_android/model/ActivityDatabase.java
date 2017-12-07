package com.example.bianca.taskmanager_android.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by BIANCA on 05.12.2017.
 */
@Database(entities={Activity.class}, version = 1)
public abstract class ActivityDatabase extends RoomDatabase{


    public abstract ActivityDao activityDao();



    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

}
