package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.chaitanya.quicksoft.glutton.databinding.ActivityProfileBinding;
import com.chaitanya.quicksoft.glutton.viewModels.ProfileViewModel;

public class Profile extends AppCompatActivity {

    String name = "", address = "", mobile = "", email = "";
    ProfileViewModel profileViewModel;
    ActivityProfileBinding activityProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        activityProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        activityProfileBinding.setLifecycleOwner(this);
        activityProfileBinding.setProfilemodelview(profileViewModel);

        getProfileDataFromDatabase();



    }

    public void getProfileDataFromDatabase(){


        class GetUserprofileData extends AsyncTask<Void, Void, LoginTable_entity> {

            @Override
            protected LoginTable_entity doInBackground(Void... voids) {

                LoginTable_entity loginTable_entity = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginTableDao().getAll();
                return loginTable_entity;
            }

            @Override
            protected void onPostExecute(LoginTable_entity loginTable_entity) {
                super.onPostExecute(loginTable_entity);
                name = loginTable_entity.getUsername();
                email = loginTable_entity.getEmail();
                address = loginTable_entity.getAddress();
                mobile = loginTable_entity.getMobilenumber();
                profileViewModel.name.set(name);
                profileViewModel.mailId.set(email);
                profileViewModel.address.set(address);
                profileViewModel.mobile.set(mobile);
            }
        }
        GetUserprofileData getUserprofileData = new GetUserprofileData();
        getUserprofileData.execute();
    }
}
