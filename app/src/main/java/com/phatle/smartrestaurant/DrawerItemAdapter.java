package com.phatle.smartrestaurant;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class DrawerItemAdapter extends RecyclerView.Adapter{

    private List<DrawerItem> list;
    private InterfaceItemClick mListener;

    public interface InterfaceItemClick{
        void onItemClick(DrawerItem drawerItem);
    }
    public void setListener(InterfaceItemClick listener){
        mListener = listener;
    }
    public DrawerItemAdapter(List<DrawerItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_nav_item,parent,false);
        return new DrawerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DrawerItem drawerItem = list.get(position);
        TextView itemName = holder.itemView.findViewById(R.id.drawer_itemName);
        ImageView itemImg = holder.itemView.findViewById(R.id.drawer_icon);
        RelativeLayout itemLayout = holder.itemView.findViewById(R.id.itemLayout);
        itemName.setText(drawerItem.getItemName());
        itemImg.setImageResource(drawerItem.getImgResID());
        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                {
                    mListener.onItemClick(drawerItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class DrawerItemViewHolder extends RecyclerView.ViewHolder {
        public DrawerItemViewHolder(View view) {
            super(view);
        }
    }
}
