package com.phatle.smartrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private List<MenuItem> mRestaurantMenu = new ArrayList<>();
    RestaurantDrawerItem IntentItem;
    MenuItemAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);
        Intent intent = getActivity().getIntent();
        IntentItem = (RestaurantDrawerItem) intent.getSerializableExtra("RestaurantDetail");

        mRestaurantMenu = IntentItem.getMenu();

        mAdapter = new MenuItemAdapter(mRestaurantMenu,getContext());


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        return view;
    }
}
