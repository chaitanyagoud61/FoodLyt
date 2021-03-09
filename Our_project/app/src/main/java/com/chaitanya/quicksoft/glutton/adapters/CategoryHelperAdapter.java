package com.chaitanya.quicksoft.glutton.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.quicksoft.glutton.activities.restaurant.Food_Items;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.utils.RestaurantUtils;
import com.chaitanya.quicksoft.glutton.interfaces.food_item_click;
import com.chaitanya.response.CategoryFoodItemResponse;


import java.util.ArrayList;
import java.util.List;

public class CategoryHelperAdapter extends RecyclerView.Adapter<CategoryHelperAdapter.ViewHolder> implements Filterable {


    private Food_Items context;
    private food_item_click food_item_click;
    private List<String> categoryNamesList;
    List<CategoryFoodItemResponse.ItemsBean> categoryItemsList = new ArrayList<>();



    public CategoryHelperAdapter(Food_Items mContext, List<String> categoryNamesList, List<CategoryFoodItemResponse.ItemsBean> categoryItemsList, food_item_click food_item_click1) {

        this.context = mContext;
        this.categoryNamesList = categoryNamesList;
        this.categoryItemsList = categoryItemsList;
        this.food_item_click = food_item_click1;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_category_card_design, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        List<CategoryFoodItemResponse.ItemsBean> categoryInsideItems = new ArrayList<>();

        // for sending the Items in the particular Category
        for(int i=0;i<categoryItemsList.size();i++) {
            if(categoryItemsList.get(i).getCategory().equals(categoryNamesList.get(position)) ) {
                categoryInsideItems.add(categoryItemsList.get(i));
            }
        }

        if(categoryInsideItems.size() > 0) {
            holder.categoryName.setText(categoryNamesList.get(position));
        } else {
            holder.categoryName.setVisibility(View.GONE);
            holder.categoryItemsList.setVisibility(View.GONE);
            holder.viewSpace.setVisibility(View.GONE);
        }

        //Initialize Adapter
        CategoryItemsAdapter categoryCardDesignAdapter = new CategoryItemsAdapter(context,categoryInsideItems,food_item_click, RestaurantUtils.getInstance());

        //Initialize Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        holder.categoryItemsList.setLayoutManager(linearLayoutManager);

        //Set Adapter
        holder.categoryItemsList.setAdapter(categoryCardDesignAdapter);

    }

    @Override
    public int getItemCount() {
        return categoryNamesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        RecyclerView categoryItemsList;
        View viewSpace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryItemsList = itemView.findViewById(R.id.categoryItemlist);
            viewSpace = itemView.findViewById(R.id.space);

        }
    }
}
