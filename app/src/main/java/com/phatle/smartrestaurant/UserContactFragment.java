package com.phatle.smartrestaurant;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserContactFragment extends Fragment{


    private List<UserContact> mList = new ArrayList<>();

    private UserContactAdapter mAdapter;

    SwipeController swipeController = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);
//        TextView mTitle = ((ProfileActivity) getActivity()).findViewById(R.id.toolbar_title);
//        mTitle.setText("UserContact");

            mList.clear();
            mList.add(new UserContact("An", "1111111111111"));
            mList.add(new UserContact("Anh", "22222222222"));
            mList.add(new UserContact("Bao", "33333333333333"));
            mList.add(new UserContact("Binh", "44444444444"));
            mList.add(new UserContact("Hong", "55555555555555"));
            mList.add(new UserContact("Minh", "888888888888"));
            mList.add(new UserContact("minh ", "888888888888"));
            mList.add(new UserContact("mẫn", "888888888888"));
            mList.add(new UserContact("mạnh", "888888888888"));
            mList.add(new UserContact("Aaa", "888888888888"));
            mList.add(new UserContact("hòa", "888888888888"));
            mList.add(new UserContact("YYY", "888888888888"));
            mList.add(new UserContact("ZZZZ", "888888888888"));
            mList.add(new UserContact("BBBBBBB", "888888888888"));

            //Convert first letter to uppercase
            for (int i = 0; i < mList.size(); i++) {
                String cap = mList.get(i).getName().substring(0, 1).toUpperCase() + mList.get(i).getName().substring(1);
                mList.get(i).setName(cap);
            }
            //Sort list theo alphabet
            Collections.sort(mList, new UserContactComparator());

            //Add vô alphabet list
            String temp = "tmp";
            for (int i = 0; i < mList.size(); i++) {
                String mTemp = mList.get(i).getName().substring(0, 1);
                if (!mTemp.equals(temp)) {
                    mList.add(i, new UserContact(mTemp));
                    temp = mTemp;
                }
            }
        mAdapter = new UserContactAdapter(mList);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mList.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mList.get(position).getPhoneNumber()));
                startActivity(intent);
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


        return view;
    }
    private class UserContactComparator implements Comparator<UserContact> {

        public int compare(UserContact a, UserContact b)
        {
            return a.name.compareTo(b.name);
        }
    }

}
