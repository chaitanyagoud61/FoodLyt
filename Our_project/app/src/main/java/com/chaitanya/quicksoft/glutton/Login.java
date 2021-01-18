package com.chaitanya.quicksoft.glutton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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
    String mobile_num = "", otp = "", email = "", address = "", user_name = "";
    JsonObject login_jsonObject = new JsonObject();
    NetworkCheck networkCheck;
    NetworkResponseInterface networkResponseInterface;
    ConnectivityManager connectivityManager;
    int user_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityLoginBinding.setLifecycleOwner(this);
        activityLoginBinding.setLoginViewModel(loginViewModel);

        networkResponseInterface = this;
        checkpermissions();

        activityLoginBinding.btnGenerateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!activityLoginBinding.etPhoneNumber.getText().toString().isEmpty() &&
                        activityLoginBinding.etPhoneNumber.getText().toString().length() == 10) {

                    mobile_num = activityLoginBinding.etPhoneNumber.getText().toString();

                    networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Login.this);
                    networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.getOtp);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_LONG).show();
                }

            }
        });
        activityLoginBinding.valdteOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!activityLoginBinding.etOtpNumber.getText().toString().isEmpty()){
                    if(activityLoginBinding.etOtpNumber.getText().toString().length() == 6) {
                        otp = activityLoginBinding.etOtpNumber.getText().toString();

                        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Login.this);
                        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.login);
                    }else {
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Enter OTP", Toast.LENGTH_LONG).show();
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


    private void checkpermissions() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Glutton_Constants.LOCATION_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0) {


        } else {
            checkpermissions();
        }

    }

    public void getotpresponse() {

        loginViewModel.getotpdatamutable(mobile_num).observe(this, new Observer<Getotpresp>() {
            @Override
            public void onChanged(Getotpresp getotpresp) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                activityLoginBinding.loginprogress.setVisibility(View.GONE);
                user_id = getotpresp.getId();
                if (getotpresp.getStatus().equalsIgnoreCase("success")) {

                    activityLoginBinding.generateOtpLyt.setVisibility(View.GONE);
                    activityLoginBinding.otpLoginLyt.setVisibility(View.VISIBLE);
                    activityLoginBinding.registerLnrLyt.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getApplicationContext(), getotpresp.getStatus(), Toast.LENGTH_LONG).show();
                }
            }
        });

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {


                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                activityLoginBinding.loginprogress.setVisibility(View.GONE);
            }
        });

    }

    public void saveData() {

        class savelogindata extends AsyncTask<Void, Void, Void> {

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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        activityLoginBinding.loginprogress.setVisibility(View.GONE);
                        /*if (s.getStatus().equalsIgnoreCase("success")) {*/
                            if (!s.getAddress().isEmpty() && !s.getName().isEmpty()) {
                                address = s.getAddress();
                                user_id = s.getId();
                                user_name = s.getName();
                                email = s.getEmail();
                                saveData();
                                Intent intent = new Intent(Login.this, Home_screen.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                            }
                     /*   } else {
                            Toast.makeText(getApplicationContext(), s.getStatus(), Toast.LENGTH_LONG).show();

                        }*/
                    }
                }, 2000);
            }
        });

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {


                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                activityLoginBinding.loginprogress.setVisibility(View.GONE);
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
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    activityLoginBinding.loginprogress.setVisibility(View.VISIBLE);
                    getotpresponse();

                    break;

                case Glutton_Constants.login:
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    activityLoginBinding.loginprogress.setVisibility(View.VISIBLE);
                    getloginresponse();

                    break;

            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Enable internet", Toast.LENGTH_LONG).show();
        }
    }
}

