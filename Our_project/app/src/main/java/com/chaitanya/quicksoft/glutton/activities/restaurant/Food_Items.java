package com.chaitanya.quicksoft.glutton.activities.restaurant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.room.DatabaseClient;
import com.chaitanya.quicksoft.glutton.utils.Glutton_Constants;
import com.chaitanya.quicksoft.glutton.room.LoginTable_entity;
import com.chaitanya.quicksoft.glutton.utils.NetworkCheck;
import com.chaitanya.quicksoft.glutton.interfaces.NetworkResponseInterface;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.utils.RestaurantUtils;
import com.chaitanya.quicksoft.glutton.adapters.CategoryItemsAdapter;
import com.chaitanya.quicksoft.glutton.adapters.RecommendedItemsAdapter;
import com.chaitanya.quicksoft.glutton.adapters.CategoryHelperAdapter;
import com.chaitanya.quicksoft.glutton.databinding.ActivityFoodItemsBinding;
import com.chaitanya.quicksoft.glutton.interfaces.food_item_click;
import com.chaitanya.quicksoft.glutton.viewModels.Food_item_viewmodel;
import com.chaitanya.response.CategoryFoodItemResponse;
import com.chaitanya.response.ItemsItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;


// Restaurant Profile Activity - Recommended and Category Items are displayed here.


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
    int user_id = 0;
    AlertDialog.Builder alertDialog, zeroCartDialog;
    int restaurant_discount = 0;

    ImageView btnClose;

    CardView searchCardLayout;

    RecyclerView foodItemsRecycler;

    CategoryItemsAdapter categoryItemsAdapter;

    RelativeLayout searchFoodLayout;
    NestedScrollView parentRelative;

    EditText editSearchFoodItemsDialog, editSearchFoodItems;

    List<CategoryFoodItemResponse.ItemsBean> productArrayList = new ArrayList<>();
    List<CategoryFoodItemResponse.ItemsBean> itemsArrayList = new ArrayList<>();

    List<String> categoryNamesList;
    List<CategoryFoodItemResponse.ItemsBean> categoryItemsList;
    List<CategoryFoodItemResponse.ItemsBean.OtherPropsBean> subCategoryItemsList;

    RecommendedItemsAdapter adapter;
    CategoryHelperAdapter itemAdapter;


    RecyclerView datalist, itemlist;


    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RelativeLayout search_info_layout;

    TextView txtVegBadge;
    Switch aSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        food_item_viewmodel = ViewModelProviders.of(this).get(Food_item_viewmodel.class);
        activityFoodItemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_food__items);

        activityFoodItemsBinding.setLifecycleOwner(this);
        activityFoodItemsBinding.setFoodItemViewmodel(food_item_viewmodel);
        networkResponseInterface = this;


        searchCardLayout = findViewById(R.id.searchcard_layout);


        parentRelative = findViewById(R.id.parentRelative);

        btnClose = findViewById(R.id.btnClose);

        // Custom Window Inflate Binding
        editSearchFoodItemsDialog = findViewById(R.id.editSearch);
        foodItemsRecycler = findViewById(R.id.search_food_items);

        searchFoodLayout = findViewById(R.id.parentLinearLayout);

        categoryNamesList = new ArrayList<>();
        txtVegBadge = (TextView) findViewById(R.id.vegBadge);

        editSearchFoodItems = (EditText) findViewById(R.id.editSearchFoodItems);

        categoryItemsList = new ArrayList<>();
        subCategoryItemsList = new ArrayList<>();
        aSwitch = findViewById(R.id.toggleswitch);

        datalist = findViewById(R.id.datalist);
        itemlist = findViewById(R.id.categoryItemlist);

        getProfileDataFromDatabase();
        intent = getIntent();
        selected_restrnt = intent.getStringExtra("selected_restrnt");
        food_item_viewmodel.restaurantAddress.set(intent.getStringExtra("restaurant_address"));
        selected_restrnt_id = intent.getIntExtra("selected_restrnt_id", 0);
        restaurant_descrp = intent.getStringExtra("restaurant_descrp");
        restaurant_discount = intent.getIntExtra("restaurant_discount",0);

        // Setting the Restaurant Discount Globally
        RestaurantUtils.setRestaurant_discount(restaurant_discount);

        activityFoodItemsBinding.selectedRestrnt.setText(selected_restrnt);
        activityFoodItemsBinding.selectedRestrntAddress.setText(restaurant_address);
        activityFoodItemsBinding.selectedRestrntDescrptn.setText(restaurant_descrp);
        recycler_model_list = new ArrayList<>();

        editSearchFoodItems.setHint("Search " + selected_restrnt);

        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Food_Items.this);
        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.LOADFOODITEMS);


        // Inflated searchview on clicking will make the Layout invisible and search Layout Visible
        editSearchFoodItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // disabling the fixed searchview edittext
                searchCardLayout.setVisibility(View.GONE);

                // showing the SearchView Layout
                searchFoodLayout.setVisibility(View.VISIBLE);

                // opening the SoftInput Keyboard automatically
                editSearchFoodItemsDialog.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editSearchFoodItemsDialog, InputMethodManager.SHOW_IMPLICIT);

                parentRelative.setEnabled(false);
                for (int i = 0; i < parentRelative.getChildCount(); i++) {
                    View view12 = parentRelative.getChildAt(i);
                    view12.setVisibility(View.GONE); // Or whatever you want to do with the view.
                }


                editSearchFoodItemsDialog.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filter(s.toString());


                    }
                });

                // closing the SearchLayout
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        searchCardLayout.setVisibility(View.VISIBLE);
                        searchFoodLayout.setVisibility(View.GONE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        for (int i = 0; i < parentRelative.getChildCount(); i++) {
                            View view12 = parentRelative.getChildAt(i);
                            view12.setVisibility(View.VISIBLE); // Or whatever you want to do with the view.
                        }

                    }
                });


                // Loading the Food Items based on searchView
                food_item_viewmodel.getLoadAllFoodItems(selected_restrnt_id).observe(Food_Items.this, new Observer<CategoryFoodItemResponse>() {
                    @Override
                    public void onChanged(CategoryFoodItemResponse categoryFoodItemResponse) {

                        if (categoryFoodItemResponse.getRest_id() != 0) {

                            categoryItemsList = categoryFoodItemResponse.getItems();

                            categoryItemsAdapter = new CategoryItemsAdapter(Food_Items.this, categoryItemsList, Food_Items.this, RestaurantUtils.getInstance());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Food_Items.this, LinearLayoutManager.VERTICAL, false);
                            foodItemsRecycler.setLayoutManager(linearLayoutManager);
                            foodItemsRecycler.setVisibility(View.INVISIBLE);
                            foodItemsRecycler.clearOnScrollListeners();
                            foodItemsRecycler.setHasFixedSize(true);
                            foodItemsRecycler.setAdapter(categoryItemsAdapter);
                            categoryItemsAdapter.notifyDataSetChanged();

                        }

                    }
                });


            }
        });

    }

    private void filter(String text) {
        ArrayList<CategoryFoodItemResponse.ItemsBean> filteredlist = new ArrayList<>();

        if (text.isEmpty() || Objects.equals(text, "")) {
            foodItemsRecycler.setVisibility(View.INVISIBLE);
        } else {

            for (CategoryFoodItemResponse.ItemsBean item : categoryItemsList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }

            categoryItemsAdapter.filterList(filteredlist);
            foodItemsRecycler.setAdapter(categoryItemsAdapter);
            categoryItemsAdapter.notifyDataSetChanged();
            foodItemsRecycler.setVisibility(View.VISIBLE);
        }

    }


    public void getFoodItems() {

        // Calls the API to load all the Items - master items List
        food_item_viewmodel.getLoadAllFoodItems(selected_restrnt_id).observe(this, new Observer<CategoryFoodItemResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(CategoryFoodItemResponse categoryFoodItemResponse) {
                if (categoryFoodItemResponse.getRest_id() != 0) {

                    categoryItemsList = categoryFoodItemResponse.getItems();

                    loadRestaurantItems(categoryItemsList);

                    recommendedRecycler(productArrayList, Food_Items.this);
                    itemsRecycler(categoryNamesList, itemsArrayList, Food_Items.this);

                    // Checking whole array list whether it contains Vegetarian
                    Boolean recommendedVeg = productArrayList.stream().allMatch(item -> item.getType().equals("Veg"));
                    Boolean itemsVeg = itemsArrayList.stream().allMatch(item -> item.getType().equals("Veg"));

                    if (recommendedVeg && itemsVeg) {
                        // showing Veg Badge

                        txtVegBadge.setVisibility(View.VISIBLE);
                    } else {
                        // showing Toggle Button
                        aSwitch.setVisibility(View.VISIBLE);
                        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {

                                    // loading Vegetarian Items Only for Recommended
                                    productArrayList.clear();
                                    itemsArrayList.clear();

                                    for (int i = 0; i < categoryItemsList.size(); i++) {

                                        if (categoryItemsList.get(i).getType().equals("Veg")) {
                                            if (categoryItemsList.get(i).getRecommended().equals("yes")) {
                                                productArrayList.add(new CategoryFoodItemResponse.ItemsBean(String.valueOf(categoryItemsList.get(i).getFood_id()), categoryItemsList.get(i).getName(), categoryItemsList.get(i).getImage(), categoryItemsList.get(i).getOriginal_price(), categoryItemsList.get(i).getStatus(), categoryItemsList.get(i).getCategory(), categoryItemsList.get(i).getPrice(), categoryItemsList.get(i).getType(), categoryItemsList.get(i).getRecommended(), categoryItemsList.get(i).getOther_props()));
                                            } else {
                                                itemsArrayList.add(new CategoryFoodItemResponse.ItemsBean(String.valueOf(categoryItemsList.get(i).getFood_id()), categoryItemsList.get(i).getName(), categoryItemsList.get(i).getImage(), categoryItemsList.get(i).getOriginal_price(), categoryItemsList.get(i).getStatus(), categoryItemsList.get(i).getCategory(), categoryItemsList.get(i).getPrice(), categoryItemsList.get(i).getType(), categoryItemsList.get(i).getRecommended(), categoryItemsList.get(i).getOther_props()));
                                            }
                                        }
                                    }

                                    // WE ARE AGAIN INITIALISING THE ADAPTER BECAUSE THE listener has BLOCK LEVEL SCOPE
                                    // initialising Adapter again by passing Array list
                                    recommendedRecycler(productArrayList, Food_Items.this);

                                    // Loading Vegetarian Items for below Items Adapter
                                    itemsRecycler(categoryNamesList, itemsArrayList, Food_Items.this);

                                }
                                if (!isChecked) {

                                    productArrayList.clear();
                                    itemsArrayList.clear();
                                    categoryNamesList.clear();
                                    loadRestaurantItems(categoryItemsList);

                                    recommendedRecycler(productArrayList, Food_Items.this);
                                    itemsRecycler(categoryNamesList, itemsArrayList, Food_Items.this);

                                }
                            }
                        });

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

    // Seperating Recommended and Non-Recommended items from the Master Arraylist
    public void loadRestaurantItems(List<CategoryFoodItemResponse.ItemsBean> categoryItemsList) {

        categoryNamesList.clear();
        productArrayList.clear();
        itemsArrayList.clear();


        for (int i = 0; i < categoryItemsList.size(); i++) {

            if (!categoryNamesList.contains(Food_Items.this.categoryItemsList.get(i).getCategory())) {
                categoryNamesList.add(Food_Items.this.categoryItemsList.get(i).getCategory());
            }
            // adding all the items from Master List to Product class

            if (categoryItemsList.get(i).getRecommended().equals("yes")) {
                // adding items to Recommended Category
                productArrayList.add(new CategoryFoodItemResponse.ItemsBean(String.valueOf(categoryItemsList.get(i).getFood_id()), categoryItemsList.get(i).getName(), categoryItemsList.get(i).getImage(), categoryItemsList.get(i).getOriginal_price(), categoryItemsList.get(i).getStatus(), categoryItemsList.get(i).getCategory(), categoryItemsList.get(i).getPrice(), categoryItemsList.get(i).getType(), categoryItemsList.get(i).getRecommended(), categoryItemsList.get(i).getOther_props()));
            } else {
                itemsArrayList.add(new CategoryFoodItemResponse.ItemsBean(String.valueOf(categoryItemsList.get(i).getFood_id()), categoryItemsList.get(i).getName(), categoryItemsList.get(i).getImage(), categoryItemsList.get(i).getOriginal_price(), categoryItemsList.get(i).getStatus(), categoryItemsList.get(i).getCategory(), categoryItemsList.get(i).getPrice(), categoryItemsList.get(i).getType(), categoryItemsList.get(i).getRecommended(), categoryItemsList.get(i).getOther_props()));
            }

        }

    }


    // Method to load Recommended Items
    public void recommendedRecycler(List<CategoryFoodItemResponse.ItemsBean> productArrayList, food_item_click food_item_click) {

        // Calling the Recommended Items Adapter by passing the Arraylist derived
        adapter = new RecommendedItemsAdapter(Food_Items.this, productArrayList, food_item_click, RestaurantUtils.getInstance());
        datalist.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Food_Items.this, 2, GridLayoutManager.VERTICAL, false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter((RecyclerView.Adapter) adapter);

        adapter.notifyDataSetChanged();

    }

    // method to load Category items -calling Categories Adapter
    public void itemsRecycler(List<String> categoryNamesList, List<CategoryFoodItemResponse.ItemsBean> itemsArrayList, food_item_click food_item_click) {

        itemAdapter = new CategoryHelperAdapter(Food_Items.this, categoryNamesList, itemsArrayList, food_item_click);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Food_Items.this, LinearLayoutManager.VERTICAL, false);
        itemlist.setLayoutManager(linearLayoutManager);
        itemlist.setAdapter((RecyclerView.Adapter) itemAdapter);

        itemAdapter.notifyDataSetChanged();


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


    // Interface method which will be called when user clicks on " + " Button from Adapter
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

                Log.d("TAG", "item already Key " + selectd_food_item_qntity);


            } else if (selectd_food_item_qntity != null && !selectd_food_item_qntity.containsKey(itemId)) {

                selectd_food_item_qntity.put(itemId, item_name + "~" + quantity);
                Log.d("TAG", "itemAdded No Key " + selectd_food_item_qntity);

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

                if (Integer.parseInt(total_price) == 0) {


                    zeroCartDialog = new AlertDialog.Builder((Food_Items.this));
                    zeroCartDialog.setMessage("Please add atleast one Item to Proceed to Cart")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog alertDialog = zeroCartDialog.create();
                    alertDialog.setTitle("Order");
                    alertDialog.show();

                } else if (Integer.parseInt(total_price) >= 99) {
                    intentMethod();

                } else {
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


    // Method called when " - " Button clicked in the Items adapter
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
                Log.d("TAG", "itemRemoved already Key " + selectd_food_item_qntity);


            } else if (selectd_food_item_qntity != null && !selectd_food_item_qntity.containsKey(itemId)) {

                selectd_food_item_qntity.put(itemId, item_name + "~" + quantity);
                Log.d("TAG", "itemRemoved Not Key Present" + selectd_food_item_qntity);

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

                    if (Integer.parseInt(total_price) == 0) {


                        zeroCartDialog = new AlertDialog.Builder((Food_Items.this));
                        zeroCartDialog.setMessage("Please add atleast one Item to Proceed to Cart")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                        AlertDialog alertDialog = zeroCartDialog.create();
                        alertDialog.setTitle("Order");
                        alertDialog.show();

                    } else if (Integer.valueOf(total_price) >= 99) {
                        intentMethod();

                    } else {
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

    public void intentMethod() {

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

        if (Integer.valueOf(total_price) != 0) {
            alertDialog = new AlertDialog.Builder(Food_Items.this);

            alertDialog.setMessage("Do you want to cancel this order")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            RestaurantUtils.quantity_hashmap.clear();
                            RestaurantUtils.restaurant_discount = 0;
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
        } else {
            finish();
        }
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }


}
