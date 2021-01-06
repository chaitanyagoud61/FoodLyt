package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.AppversionResponse;
import com.chaitanya.response.FoodItemResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SplashAnroidViewModel extends AndroidViewModel {

    public Context context;
    private MutableLiveData<AppversionResponse>
            splash_mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String>
            food_item_errorMessage = new MutableLiveData<>();

    public ObservableField<String> restaurantAddress  = new ObservableField<String>();


    public SplashAnroidViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<AppversionResponse> getAppversionDetails() {

        ApiManager.getInstance(context).getAppversionDetails( new ResponseCallBack<AppversionResponse>() {
            @Override
            public void onResponse(AppversionResponse s) {
                splash_mutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                food_item_errorMessage.postValue(message);
            }
        });
        return splash_mutableLiveData;
    }
    public LiveData<String> getErrorMessage(){
        return food_item_errorMessage;
    }

}
