package com.example.bianca.taskmanager_android.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by BIANCA on 07.11.2017.
 */

@Entity(indices = {@Index("title")})
public class Activity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "dueDate")
    private String dueDate;
    @ColumnInfo(name = "user")
    private String user_id;

    public Activity(int id, String title, String status, String dueDate, String user_id) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
        this.user_id = user_id;
    }

    @Ignore
    public Activity(){}

    @Ignore
    public Activity(String title, String status, String dueDate, String user_id) {
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (id != activity.id) return false;
        if (title != null ? !title.equals(activity.title) : activity.title != null) return false;
        if (status != null ? !status.equals(activity.status) : activity.status != null)
            return false;
        if (dueDate != null ? !dueDate.equals(activity.dueDate) : activity.dueDate != null)
            return false;
        return user_id != null ? user_id.equals(activity.user_id) : activity.user_id == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        return result;
    }
}
