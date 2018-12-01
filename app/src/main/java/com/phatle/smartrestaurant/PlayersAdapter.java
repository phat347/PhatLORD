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

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerItemViewHolder>{


    Context context;
    private List<Player> list;

    public PlayersAdapter(List<Player> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.national_team_drawer_item,parent,false);
        return new PlayerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerItemViewHolder holder, int position) {

        final Player item = list.get(position);

        GlideApp.with(context)
                .load(item.getImgRes())
                .into(holder.img);
        holder.name.setText(item.getName());
        holder.id.setVisibility(View.INVISIBLE);
        holder.stats.setText(item.getMatchePlayed()+" Trận     "+item.getPoint()+" Điểm     "+item.getGd()+" Hiệu số");

    }

    public void updateAnswers(List<Player> items) {
        list = items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PlayerItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name, id, stats;

        public PlayerItemViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.notification_img);
            name = view.findViewById(R.id.notification_name);
            id = view.findViewById(R.id.notification_time);
            stats = view.findViewById(R.id.notification_description);

        }
    }
}
