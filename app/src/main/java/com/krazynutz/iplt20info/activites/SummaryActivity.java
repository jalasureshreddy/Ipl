package com.krazynutz.iplt20info.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.krazynutz.iplt20info.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends AppCompatActivity
{
    ArrayList<String> selected=new ArrayList<>();
    String  match,opp1,opp2,score1,score2,batsmen11,batsmen12,batsmen21,batsmen22,bowler11,bowler12,bowler21,bowler22,result;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.match_no) TextView match_no;
    @BindView(R.id.match_res) TextView match_res;

    @BindView(R.id.match_one) TextView match_one;
    @BindView(R.id.scores_one) TextView scores_one;
    @BindView(R.id.player_run1) TextView player_run1;
    @BindView(R.id.bolwer_run1) TextView bolwer_run1;
    @BindView(R.id.player_run2) TextView player_run2;
    @BindView(R.id.bolwer_run2) TextView bolwer_run2;

    @BindView(R.id.match_two) TextView match_two;
    @BindView(R.id.scores_two) TextView scores_two;
    @BindView(R.id.player_run11) TextView player_run11;
    @BindView(R.id.bolwer_run11) TextView bolwer_run11;
    @BindView(R.id.player_run21) TextView player_run21;
    @BindView(R.id.bolwer_run21) TextView bolwer_run21;

    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(false);
            }
        }
        setFont();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent intent = getIntent();
        String matchno= intent.getStringExtra("Matchno");
        System.out.println("match no===="+matchno);

        selected = intent.getStringArrayListExtra("Selected");

        System.out.println("matchinfo======"+ selected);

        result=selected.get(0);
        opp1=selected.get(1);
        opp2=selected.get(2);
        score1=selected.get(3);
        score2=selected.get(4);
        batsmen11=selected.get(5);
        batsmen12=selected.get(6);
        bowler11=selected.get(7);
        bowler12=selected.get(8);
        batsmen21=selected.get(9);
        batsmen22=selected.get(10);
        bowler21=selected.get(11);
        bowler22=selected.get(12);

        //SetViews
        match_no.setText(matchno);
        match_res.setText(result);

        //Opp1
        match_one.setText(opp1);
        scores_one.setText(score1);
        player_run1.setText(batsmen11);
        player_run2.setText(batsmen12);
        bolwer_run1.setText(bowler11);
        bolwer_run2.setText(bowler12);

        //Opp2
        match_two.setText(opp2);
        scores_two.setText(score2);
        player_run11.setText(batsmen21);
        player_run21.setText(batsmen22);
        bolwer_run11.setText(bowler21);
        bolwer_run21.setText(bowler22);

        System.out.println("matchinfo"+ result+" opp1 "+opp1+" opp2 "+opp2+" score1 "+score1+" score2 "+score2+" batsmen11 "+batsmen11+" batsmen12 "+batsmen12+" bowler11 "+bowler11
                           +" bowler12 "+bowler12+" batsmen21 "+batsmen21+" batsmen22 "+batsmen22+" bowler21 "+bowler21+" bowler22 "+bowler22);
    }

   public void setFont()
    {
        match_no.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));
        match_res.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        match_one.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "BreeSerif-Regular.ttf"));
        scores_one.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "BreeSerif-Regular.ttf"));
        player_run1.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        player_run2.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        bolwer_run1.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        bolwer_run2.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));

        match_two.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "BreeSerif-Regular.ttf"));
        scores_two.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "BreeSerif-Regular.ttf"));
        player_run11.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        player_run21.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        bolwer_run11.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        bolwer_run21.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
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
