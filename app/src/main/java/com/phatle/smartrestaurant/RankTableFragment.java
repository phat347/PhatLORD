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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankTableFragment extends Fragment {

    private List<MatchResponse> mList = new ArrayList<>();
    private List<Player> mListPlayer = new ArrayList<>();
    private PlayersAdapter mAdapter;



    Player playerBin,playerBi,playerBun,playerGon;
    int binMatch =0,binPoint=0,binGD=0,biMatch=0,biPoint=0,biGD=0,bunMatch=0,bunPoint=0,bunGD=0,gonMatch=0,gonPoint=0,gonGD =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        mList = ((NationalActivity) getActivity()).mListMatch;


        getBinStats();
        getBiStats();
        getBunStats();
        getGonStats();



        playerBin = new Player("Bin",R.drawable.ic_crown,binMatch,binPoint,binGD);

        playerBi =  new Player("Bi",R.drawable.ic_usersvg,biMatch,biPoint,biGD);

        playerBun =  new Player("Bun",R.drawable.ic_usersvg,bunMatch,bunPoint,bunGD);

        playerGon =  new Player("Gôn",R.drawable.ic_usersvg,gonMatch,gonPoint,gonGD);

        mListPlayer.clear();
        mListPlayer.add(playerBin);
        mListPlayer.add(playerBi);
        mListPlayer.add(playerBun);
        mListPlayer.add(playerGon);

        // sort theo điểm
        Collections.sort(mListPlayer, new PlayerPointComparator());

        // sort theo hiệu số
        Collections.sort(mListPlayer, new PlayerGDComparator());

        mAdapter = new PlayersAdapter(mListPlayer,getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));



        return view;
    }
    public void getBinStats(){

        binMatch = 0;
        binPoint = 0;
        binGD = 0;
        if (mList.get(0).getHomeScore() != null)
        {
            binMatch ++;
            if(Integer.parseInt(mList.get(0).getHomeScore()) > Integer.parseInt(mList.get(0).getAwayScore()))
            {

                binPoint = binPoint + 3;
            }
            else if(Integer.parseInt(mList.get(0).getHomeScore()) == Integer.parseInt(mList.get(0).getAwayScore()))
            {
                binPoint = binPoint + 1;
            }

            binGD = binGD + Integer.parseInt(mList.get(0).getHomeScore()) - Integer.parseInt(mList.get(0).getAwayScore());
        }
        if (mList.get(2).getAwayScore() != null)
        {
            binMatch ++;
            if(Integer.parseInt(mList.get(2).getAwayScore()) > Integer.parseInt(mList.get(2).getHomeScore()))
            {

                binPoint = binPoint + 3;
            }
            else if(Integer.parseInt(mList.get(2).getAwayScore()) == Integer.parseInt(mList.get(2).getHomeScore()))
            {
                binPoint = binPoint + 1;
            }

            binGD = binGD + Integer.parseInt(mList.get(2).getAwayScore()) - Integer.parseInt(mList.get(2).getHomeScore());
        }
        if (mList.get(4).getAwayScore() != null)
        {
            binMatch ++;
            if(Integer.parseInt(mList.get(4).getAwayScore()) > Integer.parseInt(mList.get(4).getHomeScore()))
            {

                binPoint = binPoint + 3;
            }
            else if(Integer.parseInt(mList.get(4).getAwayScore()) == Integer.parseInt(mList.get(4).getHomeScore()))
            {
                binPoint = binPoint + 1;
            }

            binGD = binGD + Integer.parseInt(mList.get(4).getAwayScore()) - Integer.parseInt(mList.get(4).getHomeScore());

        }
        if (mList.get(5).getAwayScore() != null)
        {
            binMatch ++;
            if(Integer.parseInt(mList.get(5).getAwayScore()) > Integer.parseInt(mList.get(5).getHomeScore()))
            {

                binPoint = binPoint + 3;
            }
            else if(Integer.parseInt(mList.get(5).getAwayScore()) == Integer.parseInt(mList.get(5).getHomeScore()))
            {
                binPoint = binPoint + 1;
            }

            binGD = binGD + Integer.parseInt(mList.get(5).getAwayScore()) - Integer.parseInt(mList.get(5).getHomeScore());
        }
        if (mList.get(7).getHomeScore() != null)
        {
            binMatch ++;
            if(Integer.parseInt(mList.get(7).getHomeScore()) > Integer.parseInt(mList.get(7).getAwayScore()))
            {

                binPoint = binPoint + 3;
            }
            else if(Integer.parseInt(mList.get(7).getHomeScore()) == Integer.parseInt(mList.get(7).getAwayScore()))
            {
                binPoint = binPoint + 1;
            }

            binGD = binGD + Integer.parseInt(mList.get(7).getHomeScore()) - Integer.parseInt(mList.get(7).getAwayScore());
        }
        if (mList.get(8).getHomeScore() != null)
        {
            binMatch ++;
            if(Integer.parseInt(mList.get(8).getHomeScore()) > Integer.parseInt(mList.get(8).getAwayScore()))
            {

                binPoint = binPoint + 3;
            }
            else if(Integer.parseInt(mList.get(8).getHomeScore()) == Integer.parseInt(mList.get(8).getAwayScore()))
            {
                binPoint = binPoint + 1;
            }

            binGD = binGD + Integer.parseInt(mList.get(8).getHomeScore()) - Integer.parseInt(mList.get(8).getAwayScore());
        }
    }
    public void getBiStats(){
        biMatch = 0;
        biPoint = 0;
        biGD = 0;
        if (mList.get(3).getHomeScore() != null)
        {
            biMatch ++;
            if(Integer.parseInt(mList.get(3).getHomeScore()) > Integer.parseInt(mList.get(3).getAwayScore()))
            {

                biPoint = biPoint + 3;
            }
            else if(Integer.parseInt(mList.get(3).getHomeScore()) == Integer.parseInt(mList.get(3).getAwayScore()))
            {
                biPoint = biPoint + 1;
            }

            biGD = biGD + Integer.parseInt(mList.get(3).getHomeScore()) - Integer.parseInt(mList.get(3).getAwayScore());
        }
        if (mList.get(0).getAwayScore() != null)
        {
            biMatch ++;
            if(Integer.parseInt(mList.get(0).getAwayScore()) > Integer.parseInt(mList.get(0).getHomeScore()))
            {

                biPoint = biPoint + 3;
            }
            else if(Integer.parseInt(mList.get(0).getAwayScore()) == Integer.parseInt(mList.get(0).getHomeScore()))
            {
                biPoint = biPoint + 1;
            }

            biGD = biGD + Integer.parseInt(mList.get(0).getAwayScore()) - Integer.parseInt(mList.get(0).getHomeScore());
        }
        if (mList.get(9).getAwayScore() != null)
        {
            biMatch ++;
            if(Integer.parseInt(mList.get(9).getAwayScore()) > Integer.parseInt(mList.get(9).getHomeScore()))
            {

                biPoint = biPoint + 3;
            }
            else if(Integer.parseInt(mList.get(9).getAwayScore()) == Integer.parseInt(mList.get(9).getHomeScore()))
            {
                biPoint = biPoint + 1;
            }

            biGD = biGD + Integer.parseInt(mList.get(9).getAwayScore()) - Integer.parseInt(mList.get(9).getHomeScore());

        }
        if (mList.get(10).getAwayScore() != null)
        {
            biMatch ++;
            if(Integer.parseInt(mList.get(10).getAwayScore()) > Integer.parseInt(mList.get(10).getHomeScore()))
            {

                biPoint = biPoint + 3;
            }
            else if(Integer.parseInt(mList.get(10).getAwayScore()) == Integer.parseInt(mList.get(10).getHomeScore()))
            {
                biPoint = biPoint + 1;
            }

            biGD = biGD + Integer.parseInt(mList.get(10).getAwayScore()) - Integer.parseInt(mList.get(10).getHomeScore());
        }
        if (mList.get(5).getHomeScore() != null)
        {
            biMatch ++;
            if(Integer.parseInt(mList.get(5).getHomeScore()) > Integer.parseInt(mList.get(5).getAwayScore()))
            {

                biPoint = biPoint + 3;
            }
            else if(Integer.parseInt(mList.get(5).getHomeScore()) == Integer.parseInt(mList.get(5).getAwayScore()))
            {
                biPoint = biPoint + 1;
            }

            biGD = biGD + Integer.parseInt(mList.get(5).getHomeScore()) - Integer.parseInt(mList.get(5).getAwayScore());
        }
        if (mList.get(11).getHomeScore() != null)
        {
            biMatch ++;
            if(Integer.parseInt(mList.get(11).getHomeScore()) > Integer.parseInt(mList.get(11).getAwayScore()))
            {

                biPoint = biPoint + 3;
            }
            else if(Integer.parseInt(mList.get(11).getHomeScore()) == Integer.parseInt(mList.get(11).getAwayScore()))
            {
                biPoint = biPoint + 1;
            }

            biGD = biGD + Integer.parseInt(mList.get(11).getHomeScore()) - Integer.parseInt(mList.get(11).getAwayScore());
        }
    }
    public void getBunStats(){
        bunMatch = 0;
        bunPoint = 0;
        bunGD = 0;
        if (mList.get(1).getHomeScore() != null)
        {
            bunMatch ++;
            if(Integer.parseInt(mList.get(1).getHomeScore()) > Integer.parseInt(mList.get(1).getAwayScore()))
            {

                bunPoint = bunPoint + 3;
            }
            else if(Integer.parseInt(mList.get(1).getHomeScore()) == Integer.parseInt(mList.get(1).getAwayScore()))
            {
                bunPoint = bunPoint + 1;
            }

            bunGD = bunGD + Integer.parseInt(mList.get(1).getHomeScore()) - Integer.parseInt(mList.get(1).getAwayScore());
        }
        if (mList.get(3).getAwayScore() != null)
        {
            bunMatch ++;
            if(Integer.parseInt(mList.get(3).getAwayScore()) > Integer.parseInt(mList.get(3).getHomeScore()))
            {

                bunPoint = bunPoint + 3;
            }
            else if(Integer.parseInt(mList.get(3).getAwayScore()) == Integer.parseInt(mList.get(3).getHomeScore()))
            {
                bunPoint = bunPoint + 1;
            }

            bunGD = bunGD + Integer.parseInt(mList.get(3).getAwayScore()) - Integer.parseInt(mList.get(3).getHomeScore());
        }
        if (mList.get(6).getAwayScore() != null)
        {
            bunMatch ++;
            if(Integer.parseInt(mList.get(6).getAwayScore()) > Integer.parseInt(mList.get(6).getHomeScore()))
            {

                bunPoint = bunPoint + 3;
            }
            else if(Integer.parseInt(mList.get(6).getAwayScore()) == Integer.parseInt(mList.get(6).getHomeScore()))
            {
                bunPoint = bunPoint + 1;
            }

            bunGD = bunGD + Integer.parseInt(mList.get(6).getAwayScore()) - Integer.parseInt(mList.get(6).getHomeScore());

        }
        if (mList.get(8).getAwayScore() != null)
        {
            bunMatch ++;
            if(Integer.parseInt(mList.get(8).getAwayScore()) > Integer.parseInt(mList.get(8).getHomeScore()))
            {

                bunPoint = bunPoint + 3;
            }
            else if(Integer.parseInt(mList.get(8).getAwayScore()) == Integer.parseInt(mList.get(8).getHomeScore()))
            {
                bunPoint = bunPoint + 1;
            }

            bunGD = bunGD + Integer.parseInt(mList.get(8).getAwayScore()) - Integer.parseInt(mList.get(8).getHomeScore());
        }
        if (mList.get(4).getHomeScore() != null)
        {
            bunMatch ++;
            if(Integer.parseInt(mList.get(4).getHomeScore()) > Integer.parseInt(mList.get(4).getAwayScore()))
            {

                bunPoint = bunPoint + 3;
            }
            else if(Integer.parseInt(mList.get(4).getHomeScore()) == Integer.parseInt(mList.get(4).getAwayScore()))
            {
                bunPoint = bunPoint + 1;
            }

            bunGD = bunGD + Integer.parseInt(mList.get(4).getHomeScore()) - Integer.parseInt(mList.get(4).getAwayScore());
        }
        if (mList.get(9).getHomeScore() != null)
        {
            bunMatch ++;
            if(Integer.parseInt(mList.get(9).getHomeScore()) > Integer.parseInt(mList.get(9).getAwayScore()))
            {

                bunPoint = bunPoint + 3;
            }
            else if(Integer.parseInt(mList.get(9).getHomeScore()) == Integer.parseInt(mList.get(9).getAwayScore()))
            {
                bunPoint = bunPoint + 1;
            }

            bunGD = bunGD + Integer.parseInt(mList.get(9).getHomeScore()) - Integer.parseInt(mList.get(9).getAwayScore());
        }
    }
    public void getGonStats(){
        gonMatch = 0;
        gonPoint = 0;
        gonGD = 0;
        if (mList.get(2).getHomeScore() != null)
        {
            gonMatch ++;
            if(Integer.parseInt(mList.get(2).getHomeScore()) > Integer.parseInt(mList.get(2).getAwayScore()))
            {

                gonPoint = gonPoint + 3;
            }
            else if(Integer.parseInt(mList.get(2).getHomeScore()) == Integer.parseInt(mList.get(2).getAwayScore()))
            {
                gonPoint = gonPoint + 1;
            }

            gonGD = gonGD + Integer.parseInt(mList.get(2).getHomeScore()) - Integer.parseInt(mList.get(2).getAwayScore());
        }
        if (mList.get(1).getAwayScore() != null)
        {
            gonMatch ++;
            if(Integer.parseInt(mList.get(1).getAwayScore()) > Integer.parseInt(mList.get(1).getHomeScore()))
            {

                gonPoint = gonPoint + 3;
            }
            else if(Integer.parseInt(mList.get(1).getAwayScore()) == Integer.parseInt(mList.get(1).getHomeScore()))
            {
                gonPoint = gonPoint + 1;
            }

            gonGD = gonGD + Integer.parseInt(mList.get(1).getAwayScore()) - Integer.parseInt(mList.get(1).getHomeScore());
        }
        if (mList.get(7).getAwayScore() != null)
        {
            gonMatch ++;
            if(Integer.parseInt(mList.get(7).getAwayScore()) > Integer.parseInt(mList.get(7).getHomeScore()))
            {

                gonPoint = gonPoint + 3;
            }
            else if(Integer.parseInt(mList.get(7).getAwayScore()) == Integer.parseInt(mList.get(7).getHomeScore()))
            {
                gonPoint = gonPoint + 1;
            }

            gonGD = gonGD + Integer.parseInt(mList.get(7).getAwayScore()) - Integer.parseInt(mList.get(7).getHomeScore());

        }
        if (mList.get(11).getAwayScore() != null)
        {
            gonMatch ++;
            if(Integer.parseInt(mList.get(11).getAwayScore()) > Integer.parseInt(mList.get(11).getHomeScore()))
            {

                gonPoint = gonPoint + 3;
            }
            else if(Integer.parseInt(mList.get(11).getAwayScore()) == Integer.parseInt(mList.get(11).getHomeScore()))
            {
                gonPoint = gonPoint + 1;
            }

            gonGD = gonGD + Integer.parseInt(mList.get(11).getAwayScore()) - Integer.parseInt(mList.get(11).getHomeScore());
        }
        if (mList.get(6).getHomeScore() != null)
        {
            gonMatch ++;
            if(Integer.parseInt(mList.get(6).getHomeScore()) > Integer.parseInt(mList.get(6).getAwayScore()))
            {

                gonPoint = gonPoint + 3;
            }
            else if(Integer.parseInt(mList.get(6).getHomeScore()) == Integer.parseInt(mList.get(6).getAwayScore()))
            {
                gonPoint = gonPoint + 1;
            }

            gonGD = gonGD + Integer.parseInt(mList.get(6).getHomeScore()) - Integer.parseInt(mList.get(6).getAwayScore());
        }
        if (mList.get(10).getHomeScore() != null)
        {
            gonMatch ++;
            if(Integer.parseInt(mList.get(10).getHomeScore()) > Integer.parseInt(mList.get(10).getAwayScore()))
            {

                gonPoint = gonPoint + 3;
            }
            else if(Integer.parseInt(mList.get(10).getHomeScore()) == Integer.parseInt(mList.get(10).getAwayScore()))
            {
                gonPoint = gonPoint + 1;
            }

            gonGD = gonGD + Integer.parseInt(mList.get(10).getHomeScore()) - Integer.parseInt(mList.get(10).getAwayScore());
        }
    }
    private class PlayerPointComparator implements Comparator<Player> {


        @Override
        public int compare(Player player, Player t1) {
            return t1.getPoint() - player.getPoint();
        }
    }
    private class PlayerGDComparator implements Comparator<Player> {


        @Override
        public int compare(Player player, Player t1) {
            return t1.getGd() - player.getGd();
        }
    }
}
