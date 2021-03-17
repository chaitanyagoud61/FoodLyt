package com.chaitanya.quicksoft.glutton.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chaitanya.quicksoft.glutton.interfaces.BottomSheetDialogInterface;
import com.chaitanya.quicksoft.glutton.activities.restaurant.FoodFliter;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.utils.RestaurantUtils;
import com.chaitanya.quicksoft.glutton.interfaces.Sub_Category_Quantity;
import com.chaitanya.quicksoft.glutton.interfaces.food_item_click;
import com.chaitanya.response.CategoryFoodItemResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendedItemsAdapter extends RecyclerView.Adapter<RecommendedItemsAdapter.ViewHolder> implements Sub_Category_Quantity {

    private Context mContext;
    private List<CategoryFoodItemResponse.ItemsBean> itemsModelList;
    private food_item_click food_item_click;
    private Sub_Category_Quantity sub_category_quantity;

    HashMap<String, Integer> quantity_hashmap = new HashMap<>();
    BottomSheetDialogInterface bottomSheetDialogInterface;

    HashMap<String, Integer> new_quantity_hasmap = new HashMap<>();

    HashMap<String, Integer> total_items_quantity_hashmap = new HashMap<>();

    BroadcastReceiver broadcastReceiver;



    int quantity=0;

    FoodFliter foodFliter;
    HashMap<String,Integer> data_map = new HashMap();



    LayoutInflater inflater;

    public RecommendedItemsAdapter(Context mContext, List<CategoryFoodItemResponse.ItemsBean> itemsModelList, food_item_click food_item_click1, Sub_Category_Quantity sub_category_quantity) {
        this.mContext = mContext;
        this.itemsModelList = itemsModelList;
        this.food_item_click = food_item_click1;
        this.sub_category_quantity = sub_category_quantity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom_grid_layout1, parent, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final CategoryFoodItemResponse.ItemsBean productsModel = itemsModelList.get(position);

        if(productsModel.getType().equals("Veg")){
            holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg,0,0,0);
        }
        else{
            holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_non_veg,0,0,0);
        }


        if(productsModel.getStatus().equals("off")) {
            // Disabling all the ADD Buttons and setting Black and White Filter
            holder.addButtonLayout.setVisibility(View.GONE);

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

            Glide.with(mContext).load(productsModel.getImage()).into(holder.gridIcon);
            holder.gridIcon.setColorFilter(filter);

        } else {

            holder.title.setText(productsModel.getName());
            holder.price.setText("₹ "+productsModel.getPrice());

            int discount_value = RestaurantUtils.getRestaurant_discount();

            if(discount_value!=0) {

                holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                holder.txtDiscountPercentage.setVisibility(View.VISIBLE);
                holder.txtDiscountedPrice.setVisibility(View.VISIBLE);

                int reduced_price = (productsModel.getPrice() - (productsModel.getPrice() * discount_value/100));

                holder.txtDiscountedPrice.setText("₹ " + reduced_price);
                holder.txtDiscountPercentage.setText(discount_value + "% Off");
            }



            // Allowing ADD Button and loading Image


            Glide.with(mContext).load(productsModel.getImage()).into(holder.gridIcon);

            if(!productsModel.getOther_props().isEmpty()) {

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
                    if(itemName.contains(productsModel.getName())) {
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


                       // Log.d("TAG", String.valueOf(quantity_hashmap + productsModel.getName()));

                        Dialog customizeOrderDialog = new Dialog(mContext);
                        customizeOrderDialog.setContentView(R.layout.bottom_sheet_layout);
                        customizeOrderDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        RecyclerView subCategoryItemsRecycler = customizeOrderDialog.findViewById(R.id.sub_category_items_list);
                        customizeOrderDialog.setCanceledOnTouchOutside(true);
                        customizeOrderDialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customizeOrderDialog.dismiss();
                            }
                        });
                        customizeOrderDialog.findViewById(R.id.btnAddItems).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customizeOrderDialog.dismiss();
                            }
                        });

                        new_quantity_hasmap = RestaurantUtils.getQuantityHashMap();

                        SubCategoryItemsAdapter subCategoryCardDesignAdapter = new SubCategoryItemsAdapter(mContext,productsModel.getOther_props(), itemsModelList.get(position).getFood_id(), food_item_click, productsModel.getType(), RestaurantUtils.getInstance(), new_quantity_hasmap, holder.title.getText().toString());
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

            } else  {

                total_items_quantity_hashmap = RestaurantUtils.getQuantityHashMap();

                if(!total_items_quantity_hashmap.isEmpty()) {

                    if(total_items_quantity_hashmap.get(productsModel.getName()) == null) {
                        holder.productQuantity.setText("0");
                    } else {
                        holder.productQuantity.setText(String.valueOf(total_items_quantity_hashmap.get(productsModel.getName())));
                    }

                } else  {
                    if(data_map.containsKey(itemsModelList.get(position).getFood_id())){
                        holder.productQuantity.setText(String.valueOf(data_map.get(itemsModelList.get(position).getFood_id())));
                    }else {
                        holder.productQuantity.setText("0");
                    }

                }


                /*if(data_map.containsKey(productsModel.getFood_id())){
                    holder.productQuantity.setText(String.valueOf(data_map.get(productsModel.getFood_id())));
                }else {
                    holder.productQuantity.setText("0");
                }
*/
                holder.productPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*if(data_map.containsKey(productsModel.getFood_id())){
                            quantity = data_map.get(productsModel.getFood_id());
                        }else {
                            quantity = 0;
                        }*/


                        quantity = Integer.valueOf(holder.productQuantity.getText().toString());

                        quantity++;

                        if(data_map.containsKey(productsModel.getFood_id())){
                            data_map.put(productsModel.getFood_id(),quantity);
                        }else {
                            data_map.put(productsModel.getFood_id(),quantity);
                        }

                        holder.productQuantity.setText(String.valueOf(quantity));

                        holder.itemAdded(productsModel.getName(), quantity);

                        int final_price = 0;
                        if(RestaurantUtils.getRestaurant_discount()!=0) {
                            String[] discount = holder.txtDiscountedPrice.getText().toString().split(" ");
                            final_price = Integer.parseInt(discount[1]);
                        } else {
                            final_price = itemsModelList.get(position).getPrice();
                        }


                        holder.add_food_item_selected(String.valueOf(final_price),String.valueOf(productsModel.getFood_id()),quantity,productsModel.getName());
                    }
                });

                holder.productMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quantity = Integer.valueOf(holder.productQuantity.getText().toString());
                        quantity--;
                        if(quantity == 0) {
                            data_map.remove(productsModel.getFood_id());
                        }
                        if(quantity>=0) {
                            holder.productQuantity.setText(String.valueOf(quantity));

                            holder.itemRemoved(productsModel.getName(), quantity);

                            int final_price = 0;
                            if(RestaurantUtils.getRestaurant_discount()!=0) {
                                String[] discount = holder.txtDiscountedPrice.getText().toString().split(" ");
                                final_price = Integer.parseInt(discount[1]);
                            } else {
                                final_price = itemsModelList.get(position).getPrice();
                            }


                            holder.minus_food_item_selected(String.valueOf(final_price),String.valueOf(productsModel.getFood_id()),quantity,productsModel.getName());

                        }
                    }
                });




            }
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

    @Override
    public void itemAdded(String food_name, int quantity) {
        quantity_hashmap.put(food_name, quantity);

    }

    @Override
    public void itemRemoved(String food_name, int quantity) {
        quantity_hashmap.put(food_name,quantity);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, subProductQuantity;
        ImageView gridIcon;
        Button btnAdd;
        TextView productMinus,productPlus,productQuantity, txtDiscountedPrice, txtDiscountPercentage;
        LinearLayout addButtonLayout, subCategoryAddButtonLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            gridIcon = itemView.findViewById(R.id.imageView);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            price = itemView.findViewById(R.id.price);
            productMinus= itemView.findViewById(R.id.product_minus);
            productPlus= itemView.findViewById(R.id.product_plus);
            productQuantity= itemView.findViewById(R.id.product_quantity);
            addButtonLayout = itemView.findViewById(R.id.quantityLayout);
            subCategoryAddButtonLayout = itemView.findViewById(R.id.subCategoryQuantityLayout);
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
