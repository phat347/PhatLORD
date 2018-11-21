package com.phatle.smartrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment{

    RestaurantDrawerItem IntentItem;
    CommentItemAdapter mAdapter;
    private List<CommentItem> mRestaurantComment = new ArrayList<>();
    Button btnRating;
    SeekBar seekBar;

    FloatingActionButton btnSend;
    FrameLayout seekbarLayout;
    float value = (float) 5.0;
    boolean ratingFlag = false;
    RelativeLayout containerLayout;
    TextInputEditText etComment;
    TextInputLayout textInputLayoutComment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_fragment, container, false);

        containerLayout = view.findViewById(R.id.container);
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
            }
        });
        Intent intent = getActivity().getIntent();
        IntentItem = (RestaurantDrawerItem) intent.getSerializableExtra("RestaurantDetail");
        etComment = view.findViewById(R.id.et_user);
        textInputLayoutComment = view.findViewById(R.id.etUsernameLayout);
        btnSend = view.findViewById(R.id.fab);
        seekbarLayout = view.findViewById(R.id.seekbarLayout);
        seekBar = view.findViewById(R.id.seekbar);
        btnRating = view.findViewById(R.id.btn_rating);
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seekbarLayout.getVisibility() == View.VISIBLE)
                {
                    seekbarLayout.setVisibility(View.GONE);
                }
                else {
                    seekbarLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String comment = etComment.getText().toString();
                boolean valid= true;
                if (TextUtils.isEmpty(comment) || !ratingFlag)
                {
                    textInputLayoutComment.setError("Vui lòng nhập và rating");
                    valid = false;
                }
                else {
                    textInputLayoutComment.setError(null);
                }
                if(valid)
                {
                    mRestaurantComment.add(new CommentItem("android.resource://com.phatle.smartrestaurant/drawable/mu_mourinho",1,"Jose Mourinho",value,comment));
                    mAdapter.notifyDataSetChanged();
                    etComment.setText("");
                    etComment.clearFocus();
                    hideKeyboard(getActivity());
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String valueString;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value= (float) (i / 10.0);
                valueString = value+"/10";
                btnRating.setText(valueString);
                ratingFlag = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                btnRating.setText(valueString);
            }
        });

        mRestaurantComment = IntentItem.getComment();

        mAdapter = new CommentItemAdapter(mRestaurantComment,getContext());

        mAdapter.setListener(new CommentItemAdapter.InterfaceItemClick() {
            @Override
            public void onItemClick() {
                hideKeyboard(getActivity());
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


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
