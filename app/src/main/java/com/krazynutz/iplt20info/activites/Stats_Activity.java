package com.krazynutz.iplt20info.activites;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.utils.Constants;
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


public class Stats_Activity extends AppCompatActivity
{

    String  higestscore,hsinn1,hsinn2,hsname1,hsname2,hsscore1,hsscore2,
            mostruns,mrinn1,mrinn2,mrname1,mrname2,mrruns1,mrruns2,
            mostsixes,msinn1,msinn2,msname1,msname2, msixes1,msixes2,
            mostwickets,mwinn1,mwinn2,mwname1,mwname2,mwic1,mwic2,
            mostfours,mfinn1,mfinn2,mfname1,mfname2,mfours1,mfours2;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title1) TextView title1;
    @BindView(R.id.title2) TextView title2;
    @BindView(R.id.title3) TextView title3;
    @BindView(R.id.title4) TextView title4;
    @BindView(R.id.title5) TextView title5;

    @BindView(R.id.player1) TextView player1;
    @BindView(R.id.player2) TextView player2;
    @BindView(R.id.inn1) TextView innn1;
    @BindView(R.id.inn2) TextView innn2;
    @BindView(R.id.no1) TextView no1;
    @BindView(R.id.no2) TextView no2;

    @BindView(R.id.player_run1) TextView run_player1;
    @BindView(R.id.player_run2) TextView run_player2;
    @BindView(R.id.inn_run1) TextView run_innn1;
    @BindView(R.id.inn_run2) TextView run_innn2;
    @BindView(R.id.no_run1) TextView run_no1;
    @BindView(R.id.no_run2) TextView run_no2;

    @BindView(R.id.player_six1) TextView six_player1;
    @BindView(R.id.player_six2) TextView six_player2;
    @BindView(R.id.inn_six1) TextView six_innn1;
    @BindView(R.id.inn_six2) TextView six_innn2;
    @BindView(R.id.no_six1) TextView six_no1;
    @BindView(R.id.no_six2) TextView six_no2;

    @BindView(R.id.player_wi1) TextView wi_player1;
    @BindView(R.id.player_wi2) TextView wi_player2;
    @BindView(R.id.inn_wi1) TextView wi_innn1;
    @BindView(R.id.inn_wi2) TextView wi_innn2;
    @BindView(R.id.no_wi1) TextView wi_no1;
    @BindView(R.id.no_wi2) TextView wi_no2;

    @BindView(R.id.player_four1) TextView four_player1;
    @BindView(R.id.player_four2) TextView four_player2;
    @BindView(R.id.inn_four1) TextView four_innn1;
    @BindView(R.id.inn_four2) TextView four_innn2;
    @BindView(R.id.no_four1) TextView four_no1;
    @BindView(R.id.no_four2) TextView four_no2;

    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    String dbName, collection, docId,jsonStr;
    StorageService storageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);
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

        adRequest = new AdRequest.Builder().build();

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id_full));
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });

        dbName = "POINTSTABLE";
        collection = "Stats";
        docId = "58c7eb88e4b0fe51287e1de6";

        App42API.initialize(getApplicationContext(), "dd210bf91797594f6aed3dccd464bc5a493ba418f1763cb083e1929a6faa09b8",
                "8677f049dd2b14686e05d27a83152b43cc2c1b4aa80e3ae97ca82f0163abeb23");
        App42CacheManager.setPolicy(App42CacheManager.Policy.NETWORK_FIRST);

        new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<String, Void, String>
    {
        Storage storage;
        @Override
        protected String doInBackground(String... strings)
        {
            storageService = App42API.buildStorageService();
            storageService.findDocumentById(dbName, collection, docId, new App42CallBack()
            {
                public void onSuccess(Object response)
                {
                     storage = (Storage) response;

                    if(storage.isFromCache())
                    {
                        getData();
                    }
                    else
                    {
                       getData();
                    }

                        try
                        {
                            JSONObject jsonObj = new JSONObject(jsonStr);

                            //RUNS
                            JSONObject jsonRuns = jsonObj.getJSONObject("Most Runs");
                            mrinn1 = jsonRuns.get(Constants.inn1).toString();
                            mrinn2 = jsonRuns.get(Constants.inn2).toString();
                            mrname1 = jsonRuns.get(Constants.name1).toString();
                            mrname2 = jsonRuns.get(Constants.name2).toString();
                            mrruns1 = jsonRuns.get(Constants.runs1).toString();
                            mrruns2 = jsonRuns.get(Constants.runs2).toString();

                            //Wickets
                            JSONObject jsonwi = jsonObj.getJSONObject("Most Wickets");
                            mwinn1 = jsonwi.get(Constants.inn1).toString();
                            mwinn2 = jsonwi.get(Constants.inn2).toString();
                            mwname1 = jsonwi.get(Constants.name1).toString();
                            mwname2 = jsonwi.get(Constants.name2).toString();
                            mwic1 = jsonwi.get(Constants.wickets1).toString();
                            mwic2 = jsonwi.get(Constants.wickets2).toString();

                            JSONObject jsonsix = jsonObj.getJSONObject("Most Sixes");
                            msinn1 = jsonsix.get(Constants.inn1).toString();
                            msinn2 = jsonsix.get(Constants.inn2).toString();
                            msname1 = jsonsix.get(Constants.name1).toString();
                            msname2 = jsonsix.get(Constants.name2).toString();
                            msixes1 = jsonsix.get(Constants.sixes1).toString();
                            msixes2 = jsonsix.get(Constants.sixes2).toString();

                            JSONObject jsonfr = jsonObj.getJSONObject("Most Fours");
                            mfinn1 = jsonfr.get(Constants.inn1).toString();
                            mfinn2 = jsonfr.get(Constants.inn2).toString();
                            mfname1 = jsonfr.get(Constants.name1).toString();
                            mfname2 = jsonfr.get(Constants.name2).toString();
                            mfours1 = jsonfr.get(Constants.fours1).toString();
                            mfours2 = jsonfr.get(Constants.fours2).toString();

                            JSONObject jsonsc = jsonObj.getJSONObject("Highest Score");
                            hsinn1 = jsonsc.get(Constants.inn1).toString();
                            hsinn2 = jsonsc.get(Constants.inn2).toString();
                            hsname1 = jsonsc.get(Constants.name1).toString();
                            hsname2 = jsonsc.get(Constants.name2).toString();
                            hsscore1 = jsonsc.get(Constants.score1).toString();
                            hsscore2 = jsonsc.get(Constants.score2).toString();

                        }
                        catch (JSONException e)
                        {
                            Log.v("exception", e.toString());
                        }

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                title2.setText("Most Runs - Top 2");
                                run_player1.setText(mrname1);
                                run_player2.setText(mrname2);
                                run_innn1.setText(mrinn1);
                                run_innn2.setText(mrinn2);
                                run_no1.setText(mrruns1);
                                run_no2.setText(mrruns2);

                                title4.setText("Most Wickets - Top 2");
                                wi_player1.setText(mwname1);
                                wi_player2.setText(mwname2);
                                wi_innn1.setText(mwinn1);
                                wi_innn2.setText(mwinn2);
                                wi_no1.setText(mwic1);
                                wi_no2.setText(mwic2);

                                title3.setText("Most Sixes - Top 2");
                                six_player1.setText(msname1);
                                six_player2.setText(msname2);
                                six_innn1.setText(msinn1);
                                six_innn2.setText(msinn2);
                                six_no1.setText(msixes1);
                                six_no2.setText(msixes2);

                                title5.setText("Most Fours - Top 2");
                                four_player1.setText(mfname1);
                                four_player2.setText(mfname2);
                                four_innn1.setText(mfinn1);
                                four_innn2.setText(mfinn2);
                                four_no1.setText(mfours1);
                                four_no2.setText(mfours2);

                                title1.setText("Highest Individual Score - Top 2");
                                player1.setText(hsname1);
                                player2.setText(hsname2);
                                innn1.setText(hsinn1);
                                innn2.setText(hsinn2);
                                no1.setText(hsscore1);
                                no2.setText(hsscore2);
                            }
                        });

                }

                public void onException(Exception ex)
                {
                    System.out.println("Exception Message"+ex.getMessage());
                }

            });

            return null;
        }

            public  void getData()
            {
                System.out.println("dbName is " + storage.getDbName());
                System.out.println("collection Name is " + storage.getCollectionName());
                ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();

                for (int i = 0; i < jsonDocList.size(); i++)
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

        title1.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        title2.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        title3.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        title4.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
        title5.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "OpenSans-Semibold.ttf"));
    }
}
