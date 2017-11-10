package com.example.bianca.taskmanager_android.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by BIANCA on 07.11.2017.
 */

public class Activity implements Serializable {
    private static long id=0;
    private String title;
    private String status;
    private String dueDate;

    public Activity(String title, String status, String dueDate) {
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
        this.id++;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        Activity.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
