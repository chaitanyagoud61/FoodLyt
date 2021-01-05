package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.databinding.ActivitySignInBinding;
import com.chaitanya.quicksoft.glutton.viewModels.LoginViewModel;
import com.chaitanya.quicksoft.glutton.viewModels.Sign_view_model;
import com.chaitanya.response.SignResponse;
import com.google.gson.JsonObject;

public class SignIn extends AppCompatActivity implements NetworkResponseInterface {

    ActivitySignInBinding activitySignInBinding;
    Sign_view_model sign_view_model;
    JsonObject registartion_jsonObject = new JsonObject();
    NetworkCheck networkCheck;
    ConnectivityManager connectivityManager;
    NetworkResponseInterface networkResponseInterface;
    Dialog dialog;
    Animation animBlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sign_view_model = ViewModelProviders.of(this).get(Sign_view_model.class);
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        activitySignInBinding.setLifecycleOwner(this);
        activitySignInBinding.setSignViewModel(sign_view_model);
        networkResponseInterface = this;


        activitySignInBinding.btnRegsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!activitySignInBinding.signNme.getText().toString().isEmpty() && activitySignInBinding.signNme.getText().toString().length() > 0
                        && !activitySignInBinding.signEmail.getText().toString().isEmpty() && activitySignInBinding.signEmail.getText().toString().length() > 0 &&
                        !activitySignInBinding.signMblNum.getText().toString().isEmpty() && activitySignInBinding.signMblNum.getText().toString().length() > 0 &&
                        !activitySignInBinding.addressDno.getText().toString().isEmpty() && activitySignInBinding.addressDno.getText().toString().length() > 0 &&
                        !activitySignInBinding.addressArea.getText().toString().isEmpty() && activitySignInBinding.addressArea.getText().toString().length() > 0 &&
                        !activitySignInBinding.addressNearBy.getText().toString().isEmpty() && activitySignInBinding.addressNearBy.getText().toString().length() > 0 ) {

                    if (activitySignInBinding.signMblNum.getText().length() == 10) {

                        registartion_jsonObject.addProperty("name", activitySignInBinding.signNme.getText().toString());
                        registartion_jsonObject.addProperty("email", activitySignInBinding.signEmail.getText().toString());
                        registartion_jsonObject.addProperty("mobile", activitySignInBinding.signMblNum.getText().toString());
                        registartion_jsonObject.addProperty("address",activitySignInBinding.addressDno.getText().toString()+","+
                                activitySignInBinding.addressArea.getText().toString()+","+activitySignInBinding.addressNearBy.getText().toString()+"Rajam,Srikakulam,Andhra Pradesh");

                        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, SignIn.this);
                        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.REGISTER);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter above details", Toast.LENGTH_LONG).show();
                }

            }
        });

        activitySignInBinding.alredyHaveAccntLnrLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


    }

    public void SaveRegistartion() {

        sign_view_model.getRegistartion_data(registartion_jsonObject).observe(this, new Observer<SignResponse>() {
            @Override
            public void onChanged(SignResponse s) {

                int id = s.getId();
                if (id != 0) {

                    activitySignInBinding.signMainCrdvw.setVisibility(View.GONE);
                    activitySignInBinding.thnkBlink.setAnimation(animBlink);
                    activitySignInBinding.signMainThnkyuCrdvw.setVisibility(View.VISIBLE);
                    activitySignInBinding.btnRegsiter.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignIn.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    },2000);

                }else {
                    Toast.makeText(getApplicationContext(),"User Already Exists",Toast.LENGTH_LONG).show();
                }

            }
        });

        sign_view_model.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void IsConnected(Boolean isconnected, int calling_request_from) {

        if (isconnected) {

            switch (calling_request_from) {

                case Glutton_Constants.REGISTER:

                    SaveRegistartion();

                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Enable internet", Toast.LENGTH_LONG).show();
        }
    }

}
