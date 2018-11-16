package com.phatle.smartrestaurant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RestaurantHomeFragment extends Fragment{

    private List<RestaurantDrawerItem> mList = new ArrayList<>();
    private RestaurantItemAdapter mAdapter;
    private List<MenuItem> mRestaurantMenu = new ArrayList<>();
    private List<CommentItem> mRestaurantComment = new ArrayList<>();
    private SOService mService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);
        mService = ApiUtils.getSOService();

        //Add menu vô nhà hàng
        mRestaurantMenu.clear();
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu1","Trứng luộc",(float) 12,"Trứng luộc ngon hảo hạng"));
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu3","Thịt nướng",(float) 9.3,"Thịt nướng muối ớt"));
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu2","Heo quay",(float) 10.4,"Heo quay sốt ớt"));
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu3","Vịt nướng",(float) 7.5,"Vịt nướng than"));
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu4","Gà xối mỡ",(float) 9,"Gà ta xối mỡ ăn kèm xôi"));
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu1","Thịt luộc",(float) 15,"Thịt luộc, mắm tôm"));
//        mRestaurantMenu.add(new MenuItem("android.resource://com.phatle.smartrestaurant/drawable/img_menu4","Ba rọi chiên",(float) 22.2,"Ba rọi chiên nước mắm"));

        //Add comment vô nhà hàng
        mRestaurantComment.clear();
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_mourinho",24,"Jose Mourinho",(float) 10,"Thằng Pogba này khá đấy, mở nhà hàng ngon vl"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_pogba",23,"Paul Pogba",(float) 10,"Đại ca quá khen :))"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_martial",23,"Anthony Martial",(float) 9.9,"Ngon đấy anh hai"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_sanchez",22,"Alexis Sanchez",(float) 8.2,"Đại ca nhớ trận sau cho em đá chính nha"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_mata",22,"Juan Mata",(float) 9.6,"Cho em đá đi đại ca, để thằng Sanchez dự bị"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_mourinho",10,"Jose Mourinho",(float) 10,"2 thằng bây dự bị hết, tao cho LORD Fellaini đá chính"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_pogba",10,"Paul Pogba",(float) 10,"Sao cũng được nhưng cho em đá pen nha đại ca"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_mourinho",9,"Jose Mourinho",(float) 10,"Tao cho De Gea sút pen"));
//        mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_degea",1,"De Gea",(float) 8.4,"Ôi ngon vl, cám ơn đại ca nhiều :D"));

        mList.clear();
        //Add item theo thứ tự hình ảnh, điểm OVR, tên, type, status, khoảng cách, địa điểm, rating giá
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res1",(float)9.3, "Nhà hàng 5 sao", "Restaurant", true,300,"TPHCM",2,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res3",(float)7.3, "Nhà hàng của DeGea", "Bar", false,350,"Hà Nội",4,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res1",(float)10, "Nhà hàng của Lukaku", "Coffe", true,370,"TPHCM",3,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res2",(float)7, "Nhà hàng của Mata", "Restaurant", false,300,"TP Vũng Tàu",1,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res1",(float)8, "Nhà hàng của Sanchez", "Bar", true,400,"Hà Giang",6,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res3",(float)9.1, "Nhà hàng của Martial", "Restaurant", true,580,"TPHCM",0,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res1",(float)9.2, "Nhà hàng của Pogba", "Coffe", false,100,"TP Vũng Tàu",2,mRestaurantMenu,mRestaurantComment));
//        mList.add(new RestaurantDrawerItem("android.resource://com.phatle.smartrestaurant/drawable/img_res2",(float)9.3, "Nhà hàng của Mourinho", "Restaurant", true,200,"TPHCM",3,mRestaurantMenu,mRestaurantComment));

        mAdapter = new RestaurantItemAdapter(mList,getContext());


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mAdapter.setListener(new RestaurantItemAdapter.InterfaceItemClick() {
            @Override
            public void onItemClick(RestaurantDrawerItem item) {
                Intent intentResDetail = new Intent(getActivity(),RestaurantDetail.class);
                intentResDetail.putExtra("RestaurantDetail", (Serializable) item);
                startActivity(intentResDetail);
            }
        });


        loadAnswers();
        return view;
    }
    public void loadAnswers() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mService.getAnswers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RestaurantDrawerItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Phat","onComplete");
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phat","onError");
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Không thể lấy dữ liệu!")
                                .setContentText("Kiểm tra kết nối mạng hoặc vui lòng thử lại sau")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();

                    }

                    @Override
                    public void onNext(List<RestaurantDrawerItem> restaurantResponses) {
                        mAdapter.updateAnswers(restaurantResponses);
                        pDialog.dismiss();
                    }
                });
    }


}
