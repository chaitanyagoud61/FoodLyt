package com.chaitanya.quicksoft.glutton.retrofit;

public interface ResponseCallBack<T> {

    void onResponse(T t);
    void onError(String message);
}
