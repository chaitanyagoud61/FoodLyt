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
    String orderId="";

    ActivityOrderStatusBinding activityOrderStatusBinding;
    OrderStatusViewModel orderStatusViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderStatusViewModel = ViewModelProviders.of(this).get(OrderStatusViewModel.class);
        activityOrderStatusBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_status);

        activityOrderStatusBinding.setLifecycleOwner(this);
        activityOrderStatusBinding.setOrderstatusviewmodel(orderStatusViewModel);
        Intent intent= getIntent();
        intent.getStringExtra("OrderId");
        orderStatusViewModel.rest_name.set(intent.getStringExtra("restaurant_name")!=null && intent.getStringExtra("restaurant_name").length()>0?
                intent.getStringExtra("restaurant_name"):"");
        orderStatusViewModel.rest_address.set(intent.getStringExtra("restaurant_address")!=null && intent.getStringExtra("restaurant_address").length()>0?
                intent.getStringExtra("restaurant_address"):"");
        orderStatusViewModel.total_amount.set(intent.getStringExtra("order_price")!=null && intent.getStringExtra("order_price").length()>0?
                intent.getStringExtra("order_price"):"");
        orderStatusViewModel.date_and_time.set(intent.getStringExtra("order_date_nd_time")!=null && intent.getStringExtra("order_date_nd_time").length()>0?
                intent.getStringExtra("order_date_nd_time"):"");


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
