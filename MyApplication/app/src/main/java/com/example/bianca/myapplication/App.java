package com.example.bianca.myapplication;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.bianca.myapplication.db.ItemDb;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by BIANCA on 24.01.2018.
 */

public class App extends Application {
    public ItemDb db;
    public static int buyRequestsNr = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        db = Room.databaseBuilder(getApplicationContext(), ItemDb.class, "db-name").build();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }
}
