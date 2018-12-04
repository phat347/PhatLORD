package com.phatle.smartrestaurant;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class DragItemAdapter extends RecyclerView.Adapter<DragItemAdapter.ItemDragViewHolder>{

    int[] list = new int[0];

    Context context;

    public DragItemAdapter(int[] list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemDragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_item,parent,false);
        return new ItemDragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDragViewHolder holder, int position) {
        int item = list[position];

        holder.img.setImageResource(item);

    }


    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ItemDragViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;


        public ItemDragViewHolder(View view) {
            super(view);

            img = view.findViewById(R.id.drag_item_img);
        }
    }
}
