package com.chaitanya.quicksoft.glutton.viewModels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chaitanya.response.CategoryFoodItemResponse;
import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.google.gson.JsonObject;

public class Load_All_Food_Items_ViewModel extends AndroidViewModel {

    @SuppressLint("StaticFieldLeak")
    public Context context;
    private final MutableLiveData<CategoryFoodItemResponse>
            load_all_food_Items_Mutable = new MutableLiveData<>();
    private final MutableLiveData<String>
            load_all_food_items_response = new MutableLiveData<>();

    public ObservableField<String> restaurantAddress  = new ObservableField<String>();

    public Load_All_Food_Items_ViewModel(@NonNull Application application) {
        super(application);
        context = application;
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
        return load_all_food_items_response;
    }
}
