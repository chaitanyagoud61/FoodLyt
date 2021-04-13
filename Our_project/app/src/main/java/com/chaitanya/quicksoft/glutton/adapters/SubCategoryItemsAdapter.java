package com.chaitanya.quicksoft.glutton.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.quicksoft.glutton.activities.restaurant.FoodFliter;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.utils.RestaurantUtils;
import com.chaitanya.quicksoft.glutton.interfaces.Sub_Category_Quantity;
import com.chaitanya.quicksoft.glutton.interfaces.food_item_click;
import com.chaitanya.response.CategoryFoodItemResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryItemsAdapter extends RecyclerView.Adapter<SubCategoryItemsAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    public List<CategoryFoodItemResponse.ItemsBean.OtherPropsBean> itemsModelList;
    public String parentItemFoodId;
    private food_item_click food_item_click;
    private Sub_Category_Quantity sub_category_quantity;
    LayoutInflater inflater;
    public HashMap<String, Integer> quantity_hashmap, master_hashmap;

    int quantity=0;



    FoodFliter foodFliter;
    HashMap<String,Integer> data_map = new HashMap();
    String foodType;
    String parentItemName;


    public SubCategoryItemsAdapter(Context mContext, List<CategoryFoodItemResponse.ItemsBean.OtherPropsBean> subCategoryItemsModelList, String parentItemFoodId, food_item_click food_item_click1, String foodType, Sub_Category_Quantity sub_category_quantity1, HashMap<String, Integer> quantity_hashmap, String parentItemName) {
        this.mContext = mContext;
        this.itemsModelList = subCategoryItemsModelList;
        this.parentItemFoodId = parentItemFoodId;
        this.food_item_click = food_item_click1;
        this.foodType = foodType;
        this.sub_category_quantity = sub_category_quantity1;
        this.quantity_hashmap = quantity_hashmap;
        this.parentItemName = parentItemName;
    }

    @NonNull
    @Override
    public SubCategoryItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_design, parent, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull SubCategoryItemsAdapter.ViewHolder holder, int position) {

        final CategoryFoodItemResponse.ItemsBean.OtherPropsBean categoryItemsModel = itemsModelList.get(position);

        if(foodType.equals("Veg")){
            holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg,0,0,0);
        }
        else{
            holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_non_veg,0,0,0);
        }

        master_hashmap = RestaurantUtils.getQuantityHashMap();


        holder.title.setText(categoryItemsModel.getName());
        holder.price.setText("₹ "+categoryItemsModel.getPrice());

        int discount_value = RestaurantUtils.getRestaurant_discount();

        if(discount_value!=0) {

            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.txtDiscountPercentage.setVisibility(View.VISIBLE);
            holder.txtDiscountedPrice.setVisibility(View.VISIBLE);


            int reduced_price = (categoryItemsModel.getPrice() - (categoryItemsModel.getPrice() * discount_value/100));
            holder.txtDiscountedPrice.setText("₹ " + reduced_price);
          //  holder.txtDiscountPercentage.setText(discount_value + "% Off");
            holder.txtDiscountPercentage.setVisibility(View.GONE);
        }


        if(!quantity_hashmap.isEmpty()) {

            if(quantity_hashmap.get(parentItemName + " " + categoryItemsModel.getName()) == null) {
                holder.productQuantity.setText("0");
           } else {
                holder.productQuantity.setText(String.valueOf(quantity_hashmap.get(parentItemName+ " " + categoryItemsModel.getName())));
            }

        } else {

            if(data_map.containsKey(parentItemName+" "+categoryItemsModel.getName())){
                holder.productQuantity.setText(String.valueOf(data_map.get(parentItemName+" "+categoryItemsModel.getName())));
            }else {
                holder.productQuantity.setText("0");
            }

        }


        holder.productPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*
                if(data_map.containsKey(categoryItemsModel.getName())){
                    quantity = data_map.get(categoryItemsModel.getName());
                }else {
                    quantity = 0;
                }
