package com.chaitanya.quicksoft.glutton.retrofit;

import android.content.Context;

import com.chaitanya.response.AvailabilityResponse;
import com.chaitanya.response.FoodItemResponse;
import com.chaitanya.response.Getotpresp;
import com.chaitanya.response.HomeResponse;
import com.chaitanya.response.LoginResponse;
import com.chaitanya.response.SignResponse;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    private static ApiManager apiManager = null;
    private static WeakReference<Context> contextWeakReference;

    public static synchronized ApiManager getInstance(Context context) {
        if (apiManager == null) {
            apiManager = new ApiManager();
            contextWeakReference = new WeakReference<>(context);
        }
        return apiManager;
    }

    public void getHomeresponse(JsonObject jsonObject, final ResponseCallBack<HomeResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<HomeResponse> response=apiService.Restaurants(jsonObject);

        response.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(@NotNull Call<HomeResponse> call, @NotNull Response<HomeResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<HomeResponse> call, Throwable t) {
                callBack.onError("");
            }
        });

    }

    public void getRegisterResponse(JsonObject jsonObject, final ResponseCallBack<SignResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<SignResponse> response=apiService.Registration_response(jsonObject);

        response.enqueue(new Callback<SignResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignResponse> call, @NotNull Response<SignResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SignResponse> call, Throwable t) {
                callBack.onError("");
            }
        });

    }

    public void getotpdetails(JsonObject jsonObject, final ResponseCallBack<Getotpresp> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<Getotpresp> response=apiService.getotp(jsonObject);

        response.enqueue(new Callback<Getotpresp>() {
            @Override
            public void onResponse(@NotNull Call<Getotpresp> call, @NotNull Response<Getotpresp> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Getotpresp> call, Throwable t) {
                callBack.onError("");
            }
        });

    }

    public void getLogindetails(JsonObject jsonObject, final ResponseCallBack<LoginResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<LoginResponse> response=apiService.Login(jsonObject);

        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, Throwable t) {
                callBack.onError("");
            }
        });

    }
    public void getFoodDetails(JsonObject jsonObject, final ResponseCallBack<FoodItemResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<FoodItemResponse> response=apiService.FoodItems(jsonObject);

        response.enqueue(new Callback<FoodItemResponse>() {
            @Override
            public void onResponse(@NotNull Call<FoodItemResponse> call, @NotNull Response<FoodItemResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FoodItemResponse> call, Throwable t) {
                callBack.onError("");
            }
        });

    }
    public void getViewcartfooditemsstatus(JsonObject jsonObject, final ResponseCallBack<AvailabilityResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<AvailabilityResponse> response=apiService.View_Cart_items_status(jsonObject);

        response.enqueue(new Callback<AvailabilityResponse>() {
            @Override
            public void onResponse(@NotNull Call<AvailabilityResponse> call, @NotNull Response<AvailabilityResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AvailabilityResponse> call, Throwable t) {
                callBack.onError("");
            }
        });

    }
    public void Proceed_order(JsonObject jsonObject, final ResponseCallBack<String> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<String> response=apiService.proceed_orders(jsonObject);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, Throwable t) {
                callBack.onError("");
            }
        });

    }

    public void getOrderDetails(JsonObject jsonObject, final ResponseCallBack<String> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<String> response=apiService.Orderlist_response(jsonObject);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, Throwable t) {
                callBack.onError("");
            }
        });

    }


}
