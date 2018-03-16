package com.krazynutz.iplt20info.activites;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.utils.NetworkUtil;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CacheManager;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointsTable extends AppCompatActivity
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.psts_table) TableRow tableRow;
    @BindView(R.id.tvPointTeamName) TextView teams;
    @BindView(R.id.tvPointTeamMatches) TextView matches;
    @BindView(R.id.tvPointTeamWins) TextView wons;
    @BindView(R.id.tvPointTeamLoss) TextView lost;
    @BindView(R.id.tvPointTeamDraw) TextView draw;
    @BindView(R.id.tvPointTeamPoints) TextView points;
    @BindView(R.id.tvPointTeamRunRate) TextView nrr;

    @BindView(R.id.table1) TableRow tableRow1;
    @BindView(R.id.onesrh) TextView teams_one;
    @BindView(R.id.onematch) TextView one_match;
    @BindView(R.id.onewon) TextView one_won;
    @BindView(R.id.onelost) TextView one_lost;
    @BindView(R.id.onedraw) TextView one_draw;
    @BindView(R.id.onepoints) TextView one_points;
    @BindView(R.id.onenrr) TextView one_nrr;

    @BindView(R.id.table2) TableRow tableRow2;
    @BindView(R.id.tworcb) TextView teams_two;
    @BindView(R.id.twomatch) TextView two_match;
    @BindView(R.id.twowon) TextView two_won;
    @BindView(R.id.twolost) TextView two_lost;
    @BindView(R.id.twodraw) TextView two_draw;
    @BindView(R.id.twopoints) TextView two_points;
    @BindView(R.id.twonrr) TextView two_nrr;

    @BindView(R.id.table3) TableRow tableRow3;
    @BindView(R.id.threemi) TextView teams_three;
    @BindView(R.id.threematch) TextView three_match;
    @BindView(R.id.threewon) TextView three_won;
    @BindView(R.id.threelost) TextView three_lost;
    @BindView(R.id.threedraw) TextView three_draw;
    @BindView(R.id.threepoints) TextView three_points;
    @BindView(R.id.threenrr) TextView three_nrr;

    @BindView(R.id.table4) TableRow tableRow4;
    @BindView(R.id.fourrps) TextView teams_four;
    @BindView(R.id.fourmatch) TextView four_match;
    @BindView(R.id.fourwon) TextView four_won;
    @BindView(R.id.fourlost) TextView four_lost;
    @BindView(R.id.fourdraw) TextView four_draw;
    @BindView(R.id.fourpoints) TextView four_points;
    @BindView(R.id.fournrr) TextView four_nrr;

    @BindView(R.id.table5) TableRow tableRow5;
    @BindView(R.id.fivegl) TextView teams_five;
    @BindView(R.id.fivematch) TextView five_match;
    @BindView(R.id.fivewon) TextView five_won;
    @BindView(R.id.fivelost) TextView five_lost;
    @BindView(R.id.fivedraw) TextView five_draw;
    @BindView(R.id.fivepoints) TextView five_points;
    @BindView(R.id.fivenrr) TextView five_nrr;

    @BindView(R.id.table6) TableRow tableRow6;
    @BindView(R.id.sixkkr) TextView teams_six;
    @BindView(R.id.sixmatch) TextView six_match;
    @BindView(R.id.sixwon) TextView six_won;
    @BindView(R.id.sixlost) TextView six_lost;
    @BindView(R.id.sixdraw) TextView six_draw;
    @BindView(R.id.sixpoints) TextView six_points;
    @BindView(R.id.sixnrr) TextView six_nrr;

    @BindView(R.id.table7) TableRow tableRow7;
    @BindView(R.id.sevendd) TextView teams_seven;
    @BindView(R.id.sevenmatch) TextView seven_match;
    @BindView(R.id.sevenwon) TextView seven_won;
    @BindView(R.id.sevenlost) TextView seven_lost;
    @BindView(R.id.sevendraw) TextView seven_draw;
    @BindView(R.id.sevenpoints) TextView seven_points;
    @BindView(R.id.sevennrr) TextView seven_nrr;

    @BindView(R.id.table8) TableRow tableRow8;
    @BindView(R.id.eightkxip) TextView teams_eight;
    @BindView(R.id.eightmatch) TextView eight_match;
    @BindView(R.id.eightwon) TextView eight_won;
    @BindView(R.id.eightlost) TextView eight_lost;
    @BindView(R.id.eightdraw) TextView eight_draw;
    @BindView(R.id.eightpoints) TextView eight_points;
    @BindView(R.id.eightnrr) TextView eight_nrr;

    @BindView(R.id.match) TextView match_played;
    @BindView(R.id.won) TextView match_won;
    @BindView(R.id.lost) TextView match_lost;
    @BindView(R.id.tied) TextView match_draw;
    @BindView(R.id.point) TextView match_points;
    @BindView(R.id.nrr) TextView match_nrr;

    @BindView(R.id.adView) AdView mAdView;

     String one,teammi,threematches,threewins,threelost,threedraw,threepoints,threenrr,
            two,teamsrh,fourmatches,fourwins,fourlost,fourdraw,fourpoints,fournrr,
            three,teamrcb,fivematches,fivewins,fivelost,fivedraw,fivepoints,fivenrr,
            four,teamrps,sixmatches,sixwins,sixlost,sixdraw,sixpoints,sixnrr,
            five,teamgl,sevenmatches,sevenwins,sevenlost,sevendraw,sevenpoints,sevennrr,
            six,teamkkr,eightmatches,eightwins,eightlost,eightdraw,eightpoints,eightnrr,
            seven,teampunjab,twomatches,twowins,twolost,twodraw,twopoints,twonrr,
            eight,teamdd,onematches,onewins,onelost,onedraw,onepoints,onenrr;

    String dbName, collection, docId, jsonStr;
    StorageService storageService;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points_table);
        ButterKnife.bind(this);

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

        setFont();

        int network_status = NetworkUtil.getConnectivityStatus(getApplicationContext());
        if (network_status == 0) {
            Snackbar.make(findViewById(R.id.mainView), "Connect to Internet for Updates!", Snackbar.LENGTH_LONG).show();
        }

         AdRequest adRequest = new AdRequest.Builder().build();
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("84BAB85EBBBE2BBEB6C3C2D30B1BCBC8").build();
        mAdView.loadAd(adRequest);


        dbName = "POINTSTABLE";
        collection = "PointsNew";
        docId = "58eb45b5e4b0be582862f675";

        App42API.initialize(getApplicationContext(),"dd210bf91797594f6aed3dccd464bc5a493ba418f1763cb083e1929a6faa09b8",
                "8677f049dd2b14686e05d27a83152b43cc2c1b4aa80e3ae97ca82f0163abeb23");
        App42CacheManager.setPolicy(App42CacheManager.Policy.NETWORK_FIRST);

       new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<String, Void, String>
    {
        Storage storage;

        @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(PointsTable.this);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

            }

            @Override
            protected String doInBackground(String... arg0)
            {
                storageService = App42API.buildStorageService();
                storageService.findDocumentById(dbName, collection, docId, new App42CallBack()
                {
                    public void onSuccess(Object response)
                    {
                        storage  = (Storage)response;
                        if(storage.isFromCache())
                        {
                            networkCall();
                        }
                        else
                        {
                            networkCall();
                        }
                            try
                            {

                                JSONObject jsonObj = new JSONObject(jsonStr);

                                //DD
                                JSONObject jsonObjone= jsonObj.getJSONObject("ONE");
                                onedraw = jsonObjone.get("draw").toString();
                                onelost = jsonObjone.get("lost").toString();
                                onematches = jsonObjone.get("matches").toString();
                                onenrr = jsonObjone.get("nrr").toString();
                                onepoints = jsonObjone.get("points").toString();
                                onewins = jsonObjone.get("wins").toString();
                                one=jsonObjone.get("team").toString();


                                //GL
                                JSONObject jsonObjtwo= jsonObj.getJSONObject("TWO");
                                twodraw = jsonObjtwo.get("draw").toString();
                                twolost = jsonObjtwo.get("lost").toString();
                                twomatches = jsonObjtwo.get("matches").toString();
                                twonrr = jsonObjtwo.get("nrr").toString();
                                twopoints = jsonObjtwo.get("points").toString();
                                twowins = jsonObjtwo.get("wins").toString();
                                two=jsonObjtwo.get("team").toString();

                                //KXIP
                                JSONObject jsonObjthree= jsonObj.getJSONObject("THREE");
                                threedraw = jsonObjthree.get("draw").toString();
                                threelost = jsonObjthree.get("lost").toString();
                                threematches = jsonObjthree.get("matches").toString();
                                threenrr = jsonObjthree.get("nrr").toString();
                                threepoints = jsonObjthree.get("points").toString();
                                threewins = jsonObjthree.get("wins").toString();
                                three=jsonObjthree.get("team").toString();

                                //KKR
                                JSONObject jsonObjfour= jsonObj.getJSONObject("FOUR");
                                fourdraw = jsonObjfour.get("draw").toString();
                                fourlost = jsonObjfour.get("lost").toString();
                                fourmatches = jsonObjfour.get("matches").toString();
                                fournrr = jsonObjfour.get("nrr").toString();
                                fourpoints = jsonObjfour.get("points").toString();
                                fourwins = jsonObjfour.get("wins").toString();
                                four=jsonObjfour.get("team").toString();

                                //MI
                                JSONObject jsonObjfive= jsonObj.getJSONObject("FIVE");
                                fivedraw = jsonObjfive.get("draw").toString();
                                fivelost = jsonObjfive.get("lost").toString();
                                fivematches = jsonObjfive.get("matches").toString();
                                fivenrr = jsonObjfive.get("nrr").toString();
                                fivepoints = jsonObjfive.get("points").toString();
                                fivewins = jsonObjfive.get("wins").toString();
                                five=jsonObjfive.get("team").toString();

                                //RCB
                                JSONObject jsonObjsix= jsonObj.getJSONObject("SIX");
                                sixdraw = jsonObjsix.get("draw").toString();
                                sixlost = jsonObjsix.get("lost").toString();
                                sixmatches = jsonObjsix.get("matches").toString();
                                sixnrr = jsonObjsix.get("nrr").toString();
                                sixpoints = jsonObjsix.get("points").toString();
                                sixwins = jsonObjsix.get("wins").toString();
                                six=jsonObjsix.get("team").toString();

                                //RPS
                                JSONObject jsonObjseven= jsonObj.getJSONObject("SEVEN");
                                sevendraw = jsonObjseven.get("draw").toString();
                                sevenlost = jsonObjseven.get("lost").toString();
                                sevenmatches = jsonObjseven.get("matches").toString();
                                sevennrr = jsonObjseven.get("nrr").toString();
                                sevenpoints = jsonObjseven.get("points").toString();
                                sevenwins = jsonObjseven.get("wins").toString();
                                seven=jsonObjseven.get("team").toString();

                                //SRH
                                JSONObject jsonObjeight= jsonObj.getJSONObject("EIGHT");
                                eightdraw = jsonObjeight.get("draw").toString();
                                eightlost = jsonObjeight.get("lost").toString();
                                eightmatches = jsonObjeight.get("matches").toString();
                                eightnrr = jsonObjeight.get("nrr").toString();
                                eightpoints = jsonObjeight.get("points").toString();
                                eightwins = jsonObjeight.get("wins").toString();
                                eight=jsonObjeight.get("team").toString();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressDialog.dismiss();
                                        //setValues
                                        teams_one.setText(one);
                                        one_match.setText(onematches);
                                        one_won.setText(onewins);
                                        one_lost.setText(onelost);
                                        one_draw.setText(onedraw);
                                        one_points.setText(onepoints);
                                        one_nrr.setText(onenrr);

                                        //setValues
                                        teams_two.setText(two);
                                        two_match.setText(twomatches);
                                        two_won.setText(twowins);
                                        two_lost.setText(twolost);
                                        two_draw.setText(twodraw);
                                        two_points.setText(twopoints);
                                        two_nrr.setText(twonrr);

                                        //setValues
                                        teams_three.setText(three);
                                        three_match.setText(threematches);
                                        three_won.setText(threewins);
                                        three_lost.setText(threelost);
                                        three_draw.setText(threedraw);
                                        three_points.setText(threepoints);
                                        three_nrr.setText(threenrr);

                                        //setValues
                                        teams_four.setText(four);
                                        four_match.setText(fourmatches);
                                        four_won.setText(fourwins);
                                        four_lost.setText(fourlost);
                                        four_draw.setText(fourdraw);
                                        four_points.setText(fourpoints);
                                        four_nrr.setText(fournrr);

                                        //setValues
                                        teams_five.setText(five);
                                        five_match.setText(fivematches);
                                        five_won.setText(fivewins);
                                        five_lost.setText(fivelost);
                                        five_draw.setText(fivedraw);
                                        five_points.setText(fivepoints);
                                        five_nrr.setText(fivenrr);

                                        //setValues
                                        teams_six.setText(six);
                                        six_match.setText(sixmatches);
                                        six_won.setText(sixwins);
                                        six_lost.setText(sixlost);
                                        six_draw.setText(sixdraw);
                                        six_points.setText(sixpoints);
                                        six_nrr.setText(sixnrr);

                                        //setValues
                                        teams_seven.setText(seven);
                                        seven_match.setText(sevenmatches);
                                        seven_won.setText(sevenwins);
                                        seven_lost.setText(sevenlost);
                                        seven_draw.setText(sevendraw);
                                        seven_points.setText(sevenpoints);
                                        seven_nrr.setText(sevennrr);

                                        //setValues
                                        teams_eight.setText(eight);
                                        eight_match.setText(eightmatches);
                                        eight_won.setText(eightwins);
                                        eight_lost.setText(eightlost);
                                        eight_draw.setText(eightdraw);
                                        eight_points.setText(eightpoints);
                                        eight_nrr.setText(eightnrr);
                                    }
                                });

                            }
                            catch (JSONException e)
                            {
                                Log.v("exception", e.toString());
                            }
                    }
                    public void onException(Exception ex)
                    {
                        System.out.println("Exception Message"+ex.getMessage());
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(String str)
            {
                super.onPostExecute(str);
            }

        public void networkCall()
        {
            System.out.println("dbName is " + storage.getDbName());
            System.out.println("collection Name is " + storage.getCollectionName());
            ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();

            for(int i=0;i<jsonDocList.size();i++)
            {
                System.out.println("objectId is " + jsonDocList.get(i).getDocId());
                System.out.println("CreatedAt is " + jsonDocList.get(i).getCreatedAt());
                System.out.println("UpdatedAtis " + jsonDocList.get(i).getUpdatedAt());
                System.out.println("Jsondoc is " + jsonDocList.get(i).getJsonDoc());

                jsonStr = jsonDocList.get(i).getJsonDoc();
            }
        }
    }


   public void setFont()
    {
        teams_one.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_two.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_three.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_four.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_five.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_six.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_seven.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_eight.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));

        match_played.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima Nova Light.ttf"));
        match_won.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima Nova Light.ttf"));
        match_lost.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima Nova Light.ttf"));
        match_draw.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima Nova Light.ttf"));
        match_points.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima Nova Light.ttf"));
        match_nrr.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima Nova Light.ttf"));

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
