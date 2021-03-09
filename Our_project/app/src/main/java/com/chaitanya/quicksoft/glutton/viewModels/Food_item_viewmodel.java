package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.CategoryFoodItemResponse;
import com.chaitanya.response.FoodItemResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Food_item_viewmodel extends AndroidViewModel {

    public Context context;
    private MutableLiveData<FoodItemResponse>
            food_item_mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String>
            food_item_errorMessage = new MutableLiveData<>();

    private final MutableLiveData<CategoryFoodItemResponse>
            load_all_food_Items_Mutable = new MutableLiveData<>();
    private final MutableLiveData<String>
            load_all_food_items_response = new MutableLiveData<>();


    public ObservableField<String> restaurantAddress  = new ObservableField<String>();

    public Food_item_viewmodel(@NonNull Application application) {
        super(application);
        context = application;
    }

    public MutableLiveData<FoodItemResponse> getFood_item_mutableLiveData(int selected_restrnt_id) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("rest_id",selected_restrnt_id);

        ApiManager.getInstance(context).getFoodDetails(jsonObject, new ResponseCallBack<FoodItemResponse>() {
            @Override
            public void onResponse(FoodItemResponse s) {
                food_item_mutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                food_item_errorMessage.postValue(message);
            }
        });
        return food_item_mutableLiveData;
    }

    public MutableLiveData<CategoryFoodItemResponse> getLoadAllFoodItems(int selected_restrnt_id) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("rest_id",selected_restrnt_id);

        ApiManager.getInstance(context).getCategoryItemDetails(jsonObject, new ResponseCallBack<CategoryFoodItemResponse>() {
            @Override
            public void onResponse(CategoryFoodItemResponse s) {
                load_all_food_Items_Mutable.postValue(s);
            }

            @Override
            public void onError(String message) {
                load_all_food_items_response.postValue(message);
            }
        });
        return load_all_food_Items_Mutable;
    }

    public LiveData<String> getErrorMessage(){
        return food_item_errorMessage;
    }
}
