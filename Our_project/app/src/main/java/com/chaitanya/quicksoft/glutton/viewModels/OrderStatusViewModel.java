package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.OrderstatusResp;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrderStatusViewModel extends AndroidViewModel {

    public Context context;
    public MutableLiveData<OrderstatusResp> orderstatusdata = new MutableLiveData<>();
    public MutableLiveData<String> orderstatusdata_error = new MutableLiveData<>();

   public ObservableField<String> rest_name = new ObservableField<>();
    public ObservableField<String> rest_address = new ObservableField<>();
    public ObservableField<String> total_amount = new ObservableField<>();
    public ObservableField<String> date_and_time = new ObservableField<>();
    public ObservableField<Boolean> isfoodprepared = new ObservableField<>();
    public ObservableField<Boolean> Delivered = new ObservableField<>();
    public ObservableField<Boolean> Dispatched = new ObservableField<>();

    public OrderStatusViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }


    public MutableLiveData<OrderstatusResp> getOrderStatusData (String OrderId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("orderid",OrderId);

        ApiManager.getInstance(context).getOrderStatusDetails(jsonObject, new ResponseCallBack<OrderstatusResp>() {
            @Override
            public void onResponse(OrderstatusResp s) {
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
