package com.chaitanya.quicksoft.glutton.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.activities.restaurant.Selected_food_view_model;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCartAdapter extends RecyclerView.Adapter<ViewCartAdapter.ViewCartAdapterViewHolder> {
    public Context context;
    ArrayList<Selected_food_view_model> selected_food_view_modelArrayList = new ArrayList<>();
    public ViewCartAdapter(Context context1,ArrayList<Selected_food_view_model> selectedFoodViewModelArrayList) {
        this.context = context1;
        this.selected_food_view_modelArrayList = selectedFoodViewModelArrayList;
    }

    @Override
    public ViewCartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_food_items_row_item,null);
        return new ViewCartAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCartAdapterViewHolder holder, int position) {

        holder.selected_amount.setText("X  "+selected_food_view_modelArrayList.get(position).getselected_food_quantity());
        holder.selected_food_item.setText(selected_food_view_modelArrayList.get(position).getSelected_food());
    }

    @Override
    public int getItemCount() {
        return selected_food_view_modelArrayList.size();
    }

    class ViewCartAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView selected_food_item,selected_amount;
        public ViewCartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            selected_amount = itemView.findViewById(R.id.selected_amount);
            selected_food_item = itemView.findViewById(R.id.selected_food_item);
        }
    }
}
