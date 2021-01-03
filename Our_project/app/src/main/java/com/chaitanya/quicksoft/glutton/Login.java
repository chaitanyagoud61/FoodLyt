package com.chaitanya.quicksoft.glutton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.databinding.ActivityLoginBinding;
import com.chaitanya.quicksoft.glutton.viewModels.LoginViewModel;
import com.chaitanya.response.Getotpresp;
import com.chaitanya.response.LoginResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

public class Login extends AppCompatActivity implements NetworkResponseInterface {
    LoginViewModel loginViewModel;
    ActivityLoginBinding activityLoginBinding;
    FragmentManager fragmentManager = getFragmentManager();
    String mobile_num = "", otp = "",email="",address="",user_name="";
    JsonObject login_jsonObject = new JsonObject();
    NetworkCheck networkCheck;
    NetworkResponseInterface networkResponseInterface;
    ConnectivityManager connectivityManager;
    int user_id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityLoginBinding.setLifecycleOwner(this);
        activityLoginBinding.setLoginViewModel(loginViewModel);

        networkResponseInterface = this;

        activityLoginBinding.btnGenerateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!activityLoginBinding.etPhoneNumber.getText().toString().isEmpty() &&
                        activityLoginBinding.etPhoneNumber.getText().toString().length() == 10) {

                    mobile_num = activityLoginBinding.etPhoneNumber.getText().toString();

                    networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Login.this);
                    networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.getOtp);
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Number",Toast.LENGTH_LONG).show();
                }

            }
        });
        activityLoginBinding.valdteOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!activityLoginBinding.etOtpNumber.getText().toString().isEmpty() &&
                        activityLoginBinding.etOtpNumber.getText().toString().length() == 6) {
                    otp = activityLoginBinding.etOtpNumber.getText().toString();

                    networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Login.this);
                    networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.login);
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Otp",Toast.LENGTH_LONG).show();
                }

            }
        });

        activityLoginBinding.registerLnrLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

    public void getotpresponse() {

        loginViewModel.getotpdatamutable(mobile_num).observe(this, new Observer<Getotpresp>() {
            @Override
            public void onChanged(Getotpresp getotpresp) {
                user_id = getotpresp.getId();
                if (user_id != 0) {

                    activityLoginBinding.generateOtpLyt.setVisibility(View.GONE);
                    activityLoginBinding.otpLoginLyt.setVisibility(View.VISIBLE);
                    activityLoginBinding.registerLnrLyt.setVisibility(View.GONE);

                }
            }
        });

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }

    public void saveData(){

        class savelogindata extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... Voids) {

                LoginTable_entity loginTable_entity = new LoginTable_entity();
                loginTable_entity.setUserId(user_id);
                loginTable_entity.setUsername(user_name);
                loginTable_entity.setEmail(email);
                loginTable_entity.setAddress(address);
                loginTable_entity.setMobilenumber(mobile_num);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginTableDao().insert(loginTable_entity);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        savelogindata savelogindata = new savelogindata();
        savelogindata.execute();
    }

    public void getloginresponse() {

        loginViewModel.getLoginMutableLiveData(user_id, otp).observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse s) {

                if(!s.getAddress().isEmpty() && !s.getName().isEmpty()) {
                    address = s.getAddress();
                    user_id = s.getId();
                    user_name = s.getName();
                    email = s.getEmail();
                    saveData();
                    Intent intent = new Intent(Login.this, Home_screen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void IsConnected(Boolean isconnected, int calling_request_from) {

        if (isconnected) {
            switch (calling_request_from) {

                case Glutton_Constants.getOtp:
                    getotpresponse();

                    break;

                case Glutton_Constants.login:
                    getloginresponse();

                    break;

            }
        }else {
            Toast.makeText(getApplicationContext(),"Please Enable internet",Toast.LENGTH_LONG).show();
        }
    }
}

