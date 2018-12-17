package com.phatle.smartrestaurant;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.List;



public class FragmentTest extends Fragment{

    SparkButton btn;
    NotificationBadge mBadge,mBadge2,mBadge3,mBadge4,mBadge5,mBadge6,mBadge7,mBadge8,mBadge9;
    FloatingActionButton checkImg;
    ImageView swipeTest,swipeTest2,swipeTest3,swipeTest4,swipeTest5,swipeTest6,swipeTest7,swipeTest8,swipeTest9;
    float initialX, initialY;

    TextView itemName1,itemName2,itemName3,itemName4,itemName5,itemName6,itemName7,itemName8,itemName9,itemPrice1,itemPrice2,itemPrice3,itemPrice4,itemPrice5,itemPrice6,itemPrice7,itemPrice8,itemPrice9;
    FrameLayout holder1,holder2,holder3,holder4,holder5,holder6,holder7,holder8,holder9;
    List<DragItem> mList = new ArrayList<>();
    List<DragItem> mListShop = new ArrayList<>();
    DragItemAdapter mAdapter;
    int countBadge,countBadge2,countBadge3,countBadge4,countBadge5,countBadge6,countBadge7,countBadge8,countBadge9 = 0;
    int listSize;

    LinearLayout shelf2,shelf3;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout, container, false);

        mListShop.clear();
        mListShop.add(new DragItem(R.drawable.ic_cake,"Bánh","10",1));
        mListShop.add(new DragItem(R.drawable.ic_milk,"Sữa","20",1));
        mListShop.add(new DragItem(R.drawable.ic_candy,"Kẹo","5",1));
        mListShop.add(new DragItem(R.drawable.ic_candy,"Kẹo","5",1));
        mListShop.add(new DragItem(R.drawable.ic_candy,"Kẹo","5",1));
        mListShop.add(new DragItem(R.drawable.ic_milk,"Sữa","20",1));
        mListShop.add(new DragItem(R.drawable.ic_milk,"Sữa","20",1));
        mListShop.add(new DragItem(R.drawable.ic_candy,"Kẹo","5",1));
        mListShop.add(new DragItem(R.drawable.ic_cake,"Bánh","10",1));
        mList.clear();

        listSize = mListShop.size();

        int centerWidth = this.getResources().getDisplayMetrics().widthPixels/2;

