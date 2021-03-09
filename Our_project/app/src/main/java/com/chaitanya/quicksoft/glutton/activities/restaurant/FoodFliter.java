package com.chaitanya.quicksoft.glutton.activities.restaurant;

import android.content.Context;
import android.widget.Filter;

import com.chaitanya.quicksoft.glutton.adapters.CategoryItemsAdapter;
import com.chaitanya.response.CategoryFoodItemResponse;

import java.util.ArrayList;
import java.util.List;

public class FoodFliter extends Filter {

    public Context context;
    CategoryItemsAdapter food_item_adapter;
    List<CategoryFoodItemResponse.ItemsBean> model_list = new ArrayList<>();
    public FoodFliter(CategoryItemsAdapter food_item_adapter, List<CategoryFoodItemResponse.ItemsBean> model_list) {
        this.food_item_adapter = food_item_adapter;
        this.model_list = model_list;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults food_filter_results = new FilterResults();
        if(model_list!=null && model_list.size()>0 && charSequence!=null && charSequence.toString().trim().length()>0){
            List<CategoryFoodItemResponse.ItemsBean> filter_list = new ArrayList<>();

            for (CategoryFoodItemResponse.ItemsBean single_model_object : model_list) {
                if(single_model_object.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){

                    filter_list.add(single_model_object);
                }
            }
            food_filter_results.count = filter_list.size();
            food_filter_results.values = filter_list;
            return food_filter_results;
        }

        food_filter_results.count = model_list.size();
        food_filter_results.values = model_list;
        return food_filter_results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        food_item_adapter.itemsModelList = (ArrayList<CategoryFoodItemResponse.ItemsBean>) filterResults.values;
        food_item_adapter.notifyDataSetChanged();
    }
}
