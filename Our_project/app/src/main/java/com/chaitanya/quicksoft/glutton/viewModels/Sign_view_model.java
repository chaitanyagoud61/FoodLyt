package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.SignResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Sign_view_model extends AndroidViewModel {

    public Context context;
    private MutableLiveData<SignResponse>
            mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String>
            errorMessage = new MutableLiveData<>();

    public Sign_view_model(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public MutableLiveData<SignResponse> getRegistartion_data(JsonObject jsonObject) {


        ApiManager.getInstance(context).getRegisterResponse(jsonObject, new ResponseCallBack<SignResponse>() {
            @Override
            public void onResponse(SignResponse signResponse) {
                mutableLiveData.postValue(signResponse);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }
}
