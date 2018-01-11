package com.example.bianca.taskmanager_android.model;

/**
 * Created by BIANCA on 10.01.2018.
 */

public class User {
    private String email,password;
    private boolean isManager;

    public User(String email, String password, boolean isManager) {
        this.email = email;
        this.password = password;
        this.isManager = isManager;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
