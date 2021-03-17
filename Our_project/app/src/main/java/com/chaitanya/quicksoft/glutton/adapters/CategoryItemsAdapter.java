package com.chaitanya.quicksoft.glutton.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.quicksoft.glutton.activities.restaurant.FoodFliter;
import com.chaitanya.quicksoft.glutton.activities.restaurant.Food_Items;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.utils.RestaurantUtils;
import com.chaitanya.quicksoft.glutton.interfaces.Sub_Category_Quantity;
import com.chaitanya.quicksoft.glutton.interfaces.food_item_click;
import com.chaitanya.response.CategoryFoodItemResponse;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.ViewHolder>  {

    private Food_Items mContext;
    public List<CategoryFoodItemResponse.ItemsBean> itemsModelList;
    private food_item_click food_item_click;
    LayoutInflater inflater;

    private Sub_Category_Quantity sub_category_quantity;

    //HashMap<String, Integer> quantity_hashmap = new HashMap<String, Integer>();
    HashMap<String, Integer> new_quantity_hasmap = new HashMap<>();
    HashMap<String, Integer> total_items_quantity_hashmap = new HashMap<>();

   // BottomSheetDialogInterface bottomSheetDialogInterface;

    int quantity=0;

    BroadcastReceiver broadcastReceiver;


    FoodFliter foodFliter;
    HashMap<String,Integer> data_map = new HashMap();


    public CategoryItemsAdapter(Food_Items mContext, List<CategoryFoodItemResponse.ItemsBean> itemsModelList, food_item_click food_item_click1, Sub_Category_Quantity sub_category_quantity ) {
        this.mContext = mContext;
        this.itemsModelList = itemsModelList;
        this.food_item_click = food_item_click1;
        this.sub_category_quantity = sub_category_quantity;
    }



    @NonNull
    @Override
    public CategoryItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_design, parent, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CategoryItemsAdapter.ViewHolder holder, int position) {

        final CategoryFoodItemResponse.ItemsBean categoryItemsModel = itemsModelList.get(position);

        if (position == (itemsModelList.size() -1)) {
            holder.lastDivider.setVisibility(View.GONE);
        }

        if(categoryItemsModel.getType().equals("Veg")){
            holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg,0,0,0);
        }
        else{
            holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_non_veg,0,0,0);
        }

        holder.title.setText(categoryItemsModel.getName());
        holder.price.setText("₹ "+categoryItemsModel.getPrice());
        int discount_value = RestaurantUtils.getRestaurant_discount();

        if(discount_value!=0) {

            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.txtDiscountPercentage.setVisibility(View.VISIBLE);
            holder.txtDiscountedPrice.setVisibility(View.VISIBLE);

            int reduced_price = (categoryItemsModel.getPrice() - (categoryItemsModel.getPrice() * discount_value/100));

            holder.txtDiscountedPrice.setText("₹ " + reduced_price);
            holder.txtDiscountPercentage.setText(discount_value + "% Off");
        }

        if(!categoryItemsModel.getOther_props().isEmpty()) {

             broadcastReceiver = new BroadcastReceiver() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        int itemSize = intent.getIntExtra("itemSize", 0);
                        String parentItem = intent.getStringExtra("parentItem");

                            if(holder.title.getText().equals(parentItem)) {
                                if(itemSize == 0) {
                                    holder.subProductQuantity.setText("ADD");
                                } else  {
                                    holder.subProductQuantity.setText("ADDED (" + String.valueOf(intent.getIntExtra("itemSize",0)) + ") Items");
                                }
                            }

                    }
                };

                LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiver,
                        new IntentFilter("quantity-notify"));


            // Sub Category Items are found - Enable Customisable Layout in XML
            holder.addButtonLayout.setVisibility(View.GONE);
            holder.subCategoryAddButtonLayout.setVisibility(View.VISIBLE);

            HashMap<String, Integer> master_hashmap = RestaurantUtils.getQuantityHashMap();

            int itemQuantityAdded = 0;
            for(Map.Entry<String, Integer> mapElement: master_hashmap.entrySet()) {
                String itemName = (String) mapElement.getKey();
                if(itemName.contains(categoryItemsModel.getName())) {
                    itemQuantityAdded += ((int) master_hashmap.get(itemName));
                }
            }

            if(itemQuantityAdded!=0) {
                holder.subProductQuantity.setText("ADDED (" + itemQuantityAdded + ") Items" );
            } else {
                holder.subProductQuantity.setText("ADD");
            }


            holder.subCategoryAddButtonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Dialog customizeOrderDialog = new Dialog(mContext);
                    customizeOrderDialog.setContentView(R.layout.bottom_sheet_layout);
                    customizeOrderDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    RecyclerView subCategoryItemsRecycler = customizeOrderDialog.findViewById(R.id.sub_category_items_list);
                    customizeOrderDialog.findViewById(R.id.btnAddItems).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customizeOrderDialog.dismiss();
                        }
                    });

                    new_quantity_hasmap = RestaurantUtils.getQuantityHashMap();

                    SubCategoryItemsAdapter subCategoryCardDesignAdapter = new SubCategoryItemsAdapter(mContext,categoryItemsModel.getOther_props(), itemsModelList.get(position).getFood_id(), food_item_click, categoryItemsModel.getType(), RestaurantUtils.getInstance(), new_quantity_hasmap, categoryItemsModel.getName());
                    subCategoryItemsRecycler.setAdapter(subCategoryCardDesignAdapter);
                    subCategoryItemsRecycler.setHasFixedSize(true);

                    customizeOrderDialog.show();

                   /* FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager()
                            .beginTransaction();

                    SubCategoryBottomSheet subCategoryBottomSheet = new SubCategoryBottomSheet(mContext,bottomSheetDialogInterface, itemsModelList, categoryItemsModel.getOther_props(), food_item_click, categoryItemsModel.getType());
                    subCategoryBottomSheet.setCancelable(false);
                    subCategoryBottomSheet.show(ft,subCategoryBottomSheet.getTag());*/
                }
            });

        } else {

            total_items_quantity_hashmap = RestaurantUtils.getQuantityHashMap();

            if(!total_items_quantity_hashmap.isEmpty()) {




                if(total_items_quantity_hashmap.get(categoryItemsModel.getName()) == null) {
                    holder.productQuantity.setText("0");
                } else {
                    holder.productQuantity.setText(String.valueOf(total_items_quantity_hashmap.get(categoryItemsModel.getName())));
                }

            } else  {
                if(data_map.containsKey(itemsModelList.get(position).getFood_id())){
                    holder.productQuantity.setText(String.valueOf(data_map.get(itemsModelList.get(position).getFood_id())));
                }else {
                    holder.productQuantity.setText("0");
                }

            }




            holder.productPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    /*if(data_map.containsKey(itemsModelList.get(position).getFood_id())){
                        quantity = data_map.get(itemsModelList.get(position).getFood_id());
                    }else {
                        quantity = 0;
                    }


*/

                    quantity = Integer.valueOf(holder.productQuantity.getText().toString());

                    quantity++;

                    if(data_map.containsKey(itemsModelList.get(position).getFood_id())){
                        data_map.put(itemsModelList.get(position).getFood_id(),quantity);
                    }else {
                        data_map.put(itemsModelList.get(position).getFood_id(),quantity);
                    }

                    holder.productQuantity.setText(String.valueOf(quantity));

                    holder.itemAdded(categoryItemsModel.getName(), quantity);

                    int final_price = 0;
                    if(RestaurantUtils.getRestaurant_discount()!=0) {
                        String[] discount = holder.txtDiscountedPrice.getText().toString().split(" ");
                        final_price = Integer.parseInt(discount[1]);
                    } else {
                        final_price = itemsModelList.get(position).getPrice();
                    }

                    holder.add_food_item_selected(String.valueOf(final_price),String.valueOf(itemsModelList.get(position).getFood_id()),quantity,itemsModelList.get(position).getName());
                }
            });

            holder.productMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity = Integer.valueOf(holder.productQuantity.getText().toString());
                    quantity--;

                    if(quantity == 0) {
                        data_map.remove(categoryItemsModel.getFood_id());
                    }

                    if(quantity>=0) {
                        holder.productQuantity.setText(String.valueOf(quantity));

                        holder.itemRemoved(categoryItemsModel.getName(), quantity);

                        int final_price = 0;
                        if(RestaurantUtils.getRestaurant_discount()!=0) {
                            String[] discount = holder.txtDiscountedPrice.getText().toString().split(" ");
                            final_price = Integer.parseInt(discount[1]);
                        } else {
                            final_price = itemsModelList.get(position).getPrice();
                        }

                        holder.minus_food_item_selected(String.valueOf(final_price),String.valueOf(itemsModelList.get(position).getFood_id()),quantity,itemsModelList.get(position).getName());

                    }
                }
            });
        }
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
/*
    @Override
    public Filter getFilter() {
        if(foodFliter==null){

            foodFliter = new FoodFliter(this,itemsModelList);
        }
        return foodFliter;
    }*/

    public void filterList(List<CategoryFoodItemResponse.ItemsBean> filteredList) {
        itemsModelList = filteredList;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        Button addItem;
        TextView title, price, subProductQuantity;
        TextView productMinus,productPlus,productQuantity, txtDiscountedPrice, txtDiscountPercentage;
        LinearLayout addButtonLayout, subCategoryAddButtonLayout;
        View lastDivider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.price);
            productMinus= itemView.findViewById(R.id.product_minus);
            productPlus= itemView.findViewById(R.id.product_plus);
            productQuantity= itemView.findViewById(R.id.product_quantity);
            addButtonLayout = itemView.findViewById(R.id.quantityLayout);
            subCategoryAddButtonLayout = itemView.findViewById(R.id.subCategoryQuantityLayout);
            lastDivider = itemView.findViewById(R.id.lastDivider);
            subProductQuantity = itemView.findViewById(R.id.sub_product_quantity);
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
