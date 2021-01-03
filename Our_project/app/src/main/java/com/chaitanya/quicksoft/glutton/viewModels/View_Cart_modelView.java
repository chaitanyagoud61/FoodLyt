package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.AvailabilityResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class View_Cart_modelView extends AndroidViewModel {
    public View_Cart_modelView(@NonNull Application application) {
        super(application);
        context=application;
    }

    Context context;

    private MutableLiveData<AvailabilityResponse>
            mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String>
            paymnetmutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String>
            errorMessage = new MutableLiveData<>();

    public MutableLiveData<AvailabilityResponse> getMutableLiveViewCartfooditemsstatusData(JsonObject jsonObject) {

        ApiManager.getInstance(context).getViewcartfooditemsstatus(jsonObject, new ResponseCallBack<AvailabilityResponse>() {
            @Override
            public void onResponse(AvailabilityResponse availabilityResponse) {
                mutableLiveData.postValue(availabilityResponse);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return mutableLiveData;
    }
    public MutableLiveData<String> ProceedOrderToServer() {
        JsonObject jsonObject=new JsonObject();

        ApiManager.getInstance(context).Proceed_order(jsonObject, new ResponseCallBack<String>() {
            @Override
            public void onResponse(String s) {
                paymnetmutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return paymnetmutableLiveData;
    }

    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }
}
