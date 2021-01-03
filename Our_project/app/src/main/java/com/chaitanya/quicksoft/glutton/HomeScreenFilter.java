package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenFilter extends Filter {

    public Context context;
    public VillageListRecyclerCustomAdapter adapter;
    public List<VillageConfigCustomAdapterModel> model_list ;

    public HomeScreenFilter(VillageListRecyclerCustomAdapter adapter, List<VillageConfigCustomAdapterModel> model_list) {
        this.context = context;
        this.adapter = adapter;
        this.model_list = model_list;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults village_filter_results = new FilterResults();
        if(model_list!=null && model_list.size()>0 && charSequence!=null && charSequence.toString().trim().length()>0){
            List<VillageConfigCustomAdapterModel> filter_list = new ArrayList<>();

            for (VillageConfigCustomAdapterModel single_model_object : model_list) {
                if(single_model_object.getRestaurant_name().toLowerCase().contains(charSequence.toString().toLowerCase())){

                    filter_list.add(single_model_object);
                }
            }
            village_filter_results.count = filter_list.size();
            village_filter_results.values = filter_list;
            return village_filter_results;
        }

        village_filter_results.count = model_list.size();
        village_filter_results.values = model_list;
        return village_filter_results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.model_list = (ArrayList<VillageConfigCustomAdapterModel>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
