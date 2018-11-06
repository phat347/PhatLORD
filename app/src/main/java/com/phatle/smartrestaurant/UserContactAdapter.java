package com.phatle.smartrestaurant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserContactAdapter extends RecyclerView.Adapter{
    private List<UserContact> list;

    public UserContactAdapter(List<UserContact> list) {
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_drawer_item,parent,false);
        return new UserContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserContact userContact = list.get(position);
        TextView username = holder.itemView.findViewById(R.id.text_user_contact);
        TextView phoneNumber = holder.itemView.findViewById(R.id.text_phone_number);
        TextView alphabet = holder.itemView.findViewById(R.id.alphabet);
        username.setText(userContact.name);
        phoneNumber.setText(userContact.phoneNumber);
        alphabet.setText(userContact.alphabet);

        if (!userContact.alphabet.equals(""))
        {
            username.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.GONE);
            alphabet.setVisibility(View.VISIBLE);
        }
        else {
            username.setVisibility(View.VISIBLE);
            phoneNumber.setVisibility(View.VISIBLE);
            alphabet.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class UserContactViewHolder extends RecyclerView.ViewHolder {
        public UserContactViewHolder(View view) {
            super(view);
        }
    }
}
