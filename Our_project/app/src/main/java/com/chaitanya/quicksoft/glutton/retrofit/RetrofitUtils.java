package com.chaitanya.quicksoft.glutton.retrofit;



import com.chaitanya.quicksoft.glutton.Glutton_Constants;

import androidx.databinding.library.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    public static final String TAG = RetrofitUtils.class.getName();
    private static final int CONNECTION_TIMEOUT = 60;
    private static final boolean isDebugging = BuildConfig.DEBUG;
    private static Retrofit retrofit;

    public static ApiService getInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(Glutton_Constants.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient())
                    .build();
        return retrofit.create(ApiService.class);
    }

    private static OkHttpClient getHttpClient() {
        return getHttpClient(null);
    }

    private static OkHttpClient getHttpClient(Interceptor headerInterceptor) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(isDebugging ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(interceptor);
        if (headerInterceptor != null) {
            httpClient.addInterceptor(headerInterceptor);
        }

        return httpClient.build();
    }
}
