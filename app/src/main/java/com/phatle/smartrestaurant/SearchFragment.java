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
    private List<MenuItem> mRestaurantMenu = new ArrayList<>();
    private List<CommentItem> mRestaurantComment = new ArrayList<>();
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

        //Add Menu vô nhà hàng
        mRestaurantMenu.clear();
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu1,"Trứng luộc",(float) 12,"Trứng luộc ngon hảo hạng"));
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu3,"Thịt nướng",(float) 9.4,"Thịt nướng muối ớt"));
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu2,"Heo quay",(float) 2.6,"Heo quay sốt ớt"));
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu3,"Vịt nướng",(float) 8.9,"Vịt nướng than"));
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu4,"Gà xối mỡ",(float) 9.9,"Gà ta xối mỡ ăn kèm xôi"));
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu1,"Thịt luộc",(float) 12.9,"Thịt luộc, mắm tôm"));
        mRestaurantMenu.add(new MenuItem(R.drawable.img_menu4,"Ba rọi chiên",(float) 100.5,"Ba rọi chiên nước mắm"));


        //Add comment vô nhà hàng
        mRestaurantComment.clear();
        mRestaurantComment.add(new CommentItem(R.drawable.mu_mourinho,24,"Jose Mourinho",(float) 10,"Thằng Pogba này khá đấy, mở nhà hàng ngon vl"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_pogba,23,"Paul Pogba",(float) 10,"Đại ca quá khen :))"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_martial,23,"Anthony Martial",(float) 9.9,"Ngon đấy anh hai"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_sanchez,22,"Alexis Sanchez",(float) 8.2,"Đại ca nhớ trận sau cho em đá chính nha"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_mata,22,"Juan Mata",(float) 9.6,"Cho em đá đi đại ca, để thằng Sanchez dự bị"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_mourinho,10,"Jose Mourinho",(float) 10,"2 thằng bây dự bị hết, tao cho LORD Fellaini đá chính"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_pogba,10,"Paul Pogba",(float) 10,"Sao cũng được nhưng cho em đá pen nha đại ca"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_mourinho,9,"Jose Mourinho",(float) 10,"Tao cho De Gea sút pen"));
        mRestaurantComment.add(new CommentItem(R.drawable.mu_degea,1,"De Gea",(float) 8.4,"Ôi ngon vl, cám ơn đại ca nhiều :D"));

        mList.clear();
        //Add item theo thứ tự hình ảnh, điểm OVR, tên, type, status, khoảng cách, địa điểm, rating giá
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9.3, "Nhà hàng 5 sao", "Restaurant", true,300,"TPHCM",2,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res3,(float)7.3, "Nhà hàng của DeGea", "Bar", false,350,"Hà Nội",4,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)10, "Nhà hàng của Lukaku", "Coffe", true,370,"TPHCM",3,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res2,(float)7, "Nhà hàng của Mata", "Restaurant", false,300,"TP Vũng Tàu",1,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)8, "Nhà hàng của Sanchez", "Bar", true,400,"Hà Giang",6,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res3,(float)9.1, "Nhà hàng của Martial", "Restaurant", true,580,"TPHCM",0,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res1,(float)9.2, "Nhà hàng của Pogba", "Coffe", false,100,"TP Vũng Tàu",2,mRestaurantMenu,mRestaurantComment));
        mList.add(new RestaurantDrawerItem(R.drawable.img_res2,(float)9.3, "Nhà hàng của Mourinho", "Restaurant", true,200,"TPHCM",3,mRestaurantMenu,mRestaurantComment));



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