//        btn = view.findViewById(R.id.btn_spark);

        checkImg = view.findViewById(R.id.check);
        checkImg.hide();

        mBadge = view.findViewById(R.id.badge);
        mBadge2 = view.findViewById(R.id.badge2);
        mBadge3 = view.findViewById(R.id.badge3);
        mBadge4 = view.findViewById(R.id.badge4);
        mBadge5 = view.findViewById(R.id.badge5);
        mBadge6 = view.findViewById(R.id.badge6);
        mBadge7 = view.findViewById(R.id.badge7);
        mBadge8 = view.findViewById(R.id.badge8);
        mBadge9 = view.findViewById(R.id.badge9);

        holder1 = view.findViewById(R.id.holder_item1);
        holder2 = view.findViewById(R.id.holder_item2);
        holder3 = view.findViewById(R.id.holder_item3);
        holder4 = view.findViewById(R.id.holder_item4);
        holder5 = view.findViewById(R.id.holder_item5);
        holder6 = view.findViewById(R.id.holder_item6);
        holder7 = view.findViewById(R.id.holder_item7);
        holder8 = view.findViewById(R.id.holder_item8);
        holder9 = view.findViewById(R.id.holder_item9);

        itemName1 = view.findViewById(R.id.item_1);
        itemName2 = view.findViewById(R.id.item_2);
        itemName3 = view.findViewById(R.id.item_3);
        itemName4 = view.findViewById(R.id.item_4);
        itemName5 = view.findViewById(R.id.item_5);
        itemName6 = view.findViewById(R.id.item_6);
        itemName7 = view.findViewById(R.id.item_7);
        itemName8 = view.findViewById(R.id.item_8);
        itemName9 = view.findViewById(R.id.item_9);

        itemPrice1 = view.findViewById(R.id.item_price1);
        itemPrice2 = view.findViewById(R.id.item_price2);
        itemPrice3 = view.findViewById(R.id.item_price3);
        itemPrice4 = view.findViewById(R.id.item_price4);
        itemPrice5 = view.findViewById(R.id.item_price5);
        itemPrice6 = view.findViewById(R.id.item_price6);
        itemPrice7 = view.findViewById(R.id.item_price7);
        itemPrice8 = view.findViewById(R.id.item_price8);
        itemPrice9 = view.findViewById(R.id.item_price9);

        swipeTest = view.findViewById(R.id.swipeImg);
        swipeTest2 = view.findViewById(R.id.swipeImg2);
        swipeTest3 = view.findViewById(R.id.swipeImg3);
        swipeTest4 = view.findViewById(R.id.swipeImg4);
        swipeTest5 = view.findViewById(R.id.swipeImg5);
        swipeTest6 = view.findViewById(R.id.swipeImg6);
        swipeTest7 = view.findViewById(R.id.swipeImg7);
        swipeTest8 = view.findViewById(R.id.swipeImg8);
        swipeTest9 = view.findViewById(R.id.swipeImg9);

        shelf2 = view.findViewById(R.id.shelf_holder2);
        shelf3 = view.findViewById(R.id.shelf_holder3);
        switch (listSize)
        {
            case 1:
                holder2.setVisibility(View.INVISIBLE);
                holder3.setVisibility(View.INVISIBLE);

                shelf2.setVisibility(View.GONE);
                shelf3.setVisibility(View.GONE);
                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                break;
            case 2:
                holder3.setVisibility(View.INVISIBLE);

                shelf2.setVisibility(View.GONE);
                shelf3.setVisibility(View.GONE);
                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                break;
            case 3:

                shelf2.setVisibility(View.GONE);
                shelf3.setVisibility(View.GONE);

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                break;
            case 4:


                holder5.setVisibility(View.INVISIBLE);
                holder6.setVisibility(View.INVISIBLE);
                shelf3.setVisibility(View.GONE);

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                itemName4.setText(mListShop.get(3).getName());
                itemPrice4.setText(mListShop.get(3).getPrice()+"$");
                swipeTest4.setImageResource(mListShop.get(3).getImgRes());
                break;
            case 5:

                holder6.setVisibility(View.INVISIBLE);
                shelf3.setVisibility(View.GONE);

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                itemName4.setText(mListShop.get(3).getName());
                itemPrice4.setText(mListShop.get(3).getPrice()+"$");
                swipeTest4.setImageResource(mListShop.get(3).getImgRes());
                itemName5.setText(mListShop.get(4).getName());
                itemPrice5.setText(mListShop.get(4).getPrice()+"$");
                swipeTest5.setImageResource(mListShop.get(4).getImgRes());
                break;
            case 6:


                shelf3.setVisibility(View.GONE);

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                itemName4.setText(mListShop.get(3).getName());
                itemPrice4.setText(mListShop.get(3).getPrice()+"$");
                swipeTest4.setImageResource(mListShop.get(3).getImgRes());
                itemName5.setText(mListShop.get(4).getName());
                itemPrice5.setText(mListShop.get(4).getPrice()+"$");
                swipeTest5.setImageResource(mListShop.get(4).getImgRes());
                itemName6.setText(mListShop.get(5).getName());
                itemPrice6.setText(mListShop.get(5).getPrice()+"$");
                swipeTest6.setImageResource(mListShop.get(5).getImgRes());
                break;
            case 7:

                holder8.setVisibility(View.INVISIBLE);
                holder9.setVisibility(View.INVISIBLE);

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                itemName4.setText(mListShop.get(3).getName());
                itemPrice4.setText(mListShop.get(3).getPrice()+"$");
                swipeTest4.setImageResource(mListShop.get(3).getImgRes());
                itemName5.setText(mListShop.get(4).getName());
                itemPrice5.setText(mListShop.get(4).getPrice()+"$");
                swipeTest5.setImageResource(mListShop.get(4).getImgRes());
                itemName6.setText(mListShop.get(5).getName());
                itemPrice6.setText(mListShop.get(5).getPrice()+"$");
                swipeTest6.setImageResource(mListShop.get(5).getImgRes());
                itemName7.setText(mListShop.get(6).getName());
                itemPrice7.setText(mListShop.get(6).getPrice()+"$");
                swipeTest7.setImageResource(mListShop.get(6).getImgRes());
                break;
            case 8:

                holder9.setVisibility(View.INVISIBLE);

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                itemName4.setText(mListShop.get(3).getName());
                itemPrice4.setText(mListShop.get(3).getPrice()+"$");
                swipeTest4.setImageResource(mListShop.get(3).getImgRes());
                itemName5.setText(mListShop.get(4).getName());
                itemPrice5.setText(mListShop.get(4).getPrice()+"$");
                swipeTest5.setImageResource(mListShop.get(4).getImgRes());
                itemName6.setText(mListShop.get(5).getName());
                itemPrice6.setText(mListShop.get(5).getPrice()+"$");
                swipeTest6.setImageResource(mListShop.get(5).getImgRes());
                itemName7.setText(mListShop.get(6).getName());
                itemPrice7.setText(mListShop.get(6).getPrice()+"$");
                swipeTest7.setImageResource(mListShop.get(6).getImgRes());
                itemName8.setText(mListShop.get(7).getName());
                itemPrice8.setText(mListShop.get(7).getPrice()+"$");
                swipeTest8.setImageResource(mListShop.get(7).getImgRes());
                break;
            case 9:

                itemName1.setText(mListShop.get(0).getName());
                itemPrice1.setText(mListShop.get(0).getPrice()+"$");
                swipeTest.setImageResource(mListShop.get(0).getImgRes());
                itemName2.setText(mListShop.get(1).getName());
                itemPrice2.setText(mListShop.get(1).getPrice()+"$");
                swipeTest2.setImageResource(mListShop.get(1).getImgRes());
                itemName3.setText(mListShop.get(2).getName());
                itemPrice3.setText(mListShop.get(2).getPrice()+"$");
                swipeTest3.setImageResource(mListShop.get(2).getImgRes());
                itemName4.setText(mListShop.get(3).getName());
                itemPrice4.setText(mListShop.get(3).getPrice()+"$");
                swipeTest4.setImageResource(mListShop.get(3).getImgRes());
                itemName5.setText(mListShop.get(4).getName());
                itemPrice5.setText(mListShop.get(4).getPrice()+"$");
                swipeTest5.setImageResource(mListShop.get(4).getImgRes());
                itemName6.setText(mListShop.get(5).getName());
                itemPrice6.setText(mListShop.get(5).getPrice()+"$");
                swipeTest6.setImageResource(mListShop.get(5).getImgRes());
                itemName7.setText(mListShop.get(6).getName());
                itemPrice7.setText(mListShop.get(6).getPrice()+"$");
                swipeTest7.setImageResource(mListShop.get(6).getImgRes());
                itemName8.setText(mListShop.get(7).getName());
                itemPrice8.setText(mListShop.get(7).getPrice()+"$");
                swipeTest8.setImageResource(mListShop.get(7).getImgRes());
                itemName9.setText(mListShop.get(8).getName());
                itemPrice9.setText(mListShop.get(8).getPrice()+"$");
                swipeTest9.setImageResource(mListShop.get(8).getImgRes());
                break;
                default:
                    break;
        }


        swipeTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {



                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();



                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);


                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();


                            mList.add(mListShop.get(0));
                            mAdapter.updateAnswers(mList);
                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest.getX(),centerWidth); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest.getY(), swipeTest.getY()+800); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge.setNumber(++countBadge);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });

                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:

                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        break;
                }



                return true;
            }
        });


        swipeTest2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest2, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest2, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        Log.d("Phat","Action move");

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest2, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest2, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(mListShop.get(1));

                            mAdapter.updateAnswers(mList);
                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest2.getX(),swipeTest2.getX()); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest2.getY(), swipeTest2.getY()+1000); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest2, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge2.setNumber(++countBadge2);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:



                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        Log.d("Phat","Action outside");

                        break;
                }



                return true;
            }
        });



        swipeTest3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();



                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest3, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest3, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest3, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest3, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(mListShop.get(2));
                            mAdapter.updateAnswers(mList);

                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest3.getX(),-centerWidth); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest3.getY(), swipeTest3.getY()+1000); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest3, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge3.setNumber(++countBadge3);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:

                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        break;
                }



                return true;
            }
        });


        swipeTest4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {



                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest4, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest4, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();



                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);


                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest4, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest4, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();


                            mList.add(mListShop.get(3));
                            mAdapter.updateAnswers(mList);
                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest4.getX(),centerWidth); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest4.getY(), swipeTest4.getY()+800); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest4, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge4.setNumber(++countBadge4);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });

                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:

                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        break;
                }



                return true;
            }
        });


        swipeTest7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {



                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest7, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest7, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();



                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);


                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest7, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest7, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();


                            mList.add(mListShop.get(6));
                            mAdapter.updateAnswers(mList);
                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest7.getX(),centerWidth); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest7.getY(), swipeTest7.getY()+800); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest7, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge7.setNumber(++countBadge7);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });

                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:

                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        break;
                }



                return true;
            }
        });


        swipeTest5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest5, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest5, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        Log.d("Phat","Action move");

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest5, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest5, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(mListShop.get(4));

                            mAdapter.updateAnswers(mList);
                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest5.getX(),swipeTest5.getX()); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest5.getY(), swipeTest5.getY()+1000); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest5, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge5.setNumber(++countBadge5);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:



                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        Log.d("Phat","Action outside");

                        break;
                }



                return true;
            }
        });



        swipeTest8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest8, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest8, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        Log.d("Phat","Action move");

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest8, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest8, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(mListShop.get(7));

                            mAdapter.updateAnswers(mList);
                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest8.getX(),swipeTest8.getX()); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest8.getY(), swipeTest8.getY()+1000); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest8, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge8.setNumber(++countBadge8);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:



                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        Log.d("Phat","Action outside");

                        break;
                }



                return true;
            }
        });



        swipeTest6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();



                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest6, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest6, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest6, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest6, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(mListShop.get(5));
                            mAdapter.updateAnswers(mList);

                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest6.getX(),-centerWidth); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest6.getY(), swipeTest6.getY()+1000); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest6, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge6.setNumber(++countBadge6);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:

                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        break;
                }



                return true;
            }
        });



        swipeTest9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();



                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest9, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest9, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);



                        AnimatorSet scaleDown = new AnimatorSet();


                        scaleDown.play(scaleDownX).with(scaleDownY);


                        scaleDown.start();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(true);
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();

                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(swipeTest9, "scaleX", 1.0f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(swipeTest9, "scaleY", 1.0f);
                        scaleDownX2.setDuration(500);
                        scaleDownY2.setDuration(500);



                        AnimatorSet scaleDown2 = new AnimatorSet();


                        scaleDown2.play(scaleDownX2).with(scaleDownY2);


                        scaleDown2.start();




                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(mListShop.get(8));
                            mAdapter.updateAnswers(mList);

                            PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("X", swipeTest9.getX(),-centerWidth); // The view will be animated such that it moves to positionAnimateX.
                            PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("Y", swipeTest9.getY(), swipeTest9.getY()+1000); // The view will be animated such that it moves to positionAnimateY.
                            ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(swipeTest9, xHolder, yHolder);
                            valueAnimator.setDuration(300);
                            valueAnimator.start();
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    animator.removeListener(this);
                                    animator.setDuration(0);
                                    animator.setInterpolator(new ReverseInterpolator());
                                    animator.start();
                                    checkImg.show();
                                    mBadge9.setNumber(++countBadge9);
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            if(checkImg.isShown())
                                            {
                                                checkImg.hide();
                                            }
                                        }
                                    }, 800);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        if (initialY > finalY) {

                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:

                        break;

                    case MotionEvent.ACTION_OUTSIDE:

                        break;
                }



                return true;
            }
        });



//        btn.setEventListener(new SparkEventListener() {
//            @Override
//            public void onEvent(ImageView button, boolean buttonState) {
//                if(buttonState)
//                {
//                    Log.d("Phat","  Đã thích");
//
//                }
//                else {
//                    Log.d("Phat","  Bỏ thích");
//
//                }
//            }
//
//            @Override
//            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
//
//            }
//
//            @Override
//            public void onEventAnimationStart(ImageView button, boolean buttonState) {
//
//            }
//        });

//        TextView mTitle = ((ProfileActivity) getActivity()).findViewById(R.id.toolbar_title);
//        mTitle.setText("Test");

//
        mAdapter = new DragItemAdapter(mList,getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }
    public class ReverseInterpolator implements Interpolator {

        private final Interpolator delegate;

        public ReverseInterpolator(Interpolator delegate){
            this.delegate = delegate;
        }

        public ReverseInterpolator(){
            this(new LinearInterpolator());
        }

        @Override
        public float getInterpolation(float input) {
            return 1 - delegate.getInterpolation(input);
        }
    }
}
