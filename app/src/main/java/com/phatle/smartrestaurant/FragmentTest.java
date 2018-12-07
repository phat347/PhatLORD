package com.phatle.smartrestaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;



public class FragmentTest extends Fragment{

    SparkButton btn;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout, container, false);

        btn = view.findViewById(R.id.btn_spark);

        btn.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState)
                {
                    Log.d("Phat","  Đã thích");
                }
                else {
                    Log.d("Phat","  Bỏ thích");
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

//        TextView mTitle = ((ProfileActivity) getActivity()).findViewById(R.id.toolbar_title);
//        mTitle.setText("Test");
//        int[] s = {R.drawable.ic_circle,R.drawable.ic_circle,R.drawable.ic_save,R.drawable.ic_circle,R.drawable.ic_warning,R.drawable.ic_circle,R.drawable.ic_circle,R.drawable.ic_circle,R.drawable.ic_circle};
//
//        DragItemAdapter mAdapter = new DragItemAdapter(s,getContext());
//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        return view;
    }
}
