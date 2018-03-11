package com.krazynutz.iplt20info.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.adapters.TeamMatchList_Adaptor;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamMatches extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ArrayList<String> date, time, play, place, result;
    @BindView(R.id.recycler_viewH)
    RecyclerView viewH;
    private TeamMatchList_Adaptor horizontalAdapter;
    int pageNo;
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;

    private String dbName;
    private String collection;
    private String docId;

    StorageService storageService;

    String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_matches);
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

        dbName = "POINTSTABLE";
        collection = "TeamMatches";
        docId = "5a9d71c5e4b0dc37065fd885";

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

        Intent intent = getIntent();
        pageNo = intent.getExtras().getInt("pageno");


        date = new ArrayList<>();
        play = new ArrayList<>();
        place = new ArrayList<>();
        result = new ArrayList<>();

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(TeamMatches.this, LinearLayoutManager.VERTICAL, false);
        viewH.setLayoutManager(horizontalLayoutManagaer);
        viewH.setHasFixedSize(true);
        viewH.setDrawingCacheEnabled(true);
        viewH.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        new RemoteDataTask().execute();
    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDoalog;
        Storage storage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDoalog = new ProgressDialog(TeamMatches.this);
            progressDoalog.setMessage("Its Loading....");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.setIndeterminate(true);
            progressDoalog.setCancelable(false);
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            storageService = App42API.buildStorageService();
            storageService.findDocumentById(dbName, collection, docId, new App42CallBack() {
                public void onSuccess(Object response) {
                    storage = (Storage) response;
                    if (storage.isFromCache()) {
                        getResult();
                    } else {
                        getResult();
                    }
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        JSONObject jsonObjectsrh = jsonObj.getJSONObject("srh");

                        JSONObject jsonObjectmi = jsonObj.getJSONObject("mi");

                        JSONObject jsonObjectrcb = jsonObj.getJSONObject("rcb");

                        JSONObject jsonObjectcsk = jsonObj.getJSONObject("csk");

                        JSONObject jsonObjectrr = jsonObj.getJSONObject("rr");

                        JSONObject jsonObjectdd = jsonObj.getJSONObject("dd");

                        JSONObject jsonObjectkix = jsonObj.getJSONObject("kix");

                        JSONObject jsonObjectkkr = jsonObj.getJSONObject("kkr");

                        if (pageNo == 0) {
                            getData(jsonObjectsrh);
                        } else if (pageNo == 1) {
                            getData(jsonObjectmi);
                        } else if (pageNo == 2) {
                            getData(jsonObjectrcb);
                        } else if (pageNo == 3) {
                            getData(jsonObjectcsk);
                        } else if (pageNo == 4) {
                            getData(jsonObjectrr);
                        } else if (pageNo == 5) {
                            getData(jsonObjectdd);
                        } else if (pageNo == 6) {
                            getData(jsonObjectkix);
                        } else if (pageNo == 7) {
                            getData(jsonObjectkkr);
                        }

                    } catch (JSONException e) {
                        Log.v("exception", e.toString());
                    }
                }

                public void onException(Exception ex) {
                    System.out.println("Exception Message" + ex.getMessage());
                }

            });

            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            horizontalAdapter = new TeamMatchList_Adaptor(getApplicationContext(), date, play, place, result);
            viewH.setAdapter(horizontalAdapter);
            horizontalAdapter.notifyDataSetChanged();
        }


        public void getResult() {
            System.out.println("dbName is " + storage.getDbName());
            System.out.println("collection Name is " + storage.getCollectionName());
            ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();

            for (int i = 0; i < jsonDocList.size(); i++) {
                System.out.println("objectId is " + jsonDocList.get(i).getDocId());
                System.out.println("CreatedAt is " + jsonDocList.get(i).getCreatedAt());
                System.out.println("UpdatedAtis " + jsonDocList.get(i).getUpdatedAt());
                System.out.println("Jsondoc is " + jsonDocList.get(i).getJsonDoc());

                jsonStr = jsonDocList.get(i).getJsonDoc();
            }
        }

        public void getData(JSONObject jsonObject) {
            JSONArray jsonArraydate = null;
            try {
                jsonArraydate = jsonObject.getJSONArray("date");
                for (int i = 0; i < jsonArraydate.length(); i++) {
                    System.out.println("date===" + jsonArraydate.get(i));
                    String datestring = jsonArraydate.get(i).toString();
                    date.add(datestring);
                }


                JSONArray jsonArrayteams = jsonObject.getJSONArray("teams");
                for (int i = 0; i < jsonArrayteams.length(); i++) {
                    System.out.println("teams===" + jsonArrayteams.get(i));
                    String playstring = jsonArrayteams.get(i).toString();
                    play.add(playstring);
                }

                JSONArray jsonArrayplace = jsonObject.getJSONArray("place");
                for (int i = 0; i < jsonArrayplace.length(); i++) {
                    System.out.println("place===" + jsonArrayplace.get(i));
                    String placestring = jsonArrayplace.get(i).toString();
                    place.add(placestring);
                }

                JSONArray jsonArrayresult = jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArrayresult.length(); i++) {
                    System.out.println("result===" + jsonArrayresult.get(i));
                    String result_tring = jsonArrayresult.get(i).toString();
                    result.add(result_tring);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDoalog.dismiss();
                        if (pageNo == 0) {
                            viewH.setBackgroundResource(R.drawable.srh_bg);
                        } else if (pageNo == 1) {
                            viewH.setBackgroundResource(R.drawable.mi_bg);
                        } else if (pageNo == 2) {
                            viewH.setBackgroundResource(R.drawable.rcb_bg);
                        } else if (pageNo == 3) {
                            viewH.setBackgroundResource(R.drawable.rp_bg);
                        } else if (pageNo == 4) {
                            viewH.setBackgroundResource(R.drawable.gl_bg);
                        } else if (pageNo == 5) {
                            viewH.setBackgroundResource(R.drawable.dd_bg);
                        } else if (pageNo == 6) {
                            viewH.setBackgroundResource(R.drawable.kp_bg);
                        } else if (pageNo == 7) {
                            viewH.setBackgroundResource(R.drawable.kkr_bg);
                        }

                        horizontalAdapter.notifyDataSetChanged();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
