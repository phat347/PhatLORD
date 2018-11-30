package com.phatle.smartrestaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RandomFragment extends Fragment {

    private List<NationalTeamResponse> mList = new ArrayList<>();
    private NationalTeamAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);




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
            }
        });


        return view;
    }
}
