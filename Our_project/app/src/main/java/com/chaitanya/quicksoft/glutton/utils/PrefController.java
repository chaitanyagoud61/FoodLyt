package com.chaitanya.quicksoft.glutton.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefController {

    public static void SavePref(String key, String value, Context context){

        SharedPreferences.Editor editor = context.getSharedPreferences("Location",Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String LoadData(String key, String value, Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("Location",Context.MODE_PRIVATE);
      String lct_name = sharedPreferences.getString(key,value);
      return lct_name;
    }
    public static void removeData(String key, String value, Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("Location",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
