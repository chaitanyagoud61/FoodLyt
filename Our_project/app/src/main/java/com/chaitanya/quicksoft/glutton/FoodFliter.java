package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class FoodFliter extends Filter {

    public Context context;
    Food_Item_Adapter food_item_adapter;
    List<Food_item_row_model> model_list = new ArrayList<>();
    public FoodFliter(Food_Item_Adapter food_item_adapter,List<Food_item_row_model> model_list) {
        this.food_item_adapter = food_item_adapter;
        this.model_list = model_list;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults food_filter_results = new FilterResults();
        if(model_list!=null && model_list.size()>0 && charSequence!=null && charSequence.toString().trim().length()>0){
            List<Food_item_row_model> filter_list = new ArrayList<>();

            for (Food_item_row_model single_model_object : model_list) {
                if(single_model_object.getFood_name().toLowerCase().contains(charSequence.toString().toLowerCase())){

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
        food_item_adapter.model_list = (ArrayList<Food_item_row_model>) filterResults.values;
        food_item_adapter.notifyDataSetChanged();
    }
}
