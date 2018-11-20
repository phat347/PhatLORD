package com.phatle.smartrestaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationItemAdapter extends RecyclerView.Adapter<NotificationItemAdapter.RestaurantItemViewHolder>{
    Context context;
    private List<NotificationResponse> list;

    public NotificationItemAdapter(List<NotificationResponse> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_drawer_item,parent,false);
        return new RestaurantItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemViewHolder holder, int position) {

        final NotificationResponse item = list.get(position);

        Picasso.with(context)
                .load(item.getImg())
                .into(holder.img);
        holder.name.setText(item.getName());
        holder.time.setText("  ("+item.getTime()+" hours ago)");
        holder.description.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RestaurantItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name, time, description;

        public RestaurantItemViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.notification_img);
            name = view.findViewById(R.id.notification_name);
            time = view.findViewById(R.id.notification_time);
            description = view.findViewById(R.id.notification_description);

        }
    }
}
