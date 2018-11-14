package com.phatle.smartrestaurant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>{

    private List<MenuItem> list;

    public MenuItemAdapter(List<MenuItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_drawer_item,parent,false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem item = list.get(position);
        holder.menuImage.setImageResource(item.getImgRes());
        holder.name.setText(item.getName());

        int score = (int)(item.getPrice()*10)/10;
        int point = (int)(item.getPrice()*10)%10;

        if(point != 0) {
            holder.price.setText("$"+score + "," + point);
        }
        else holder.price.setText("$"+score+ ","+"00");

        holder.description.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView menuImage;
        public TextView name, description, price;
        public MenuItemViewHolder(View view) {
            super(view);
            menuImage = view.findViewById(R.id.restaurant_img);
            name = view.findViewById(R.id.menu_name);
            description = view.findViewById(R.id.menu_description);
            price = view.findViewById(R.id.menu_price);
        }
    }
}