*/
                quantity = Integer.valueOf(holder.productQuantity.getText().toString());

                sendQuantityUpdate("Add");

                quantity++;

                if(data_map.containsKey(parentItemName+" "+categoryItemsModel.getName())){
                    data_map.put(String.valueOf(parentItemName+" "+categoryItemsModel.getName()),quantity);
                }else {
                    data_map.put(String.valueOf(parentItemName+ " "+ categoryItemsModel.getName()),quantity);
                }

                holder.productQuantity.setText(String.valueOf(quantity));

                //quantity_hashmap.put(categoryItemsModel.getName(), quantity);
                //Storing the quantity of Item selected
                holder.itemAdded(parentItemName +" "+ categoryItemsModel.getName(), quantity);

                //Log.d("TAG", "itemAddedinAdapter "+ parentItemFoodId + 00 + (position+1));



                int final_price = 0;
                if(RestaurantUtils.getRestaurant_discount()!=0) {
                    String[] discount = holder.txtDiscountedPrice.getText().toString().split(" ");
                    final_price = Integer.parseInt(discount[1]);
                } else {
                    final_price = itemsModelList.get(position).getPrice();
                }

                // NOTE: Here due to lack of IDs present in other_props array we fetched from parent and appending to parentID + 00 + position.
                // For example parentItemID is 1 then here new Item ID will be 1001. [DEPRECATED]

                // Date: 12/04/2021 Lalith added IDs to Sub Items to avoid problems on Server Side

                holder.add_food_item_selected(String.valueOf(final_price), String.valueOf(categoryItemsModel.getFood_id()),quantity,parentItemName +" "+itemsModelList.get(position).getName());
            }
        });

        holder.productMinus.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                quantity = Integer.valueOf(holder.productQuantity.getText().toString());

                sendQuantityUpdate("Sub");


                quantity--;



                if (quantity == 0) {
                   // sendQuantityUpdate();

                    data_map.remove(parentItemName+" "+categoryItemsModel.getName());
                }





                if(quantity>=0) {
                    holder.productQuantity.setText(String.valueOf(quantity));


                    //quantity_hashmap.put(categoryItemsModel.getName(),quantity);
                    //Updating the quantity of Item removed

                    holder.itemRemoved(parentItemName + " "+ categoryItemsModel.getName(), quantity);

                    Log.d("TAG B", String.valueOf(master_hashmap));



                    data_map.put(parentItemName+ " " + categoryItemsModel.getName(), quantity);

                    int final_price = 0;
                    if(RestaurantUtils.getRestaurant_discount()!=0) {
                        String[] discount = holder.txtDiscountedPrice.getText().toString().split(" ");
                        final_price = Integer.parseInt(discount[1]);
                    } else {
                        final_price = itemsModelList.get(position).getPrice();
                    }


                    // NOTE: Here due to lack of IDs present in other_props array we fetched from parent and appending to parentID + 00 + position.
                    // For example parentItemID is 1 then here new Item ID will be 1001.

                    // Date: 12/04/2021 Lalith added IDs to Sub Items to avoid problems on Server Side

                    holder.minus_food_item_selected(String.valueOf(final_price),String.valueOf(categoryItemsModel.getFood_id()),quantity,parentItemName+ " " +itemsModelList.get(position).getName());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsModelList.size();
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
        /*if(foodFliter==null){

            foodFliter = new FoodFliter(this,itemsModelList);
        }*/
        return foodFliter;
    }

    public void sendQuantityUpdate(String op) {

        int itemQuantityAdded = 0;

        for(Map.Entry<String, Integer> mapElement: master_hashmap.entrySet()) {

            String itemName = (String) mapElement.getKey();

            if(itemName.contains(parentItemName)) {
                itemQuantityAdded += ((int) master_hashmap.get(itemName));
            }

        }

        Log.d("TAG", itemQuantityAdded + " Hashmap " + String.valueOf(master_hashmap));



        Intent intent = new Intent("quantity-notify");
        if(op.equals("Add")) {
            intent.putExtra("itemSize",itemQuantityAdded + 1);
        } else if(op.equals("Sub") && itemQuantityAdded!=0){
            intent.putExtra("itemSize",itemQuantityAdded - 1);
        } else {
            intent.putExtra("itemSize", 0);
        }
        intent.putExtra("parentItem", parentItemName);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button addItem;
        TextView title, price;
        TextView productMinus,productPlus,productQuantity,txtDiscountedPrice,txtDiscountPercentage;
        LinearLayout addButtonLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.price);
            productMinus= itemView.findViewById(R.id.product_minus);
            productPlus= itemView.findViewById(R.id.product_plus);
            productQuantity= itemView.findViewById(R.id.product_quantity);
            addButtonLayout = itemView.findViewById(R.id.quantityLayout);
            txtDiscountedPrice = itemView.findViewById(R.id.reduced_price);
            txtDiscountPercentage = itemView.findViewById(R.id.discount_val);


        }

        public void add_food_item_selected(final String item_price,final String itemId,final int quantity,String item_name){
            food_item_click.add_selected_food_item_click(item_price,itemId,quantity,item_name);

        }
        public void minus_food_item_selected(final String item_price,final String itemId,final int quantity,String item_name){
            food_item_click.minus_selected_food_item_click(item_price,itemId,quantity,item_name);

        }

        public void itemAdded(final String food_name, final int quantity) {
            sub_category_quantity.itemAdded(food_name,quantity);
        }

        public void itemRemoved(final String food_name, final int quantity) {
            sub_category_quantity.itemRemoved(food_name, quantity);
        }
    }
}
