package com.example.bianca.myapplication.utils;

import com.example.bianca.myapplication.model.Item;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by BIANCA on 24.01.2018.
 */

public interface ItemService {
    String SERVICE_ENDPOINT = "http://172.30.112.58:4000";

    @GET("cars")
    Observable<List<Item>> getItems();

    @GET("all")
    Observable<List<Item>> getAllItems();

    @POST("addCar")
    Observable<Item> addItem(@Body Item e);

    @POST("buyCar")
    Observable<Item> buyItem(@Body Item e);

    @POST("returnCar")
    Observable<Item> returnItem(@Body Item e);

    @POST("removeCar")
    Observable<Item> deleteItem(@Body Item item);
}
