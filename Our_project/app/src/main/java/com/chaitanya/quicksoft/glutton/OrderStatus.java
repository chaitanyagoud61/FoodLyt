package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.chaitanya.quicksoft.glutton.databinding.ActivityOrderStatusBinding;
import com.chaitanya.quicksoft.glutton.viewModels.OrderStatusViewModel;

public class OrderStatus extends AppCompatActivity {


    String restaurant_name="",restaurant_address="",
            order_price="",order_list_food_name_nd_qnty="",
            order_date_nd_time="";
    int orderId=0;

    ActivityOrderStatusBinding activityOrderStatusBinding;
    OrderStatusViewModel orderStatusViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderStatusViewModel = ViewModelProviders.of(this).get(OrderStatusViewModel.class);
        activityOrderStatusBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_status);


        Intent intent= getIntent();
        restaurant_name = intent.getStringExtra("restaurant_name");
        restaurant_address = intent.getStringExtra("restaurant_address");
        order_price = intent.getStringExtra("order_price");
        order_date_nd_time = intent.getStringExtra("order_date_nd_time");


        orderStatusViewModel.getOrderStatusData(orderId).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        orderStatusViewModel.geterrormsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }
}
