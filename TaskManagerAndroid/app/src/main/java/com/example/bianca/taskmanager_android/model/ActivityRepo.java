package com.example.bianca.taskmanager_android.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import static com.example.bianca.taskmanager_android.model.ActivityDatabase.MIGRATION_1_2;

/**
 * Created by BIANCA on 06.12.2017.
 */

public class ActivityRepo {
    private final ActivityDatabase activityDatabase;

    public ActivityRepo(Context conext){
        activityDatabase = Room.databaseBuilder(conext, ActivityDatabase.class,"task-manager.db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build();

       //this.activityDatabase.activityDao().clear();
        //this.addData();
    }

    public void addData(){
        Activity task1 = new Activity();
        task1.setTitle("Meeting");
        task1.setStatus("To Do");
        task1.setDueDate("11.12.2017");
        activityDatabase.activityDao().insert(task1);

//        Activity task2 = new Activity("Get grandma from airport", "To Do",  "11.12.2017");
//        activityDatabase.activityDao().insert(task2);
//
//        Activity task3 = new Activity("Walk the dog", "To Do",  "11.12.2017");
//        activityDatabase.activityDao().insert(task3);
    }

    public List<Activity> getAll(){
        return activityDatabase.activityDao().getEntries();
    }

    public Activity getActivity(int id){
        return activityDatabase.activityDao().getEntry(id);
    }

    public int getActivityByPosition(int pos){
        return activityDatabase.activityDao().getEntries().get(pos).getId();
    }

    public List<Activity> getAllByStatus(String status){
        return activityDatabase.activityDao().getEntriesByStatus(status);
    }

    public void insertActivity(Activity activity){
        activityDatabase.activityDao().insert(activity);
    }

    public void updateActivity(Activity activity){
        activityDatabase.activityDao().update(activity);
    }
    public void deleteActivity(int activity){
        activityDatabase.activityDao().delete(activity);
    }
}
