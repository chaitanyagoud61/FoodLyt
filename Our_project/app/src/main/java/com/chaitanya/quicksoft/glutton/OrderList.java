package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.databinding.ActivityOrderListBinding;
import com.chaitanya.quicksoft.glutton.viewModels.OrderListViewModel;
import com.chaitanya.response.OrderlistItem;
import com.chaitanya.response.Orderlistresp;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends AppCompatActivity implements NetworkResponseInterface,OrderClickListner{

    ActivityOrderListBinding activityOrderListBinding;
    OrderListViewModel orderListViewModel;
    NetworkCheck networkCheck;
    NetworkResponseInterface networkResponseInterface;
    ConnectivityManager connectivityManager;
    OrderListModel orderListModel;
    List<OrderListModel> orderListModelArrayList = new ArrayList<>();
    int user_id=0;
    String name="",email="",address="",mobile="";
    OrderListAdapter orderListAdapter;
    List<OrderlistItem> orderlistItem = new ArrayList<>();
    OrderClickListner orderClickListner;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
        activityOrderListBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_list);

        getProfileDataFromDatabase();
        activityOrderListBinding.setLifecycleOwner(this);
        activityOrderListBinding.setOrderlistviewmodel(orderListViewModel);
        networkResponseInterface = this;
        orderClickListner = this;
        context = this;

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
                networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, OrderList.this);
                networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.LOADTOTALORDERS);

            }
        }
        GetUserprofileData getUserprofileData = new GetUserprofileData();
        getUserprofileData.execute();
    }

    public void gettotalorders(){


        orderListViewModel.getOrder_list_data(user_id).observe(this, new Observer<Orderlistresp>() {
            @Override
            public void onChanged(Orderlistresp s) {

                orderlistItem = s.getOrderlist();

                for (OrderlistItem orderItem: orderlistItem) {

                    orderListModel = new OrderListModel(orderItem.getRestName(),orderItem.getRestAddress(),
                            String.valueOf(orderItem.getTotalPrice()), String.valueOf(orderItem.getFdItemCount()),orderItem.getDateTime(), orderItem.isDelivered(),orderItem.isInprogress(),orderItem.getOrderid());
                    orderListModelArrayList.add(orderListModel);
                }

                orderListAdapter = new OrderListAdapter(OrderList.this,orderListModelArrayList,OrderList.this);
                activityOrderListBinding.orderlistRcylr.setAdapter(orderListAdapter);

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

        Intent intent = new Intent(OrderList.this,OrderStatus.class);
        intent.putExtra("OrderId",orderListModel.getOrderId());
        intent.putExtra("restaurant_name",orderListModel.getRestaurant_name());
        intent.putExtra("restaurant_address",orderListModel.getRestaurant_address());
        intent.putExtra("order_price","Rs. "+orderListModel.getOrder_price());
        intent.putExtra("order_date_nd_time",orderListModel.getOrder_date_nd_time());
        startActivity(intent);
    }
}
