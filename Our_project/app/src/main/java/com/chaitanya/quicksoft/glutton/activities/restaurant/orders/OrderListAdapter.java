package com.chaitanya.quicksoft.glutton.activities.restaurant.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaitanya.quicksoft.glutton.BR;
import com.chaitanya.quicksoft.glutton.interfaces.OrderClickListner;
import com.chaitanya.quicksoft.glutton.R;
import com.chaitanya.quicksoft.glutton.databinding.OderListRowItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListAdapterViewHolder> implements OrderClickListner {

    private Context context;
    OrderListModel orderListModel;
    OrderClickListner orderClickListner;
    List<OrderListModel> orderListModelArrayList = new ArrayList<>();

    public OrderListAdapter(Context context, List<OrderListModel> orderListModelArrayList,OrderClickListner orderClickListner) {
        this.context = context;
        this.orderListModelArrayList = orderListModelArrayList;
        this.orderClickListner = orderClickListner;
    }

    @NonNull
    @Override
    public OrderListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OderListRowItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.oder_list_row_item, parent, false);

        return new OrderListAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapterViewHolder holder, int position) {
        OrderListModel dataModel =orderListModelArrayList.get(position);


        holder.oderListRowItemBinding.setOrderlistmodel(dataModel);
        holder.bind(dataModel);
        if(dataModel.getCancelled()) {
            holder.oderListRowItemBinding.setOrderClickListner(null);
        } else {
            holder.oderListRowItemBinding.setOrderClickListner(this);

        }
    }

    @Override
    public int getItemCount() {
        return orderListModelArrayList.size();
    }


    public void getselectedorder(OrderListModel orderListModel) {
        orderClickListner.getselectedorder(orderListModel);
    }

    public class OrderListAdapterViewHolder extends RecyclerView.ViewHolder {
        OderListRowItemBinding oderListRowItemBinding;
        TextView txtOrderStatus;

        public OrderListAdapterViewHolder(OderListRowItemBinding oderListRowItemBinding1) {
            super(oderListRowItemBinding1.getRoot());
            this.oderListRowItemBinding = oderListRowItemBinding1;

        }
        public void bind(Object obj) {
            oderListRowItemBinding.setVariable(BR.orderlistmodel, obj);
            oderListRowItemBinding.executePendingBindings();
        }

    }
}
