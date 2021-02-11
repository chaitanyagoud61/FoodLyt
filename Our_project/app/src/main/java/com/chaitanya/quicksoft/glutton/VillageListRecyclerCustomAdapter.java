package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class VillageListRecyclerCustomAdapter extends RecyclerView.Adapter<VillageListRecyclerCustomAdapter.VillageListRecyclerViewHolder> implements  Filterable{

    private Context context;
    VillageConfigCustomAdapterModel villageConfigCustomAdapterModel;
    ArrayList<VillageConfigCustomAdapterModel> model_list = new ArrayList<>();
    Home_CustomAdapter_Item_Click home_customAdapter_item_click;
    HomeScreenFilter homeScreenFilter;


    public VillageListRecyclerCustomAdapter(Context context, ArrayList<VillageConfigCustomAdapterModel> model_list,
                                            Home_CustomAdapter_Item_Click home_customAdapter_item_click) {

        this.model_list = model_list;
        this.context = context;
        this.home_customAdapter_item_click = home_customAdapter_item_click;
    }

    @Override
    public VillageListRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_screen_row_item, null);

        return new VillageListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VillageListRecyclerViewHolder viewHolder, int i) {


        try {
            viewHolder.hme_htl_name.setText(model_list.get(i).getRestaurant_name());
            viewHolder.hme_htl_address.setText(model_list.get(i).getAddress());
            Glide.with(context)
                    .load(model_list.get(i).getImage())
                    .into(viewHolder.hme_htl_image);
            viewHolder.offers.setText(model_list.get(i).getOffers());


            viewHolder.SendRequestAccordingToSelectedItem(model_list.get(i).getRestaurant_name(), model_list.get(i).getAddress(),
                    model_list.get(i).getImage(), model_list.get(i).getOffers(), model_list.get(i).getRestaurant_id(), model_list.get(i).getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return model_list.size();
    }

    @Override
    public Filter getFilter() {
        if(homeScreenFilter==null){

            homeScreenFilter = new HomeScreenFilter(this,model_list);
        }
        return homeScreenFilter;
    }


    public class VillageListRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView hme_htl_name, hme_htl_address, offers;
        ImageView hme_htl_image;
        CardView home_cardview;
        LinearLayout home_linr_lyt;

        public VillageListRecyclerViewHolder(View itemView) {
            super(itemView);

            hme_htl_image = (ImageView) itemView.findViewById(R.id.hme_htl_image);
            hme_htl_name = (TextView) itemView.findViewById(R.id.hme_htl_name);
            hme_htl_address = (TextView) itemView.findViewById(R.id.hme_htl_address);
            offers = (TextView) itemView.findViewById(R.id.offers);
            home_cardview = (CardView) itemView.findViewById(R.id.home_cardview);
            home_linr_lyt = (LinearLayout) itemView.findViewById(R.id.home_linr_lyt);

        }

        public void SendRequestAccordingToSelectedItem(final String restaurant_name, final String address, final String dummy,
                                                       final String offers, int restaurant_id,final String restaurant_descrp) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    home_customAdapter_item_click.Village_List_Custom_Adapter_Item_click(restaurant_name, address, dummy, offers, restaurant_id,restaurant_descrp);

                }
            });
        }
    }

}
