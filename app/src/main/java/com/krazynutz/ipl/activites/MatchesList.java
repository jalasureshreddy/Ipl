package com.krazynutz.ipl.activites;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.krazynutz.ipl.R;
import com.krazynutz.ipl.adapters.MatchList_Adaptor;
import com.l4digital.fastscroll.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchesList extends AppCompatActivity
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String[] mdate, mtime, mplay, mplace;
    private List<String> date, time, play, place;
    @BindView(R.id.recycler_viewH)
    FastScrollRecyclerView viewH;
    private MatchList_Adaptor verticalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_listview);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(false);
            }
        }

        mdate = getResources().getStringArray(R.array.matches_date);
       // mtime = getResources().getStringArray(R.array.match_time);
        mplay = getResources().getStringArray(R.array.match_play);
        mplace = getResources().getStringArray(R.array.match_venue);

        date = new ArrayList<>(Arrays.asList(mdate));
       // time = new ArrayList<>(Arrays.asList(mtime));
        play = new ArrayList<>(Arrays.asList(mplay));
        place = new ArrayList<>(Arrays.asList(mplace));

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewH.setLayoutManager(horizontalLayoutManagaer);
        viewH.setHasFixedSize(true);
        verticalAdapter = new MatchList_Adaptor(getApplicationContext(),date,play,place);
        viewH.setAdapter(verticalAdapter);
    }
}
