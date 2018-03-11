package com.krazynutz.iplt20info.activites;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.adapters.MatchList_Adaptor;
import com.krazynutz.iplt20info.utils.Constants;
import com.krazynutz.iplt20info.utils.NetworkUtil;
import com.l4digital.fastscroll.FastScrollRecyclerView;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CacheManager;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchesList extends AppCompatActivity
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String[] mdate, mplay, mplace, mname1, mname2;
    private List<String> date, play, place;

    @BindView(R.id.recycler_viewH)
    FastScrollRecyclerView viewH;

    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    String dbName, collection, docId, jsonStr, match, res;
    ProgressDialog mProgressDialog;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> name2 = new ArrayList<>();
    //AdView mAdView;

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

      //  Toast.makeText(getApplicationContext(), "Please wait while Loading!", Toast.LENGTH_LONG).show();

       /* int network_status = NetworkUtil.getConnectivityStatus(getApplicationContext());
        if (network_status == 0) {
            Snackbar.make(findViewById(R.id.mainView), "Connect to Internet for Updates!", Snackbar.LENGTH_LONG).show();
        }*/

        adRequest = new AdRequest.Builder().build();

        mdate = getResources().getStringArray(R.array.matches_date);
        mplay = getResources().getStringArray(R.array.match_play);
        mplace = getResources().getStringArray(R.array.match_venue);
        mname1 = getResources().getStringArray(R.array.srt_name1);
        mname2 = getResources().getStringArray(R.array.srt_name2);

        date = new ArrayList<>(Arrays.asList(mdate));
        play = new ArrayList<>(Arrays.asList(mplay));
        place = new ArrayList<>(Arrays.asList(mplace));
        name = new ArrayList<>(Arrays.asList(mname1));
        name2 = new ArrayList<>(Arrays.asList(mname2));

      /*  mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MatchesList.this, LinearLayoutManager.VERTICAL, false);
        viewH.setLayoutManager(horizontalLayoutManagaer);
        viewH.setHasFixedSize(true);
        MatchList_Adaptor verticalAdapter = new MatchList_Adaptor(getApplicationContext(),date,play,place,name,name2);
        viewH.setAdapter(verticalAdapter);

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
            mProgressDialog = new ProgressDialog(MatchesList.this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            dbName = "POINTSTABLE";
            collection = "MatchSchedule";
            docId = "5aa4437be4b09e5367a004ee";

            App42API.initialize(getApplicationContext(), Constants.apiKey,
                    Constants.secretKey);
            App42CacheManager.setPolicy(App42CacheManager.Policy.NETWORK_FIRST);

            StorageService storageService = App42API.buildStorageService();
            storageService.findDocumentById(dbName, collection, docId, new App42CallBack()
            {
                public void onSuccess(Object response)
                {
                    storage = (Storage) response;
                    if(storage.isFromCache())
                    {
                        getResult();
                    }
                    else
                    {
                        getResult();
                    }

                        try
                        {
                            JSONObject jsonObj = new JSONObject(jsonStr);

                         //   JSONArray names=jsonObj.getJSONObject(jsonObj)

                            for(int i = 0; i<jsonObj.names().length(); i++){
                                JSONArray names =jsonObj.getJSONArray(jsonObj.names().get(i).toString());

                                String  time=names.getJSONObject(0).getString("time");
                                String teams=names.getJSONObject(0).getString("teams");
                                String stadium=names.getJSONObject(0).getString("teams");
                                System.out.println("key = " + jsonObj.names().getString(i) + " time = "+time);
                                if(names.getJSONObject(1) !=null)
                                {
                                   String time1= names.getJSONObject(1).getString("time");
                                    String team1=  names.getJSONObject(1).getString("teams");
                                    String stadium1= names.getJSONObject(1).getString("stadium");
                                    System.out.println("key1 = " + jsonObj.names().getString(i) + " time1 = "+time1);
                                }
                               /* JSONArray mat=names.getJSONArray(0);
                                for(int j=0;j<mat.length();j++)
                                {
                                    String match=mat.getString(0);
                                    String st=mat.getString(1);

                                JSONArray mat1=names.getJSONArray(1);
                                String match1="n";
                                if(mat1.length()>0)
                                {
                                    match1=mat.getString(0);
                                    String st1=mat.getString(1);
                                }*/


                               // }


                            }

                            /*JSONArray result = jsonObj.getJSONArray("Results");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject c = result.getJSONObject(i);

                                match = c.getString(Constants.match);
                                res = c.getString(Constants.result);
                                name.add(match);
                                name2.add(res);
                                System.out.println("RRRR===>" + match + res);

                            }*/
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
            mProgressDialog.dismiss();

        }

       public void getResult()
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

                jsonStr=jsonDocList.get(i).getJsonDoc();
            }
        }
    }

}
