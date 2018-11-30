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

public class NationalTeamAdapter extends RecyclerView.Adapter<NationalTeamAdapter.NationalItemViewHolder>{


    Context context;
    private List<NationalTeamResponse> list;

    public NationalTeamAdapter(List<NationalTeamResponse> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NationalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.national_team_drawer_item,parent,false);
        return new NationalItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NationalItemViewHolder holder, int position) {

        final NationalTeamResponse item = list.get(position);

        Picasso.with(context)
                .load(item.getImg())
                .into(holder.img);
        holder.name.setText(item.getName());
        holder.id.setText("Số: "+item.getId());
        holder.stats.setText("  FW: "+ item.getFw() + "  MF: "+ item.getMf() + "  DF: "+ item.getDf());

    }

    public void updateAnswers(List<NationalTeamResponse> items) {
        list = items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NationalItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name, id, stats;

        public NationalItemViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.notification_img);
            name = view.findViewById(R.id.notification_name);
            id = view.findViewById(R.id.notification_time);
            stats = view.findViewById(R.id.notification_description);

        }
    }
}
