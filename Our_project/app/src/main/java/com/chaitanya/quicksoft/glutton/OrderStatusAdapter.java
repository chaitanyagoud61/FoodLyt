package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chaitanya.quicksoft.glutton.databinding.OrderStatusRowItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.OrderStatusAdapterViewHolder> {

    private Context context;
    Orderstatuslocalmodel orderstatuslocalmodel;
    List<Orderstatuslocalmodel> orderstatuslocalmodelList = new ArrayList<>();

    public OrderStatusAdapter(Context context, List<Orderstatuslocalmodel> orderstatuslocalmodelLists) {
        this.context = context;
        this.orderstatuslocalmodelList = orderstatuslocalmodelLists;
    }

    @NonNull
    @Override
    public OrderStatusAdapter.OrderStatusAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderStatusRowItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.order_status_row_item, parent, false);

        return new OrderStatusAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusAdapterViewHolder holder, int position) {
        orderstatuslocalmodel =orderstatuslocalmodelList.get(position);
        holder.orderStatusRowItemBinding.setOrderstatuslocalmodel(orderstatuslocalmodel);
        holder.bind(orderstatuslocalmodel);
    }

    @Override
    public int getItemCount() {
        return orderstatuslocalmodelList.size();
    }


    public class OrderStatusAdapterViewHolder extends RecyclerView.ViewHolder {
        OrderStatusRowItemBinding orderStatusRowItemBinding;
        public OrderStatusAdapterViewHolder(OrderStatusRowItemBinding orderStatusRowItemBinding1) {
            super(orderStatusRowItemBinding1.getRoot());
            this.orderStatusRowItemBinding = orderStatusRowItemBinding1;

        }
        public void bind(Object obj) {
            orderStatusRowItemBinding.setVariable(BR.orderstatuslocalmodel, obj);
            orderStatusRowItemBinding.executePendingBindings();
        }

    }
}
