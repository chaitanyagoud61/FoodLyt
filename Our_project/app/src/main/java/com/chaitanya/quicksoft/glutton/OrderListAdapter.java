package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListAdapterViewHolder> {

    private Context context;
    OrderListModel orderListModel;
    ArrayList<OrderListModel> orderListModelArrayList = new ArrayList<>();

    public OrderListAdapter(Context context, ArrayList<OrderListModel> orderListModelArrayList) {
        this.context = context;
        this.orderListModelArrayList = orderListModelArrayList;
    }

    @NonNull
    @Override
    public OrderListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oder_list_row_item,null);
        return new OrderListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderListModelArrayList.size();
    }

    public class OrderListAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView order_list_restrnt_name,order_list_address,order_price,
                order_list_food_name_nd_qnty,order_date_nd_time,order_list_status;
        ImageView order_list_status_image;
        public OrderListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            order_list_restrnt_name = (TextView)itemView.findViewById(R.id.order_list_restrnt_name);
            order_list_address = (TextView)itemView.findViewById(R.id.order_list_address);
            order_price = (TextView)itemView.findViewById(R.id.order_price);
            order_list_food_name_nd_qnty = (TextView)itemView.findViewById(R.id.order_list_food_name_nd_qnty);
            order_date_nd_time = (TextView)itemView.findViewById(R.id.order_date_nd_time);
            order_list_status = (TextView)itemView.findViewById(R.id.order_list_status);
            order_list_status_image = (ImageView) itemView.findViewById(R.id.order_list_status_image);
        }
    }
}
