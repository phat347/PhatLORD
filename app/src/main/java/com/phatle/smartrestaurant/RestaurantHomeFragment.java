package com.phatle.smartrestaurant;

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

public class RestaurantHomeFragment extends Fragment{

    private List<RestaurantDrawerItem> mList = new ArrayList<>();
    private RestaurantItemAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);


        mList.clear();
        //Add item theo thứ tự hình ảnh, điểm OVR, tên, type, status, khoảng cách, địa điểm, rating giá
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9.3, "Nhà hàng 5 sao", "Restaurant", true,"300","TPHCM",5));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)7.3, "Nhà hàng 3 sao", "Bar", false,"350","HaNoi",4));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)10, "Nhà hàng 4 sao", "Coffe", true,"370","TPHCM",3));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)7, "Nhà hàng 5 sao", "Restaurant", true,"300","TPHCM",4));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)8, "Nhà hàng 5 sao", "Restaurant", true,"300","TPHCM",4));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9, "Nhà hàng 5 sao", "Restaurant", true,"300","TPHCM",4));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9, "Nhà hàng 5 sao", "Restaurant", true,"300","TPHCM",4));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9, "Nhà hàng 5 sao", "Restaurant", true,"300","TPHCM",4));

        mAdapter = new RestaurantItemAdapter(mList);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        return view;
    }
}