package com.chaitanya.quicksoft.glutton.retrofit;

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
import com.chaitanya.response.SignResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("getotp")
    Call<Getotpresp> getotp(@Body JsonObject jsonObject);

    @POST("verifyotp")
    Call<LoginResponse> Login(@Body JsonObject jsonObject);

    @POST("getrestaurants")
    Call<HomeResponse> Restaurants(@Body JsonObject jsonObject);

    @POST("getfooditems")
    Call<FoodItemResponse> FoodItems(@Body JsonObject jsonObject);

    @GET("getversion")
    Call<AppversionResponse> getversion();


    @POST("getavailability")
    Call<AvailabilityResponse> View_Cart_items_status(@Body JsonObject jsonObject);

    @POST("orderdetails")
    Call<FinalOrderResponse> proceed_orders(@Body JsonObject jsonObject);

    @POST("newuser")
    Call<SignResponse> Registration_response(@Body JsonObject jsonObject);

    @POST("orderstatus")
    Call<OrderstatusResp> getorderstatus(@Body JsonObject jsonObject);


    @POST("getorders")
    Call<Orderlistresp> Orderlist_response(@Body JsonObject jsonObject);

    @POST("getpricing")
    Call<GstResponse> GetPrice_response(@Body JsonObject jsonObject);
}
