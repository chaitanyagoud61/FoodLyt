package com.chaitanya.quicksoft.glutton.activities.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.adapters.CategoryItemsAdapter;
import com.chaitanya.quicksoft.glutton.viewModels.Food_item_viewmodel;
import com.chaitanya.response.CategoryFoodItemResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    EditText edtSearchFoodItems;
    Food_item_viewmodel food_item_viewmodel;
    RecyclerView foodItemsRecycler;
    CategoryItemsAdapter categoryItemsAdapter;

    int selectedRestaurant;

    List<CategoryFoodItemResponse.ItemsBean> categoryFoodItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        food_item_viewmodel = ViewModelProviders.of(this).get(Food_item_viewmodel.class);
        categoryFoodItems = new ArrayList<>();

        foodItemsRecycler = findViewById(R.id.search_food_items);

        edtSearchFoodItems = findViewById(R.id.editSearch);
        edtSearchFoodItems.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtSearchFoodItems, InputMethodManager.SHOW_IMPLICIT);


        selectedRestaurant = getIntent().getIntExtra("restaurantID", 0);

        food_item_viewmodel.getLoadAllFoodItems(selectedRestaurant).observe(this, new Observer<CategoryFoodItemResponse>() {
            @Override
            public void onChanged(CategoryFoodItemResponse categoryFoodItemResponse) {

                if (categoryFoodItemResponse.getRest_id()!=0) {

                   /* categoryFoodItems = categoryFoodItemResponse.getItems();

                    //categoryItemsAdapter = new CategoryItemsAdapter(getApplicationContext(), categoryFoodItems, SearchResultsActivity.this);
                    foodItemsRecycler.setHasFixedSize(true);
                    foodItemsRecycler.setAdapter(categoryItemsAdapter);
*/
                }

            }
        });










        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);




    }
}