package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.FoodItemResponse;
import com.chaitanya.response.Orderlistresp;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrderListViewModel extends AndroidViewModel {

    public Context context;
    public MutableLiveData<Orderlistresp> order_list_data = new MutableLiveData<>();
    public MutableLiveData<String> error_data = new MutableLiveData<>();

    public OrderListViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public MutableLiveData<Orderlistresp> getOrder_list_data(int user_id) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",user_id);

        ApiManager.getInstance(context).getOrderDetails(jsonObject, new ResponseCallBack<Orderlistresp>() {
            @Override
            public void onResponse(Orderlistresp s) {
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
