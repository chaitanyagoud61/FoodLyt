package com.chaitanya.quicksoft.glutton.activities.restaurant.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.databinding.ActivityOrderStatusBinding;
import com.chaitanya.quicksoft.glutton.viewModels.OrderStatusViewModel;
import com.chaitanya.response.FooddetailsItem;
import com.chaitanya.response.OrderstatusResp;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity {


    String restaurant_name="",restaurant_address="",
            order_price="",order_list_food_name_nd_qnty="",
            order_date_nd_time="";
    String orderId="";
    Orderstatuslocalmodel orderstatuslocalmodel;
    List<Orderstatuslocalmodel> orderstatuslocalmodelList = new ArrayList<>();
    List<FooddetailsItem> fooddetailsItemslist = new ArrayList<>();

    ActivityOrderStatusBinding activityOrderStatusBinding;
    OrderStatusViewModel orderStatusViewModel;
    OrderStatusAdapter orderStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderStatusViewModel = ViewModelProviders.of(this).get(OrderStatusViewModel.class);
        activityOrderStatusBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_status);

        activityOrderStatusBinding.setLifecycleOwner(this);
        activityOrderStatusBinding.setOrderstatusviewmodel(orderStatusViewModel);
        Intent intent= getIntent();
        orderId = intent.getStringExtra("OrderId");
        orderStatusViewModel.rest_name.set(intent.getStringExtra("restaurant_name")!=null && intent.getStringExtra("restaurant_name").length()>0?
                intent.getStringExtra("restaurant_name"):"");
        orderStatusViewModel.rest_address.set(intent.getStringExtra("restaurant_address")!=null && intent.getStringExtra("restaurant_address").length()>0?
                intent.getStringExtra("restaurant_address"):"");
        orderStatusViewModel.total_amount.set(intent.getStringExtra("order_price")!=null && intent.getStringExtra("order_price").length()>0?
                intent.getStringExtra("order_price"):"");
        orderStatusViewModel.date_and_time.set(intent.getStringExtra("order_date_nd_time")!=null && intent.getStringExtra("order_date_nd_time").length()>0?
                intent.getStringExtra("order_date_nd_time"):"");


        orderStatusViewModel.getOrderStatusData(orderId).observe(this, new Observer<OrderstatusResp>() {
            @Override
            public void onChanged(OrderstatusResp s) {

                orderStatusViewModel.isfoodprepared.set(s.Isfoodprepared());
                orderStatusViewModel.Delivered.set(s.Delivered());
                orderStatusViewModel.Dispatched.set(s.Dispatched());
                fooddetailsItemslist = s.getFooddetails();
                for (FooddetailsItem food:fooddetailsItemslist) {
                    orderstatuslocalmodel = new Orderstatuslocalmodel(food.getName(),food.getPrice(),food.getQuantity()!=null?food.getQuantity():"" );
                    orderstatuslocalmodelList.add(orderstatuslocalmodel);
                }
                orderStatusAdapter = new OrderStatusAdapter(OrderStatus.this,orderstatuslocalmodelList);
                activityOrderStatusBinding.orderstatusRecylr.setAdapter(orderStatusAdapter);

                

            }
        });

        orderStatusViewModel.geterrormsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }
}
