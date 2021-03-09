package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.Getotpresp;
import com.chaitanya.response.HomeResponse;
import com.chaitanya.response.LoginResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeScreenViewModel extends AndroidViewModel {
    Context context;
    private MutableLiveData<HomeResponse>
            home_mutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String>
            errorMessage = new MutableLiveData<>();

    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
        this.context=application;
    }

    public MutableLiveData<HomeResponse> gethomerestrntsdata(int location_id) {
        JsonObject hme_jsonObject = new JsonObject();
        hme_jsonObject.addProperty("city_id", location_id);

        ApiManager.getInstance(context).getHomeresponse(hme_jsonObject, new ResponseCallBack<HomeResponse>() {
            @Override
            public void onResponse(HomeResponse s) {

                home_mutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return home_mutableLiveData;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
