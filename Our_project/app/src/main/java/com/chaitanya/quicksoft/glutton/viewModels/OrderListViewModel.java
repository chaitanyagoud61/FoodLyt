package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.FoodItemResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrderListViewModel extends AndroidViewModel {

    public Context context;
    public MutableLiveData<String> order_list_data = new MutableLiveData<>();
    public MutableLiveData<String> error_data = new MutableLiveData<>();

    public OrderListViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public MutableLiveData<String> getOrder_list_data(int user_id) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",user_id);

        ApiManager.getInstance(context).getOrderDetails(jsonObject, new ResponseCallBack<String>() {
            @Override
            public void onResponse(String s) {
                order_list_data.postValue(s);
            }

            @Override
            public void onError(String message) {
                error_data.postValue(message);
            }
        });
        return order_list_data;
    }
    public LiveData<String> getErrorMessage(){
        return error_data;
    }
}
