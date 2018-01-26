package com.example.bianca.myapplication.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.bianca.myapplication.model.Item;

import java.util.List;

import retrofit2.http.DELETE;

/**
 * Created by BIANCA on 24.01.2018.
 */
@Dao
public interface ItemDao {
    @Insert
    void addItem(Item item);

    @Insert
    void addItems(List<Item> items);

    @Delete
    void deleteItem(Item i);

    @Query("delete from items")
    void deleteItems();

    @Update
    void updateItem(Item i);

    @Query("select * from items")
    LiveData<List<Item>> getItems();

//    @Query("select * from items where toBuy = 1")
//    List<Item> getBuyRequests();
}
