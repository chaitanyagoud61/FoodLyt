package com.chaitanya.quicksoft.glutton.retrofit;

import android.content.Context;

import com.chaitanya.response.CategoryFoodItemResponse;
import com.chaitanya.response.AppversionResponse;
import com.chaitanya.response.AvailabilityResponse;
import com.chaitanya.response.FinalOrderResponse;
import com.chaitanya.response.FoodItemResponse;
import com.chaitanya.response.Getotpresp;
import com.chaitanya.response.GstResponse;
import com.chaitanya.response.HomeResponse;
import com.chaitanya.response.LoginResponse;
import com.chaitanya.response.Orderlistresp;
import com.chaitanya.response.OrderstatusResp;
import com.chaitanya.response.PromoCodeResponse;
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

    // Category Items Details

    public void getCategoryItemDetails(JsonObject jsonObject, final ResponseCallBack<CategoryFoodItemResponse> callBack) {

        ApiService apiService=RetrofitUtils.getInstance();
        Call<CategoryFoodItemResponse> response=apiService.FoodCategoryItemResponse(jsonObject);

        response.enqueue(new Callback<CategoryFoodItemResponse>() {
            @Override
            public void onResponse(@NotNull Call<CategoryFoodItemResponse> call, @NotNull Response<CategoryFoodItemResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoryFoodItemResponse> call, Throwable t) {
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
                if(response.isSuccessful() && response.body()!=null) {
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
                callBack.onError(t.getMessage().toString());
            }
        });

    }

    public void getAppversionDetails(final ResponseCallBack<AppversionResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<AppversionResponse> response=apiService.getversion();

        response.enqueue(new Callback<AppversionResponse>() {
            @Override
            public void onResponse(@NotNull Call<AppversionResponse> call, @NotNull Response<AppversionResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AppversionResponse> call, Throwable t) {
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
                callBack.onError(t.getMessage().toString());
            }
        });

    }
    public void Proceed_order(JsonObject jsonObject, final ResponseCallBack<FinalOrderResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<FinalOrderResponse> response=apiService.proceed_orders(jsonObject);

        response.enqueue(new Callback<FinalOrderResponse>() {
            @Override
            public void onResponse(@NotNull Call<FinalOrderResponse> call, @NotNull Response<FinalOrderResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FinalOrderResponse> call, Throwable t) {
                callBack.onError(t.getMessage().toString());
            }
        });

    }

    public void getGst(JsonObject jsonObject, final ResponseCallBack<GstResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<GstResponse> response=apiService.GetPrice_response(jsonObject);

        response.enqueue(new Callback<GstResponse>() {
            @Override
            public void onResponse(@NotNull Call<GstResponse> call, @NotNull Response<GstResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GstResponse> call, Throwable t) {
                callBack.onError(t.getMessage().toString());
            }
        });

    }

    public void getPromoCodes(JsonObject jsonObject, final ResponseCallBack<PromoCodeResponse> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<PromoCodeResponse> response=apiService.GetPromoCode_response(jsonObject);

        response.enqueue(new Callback<PromoCodeResponse>() {
            @Override
            public void onResponse(@NotNull Call<PromoCodeResponse> call, @NotNull Response<PromoCodeResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PromoCodeResponse> call, Throwable t) {
                callBack.onError("");
            }
        });

    }


    public void getOrderDetails(JsonObject jsonObject, final ResponseCallBack<Orderlistresp> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<Orderlistresp> response=apiService.Orderlist_response(jsonObject);

        response.enqueue(new Callback<Orderlistresp>() {
            @Override
            public void onResponse(@NotNull Call<Orderlistresp> call, @NotNull Response<Orderlistresp> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Orderlistresp> call, Throwable t) {
                callBack.onError(t.getMessage().toString());
            }
        });

    }
    public void getOrderStatusDetails(JsonObject jsonObject, final ResponseCallBack<OrderstatusResp> callBack){

        ApiService apiService=RetrofitUtils.getInstance();
        Call<OrderstatusResp> response=apiService.getorderstatus(jsonObject);

        response.enqueue(new Callback<OrderstatusResp>() {
            @Override
            public void onResponse(@NotNull Call<OrderstatusResp> call, @NotNull Response<OrderstatusResp> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    callBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrderstatusResp> call, Throwable t) {
                callBack.onError(t.getMessage().toString());
            }
        });

    }




}
