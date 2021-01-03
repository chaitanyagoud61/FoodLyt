package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.chaitanya.quicksoft.glutton.databinding.ActivitySplashScreenBinding;

public class Splash_screen extends AppCompatActivity {

    ActivitySplashScreenBinding activitySplashScreenBinding;
    Animation  animMove ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);
        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);


        /*activitySplashScreenBinding.splashImage.startAnimation(animMove);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Splash_screen.this,Login.class);
                startActivity(intent);
                finish();
            }
        },2000);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

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
