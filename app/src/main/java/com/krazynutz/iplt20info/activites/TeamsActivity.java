package com.krazynutz.iplt20info.activites;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.fragments.Teams_Fragment;

public class TeamsActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    String[] teams;
    int TEAMS_COUNT;
    PagerSlidingTabStrip tabs;
    ImageView imHeader;
    public static int pageNo;
    FirebaseAnalytics mFirebaseAnalytics;
    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        teams = getResources().getStringArray(R.array.teams);
        TEAMS_COUNT = teams.length;
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        imHeader = ((ImageView) findViewById(R.id.imHeader));
        toolbar = mViewPager.getToolbar();
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));


        if (toolbar != null) {
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

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // AdRequest adRequest = new AdRequest.Builder().addTestDevice("A13F30D3EC2BADE120367028FB301007").build();
        mAdView.loadAd(adRequest);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Teams_Fragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return TEAMS_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return teams[position];
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {

            @Override
            public HeaderDesign getHeaderDesign(int page) {

                pageNo = page;
                if (page == 0) {
                    imHeader.setImageResource(R.drawable.srh);

                } else if (page == 1) {
                    imHeader.setImageResource(R.drawable.mi);
                } else if (page == 2) {
                    imHeader.setImageResource(R.drawable.rcb);
                } else if (page == 3) {
                    imHeader.setImageResource(R.drawable.pune);
                } else if (page == 4) {
                    imHeader.setImageResource(R.drawable.gujarat);
                } else if (page == 5) {
                    imHeader.setImageResource(R.drawable.delhi);
                } else if (page == 6) {
                    imHeader.setImageResource(R.drawable.punjab);
                } else if (page == 7) {
                    imHeader.setImageResource(R.drawable.kkr);
                }
                // imHeader.setImageResource(topics_drawables[paramAnonymousInt]);
                return HeaderDesign.fromColorAndDrawable(getResources().getColor(R.color.colorPrimary),
                        getResources().getDrawable(R.drawable.stadium));

            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(2);
        tabs = mViewPager.getPagerTitleStrip();
        tabs.setViewPager(mViewPager.getViewPager());
        tabs.setTypeface(null, Typeface.BOLD);
        //tabs.setShouldExpand(true);
        tabs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        mViewPager.getViewPager().setCurrentItem(getIntent().getIntExtra("teams", 0));
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
