package com.krazynutz.ipl.activites;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.krazynutz.ipl.R;
import com.krazynutz.ipl.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Stadium_List extends AppCompatActivity
{

    @BindView(R.id.expandableListView)
    ExpandableListView expListView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium_list);
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

        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        String[] std= getResources().getStringArray(R.array.stadiums);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(std));

        listDataHeader.addAll(data);

        List<String> hyd = new ArrayList<String>();
        String[] std_hyd= getResources().getStringArray(R.array.hyd_std);
        ArrayList<String> data1 = new ArrayList<>(Arrays.asList(std_hyd));
        hyd.addAll(data1);

        List<String> ben = new ArrayList<String>();
        String[] std_ben= getResources().getStringArray(R.array.beng_std);
        ArrayList<String> data2 = new ArrayList<>(Arrays.asList(std_ben));
        ben.addAll(data2);

        List<String> mub = new ArrayList<String>();
        String[] std_mub= getResources().getStringArray(R.array.mum_std);
        ArrayList<String> data3 = new ArrayList<>(Arrays.asList(std_mub));
        mub.addAll(data3);

        List<String> kol = new ArrayList<String>();
        String[] std_kol= getResources().getStringArray(R.array.kol_std);
        ArrayList<String> data4 = new ArrayList<>(Arrays.asList(std_kol));
        kol.addAll(data4);

        List<String> pun = new ArrayList<String>();
        String[] std_pun= getResources().getStringArray(R.array.pune_std);
        ArrayList<String> data5 = new ArrayList<>(Arrays.asList(std_pun));
        pun.addAll(data5);

        List<String> del = new ArrayList<String>();
        String[] std_del= getResources().getStringArray(R.array.delhi_std);
        ArrayList<String> data6 = new ArrayList<>(Arrays.asList(std_del));
        del.addAll(data6);

        List<String> raj = new ArrayList<String>();
        String[] std_raj= getResources().getStringArray(R.array.raj_std);
        ArrayList<String> data7 = new ArrayList<>(Arrays.asList(std_raj));
        raj.addAll(data7);

        List<String> ind = new ArrayList<String>();
        String[] std_ind= getResources().getStringArray(R.array.ind_std);
        ArrayList<String> data8 = new ArrayList<>(Arrays.asList(std_ind));
        ind.addAll(data8);

        List<String> moh = new ArrayList<String>();
        String[] std_moh= getResources().getStringArray(R.array.moh_std);
        ArrayList<String> data9 = new ArrayList<>(Arrays.asList(std_moh));
        moh.addAll(data9);

        List<String> kan = new ArrayList<String>();
        String[] std_kan= getResources().getStringArray(R.array.kan_std);
        ArrayList<String> data10 = new ArrayList<>(Arrays.asList(std_kan));
        kan.addAll(data10);

        List<String> chen = new ArrayList<String>();
        String[] std_chen= getResources().getStringArray(R.array.chen_std);
        ArrayList<String> data11 = new ArrayList<>(Arrays.asList(std_chen));
        chen.addAll(data11);

        listDataChild.put(listDataHeader.get(0), hyd);
        listDataChild.put(listDataHeader.get(1), ben);
        listDataChild.put(listDataHeader.get(2), mub);
        listDataChild.put(listDataHeader.get(3), kol);
        listDataChild.put(listDataHeader.get(4), pun);
        listDataChild.put(listDataHeader.get(5), del);
        listDataChild.put(listDataHeader.get(6), raj);
        listDataChild.put(listDataHeader.get(7), ind);
        listDataChild.put(listDataHeader.get(8), moh);
        listDataChild.put(listDataHeader.get(9), kan);
        listDataChild.put(listDataHeader.get(10), chen);
    }
}
