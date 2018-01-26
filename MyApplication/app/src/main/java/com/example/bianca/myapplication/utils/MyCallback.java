package com.example.bianca.myapplication.utils;

import com.example.bianca.myapplication.model.Item;

/**
 * Created by BIANCA on 24.01.2018.
 */

public interface MyCallback {
    void add(Item item);
    void showError(String message);
    void clear();
}
