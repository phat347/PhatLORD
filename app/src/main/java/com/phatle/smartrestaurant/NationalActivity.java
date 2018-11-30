package com.phatle.smartrestaurant;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NationalActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomBarAdapter pagerAdapter;
    RestaurantDrawerItem IntentItem;

    TextView toolbarTitle;
    ImageView backBtn;
    private SOService mService;

    public List<NationalTeamResponse> mList;

    public InterfacePassNationalteam mListener;
    public interface InterfacePassNationalteam{
        //gửi Data từ API khi load xong vô Fragment RestaurantSearch
        void onPass(List<NationalTeamResponse> list);
    }
    public void setListener(InterfacePassNationalteam listener){
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national);

        tabLayout = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        toolbarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.btn_menu);
        toolbarTitle.setText(getString(R.string.random));
        mService = ApiUtils.getSOService();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new RandomFragment());
        pagerAdapter.addFragments(new FragmentTest());
        pagerAdapter.addFragments(new FragmentTest());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
        tv1.setScaleY(-1);
        tv1.setText("Bốc thăm");
        TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
        tv2.setScaleY(-1);
        tv2.setText("Lịch sử");
        TextView tv3 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(2)).getChildAt(1));
        tv3.setScaleY(-1);
        tv3.setText("BXH");

        loadAnswers();
    }
    public void loadAnswers(){

        final SweetAlertDialog pDialog = new SweetAlertDialog(NationalActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mService.getNationalAnswers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NationalTeamResponse>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Phat","onComplete");
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phat","onError");
                        pDialog.dismiss();
                        new SweetAlertDialog(NationalActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                    public void onNext(List<NationalTeamResponse> listResponse) {
                        mList = listResponse;
                        pDialog.dismiss();
                        if(mListener != null)
                        {
                            mListener.onPass(listResponse);

                        }

                    }
                });

    }
}
