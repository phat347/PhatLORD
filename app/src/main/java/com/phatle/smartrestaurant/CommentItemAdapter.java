package com.phatle.smartrestaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.CommentItemViewHolder>{


    private List<CommentItem> list;
    Context context;
    private InterfaceItemClick mListener;
    public interface InterfaceItemClick{
        void onItemClick();
    }
    public void setListener(InterfaceItemClick listener){
        this.mListener = listener;
    }
    public CommentItemAdapter(List<CommentItem> list , Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_drawer_item,parent,false);
        return new CommentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemViewHolder holder, int position) {
        CommentItem item = list.get(position);
        if(item.getImgRes() == null || item.getImgRes().equals(""))
        {
            Picasso.with(context)
                    .load(R.drawable.user)
                    .transform(new CropCircleTransformation())
                    .resize(70, 70)
                    .into(holder.personCommentImage);
        }
        else {
            Picasso.with(context)
                    .load(item.getImgRes())
                    .transform(new CropCircleTransformation())
                    .resize(70, 70)
                    .into(holder.personCommentImage);
        }
        holder.name.setText(item.getName());

        int score = (int)(item.getScore()*10)/10;
        int point = (int)(item.getScore()*10)%10;

        if(point != 0) {
            holder.score.setText(score + "," + point + "/10");
        }
        else holder.score.setText(score+"/10");

        holder.comment.setText(item.getComment());
        if (item.getTime()== -1)
        {
            holder.time.setText("a moment ago");
        }
        else {
            holder.time.setText(item.getTime()+ " hours ago");
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                {
                    mListener.onItemClick();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView personCommentImage;
        public TextView name, comment, score, time;
        public CardView itemLayout;
        public CommentItemViewHolder(View view) {
            super(view);
            personCommentImage = view.findViewById(R.id.restaurant_img);
            name = view.findViewById(R.id.menu_name);
            comment = view.findViewById(R.id.menu_description);
            score = view.findViewById(R.id.menu_price);
            time = view.findViewById(R.id.comment_time);
            itemLayout = view.findViewById(R.id.itemLayout);
        }
    }
}
