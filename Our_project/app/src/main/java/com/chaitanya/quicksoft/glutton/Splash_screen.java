package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaitanya.quicksoft.glutton.databinding.ActivitySplashScreenBinding;
import com.chaitanya.quicksoft.glutton.viewModels.SplashAnroidViewModel;
import com.chaitanya.response.AppversionResponse;
import com.chaitanya.response.FoodItemResponse;
import com.chaitanya.response.ItemsItem;

import org.json.JSONObject;

public class Splash_screen extends AppCompatActivity {

    ActivitySplashScreenBinding activitySplashScreenBinding;
    Animation  animMove ;
    AlertDialog.Builder builder;
    SplashAnroidViewModel splashAnroidViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashAnroidViewModel = ViewModelProviders.of(this).get(SplashAnroidViewModel.class);
        activitySplashScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);
        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        builder = new AlertDialog.Builder(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkAppVersion();

    }
    public static int versionCompare(String str1, String str2) {

        int retn_vle = 0;

        if (str1.equalsIgnoreCase(str2)) {

            retn_vle = 0;
        } else {

            retn_vle = 1;
        }

        return retn_vle;
    }

    public void ShowUpdateDialog(){

        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        try {

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException anfe) {

                            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }


    private void checkAppVersion() {

        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        final String currentVersion = pInfo.versionName;
        GetVersionCode(currentVersion);
    }

    private void GetVersionCode(final String currentVersion) {


        splashAnroidViewModel.getAppversionDetails().observe(this, new Observer<AppversionResponse>() {
            @Override
            public void onChanged(AppversionResponse appversionResponse) {

                if (appversionResponse.getAppversion().length()>0) {

                    int result = versionCompare(appversionResponse.getAppversion(), currentVersion);
                    if (result > 0) {
                        ShowUpdateDialog();
                    }else {
                        sendUserDepndOnLogin();
                    }
                }
            }
        });

        splashAnroidViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }

    public void sendUserDepndOnLogin() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                class login_check extends AsyncTask<Void,Void,LoginTable_entity> {

                    @Override
                    protected LoginTable_entity doInBackground(Void... voids) {

                        LoginTable_entity loginTable_entity = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginTableDao().getAll();
                        return loginTable_entity;
                    }

                    @Override
                    protected void onPostExecute(LoginTable_entity loginTable_entity) {
                        super.onPostExecute(loginTable_entity);
                        if(loginTable_entity!=null && loginTable_entity.getUsername().trim().length()>0){

                            startActivity(new Intent(Splash_screen.this, Home_screen.class));
                            finish();
                        }else {

                            activitySplashScreenBinding.splashPb.setVisibility(View.GONE);
                            Intent intent = new Intent(Splash_screen.this,Login.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                }
                login_check login_check = new login_check();
                login_check.execute();
            }
        },2000);

    }
}
