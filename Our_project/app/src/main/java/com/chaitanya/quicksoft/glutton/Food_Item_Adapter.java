package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Food_Item_Adapter extends RecyclerView.Adapter<Food_Item_Adapter.Food_item_ViewHolder> implements Filterable {
    private Context context;
    Food_item_row_model foodItemRowModel;
    ArrayList<Food_item_row_model> model_list = new ArrayList<>();
    HashMap<Integer,String> quantity_hashmap = new HashMap<>();
    food_item_click food_item_click;
    int quantity=0;
    FoodFliter foodFliter;
    HashMap<String,Integer> data_map = new HashMap();

    public Food_Item_Adapter(Context context,ArrayList<Food_item_row_model> food_item_row_model_list,food_item_click food_item_click1) {
        this.context = context;
        this.model_list = food_item_row_model_list;
        this.food_item_click = food_item_click1;
    }

    @NonNull
    @Override
    public Food_item_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row_item, null);

        return new Food_Item_Adapter.Food_item_ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(Food_item_ViewHolder holder, int position) {
        if(model_list.get(position).getFood_category().equalsIgnoreCase("Non-Veg")){
            holder.food_avld_or_not.setImageResource(R.drawable.ic_redcircle);
        }else if(model_list.get(position).getFood_category().equalsIgnoreCase("Veg")){
            holder.food_avld_or_not.setImageResource(R.drawable.ic_greencircle);
        }
        if(data_map.containsKey(model_list.get(position).getFood_item_id())){
            holder.item_quanty.setText(String.valueOf(data_map.get(model_list.get(position).getFood_item_id())));
        }else {
            holder.item_quanty.setText("0");
        }
        holder.item_price.setText(model_list.get(position).getPrice());
        holder.food_name.setText(model_list.get(position).getFood_name());
        Glide.with(context).load(model_list.get(position).getFood_image()).into(holder.rstrnt_food_image);
        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // quantity = Integer.valueOf(holder.item_quanty.getText().toString());
                if(data_map.containsKey(model_list.get(position).getFood_item_id())){
                   quantity = data_map.get(model_list.get(position).getFood_item_id());
                }else {
                    quantity = 0;
                }
                quantity++;

                if(data_map.containsKey(model_list.get(position).getFood_item_id())){
                    data_map.put(model_list.get(position).getFood_item_id(),quantity);
                }else {
                    data_map.put(model_list.get(position).getFood_item_id(),quantity);
                }

                holder.item_quanty.setText(String.valueOf(quantity));
                holder.add_food_item_selected(model_list.get(position).getPrice(),String.valueOf(model_list.get(position).getFood_item_id()),quantity,model_list.get(position).getFood_name());
            }
        });
        holder.minus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // quantity = Integer.valueOf(holder.item_quanty.getText().toString());
                if(data_map.containsKey(model_list.get(position).getFood_item_id())){
                    quantity = data_map.get(model_list.get(position).getFood_item_id());
                }else {
                    quantity = 0;
                }
                quantity--;

                if(quantity>=0) {
                    if(data_map.containsKey(model_list.get(position).getFood_item_id())){
                        data_map.put(model_list.get(position).getFood_item_id(),quantity);
                    }else {

                    }
                    holder.item_quanty.setText(String.valueOf(quantity));
                    holder.minus_food_item_selected(model_list.get(position).getPrice(),String.valueOf(model_list.get(position).getFood_item_id()),quantity,model_list.get(position).getFood_name());

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return model_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public Filter getFilter() {
        if(foodFliter==null){

            foodFliter = new FoodFliter(this,model_list);
        }
        return foodFliter;
    }


    public class Food_item_ViewHolder extends RecyclerView.ViewHolder {

        ImageView food_avld_or_not,rstrnt_food_image;
        TextView food_name,item_price,add_item,minus_item,item_quanty;
        public Food_item_ViewHolder(View itemView) {
            super(itemView);
            food_avld_or_not = itemView.findViewById(R.id.food_avld_or_not);
            rstrnt_food_image = itemView.findViewById(R.id.rstrnt_food_image);
            food_name = itemView.findViewById(R.id.food_name);
            item_price = itemView.findViewById(R.id.item_price);
            add_item = itemView.findViewById(R.id.add_item);
            minus_item = itemView.findViewById(R.id.minus_item);
            item_quanty = itemView.findViewById(R.id.item_quanty);

        }
        public void add_food_item_selected(final String item_price,final String itemId,final int quantity,String item_name){
            food_item_click.add_selected_food_item_click(item_price,itemId,quantity,item_name);

        }
        public void minus_food_item_selected(final String item_price,final String itemId,final int quantity,String item_name){
            food_item_click.minus_selected_food_item_click(item_price,itemId,quantity,item_name);

        }
    }
}
