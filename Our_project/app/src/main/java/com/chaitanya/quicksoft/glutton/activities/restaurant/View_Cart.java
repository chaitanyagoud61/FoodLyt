package com.chaitanya.quicksoft.glutton.activities.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chaitanya.quicksoft.glutton.interfaces.BottomSheetDialogInterface;
import com.chaitanya.quicksoft.glutton.Home_screen;
import com.chaitanya.quicksoft.glutton.utils.NetworkCheck;
import com.chaitanya.quicksoft.glutton.interfaces.NetworkResponseInterface;
import com.chaitanya.quicksoft.glutton.activities.restaurant.orders.OrderList;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.adapters.ViewCartAdapter;
import com.chaitanya.quicksoft.glutton.databinding.ActivityViewCartBinding;
import com.chaitanya.quicksoft.glutton.utils.Glutton_Constants;
import com.chaitanya.quicksoft.glutton.utils.RestaurantUtils;
import com.chaitanya.quicksoft.glutton.viewModels.View_Cart_modelView;
import com.chaitanya.response.AvailabilityResponse;
import com.chaitanya.response.FinalOrderResponse;
import com.chaitanya.response.FoodItemsItem;
import com.chaitanya.response.GstResponse;
import com.chaitanya.response.PromoCodeResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class View_Cart extends AppCompatActivity implements PaymentResultWithDataListener, NetworkResponseInterface, BottomSheetDialogInterface {

    View_Cart_modelView view_cart_modelView;
    ActivityViewCartBinding activityViewCartBinding;
    String selected_restrnt = "", restaurant_address = "",
            selected_restrnt_id = "", intial_amount = "0", delivery_fee = "0", items_status = "",gst="0";
    HashMap<String, String> cart_hashMap = new HashMap<>();
    Selected_food_view_model selected_food_view_model;
    ArrayList<Selected_food_view_model> selectedFoodViewModelList = new ArrayList<>();
    ViewCartAdapter viewCartAdapter;
    double total = 0.0;
    String name = "", address = "", mobile = "", email = "";
    NetworkResponseInterface networkResponseInterface;
    NetworkCheck networkCheck;
    ConnectivityManager connectivityManager;
    JsonObject availability_jsonObject = new JsonObject();
    JsonObject innr_availability_jsonObject = new JsonObject();
    JsonArray availability_jsonarray = new JsonArray();
    JsonArray order_placement_jsonarray = new JsonArray();
    JsonObject order_placement_jsonObject = new JsonObject();
    JsonObject order_placement_food_items_jsonObject = new JsonObject();

    JsonObject get_promo_codes = new JsonObject();

    int code_id;

    EditText edtVerifyPromoCode;

    int discount_availed = 0, min_discount = 0, max_discount = 0;

    List<PromoCodeResponse.PromocodesBean> promoCodeResponseList;

    List<FoodItemsItem> foodItemsItemList = new ArrayList<>();
    Boolean items_avablty = false;
    int user_id = 0;
    String payment_id = "", payment_order_id = "";
    Snackbar snackbar;
    BottomSheetDialogInterface bottomSheetDialogInterface;

    TextView txtDiscountedAmount, txtDiscountPercentage;

    Button btnVerifyPromoCode;
    LinearLayout discountValueLayout;

    String promoCodeEntered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view_cart_modelView = ViewModelProviders.of(this).get(View_Cart_modelView.class);
        activityViewCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_view__cart);

        activityViewCartBinding.setLifecycleOwner(this);
        activityViewCartBinding.setViewCartModelView(view_cart_modelView);

        bottomSheetDialogInterface = this;

        discountValueLayout = findViewById(R.id.discount_val_layout);

        btnVerifyPromoCode = findViewById(R.id.btn_verify_promo_code);

        promoCodeResponseList = new ArrayList<>();
        edtVerifyPromoCode = findViewById(R.id.edt_promo_code);

        txtDiscountedAmount = findViewById(R.id.discounted_amount);
        txtDiscountPercentage = findViewById(R.id.discount_applied);

        Intent intent = getIntent();
        cart_hashMap = (HashMap<String, String>) intent.getSerializableExtra("map");
        selected_restrnt = intent.getStringExtra("selected_restrnt");
        restaurant_address = intent.getStringExtra("restaurant_address");
        selected_restrnt_id = intent.getStringExtra("selected_restrnt_id");

        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        mobile = intent.getStringExtra("mobile");
        email = intent.getStringExtra("email");
        user_id = intent.getIntExtra("user_id",0);

        view_cart_modelView.addressObservable.set(address);

        intial_amount = intent.getStringExtra("intial_amount") != null && !intent.getStringExtra("intial_amount").isEmpty() ?
                intent.getStringExtra("intial_amount") : "0";

       // Toast.makeText(this, "Received Amount: " + intial_amount, Toast.LENGTH_SHORT).show();

        activityViewCartBinding.selectedRestrntInViewcart.setText(selected_restrnt);
        activityViewCartBinding.selectedRestrntAddressInViewcart.setText(restaurant_address);
        activityViewCartBinding.noOfItemsSelected.setText(cart_hashMap.size() + " Item(s)");

        Set<String> cart_keys = cart_hashMap.keySet();
        for (String fooditem : cart_keys) {
            innr_availability_jsonObject = new JsonObject();
            String item_name_and_food_quantity = "", food_name = "", food_quantity = "";
            item_name_and_food_quantity = String.valueOf(cart_hashMap.get(fooditem));
            String[] name_quantity = item_name_and_food_quantity.split("~");
            food_name = name_quantity[0];
            food_quantity = name_quantity[1];
            int food_id_modified = Integer.parseInt(fooditem);

            /* if(Integer.parseInt(fooditem) > 100) {
                food_id_modified = (Integer.parseInt(fooditem) /100);
                //  Toast.makeText(this, "More than 100 Decreased Value" + (Integer.parseInt(fooditem) /100) , Toast.LENGTH_SHORT).show();
            } else {
                food_id_modified = Integer.parseInt(fooditem);
            }*/


            selected_food_view_model = new Selected_food_view_model(food_name, food_quantity);
            selectedFoodViewModelList.add(selected_food_view_model);
            innr_availability_jsonObject.addProperty("food_id", food_id_modified);
            availability_jsonarray.add(innr_availability_jsonObject);
        }

        availability_jsonObject.addProperty("city_id", Integer.valueOf("1"));
        availability_jsonObject.addProperty("rest_id", Integer.valueOf(selected_restrnt_id));
        availability_jsonObject.add("selected_food_items", availability_jsonarray);

        Log.d("ORDER TAG", "Get  " +String.valueOf(order_placement_jsonObject));

        
        GridLayoutManager gridLayoutManager = new GridLayoutManager(View_Cart.this, 1);
        activityViewCartBinding.selectedFoodItemsRyclr.setLayoutManager(gridLayoutManager);
        viewCartAdapter = new ViewCartAdapter(this, selectedFoodViewModelList);
        activityViewCartBinding.selectedFoodItemsRyclr.setAdapter(viewCartAdapter);
        networkResponseInterface = this;

        activityViewCartBinding.tapForSafetyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityViewCartBinding.safetyInfoContent.isShown()) {
                    activityViewCartBinding.safetyInfoContent.setVisibility(View.GONE);
                } else if (!activityViewCartBinding.safetyInfoContent.isShown()) {
                    activityViewCartBinding.safetyInfoContent.setVisibility(View.VISIBLE);
                }
            }
        });

        activityViewCartBinding.viewcartpayoncash.setOnClickListener(view -> {

            networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, View_Cart.this);
            networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.CASHONDELIVERY);
        });


        // Calling GST method
        getGSt();

        activityViewCartBinding.startPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, View_Cart.this);
                networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.PAYMENT_TRANSCATION);

            }
        });

        activityViewCartBinding.goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goback_intent = new Intent(View_Cart.this, Home_screen.class);
                goback_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goback_intent);
                finish();

            }
        });


        btnVerifyPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredMsg = edtVerifyPromoCode.getText().toString().trim();
                if(!enteredMsg.isEmpty()) {
                    // Calling Verify Promo Code method
                    verifyPromoCode();
                } else {
                    Toast.makeText(View_Cart.this, "Please enter Promo Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verifyPromoCode() {

        promoCodeEntered = edtVerifyPromoCode.getText().toString().trim();

        if(checkPromoCodeAvailability(promoCodeEntered)) {

            int initial_amt = Integer.parseInt(intial_amount);
            int discount_calculation = (initial_amt * discount_availed/100);
            int final_price_after_discount = 0;

            // Checking whether it satisfies minimum Discount Amt
            if(initial_amt > min_discount) {

                if(discount_calculation < max_discount) {

                    // Here, the price is less than max discount value, so we are doing normal Discount calculation
                    final_price_after_discount = (initial_amt - (initial_amt * discount_availed/100));

                    //Calling method to show Custom Dialog
                    showPromoCodeStatusDialog("Success", "Total Discount");

                } else {
                    // Here the price is more than max value, so we are just Subtracting value
                    final_price_after_discount = initial_amt - max_discount;

                    //Calling method to show Custom Dialog
                    showPromoCodeStatusDialog("Success", "Partial Discount");

                }

                txtDiscountedAmount.setVisibility(View.VISIBLE);

                discountValueLayout.setVisibility(View.VISIBLE);
                txtDiscountPercentage.setText(discount_availed+"%");

                txtDiscountedAmount.setText("₹ "+intial_amount);
                txtDiscountedAmount.setPaintFlags(txtDiscountedAmount.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);




            } else {

                final_price_after_discount = initial_amt;
                // Showing Custom Dialog with minimum Discount
                showPromoCodeStatusDialog("LowPrice", "");

                //txtDiscountedAmount.setVisibility(View.VISIBLE);

                //discountValueLayout.setVisibility(View.VISIBLE);
                //txtDiscountPercentage.setText(discount_availed+"%");

                //txtDiscountedAmount.setText("₹ "+intial_amount);
               // txtDiscountedAmount.setPaintFlags(txtDiscountedAmount.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);




            }
            activityViewCartBinding.intialAmount.setText("₹ " + final_price_after_discount);
            activityViewCartBinding.delivryAmount.setText("₹ " + delivery_fee);
            activityViewCartBinding.gstTxt.setText("₹ " + gst);
            activityViewCartBinding.finalPay.setText("₹ " + String.valueOf( Integer.valueOf(gst) + Integer.parseInt(activityViewCartBinding.intialAmount.getText().toString().split(" ")[1]) + Integer.valueOf(delivery_fee)));





        } else {
            discountValueLayout.setVisibility(View.GONE);

            txtDiscountedAmount.setVisibility(View.INVISIBLE);

            activityViewCartBinding.intialAmount.setText("₹ " +intial_amount);

            activityViewCartBinding.delivryAmount.setText("₹ " + delivery_fee);
            activityViewCartBinding.gstTxt.setText("₹ " + gst);
            activityViewCartBinding.finalPay.setText("₹ " + String.valueOf( Integer.valueOf(gst) + Integer.parseInt(activityViewCartBinding.intialAmount.getText().toString().split(" ")[1])+ Integer.valueOf(delivery_fee)));

            showPromoCodeStatusDialog("Error", "");
        }

    }

    private Boolean checkPromoCodeAvailability(String promoCodeEntered) {
        for(int i=0; i < promoCodeResponseList.size(); i++) {
            if (promoCodeResponseList.get(i).getCode().equals(promoCodeEntered)) {
                //Valid Promo Code Entered - Avail the Discount
                discount_availed = promoCodeResponseList.get(i).getDiscount();
                code_id = promoCodeResponseList.get(i).getCode_id();
                min_discount = promoCodeResponseList.get(i).getMinimum();
                max_discount = promoCodeResponseList.get(i).getUpto();

                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;


    }

    private void showPromoCodeStatusDialog(String status, String message) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        TextView txtStatusMessage = dialog.findViewById(R.id.txtMessage);
        ImageView imageView = dialog.findViewById(R.id.statusImageView);
        Button btnDone = dialog.findViewById(R.id.btnDone);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(status.equals("Success") && discount_availed!=0) {

            if(message.equals("Total Discount")) {

                btnDone.setText("yay! Thanks");
                imageView.setImageResource(R.drawable.success);
                txtStatusMessage.setText("Success! You have availed " + discount_availed + "% Discount");
            }
             else if(message.equals("Partial Discount") && max_discount!=0) {

                btnDone.setText("yay! Thanks");
                imageView.setImageResource(R.drawable.success);
                txtStatusMessage.setText("Success! You have availed discount upto ₹" + max_discount);
            }

        } else if(status.equals("LowPrice") && min_discount!=0)  {
            btnDone.setText("Okay");
            imageView.setImageResource(R.drawable.wrong);
            txtStatusMessage.setText("This Promo Code will be availed for above ₹ " + min_discount);
        }
        else {
            imageView.setImageResource(R.drawable.wrong);

            btnDone.setText("Okay");
            txtStatusMessage.setText("Sorry, The Promo Code Entered is invalid or Expired!");
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void getFoodAvldStatusforPay(String mode){

        view_cart_modelView.getMutableLiveViewCartfooditemsstatusData(availability_jsonObject).observe(this, new Observer<AvailabilityResponse>() {
            @Override
            public void onChanged(AvailabilityResponse availabilityResponse) {

                if (availabilityResponse.getStatus().equalsIgnoreCase("on")) {

                    foodItemsItemList = availabilityResponse.getFoodItems();
                    for (FoodItemsItem foodItemsItem : foodItemsItemList) {

                        if (foodItemsItem.getStatus().equalsIgnoreCase("on")) {

                            items_avablty = false;
                        } else if (foodItemsItem.getStatus().equalsIgnoreCase("off")) {

                            items_avablty = true;
                        }

                    }

                    if (items_avablty) {

                        activityViewCartBinding.viewcartstatuscontentUnaval.setVisibility(View.VISIBLE);

                        for (FoodItemsItem foodItemsItem : foodItemsItemList) {

                            if (foodItemsItem.getStatus().equalsIgnoreCase("on")) {


                            } else if (foodItemsItem.getStatus().equalsIgnoreCase("off")) {

                                String items = activityViewCartBinding.unavlbeItemCount.getText().toString();
                                if (items.isEmpty()) {
                                    activityViewCartBinding.unavlbeItemCount.setText(foodItemsItem.getName());
                                } else {
                                    activityViewCartBinding.unavlbeItemCount.setText(items + "," + foodItemsItem.getName());
                                }
                            }

                        }
                        activityViewCartBinding.viewcartstatuscontentAval.setVisibility(View.GONE);
                        activityViewCartBinding.startPayment.setVisibility(View.GONE);
                        activityViewCartBinding.viewcartpayoncash.setVisibility(View.GONE);

                    } else if (!items_avablty) {
                        activityViewCartBinding.viewcartstatuscontentAval.setVisibility(View.VISIBLE);
                        activityViewCartBinding.viewcartstatuscontentUnaval.setVisibility(View.GONE);
                        activityViewCartBinding.startPayment.setVisibility(View.VISIBLE);
                        //activityViewCartBinding.viewcartpayoncash.setVisibility(View.VISIBLE);
                        if(!mode.isEmpty() && mode.equalsIgnoreCase("online")){
                            startPayment();
                        }else if(!mode.isEmpty() && mode.equalsIgnoreCase("cod")){
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            activityViewCartBinding.pytmprogress.setVisibility(View.GONE);
                            Bottomfrag frag= new Bottomfrag(bottomSheetDialogInterface);
                            frag.show(getSupportFragmentManager(),"PAYMENT TYPE");
                        }
                    }


                } else if (availabilityResponse.getStatus().equalsIgnoreCase("off")) {

                    activityViewCartBinding.viewcartrestrntstatusAval.setVisibility(View.VISIBLE);
                    activityViewCartBinding.startPayment.setVisibility(View.GONE);
                    activityViewCartBinding.viewcartpayoncash.setVisibility(View.GONE);
                    activityViewCartBinding.viewcartstatuscontentAval.setVisibility(View.GONE);

                }

            }
        });

        view_cart_modelView.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //Toast.makeText(getApplicationContext(), "Error: getFood"+s, Toast.LENGTH_LONG).show();
            }
        });

    }



    public void startordertoserver(String payment_mode) {

        order_placement_jsonObject = new JsonObject();
        order_placement_jsonarray = new JsonArray();

        order_placement_jsonObject.addProperty("id", user_id);
        order_placement_jsonObject.addProperty("payment_id", payment_id);
        order_placement_jsonObject.addProperty("transaction_id", payment_order_id);
        order_placement_jsonObject.addProperty("rest_id", Integer.valueOf(selected_restrnt_id));
        order_placement_jsonObject.addProperty("city_id", 1);

        Set<String> cart_keys = cart_hashMap.keySet();
        for (String fooditem : cart_keys) {
            order_placement_food_items_jsonObject = new JsonObject();
            String item_name_and_food_quantity = "", food_name = "", food_quantity = "";
            item_name_and_food_quantity = String.valueOf(cart_hashMap.get(fooditem));
            String[] name_quantity = item_name_and_food_quantity.split("~");
            food_name = name_quantity[0];
            food_quantity = name_quantity[1];
            int food_id_modified = Integer.parseInt(fooditem);

            /* if(Integer.parseInt(fooditem) > 100) {
                food_id_modified = (Integer.parseInt(fooditem) /100);
              //  Toast.makeText(this, "More than 100 Decreased Value" + (Integer.parseInt(fooditem) /100) , Toast.LENGTH_SHORT).show();
            } else {
                food_id_modified = Integer.parseInt(fooditem);
            }*/

            order_placement_food_items_jsonObject.addProperty("food_id", food_id_modified);
            order_placement_food_items_jsonObject.addProperty("name", food_name);
            order_placement_food_items_jsonObject.addProperty("quantity", Integer.valueOf(food_quantity));
            order_placement_jsonarray.add(order_placement_food_items_jsonObject);
        }


        order_placement_jsonObject.add("food_items", order_placement_jsonarray);
        order_placement_jsonObject.addProperty("total_price", String.valueOf(Integer.valueOf(gst) + Integer.parseInt(activityViewCartBinding.intialAmount.getText().toString().split(" ")[1])+ Integer.valueOf(delivery_fee)));
        order_placement_jsonObject.addProperty("paymentmode", payment_mode);
        // Order Placed with PromoCode or not

        if(discount_availed == 0) {
            // Promocode is not applied
            order_placement_jsonObject.addProperty("appliedpromocode","No");
            order_placement_jsonObject.addProperty("code_id", 0);

           // Toast.makeText(this, "Placed Order without Promocode", Toast.LENGTH_SHORT).show();
        } else {
            // Promocode applied Successfully
            order_placement_jsonObject.addProperty("appliedpromocode", "Yes");
            order_placement_jsonObject.addProperty("code_id", code_id);

           // Toast.makeText(this, "Placed Order with Promocode", Toast.LENGTH_SHORT).show();

        }

        order_placement_jsonObject.addProperty("address", activityViewCartBinding.finalEdtLctn.getText().toString());
        Log.d("ORDER TAG", "Start Order " +String.valueOf(order_placement_jsonObject));

        view_cart_modelView.ProceedOrderToServer(order_placement_jsonObject).observe(this, new Observer<FinalOrderResponse>() {
            @Override
            public void onChanged(FinalOrderResponse s) {

                if (!s.getStatus().isEmpty()) {

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    activityViewCartBinding.pytmprogress.setVisibility(View.GONE);

                    Intent intent = new Intent(View_Cart.this, OrderList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if (s.getStatus().equalsIgnoreCase("success")) {

                        ProgressDialog progressBar = new ProgressDialog(View_Cart.this);
                        progressBar.setCancelable(false);//you can cancel it by pressing back button
                        progressBar.setMessage("Your Order is successfully placed ...");
                        progressBar.setTitle("ORDER Confirmed");
                        progressBar.setIcon(R.drawable.ic_tick);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                                finish();
                                progressBar.dismiss();
                            }
                        },4000);

                        RestaurantUtils.quantity_hashmap.clear();

                    } else if (s.getStatus().equalsIgnoreCase("failed")) {

                        ProgressDialog progressBar = new ProgressDialog(View_Cart.this);
                        progressBar.setCancelable(false);//you can cancel it by pressing back button
                        progressBar.setMessage("Your Order is Cancelled ...");
                        progressBar.setTitle("ORDER Cancelled");
                        progressBar.setIcon(R.drawable.worng);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                                finish();
                                progressBar.dismiss();
                            }
                        },4000);

                        RestaurantUtils.quantity_hashmap.clear();

                    }

                }
            }
        });

        view_cart_modelView.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

               // Toast.makeText(getApplicationContext(),"Error: Order "+s,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getGSt(){

        order_placement_jsonObject = new JsonObject();
        order_placement_jsonarray = new JsonArray();
        order_placement_jsonObject.addProperty("user_id", user_id);
        order_placement_jsonObject.addProperty("rest_id", Integer.valueOf(selected_restrnt_id));

        Set<String> cart_keys = cart_hashMap.keySet();
        for (String fooditem : cart_keys) {
            order_placement_food_items_jsonObject = new JsonObject();
            String item_name_and_food_quantity = "", food_name = "", food_quantity = "";
            item_name_and_food_quantity = String.valueOf(cart_hashMap.get(fooditem));
            String[] name_quantity = item_name_and_food_quantity.split("~");
            food_name = name_quantity[0];
            food_quantity = name_quantity[1];
            int food_id_modified = Integer.parseInt(fooditem);

            /*
            if(Integer.parseInt(fooditem) > 100) {
                food_id_modified = (Integer.parseInt(fooditem) /100);
               // Toast.makeText(this, "More than 100 Decreased Value" + (Integer.parseInt(fooditem) /100) , Toast.LENGTH_SHORT).show();
            } else {
                food_id_modified = Integer.parseInt(fooditem);
            }
*/

            order_placement_food_items_jsonObject.addProperty("food_id", food_id_modified);
            order_placement_food_items_jsonObject.addProperty("name", food_name);
            order_placement_food_items_jsonObject.addProperty("quantity", Integer.valueOf(food_quantity));
            order_placement_jsonarray.add(order_placement_food_items_jsonObject);
        }

        order_placement_jsonObject.add("food_items", order_placement_jsonarray);
        order_placement_jsonObject.addProperty("total_price", Integer.valueOf(intial_amount));
        order_placement_jsonObject.addProperty("city_id", 1);


        view_cart_modelView.GetGStPrice_Dataresponse(order_placement_jsonObject).observe(this, new Observer<GstResponse>() {
            @Override
            public void onChanged(GstResponse gstResponse) {

                delivery_fee = String.valueOf(gstResponse.getDeliveryPartnerFee());
                gst = String.valueOf(gstResponse.getTaxes());

                activityViewCartBinding.intialAmount.setText("₹ " + intial_amount);

                activityViewCartBinding.delivryAmount.setText("₹ " + delivery_fee);
                activityViewCartBinding.gstTxt.setText("₹ " + gst);
                activityViewCartBinding.finalPay.setText("₹ " + String.valueOf( Integer.valueOf(gst) + Integer.parseInt(activityViewCartBinding.intialAmount.getText().toString().split(" ")[1]) + Integer.valueOf(delivery_fee)));
            }
        });


        // Adding properties for PromoCodes API call
       // get_promo_codes = new JsonObject();
        get_promo_codes.addProperty("city_id", 1);
        get_promo_codes.addProperty("rest_id",selected_restrnt_id);
        get_promo_codes.addProperty("user_id", user_id);

       // Toast.makeText(View_Cart.this, "Hello "+ get_promo_codes, Toast.LENGTH_SHORT).show();


        view_cart_modelView.GetPromoCode_Dataresponse(get_promo_codes).observe(this, new Observer<PromoCodeResponse>() {
            @Override
            public void onChanged(PromoCodeResponse promoCodeResponse) {

                // Need to build the PromoCodeList here once the page loads

               promoCodeResponseList = promoCodeResponse.getPromocodes();


            }
        });

        view_cart_modelView.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

              //  Toast.makeText(getApplicationContext(),"Error:gst "+s,Toast.LENGTH_LONG).show();
            }
        });
    }



    public void startPayment() {

        Checkout start_checkout = new Checkout();

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject profile_jsonObjt = new JSONObject();
            jsonObject.put("name", "Glutton");
            jsonObject.put("descripiton", "Order payment");
            jsonObject.put("image", "https://www.dropbox.com/s/p7x77d0o7eq7b87/logo-modified.png?raw=1");
            jsonObject.put("currency", "INR");
            jsonObject.put("theme.color", "#3399cc");
            jsonObject.put("prefill.email", "gaurav.kumar@example.com");
            jsonObject.put("prefill.contact", "9988776655");
            jsonObject.put("prefill.email", "gaurav.kumar@example.com");
            jsonObject.put("prefill.contact", "9988776655");

            total = (Double.valueOf(intial_amount) + Integer.valueOf(delivery_fee));
            total = total * 100;
            jsonObject.put("amount", total);

            start_checkout.open(this, jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        payment_id = paymentData.getPaymentId();
        payment_order_id = paymentData.getOrderId();
        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, View_Cart.this);
        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.ORDERTOSERVERAFTERSUCCESSFULLTRANSCATION);

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, View_Cart.this);
        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.ORDERTOSERVERAFTERSUCCESSFULLTRANSCATION);
    }

    @Override
    public void IsConnected(Boolean isconnected, int calling_request_from) {
        if (isconnected) {

            switch (calling_request_from) {

                case Glutton_Constants.PAYMENT_TRANSCATION:

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    activityViewCartBinding.pytmprogress.setVisibility(View.VISIBLE);
                    getFoodAvldStatusforPay("Cod");

                    break;

                case Glutton_Constants.ORDERTOSERVERAFTERSUCCESSFULLTRANSCATION:

                    //startordertoserver("online");
                    break;

                case Glutton_Constants.CASHONDELIVERY:

                   // getFoodAvldStatusforPay("Cod");
                    break;
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please Enable internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getresponse(Object o) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        activityViewCartBinding.pytmprogress.setVisibility(View.VISIBLE);
        startordertoserver(o.toString());

    }

    @Override
    public void onClick(View view) {

    }
}
