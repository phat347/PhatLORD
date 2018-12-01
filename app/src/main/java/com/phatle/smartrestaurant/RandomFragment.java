package com.phatle.smartrestaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFragment extends Fragment {

    private List<NationalTeamResponse> mList = new ArrayList<>();
    private NationalTeamAdapter mAdapter;

    FloatingActionButton random_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_random, container, false);




        random_btn = view.findViewById(R.id.fab);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        ((NationalActivity) getActivity()).setListener(new NationalActivity.InterfacePassNationalteam() {
            @Override
            public void onPass(List<NationalTeamResponse> list) {
                mList = list;
                mAdapter = new NationalTeamAdapter(mList,getContext());
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if(dy < 0){
                            random_btn.show();
                        }
                        else if (dy > 0)
                        {
                            random_btn.hide();
                        }
                    }
                });
                random_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int randomNum = ThreadLocalRandom.current().nextInt(1, mList.size() + 1);
                        for (int i = 0; i < mList.size(); i++) {
                            if(mList.get(i).getId().equals(randomNum))
                            {
                                recyclerView.smoothScrollToPosition(i);
                                CustomDialogString dialog = new CustomDialogString(getContext(), "Số " + mList.get(i).getId(), "Bạn bốc trúng "+ mList.get(i).getName(),mList.get(i).getImg());
                                dialog.setupOkButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        random_btn.show();
                                    }
                                });
                                dialog.show();
                                dialog.setCanceledOnTouchOutside(false);
                            }

                        }
                    }
                });
            }
        });


        return view;
    }
}
