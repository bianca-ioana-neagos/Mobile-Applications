package com.example.bianca.myapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.realm.RealmObject;

/**
 * Created by BIANCA on 24.01.2018.
 */
@Entity(tableName = "items")
public class Item extends RealmObject{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type;
    private String status;
    private int quantity;



//    public boolean isToBuy() {
//        return toBuy;
//    }

    public Item() {
    }

    public Item(int id, String name, String type, String status, int quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //    public void setToBuy(boolean toBuy) {
//        this.toBuy = toBuy;
//    }
}
