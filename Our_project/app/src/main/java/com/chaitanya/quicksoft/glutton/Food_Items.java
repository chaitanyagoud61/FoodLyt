package com.chaitanya.quicksoft.glutton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.databinding.ActivityFoodItemsBinding;
import com.chaitanya.quicksoft.glutton.viewModels.Food_item_viewmodel;
import com.chaitanya.response.FoodItemResponse;
import com.chaitanya.response.ItemsItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Food_Items extends AppCompatActivity implements food_item_click, NetworkResponseInterface {

    Food_item_row_model foodItemRowModel;
    ArrayList<Food_item_row_model> recycler_model_list;
    Food_Item_Adapter food_item_adapter;
    Food_item_viewmodel food_item_viewmodel;
    ActivityFoodItemsBinding activityFoodItemsBinding;
    Intent intent;
    String selected_restrnt = "", restaurant_address = "";
    int selected_restrnt_id;
    Snackbar snackbar;
    String total_price = "0", restaurant_descrp = "";
    HashMap<String, String> selectd_food_item_qntity = new HashMap<>();
    HashMap<String, String> final_selected_food_item_qntity = new HashMap<>();
    List<ItemsItem> itemsItemList = new ArrayList<>();
    NetworkCheck networkCheck;
    NetworkResponseInterface networkResponseInterface;
    ConnectivityManager connectivityManager;
    String name = "", address = "", mobile = "", email = "";
    int user_id=0;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        food_item_viewmodel = ViewModelProviders.of(this).get(Food_item_viewmodel.class);
        activityFoodItemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_food__items);

        activityFoodItemsBinding.setLifecycleOwner(this);
        activityFoodItemsBinding.setFoodItemViewmodel(food_item_viewmodel);
        networkResponseInterface = this;

        getProfileDataFromDatabase();
        intent = getIntent();
        selected_restrnt = intent.getStringExtra("selected_restrnt");
        food_item_viewmodel.restaurantAddress.set(intent.getStringExtra("restaurant_address"));
        selected_restrnt_id = intent.getIntExtra("selected_restrnt_id", 0);
        restaurant_descrp = intent.getStringExtra("restaurant_descrp");
        activityFoodItemsBinding.selectedRestrnt.setText(selected_restrnt);
        activityFoodItemsBinding.selectedRestrntAddress.setText(restaurant_address);
        activityFoodItemsBinding.selectedRestrntDescrptn.setText(restaurant_descrp);
        recycler_model_list = new ArrayList<>();

        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Food_Items.this);
        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.LOADFOODITEMS);

        activityFoodItemsBinding.foodSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (food_item_adapter != null) {

                    food_item_adapter.getFilter().filter(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (food_item_adapter != null) {

                    food_item_adapter.getFilter().filter(s);
                }
                return false;
            }
        });

    }

    public void getFoodItems() {

        food_item_viewmodel.getFood_item_mutableLiveData(selected_restrnt_id).observe(this, new Observer<FoodItemResponse>() {
            @Override
            public void onChanged(FoodItemResponse foodItemResponse) {

                if (foodItemResponse.getRestId() != 0) {

                    itemsItemList = foodItemResponse.getItems();
                    if (itemsItemList != null && itemsItemList.size() > 0) {

                        for (ItemsItem items : itemsItemList) {

                            foodItemRowModel = new Food_item_row_model(items.getPrice(), items.getName(), items.getStatus(), items.getImage(), items.getFoodId(), items.getCategory());
                            recycler_model_list.add(foodItemRowModel);
                        }
                        food_item_adapter = new Food_Item_Adapter(Food_Items.this, recycler_model_list, Food_Items.this);
                        activityFoodItemsBinding.foodItemRecylr.setItemViewCacheSize(recycler_model_list.size());
                        activityFoodItemsBinding.foodItemRecylr.setAdapter(food_item_adapter);

                    }
                }
            }
        });

        food_item_viewmodel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

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
                email = loginTable_entity.getEmail();
                address = loginTable_entity.getAddress();
                mobile = loginTable_entity.getMobilenumber();
                user_id = loginTable_entity.getUserId();
            }
        }
        GetUserprofileData getUserprofileData = new GetUserprofileData();
        getUserprofileData.execute();
    }

    @Override
    public void add_selected_food_item_click(String selected_item_price, String itemId, int quantity, String item_name) {

        if (!total_price.isEmpty() && !selected_item_price.isEmpty()) {
            String selected_quantity = "", selected_food_item = "";
            total_price = String.valueOf(Integer.valueOf(total_price) + Integer.valueOf(selected_item_price));
            if (selectd_food_item_qntity != null && selectd_food_item_qntity.containsKey(itemId)) {
                String previous_qntity_and_name = selectd_food_item_qntity.get(itemId);
                String[] selected_data = previous_qntity_and_name.split("~");
                selected_food_item = selected_data[0];
                selected_quantity = selected_data[1];
                selected_quantity = String.valueOf(quantity);
                previous_qntity_and_name = selected_food_item + "~" + selected_quantity;
                selectd_food_item_qntity.put(itemId, previous_qntity_and_name);


            } else if (selectd_food_item_qntity != null && !selectd_food_item_qntity.containsKey(itemId)) {

                selectd_food_item_qntity.put(itemId, item_name + "~" + quantity);
            }
        }

        final Snackbar snackbar = Snackbar.make(activityFoodItemsBinding.foodItemCoordnteLyt, "", Snackbar.LENGTH_LONG);

        View customSnackView = getLayoutInflater().inflate(R.layout.customsnackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        snackbar.setDuration(60000);

        Button bGotoWebsite = customSnackView.findViewById(R.id.gotoWebsiteButton);
        TextView price_txt = customSnackView.findViewById(R.id.snckbr_price);
        price_txt.setText(String.valueOf("₹ " + total_price));

        bGotoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.valueOf(total_price) >= 99) {
                    intentMethod();

                }
                else {
                    alertDialog = new AlertDialog.Builder(Food_Items.this);

                    alertDialog.setMessage("Extra delivery charges will be applied on cart value less than ₹ 99")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    intentMethod();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = alertDialog.create();
                    alert.setTitle("Order");
                    alert.show();
                }
            }
        });


        snackbarLayout.addView(customSnackView, 0);

        snackbar.show();
    }


    @Override
    public void minus_selected_food_item_click(String selected_item_price, String itemId, int quantity, String item_name) {

        if (!total_price.isEmpty() && !selected_item_price.isEmpty()) {
            String selected_quantity = "", selected_food_item = "";
            total_price = String.valueOf(Integer.valueOf(total_price) - Integer.valueOf(selected_item_price));
            if (selectd_food_item_qntity != null && selectd_food_item_qntity.containsKey(itemId)) {
                String previous_qntity_and_name = selectd_food_item_qntity.get(itemId);
                String[] selected_data = previous_qntity_and_name.split("~");
                selected_food_item = selected_data[0];
                selected_quantity = selected_data[1];
                selected_quantity = String.valueOf(quantity);
                previous_qntity_and_name = selected_food_item + "~" + selected_quantity;
                selectd_food_item_qntity.put(itemId, previous_qntity_and_name);

            } else if (selectd_food_item_qntity != null && !selectd_food_item_qntity.containsKey(itemId)) {

                selectd_food_item_qntity.put(itemId, item_name + "~" + quantity);

            }


            final Snackbar snackbar = Snackbar.make(activityFoodItemsBinding.foodItemCoordnteLyt, "", Snackbar.LENGTH_LONG);

            View customSnackView = getLayoutInflater().inflate(R.layout.customsnackbar, null);
            snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
            Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
            snackbarLayout.setPadding(0, 0, 0, 0);
            snackbar.setDuration(60000);
            Button bGotoWebsite = customSnackView.findViewById(R.id.gotoWebsiteButton);
            TextView price_txt = customSnackView.findViewById(R.id.snckbr_price);
            price_txt.setText(String.valueOf("₹ " + total_price));

            bGotoWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Integer.valueOf(total_price) >= 99 ) {
                        intentMethod();

                    }else {
                        alertDialog = new AlertDialog.Builder(Food_Items.this);

                        alertDialog.setMessage("Extra delivery charges will be applied on cart value less than ₹ 99")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        intentMethod();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert = alertDialog.create();
                        alert.setTitle("Order");
                        alert.show();
                    }
                }
            });


            snackbarLayout.addView(customSnackView, 0);

            snackbar.show();


        }

    }

    public void intentMethod(){

        final_selected_food_item_qntity = new HashMap<>();
        Set<String> keys = selectd_food_item_qntity.keySet();
        for (String key : keys) {
            String selected_quantity = "", selected_food_item = "";
            String previous_qntity_and_name = selectd_food_item_qntity.get(key);
            String[] selected_data = previous_qntity_and_name.split("~");
            selected_food_item = selected_data[0];
            selected_quantity = selected_data[1];
            int item_qnty = Integer.valueOf(selected_quantity);
            if (item_qnty != 0 && item_qnty > 0) {
                final_selected_food_item_qntity.put(key, previous_qntity_and_name);
            }
        }
        if (final_selected_food_item_qntity.size() != 0 && final_selected_food_item_qntity.size() > 0) {

            Intent intent = new Intent(Food_Items.this, View_Cart.class);
            intent.putExtra("map", final_selected_food_item_qntity);
            intent.putExtra("selected_restrnt", selected_restrnt);
            intent.putExtra("selected_restrnt_id", String.valueOf(selected_restrnt_id));
            intent.putExtra("restaurant_address", food_item_viewmodel.restaurantAddress.get());
            intent.putExtra("intial_amount", total_price);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
            intent.putExtra("mobile", mobile);
            intent.putExtra("email", email);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        }

    }

    @Override
    public void IsConnected(Boolean isconnected, int calling_request_from) {
        if (isconnected) {

            switch (calling_request_from) {

                case Glutton_Constants.LOADFOODITEMS:

                    getFoodItems();

                    break;

            }

        } else {
            Toast.makeText(getApplicationContext(), "Please Enable internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        if(Integer.valueOf(total_price)!=0) {
            alertDialog = new AlertDialog.Builder(Food_Items.this);

            alertDialog.setMessage("Do you want to cancel this order")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = alertDialog.create();
            alert.setTitle("Cancel Order");
            alert.show();
        }else {
            finish();
        }
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }
}
