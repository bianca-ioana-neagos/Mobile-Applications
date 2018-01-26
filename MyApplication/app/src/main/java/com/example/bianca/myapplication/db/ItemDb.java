package com.example.bianca.myapplication.db;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.bianca.myapplication.model.Item;

/**
 * Created by BIANCA on 24.01.2018.
 */
@Database(entities = {Item.class}, version = 1)

public abstract class ItemDb extends RoomDatabase{
    public abstract ItemDao getItemDao();
}
