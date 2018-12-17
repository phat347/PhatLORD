package com.phatle.smartrestaurant;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class DragItemAdapter extends RecyclerView.Adapter<DragItemAdapter.ItemDragViewHolder>{

    List<DragItem> list;

    Context context;

    public DragItemAdapter(List<DragItem> list,Context context) {
        this.list = list;
        this.context = context;
    }

    public void updateAnswers(List<DragItem> items) {
        list = items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemDragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_item,parent,false);
        return new ItemDragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDragViewHolder holder, int position) {
        DragItem item = list.get(position);

        holder.img.setImageResource(item.getImgRes());
        holder.qty.setText("Số lượng: "+item.getQty());
        holder.price.setText(item.getPrice()+"$");
        holder.name.setText(item.getName());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemDragViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name,price,qty;

        public ItemDragViewHolder(View view) {
            super(view);

            img = view.findViewById(R.id.drag_item_img);
            name = view.findViewById(R.id.item_name);
            price = view.findViewById(R.id.item_price);
            qty = view.findViewById(R.id.item_qty);
        }
    }
}
