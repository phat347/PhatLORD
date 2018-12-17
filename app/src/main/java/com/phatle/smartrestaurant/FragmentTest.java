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
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.List;



public class FragmentTest extends Fragment{

    SparkButton btn;
    NotificationBadge mBadge,mBadge2,mBadge3;
    FloatingActionButton checkImg;
    ImageView swipeTest,swipeTest2,swipeTest3;
    float initialX, initialY;

    TextView itemName1,itemName2,itemName3,itemPrice1,itemPrice2,itemPrice3;
    FrameLayout holder1,holder2,holder3;
    List<DragItem> mList = new ArrayList<>();
    DragItemAdapter mAdapter;
    int countBadge,countBadge2,countBadge3 = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout, container, false);

        mList.clear();
        int centerWidth = this.getResources().getDisplayMetrics().widthPixels/2;

//        btn = view.findViewById(R.id.btn_spark);

        checkImg = view.findViewById(R.id.check);
        checkImg.hide();

        mBadge = view.findViewById(R.id.badge);
        mBadge2 = view.findViewById(R.id.badge2);
        mBadge3 = view.findViewById(R.id.badge3);

        holder1 = view.findViewById(R.id.holder_item1);
        holder2 = view.findViewById(R.id.holder_item2);
        holder3 = view.findViewById(R.id.holder_item3);

        itemName1 = view.findViewById(R.id.item_1);
        itemName2 = view.findViewById(R.id.item_2);
        itemName3 = view.findViewById(R.id.item_3);

        itemPrice1 = view.findViewById(R.id.item_price1);
        itemPrice2 = view.findViewById(R.id.item_price2);
        itemPrice3 = view.findViewById(R.id.item_price3);

        swipeTest = view.findViewById(R.id.swipeImg);
        swipeTest2 = view.findViewById(R.id.swipeImg2);
        swipeTest3 = view.findViewById(R.id.swipeImg3);
        swipeTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                switch (action) {



                    case MotionEvent.ACTION_DOWN:
                        ((NationalActivity) getActivity()).viewPager.setPagingEnabled(false);

                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();
                        Log.d("Phat","Down   "+initialX+","+initialY);

                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);

//                        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(swipeTest, "translationY", -100);
//                        moveUpY.setDuration(1500);

                        AnimatorSet scaleDown = new AnimatorSet();
//                        AnimatorSet moveUp = new AnimatorSet();

                        scaleDown.play(scaleDownX).with(scaleDownY);
//                        moveUp.play(moveUpY);

                        scaleDown.start();
//                        moveUp.start();


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

//                        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(swipeTest, "translationY", -100);
//                        moveUpY.setDuration(1500);

                        AnimatorSet scaleDown2 = new AnimatorSet();
//                        AnimatorSet moveUp = new AnimatorSet();

                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
//                        moveUp.play(moveUpY);

                        scaleDown2.start();
                        Log.d("Phat","Up   "+finalX+","+finalY);



                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();


                            Log.d("Phat","   Bottom X:"+view.getBottom()+"  Bottom Y:"+view.getWidth()/2);

                            Log.d("Phat","  GetX:"+swipeTest.getX()+"  GetY:"+swipeTest.getY());
                            mList.add(new DragItem(R.drawable.ic_cake,"Bánh","10",1));
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
                        Log.d("Phat","Down   "+initialX+","+initialY);

                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest2, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest2, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);

//                        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(swipeTest, "translationY", -100);
//                        moveUpY.setDuration(1500);

                        AnimatorSet scaleDown = new AnimatorSet();
//                        AnimatorSet moveUp = new AnimatorSet();

                        scaleDown.play(scaleDownX).with(scaleDownY);
//                        moveUp.play(moveUpY);

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

//                        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(swipeTest, "translationY", -100);
//                        moveUpY.setDuration(1500);

                        AnimatorSet scaleDown2 = new AnimatorSet();
//                        AnimatorSet moveUp = new AnimatorSet();

                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
//                        moveUp.play(moveUpY);

                        scaleDown2.start();
                        Log.d("Phat","Up   "+finalX+","+finalY);



                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(new DragItem(R.drawable.ic_milk,"Sữa","20",1));
                            Log.d("Phat","   Bottom X:"+view.getBottom()+"  Bottom Y:"+view.getWidth()/2);

                            Log.d("Phat","  GetX:"+swipeTest2.getX()+"  GetY:"+swipeTest2.getY());
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
                        Log.d("Phat","Down   "+initialX+","+initialY);


                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(swipeTest3, "scaleX", 1.3f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(swipeTest3, "scaleY", 1.3f);
                        scaleDownX.setDuration(500);
                        scaleDownY.setDuration(500);

//                        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(swipeTest, "translationY", -100);
//                        moveUpY.setDuration(1500);

                        AnimatorSet scaleDown = new AnimatorSet();
//                        AnimatorSet moveUp = new AnimatorSet();

                        scaleDown.play(scaleDownX).with(scaleDownY);
//                        moveUp.play(moveUpY);

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

//                        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(swipeTest, "translationY", -100);
//                        moveUpY.setDuration(1500);

                        AnimatorSet scaleDown2 = new AnimatorSet();
//                        AnimatorSet moveUp = new AnimatorSet();

                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
//                        moveUp.play(moveUpY);

                        scaleDown2.start();
                        Log.d("Phat","Up   "+finalX+","+finalY);



                        if (initialX < finalX) {
//                            Toast.makeText(getContext(), "Swipe trái sang phải", Toast.LENGTH_SHORT).show();
                        }

                        if (initialX > finalX) {

//                            Toast.makeText(getContext(), "Swipe phải sang trái", Toast.LENGTH_SHORT).show();
                        }

                        if (finalY - initialY >= 40) {
//                            Toast.makeText(getContext(), "Swipe xuống", Toast.LENGTH_SHORT).show();

                            mList.add(new DragItem(R.drawable.ic_candy,"Kẹo","5",1));
                            mAdapter.updateAnswers(mList);
                            Log.d("Phat","   Bottom X:"+view.getBottom()+"  Bottom Y:"+view.getWidth()/2);

                            Log.d("Phat","  GetX:"+swipeTest3.getX()+"  GetY:"+swipeTest3.getY());
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
