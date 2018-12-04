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

    TextView toolbarTitle;
    ImageView backBtn;
    private SOService mService;

    public List<NationalTeamResponse> mList;

    public List<MatchResponse> mListMatch;
    public InterfacePassNationalteam mListener;
    public interface InterfacePassNationalteam{
        void onPass(List<NationalTeamResponse> list);
    }
    public void setListener(InterfacePassNationalteam listener){
        mListener = listener;
    }

    public InterfacePassMatchItem mListenerMatch;
    public interface InterfacePassMatchItem{
        void onPass(List<MatchResponse> list);
    }
    public void setListenerMatch(InterfacePassMatchItem listener){
        mListenerMatch = listener;
    }

    public InterfacePassMatchItem2 mListenerRanked;
    public interface InterfacePassMatchItem2{
        void onPass(List<MatchResponse> list);
    }
    public void setListenerMatch2(InterfacePassMatchItem2 listener){
        mListenerRanked = listener;
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
        pagerAdapter.addFragments(new MatchHistoryFragment());
        pagerAdapter.addFragments(new RankTableFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());
//        pagerAdapter.addFragments(new RandomFragment());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("Ngành hàng 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Ngành hàng 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Ngành hàng 3"));
//        tabLayout.addTab(tabLayout.newTab().setText("Ngành hàng 4"));
//        tabLayout.addTab(tabLayout.newTab().setText("Ngành hàng 5"));
//        tabLayout.addTab(tabLayout.newTab().setText("Ngành hàng 6"));
//        TabLayout.Tab tab1 = tabLayout.getTabAt(0);
//        TabLayout.Tab tab2 = tabLayout.getTabAt(1);
//        TabLayout.Tab tab3 = tabLayout.getTabAt(2);
//        TabLayout.Tab tab4 = tabLayout.getTabAt(3);
//        TabLayout.Tab tab5 = tabLayout.getTabAt(4);
//        TabLayout.Tab tab6 = tabLayout.getTabAt(5);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition()  == 0)
//                {
//                    viewPager.setCurrentItem(0,true);
//                }
//
//                if(tab.getPosition()  == 1)
//                {
//                    viewPager.setCurrentItem(3,true);
//                }
//                if(tab.getPosition()  == 2)
//                {
//                    viewPager.setCurrentItem(6,true);
//                }
//                if(tab.getPosition()  == 3)
//                {
//                    viewPager.setCurrentItem(9,true);
//                }
//                if(tab.getPosition()  == 4)
//                {
//                    viewPager.setCurrentItem(12,true);
//                }
//                if(tab.getPosition()  == 5)
//                {
//                    viewPager.setCurrentItem(15,true);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position<=2)
//                {
//                    tab1.select();
//                    if(position==2)
//                    {
//                        viewPager.setCurrentItem(2);
//                    }
//                }
//                else if(position <=5)
//                {
//                    tab2.select();
//                    if(position==5)
//                    {
//                        viewPager.setCurrentItem(5);
//                    }
//                }
//                else if(position <=8)
//                {
//                    tab3.select();
//                    if(position==8)
//                    {
//                        viewPager.setCurrentItem(8);
//                    }
//                }
//                else if(position <=11)
//                {
//                    tab4.select();
//                    if(position==11)
//                    {
//                        viewPager.setCurrentItem(11);
//                    }
//                }
//                else if(position <=14)
//                {
//                    tab5.select();
//                    if(position==14)
//                    {
//                        viewPager.setCurrentItem(14);
//                    }
//                }
//                else if(position <=17)
//                {
//                    tab6.select();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
        tv1.setScaleY(-1);
        tv1.setText("Bốc thăm");
//        tv1.setText("Ngành hàng 1");
        TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
        tv2.setScaleY(-1);
        tv2.setText("Lịch sử");
//        tv2.setText("Ngành hàng 2");
        TextView tv3 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(2)).getChildAt(1));
        tv3.setScaleY(-1);
        tv3.setText("BXH");
//        tv3.setText("Ngành hàng 3");
//        TextView tv4 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(3)).getChildAt(1));
//        tv4.setScaleY(-1);
//        tv4.setText("Ngành hàng 4");
//        TextView tv5 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(4)).getChildAt(1));
//        tv5.setScaleY(-1);

//        tv5.setText("Ngành hàng 5");
//        TextView tv6 = (TextView)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(5)).getChildAt(1));
//        tv6.setScaleY(-1);

//        tv6.setText("Ngành hàng 6");

        loadAnswers();
    }
    public void loadAnswers(){

        final SweetAlertDialog pDialog = new SweetAlertDialog(NationalActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mService.getMatchAnswers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MatchResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
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
                    public void onNext(List<MatchResponse> matchResponses) {
                        mListMatch = matchResponses;
                        if(mListenerMatch != null)
                        {
                            mListenerMatch.onPass(matchResponses);

                        }
                        if(mListenerRanked != null)
                        {
                            mListenerRanked.onPass(matchResponses);

                        }
                    }
                });
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
