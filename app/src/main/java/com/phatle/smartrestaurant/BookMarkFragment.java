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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookMarkFragment extends Fragment{

    private List<RestaurantDrawerItem> mList = new ArrayList<>();
    private RestaurantItemAdapter3 mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);


        mList = ((ProfileActivity) getActivity()).mListBookmark;
        mAdapter = new RestaurantItemAdapter3(mList,getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mAdapter.setListener(new RestaurantItemAdapter3.InterfaceItemClick() {
            @Override
            public void onItemClick(RestaurantDrawerItem item) {
                Intent intentResDetail = new Intent(getActivity(),RestaurantDetail.class);
                intentResDetail.putExtra("RestaurantDetail", (Serializable) item);
                startActivity(intentResDetail);
            }

            @Override
            public void onDirectClick(RestaurantDrawerItem item) {
                Intent intentResDetail = new Intent(getActivity(),DirectActivity.class);
                intentResDetail.putExtra("RestaurantDetail", (Serializable) item);
                startActivity(intentResDetail);
            }
        });

        ((ProfileActivity) getActivity()).setListenerFragmentBookmark(new ProfileActivity.InterfacePassDataRestaurantBookmark() {
            @Override
            public void onPass(List<RestaurantDrawerItem> list) {
                mAdapter.updateAnswers(list);
            }
        });
        return view;
    }
}
