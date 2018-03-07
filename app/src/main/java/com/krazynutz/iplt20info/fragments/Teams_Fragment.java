package com.krazynutz.iplt20info.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.adapters.Topics_Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Teams_Fragment extends Fragment
{
    Context mContext;
    private RecyclerView mRecyclerView;
    private List<String> stringList;
    RecyclerView.Adapter mAdapter;
    public static Teams_Fragment newInstance(int position)
    {
        Teams_Fragment localteamsFragment = new Teams_Fragment();
        Bundle localBundle = new Bundle();
        localBundle.putInt("teams", position);
        localteamsFragment.setArguments(localBundle);
        return localteamsFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle)
    {
        return inflater.inflate(R.layout.fragment_sub_topics, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewfr);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        getArguments().getInt("teams", 0);
        TypedArray list = mContext.getResources().obtainTypedArray(R.array.topics_array);
       // CharSequence[] strings = list.getTextArray(getArguments().getInt("teams", 0));

        System.out.println("GGGG" + getArguments().getInt("teams", 0));

        System.out.println("LIST->>" + list);
        list.recycle();

        String[] data = getResources().getStringArray(R.array.players_matches);
        stringList = new ArrayList<>(Arrays.asList(data));

        mAdapter = new RecyclerViewMaterialAdapter(new Topics_Adapter(mContext,stringList));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(mContext, mRecyclerView);
    }
    }
