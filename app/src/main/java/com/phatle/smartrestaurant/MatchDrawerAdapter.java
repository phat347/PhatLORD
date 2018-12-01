package com.phatle.smartrestaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MatchDrawerAdapter extends RecyclerView.Adapter<MatchDrawerAdapter.MatchItemViewHolder>{

    Context context;
    private List<MatchResponse> list;

    public MatchDrawerAdapter(List<MatchResponse> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_drawer_item,parent,false);
        return new MatchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchItemViewHolder holder, int position) {

        final MatchResponse item = list.get(position);

        GlideApp.with(context)
                .load(item.getHomeImg())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgHome);
        GlideApp.with(context)
                .load(item.getAwayImg())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgAway);
        holder.homeName.setText(item.getHomeName());
        holder.awayName.setText(item.getAwayName());

        if(item.getHomeScore()==null || item.getAwayScore()==null)
        {
            holder.score.setText("Chưa đá");
        }
        else {
            holder.score.setText( item.getHomeScore()+"    -    "+item.getAwayScore());
        }

    }

    public void updateAnswers(List<MatchResponse> items) {
        list = items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MatchItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgHome,imgAway;
        public TextView homeName,awayName,score;

        public MatchItemViewHolder(View view) {
            super(view);
            imgHome = view.findViewById(R.id.notification_img);
            imgAway = view.findViewById(R.id.notification_img2);
            homeName = view.findViewById(R.id.home_name);
            awayName = view.findViewById(R.id.away_name);
            score = view.findViewById(R.id.score_line);

        }
    }
}
