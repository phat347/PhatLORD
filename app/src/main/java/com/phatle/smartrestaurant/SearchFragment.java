package com.phatle.smartrestaurant;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{
    private List<RestaurantDrawerItem> mList = new ArrayList<>();
    List<RestaurantDrawerItem> itemSearch = new ArrayList<>();
    private RestaurantItemAdapter mAdapter;
    AutoCompleteTextView searchTextView;
    ImageView searchBtn;
    LinearLayout layoutEmpty;
    RecyclerView recyclerView;
    RestoreBottomTab mListener;
    public interface RestoreBottomTab{
        void onRestore();
    }
    public void setListener(RestoreBottomTab listener)
    {
        this.mListener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchTextView = view.findViewById(R.id.auto_complete_search);
        searchBtn = view.findViewById(R.id.search_img);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });
        if(itemSearch.size() != 0)
        {
            layoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        mList.clear();
        //Add item theo thứ tự hình ảnh, điểm OVR, tên, type, status, khoảng cách, địa điểm, rating giá
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9.3, "Nhà hàng 5 sao", "Restaurant", true,300,"TPHCM",2));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res3,(float)7.3, "Nhà hàng 3 sao", "Bar", false,350,"Hà Nội",4));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)10, "Nhà hàng 4 sao", "Coffe", true,370,"Sao Hỏa",3));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res2,(float)7, "Nhà hàng 2 sao", "Restaurant", false,300,"Sao Kim",1));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)8, "Nhà hàng 5 sao", "Bar", true,400,"Sao Hố",6));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res3,(float)9.1, "Nhà hàng 1 sao", "Restaurant", true,580,"Sao Tào Lao",0));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9.2, "Nhà hàng 5 sao", "Coffe", false,100,"TP Vũng Tàu",2));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res2,(float)9.3, "Nhà hàng 5 sao", "Restaurant", true,200,"Hà Lội",3));



        List<String> locationList = new ArrayList<>();
        locationList.clear();

        if (mList.size() != 0)
        {
            for (RestaurantDrawerItem item : mList)
            {
                locationList.add(item.getLocation());
            }

            String[] locationListArray = new String[ locationList.size() ];
            locationList.toArray( locationListArray );
            ArrayAdapter adapterLocation = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,locationListArray);

            searchTextView.setAdapter(adapterLocation);


            // Sét đặt số ký tự nhỏ nhất, để cửa sổ gợi ý hiển thị
            searchTextView.setThreshold(1);
            searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    hideKeyboard(getActivity());
                }
            });
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String location = searchTextView.getText().toString().toLowerCase();

                    itemSearch.clear();

                    for (int i = 0; i < mList.size() ; i++) {
                        if (mList.get(i).getLocation().toLowerCase().contains(location) && !location.equals(""))
                        {
                            itemSearch.add(mList.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    if(itemSearch.size() != 0)
                    {
                        layoutEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    else {
                        layoutEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    hideKeyboard(getActivity());
                    if (mListener != null)
                    {
                        mListener.onRestore();
                    }

                }
            });
            mAdapter = new RestaurantItemAdapter(itemSearch);

            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

            mAdapter.setListener(new RestaurantItemAdapter.InterfaceItemClick() {
                @Override
                public void onItemClick(RestaurantDrawerItem item) {
                    hideKeyboard(getActivity());
                    Intent intentResDetail = new Intent(getActivity(),RestaurantDetail.class);
                    intentResDetail.putExtra("RestaurantDetail", (Serializable) item);
                    startActivity(intentResDetail);
                }
            });


        }


        return view;
    }
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
