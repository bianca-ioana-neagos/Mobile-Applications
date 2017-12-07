package com.example.bianca.taskmanager_android.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by BIANCA on 05.12.2017.
 */

@Dao
public interface ActivityDao {
    @Query("select * from Activity")
    List<Activity> getEntries();

    @Query("select * from Activity"+" where id= :idArg")
    Activity getEntry(int idArg);

    @Query("select * from Activity"+" where status = :statusArg")
    List<Activity> getEntriesByStatus(String statusArg);

    @Insert
    void insert(Activity activity);

    @Query("delete from Activity"+" where id = :activity")
    void delete(int activity);

    @Update
    void update(Activity activity);

    @Query("delete from Activity")
    void clear();


}
