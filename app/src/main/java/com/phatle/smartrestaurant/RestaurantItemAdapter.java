package com.phatle.smartrestaurant;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RestaurantItemAdapter extends RecyclerView.Adapter<RestaurantItemAdapter.RestaurantItemViewHolder>{

    private List<RestaurantDrawerItem> list;

    public RestaurantItemAdapter(List<RestaurantDrawerItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RestaurantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_drawer_item,parent,false);
        return new RestaurantItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemViewHolder holder, int position) {
        RestaurantDrawerItem item = list.get(position);
        holder.restaurantImage.setImageResource(item.getImgRes());
        holder.name.setText(item.getName());
        holder.type.setText(item.getType());

        int score = (int)(item.getOverallRating()*10)/10;
        int point = (int)(item.getOverallRating()*10)%10;



        if(item.isStatus())
        {
            holder.status1.setText("Open now");
            holder.status2.setText("    •    " + item.getDistance() + "m from you    •    " + item.getLocation() + ", Vietnam");
        }
        else
        {
            holder.status1.setText("Closed");
            holder.status1.setTextColor(Color.RED);
            holder.status2.setText("    •    " + item.getDistance() + "m from you    •    " + item.getLocation() + ", Vietnam");
        }
        if(point != 0) {
            holder.overallRating.setText(score + "." + point);
        }
        else holder.overallRating.setText(score+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RestaurantItemViewHolder extends RecyclerView.ViewHolder {

        public  ImageView restaurantImage;
        public  TextView name, type, status1, status2, overallRating;
        public RestaurantItemViewHolder(View view) {
            super(view);
            restaurantImage = view.findViewById(R.id.restaurant_img);
            name = view.findViewById(R.id.restaurant_name);
            type = view.findViewById(R.id.restaurant_type);
            status1 = view.findViewById(R.id.restaurant_status1);
            status2 = view.findViewById(R.id.restaurant_status2);
            overallRating = view.findViewById(R.id.restaurant_score);

        }
    }
}
