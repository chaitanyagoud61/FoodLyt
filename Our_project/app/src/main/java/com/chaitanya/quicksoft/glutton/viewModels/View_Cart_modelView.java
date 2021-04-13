package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import com.chaitanya.quicksoft.glutton.retrofit.ApiManager;
import com.chaitanya.quicksoft.glutton.retrofit.ResponseCallBack;
import com.chaitanya.response.AvailabilityResponse;
import com.chaitanya.response.FinalOrderResponse;
import com.chaitanya.response.GstResponse;
import com.chaitanya.response.PromoCodeResponse;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
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
    private MutableLiveData<FinalOrderResponse>
            paymnetmutableLiveData = new MutableLiveData<>();
    private MutableLiveData<GstResponse>
            gstmutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PromoCodeResponse>
            promoCodeResponseData = new MutableLiveData<>();
    private MutableLiveData<String>
            errorMessage = new MutableLiveData<>();

    public ObservableField<String> addressObservable = new ObservableField<>();

    public MutableLiveData<AvailabilityResponse> getMutableLiveViewCartfooditemsstatusData(JsonObject jsonObject) {
        mutableLiveData = new MutableLiveData<>();
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
    public MutableLiveData<FinalOrderResponse> ProceedOrderToServer(JsonObject jsonObject) {

        ApiManager.getInstance(context).Proceed_order(jsonObject, new ResponseCallBack<FinalOrderResponse>() {
            @Override
            public void onResponse(FinalOrderResponse s) {
                paymnetmutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return paymnetmutableLiveData;
    }
    public MutableLiveData<GstResponse> GetGStPrice_Dataresponse(JsonObject jsonObject) {

        ApiManager.getInstance(context).getGst(jsonObject, new ResponseCallBack<GstResponse>() {
            @Override
            public void onResponse(GstResponse s) {
                gstmutableLiveData.postValue(s);
            }

            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return gstmutableLiveData;
    }

    public MutableLiveData<PromoCodeResponse> GetPromoCode_Dataresponse(JsonObject jsonObject) {

        ApiManager.getInstance(context).getPromoCodes(jsonObject, new ResponseCallBack<PromoCodeResponse>() {
            @Override
            public void onResponse(PromoCodeResponse s) {
                promoCodeResponseData.postValue(s);
            }


            @Override
            public void onError(String message) {
                errorMessage.postValue(message);
            }
        });
        return promoCodeResponseData;
    }


    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }
}
