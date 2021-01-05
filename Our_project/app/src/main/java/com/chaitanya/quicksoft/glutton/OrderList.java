package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.databinding.ActivityOrderListBinding;
import com.chaitanya.quicksoft.glutton.viewModels.OrderListViewModel;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity implements NetworkResponseInterface,OrderClickListner{

    ActivityOrderListBinding activityOrderListBinding;
    OrderListViewModel orderListViewModel;
    NetworkCheck networkCheck;
    NetworkResponseInterface networkResponseInterface;
    ConnectivityManager connectivityManager;
    OrderListModel orderListModel;
    ArrayList<OrderListModel> orderListModelArrayList = new ArrayList<>();
    int user_id=0;
    String name="",email="",address="",mobile="";
    OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
        activityOrderListBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_list);

        activityOrderListBinding.setLifecycleOwner(this);
        activityOrderListBinding.setOrderlistviewmodel(orderListViewModel);
        networkResponseInterface = this;

        getProfileDataFromDatabase();
        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, OrderList.this);
        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.LOADTOTALORDERS);


    }

    @Override
    public void IsConnected(Boolean isconnected, int calling_request_from) {

        if(isconnected){

            switch (calling_request_from){

                case Glutton_Constants.LOADTOTALORDERS:

                    gettotalorders();

                    break;
            }

        }else {

            Toast.makeText(getApplicationContext(),"Please Enable internet",Toast.LENGTH_LONG).show();
        }
    }

    public void getProfileDataFromDatabase() {


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
                user_id = loginTable_entity.getUserId();
                email = loginTable_entity.getEmail();
                address = loginTable_entity.getAddress();
                mobile = loginTable_entity.getMobilenumber();
            }
        }
        GetUserprofileData getUserprofileData = new GetUserprofileData();
        getUserprofileData.execute();
    }

    public void gettotalorders(){


        orderListViewModel.getOrder_list_data(user_id).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {


            }
        });

        orderListViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderList.this, Home_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void getselectedorder(OrderListModel orderListModel) {

        orderListModel.getOrderId();
    }
}
