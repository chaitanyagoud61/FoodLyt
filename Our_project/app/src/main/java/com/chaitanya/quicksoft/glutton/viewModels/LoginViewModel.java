package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.Getotpresp;
import com.chaitanya.response.LoginResponse;
import com.google.gson.JsonObject;

public class LoginViewModel extends AndroidViewModel {
    Context context;

    private MutableLiveData<LoginResponse>
            mutableLiveData = new MutableLiveData<>();


    private MutableLiveData<Getotpresp>
            login_mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String>
            errorMessage = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application;

    }

    public MutableLiveData<LoginResponse> getLoginMutableLiveData(int userId, String otp) {
        JsonObject login_jsonObject = new JsonObject();
        login_jsonObject.addProperty("id", userId);
        login_jsonObject.addProperty("otp", otp);

        ApiManager.getInstance(context).getLogindetails(login_jsonObject, new ResponseCallBack<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse s) {
                mutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Getotpresp> getotpdatamutable(String mobl_num) {
        JsonObject otp_jsonObject = new JsonObject();
        otp_jsonObject.addProperty("mobile",mobl_num);

        ApiManager.getInstance(context).getotpdetails(otp_jsonObject, new ResponseCallBack<Getotpresp>() {
            @Override
            public void onResponse(Getotpresp getotpresp) {
                login_mutableLiveData.postValue(getotpresp);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return login_mutableLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
