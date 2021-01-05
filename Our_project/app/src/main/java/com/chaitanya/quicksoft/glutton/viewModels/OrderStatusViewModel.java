package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrderStatusViewModel extends AndroidViewModel {

    public Context context;
    public MutableLiveData<String> orderstatusdata = new MutableLiveData<>();
    public MutableLiveData<String> orderstatusdata_error = new MutableLiveData<>();
    public OrderStatusViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }


    public MutableLiveData<String> getOrderStatusData (int OrderId){
        JsonObject jsonObject = new JsonObject();

        ApiManager.getInstance(context).getOrderStatusDetails(jsonObject, new ResponseCallBack<String>() {
            @Override
            public void onResponse(String s) {
                orderstatusdata.postValue(s);
            }

            @Override
            public void onError(String message) {
                orderstatusdata_error.postValue(message);
            }
        });

        return orderstatusdata;
    }

    public LiveData<String> geterrormsg(){

        return orderstatusdata_error;
    }
}
