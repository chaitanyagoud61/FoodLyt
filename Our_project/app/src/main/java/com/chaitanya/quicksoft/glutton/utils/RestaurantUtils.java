package com.chaitanya.quicksoft.glutton.utils;

import android.app.Application;

import com.chaitanya.quicksoft.glutton.interfaces.Sub_Category_Quantity;

import java.util.HashMap;

public class RestaurantUtils extends Application implements Sub_Category_Quantity {


    public static HashMap<String,Integer> quantity_hashmap = new HashMap<>();

    private static RestaurantUtils instance;

    public RestaurantUtils() {
        instance = this;
    }

    public static RestaurantUtils getInstance() {
        return instance;
    }

    public static HashMap<String, Integer> getQuantityHashMap() {
        return quantity_hashmap;
    }


    @Override
    public void itemAdded(String food_name, int quantity) {

        quantity_hashmap.put(food_name, quantity);

    }

    @Override
    public void itemRemoved(String food_name, int quantity) {

        quantity_hashmap.put(food_name, quantity);

    }
}
