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
            holder.status1.setTextColor(Color.rgb(0,128,0));
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

        switch (item.getPriceRating()){
            case 0:
                holder.dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar2.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar3.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar4.setImageResource(R.drawable.ic_dollarsign_inactive);
                break;
            case 1:
                holder.dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar2.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar3.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            case 2:
                holder.dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar2.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar3.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            case 3:
                holder.dollar1.setImageResource(R.drawable.ic_dollarsign_inactive);
                holder.dollar2.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar3.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            case 4:
                holder.dollar1.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar2.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar3.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;
            default:
                holder.dollar1.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar2.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar3.setImageResource(R.drawable.ic_dollarsign);
                holder.dollar4.setImageResource(R.drawable.ic_dollarsign);
                break;


        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RestaurantItemViewHolder extends RecyclerView.ViewHolder {

        public    ImageView restaurantImage;
        public  TextView name, type, status1, status2, overallRating;
        public ImageView dollar1, dollar2, dollar3, dollar4;
        public RestaurantItemViewHolder(View view) {
            super(view);
            restaurantImage = view.findViewById(R.id.restaurant_img);
            name = view.findViewById(R.id.restaurant_name);
            type = view.findViewById(R.id.restaurant_type);
            status1 = view.findViewById(R.id.restaurant_status1);
            status2 = view.findViewById(R.id.restaurant_status2);
            overallRating = view.findViewById(R.id.restaurant_score);
            dollar1 = view.findViewById(R.id.dollar1);
            dollar2 = view.findViewById(R.id.dollar2);
            dollar3 = view.findViewById(R.id.dollar3);
            dollar4 = view.findViewById(R.id.dollar4);
        }
    }
}
