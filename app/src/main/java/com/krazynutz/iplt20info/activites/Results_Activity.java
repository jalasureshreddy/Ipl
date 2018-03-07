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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.adapters.ResAdapter;
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

public class Results_Activity extends AppCompatActivity
{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String[] mdate, mplay, mplace, mname1, mname2;
    private List<String> date, play, place;

    @BindView(R.id.recycler_viewH)
    FastScrollRecyclerView viewH;

    AdRequest adRequest;
    StorageService storageService;

    private ProgressDialog mProgressDialog;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> name1 = new ArrayList<>();
    private ArrayList<String> name2 = new ArrayList<>();
    private ArrayList<String> match_no=new ArrayList<>();
    AdView mAdView;
    ResAdapter verticalAdapter;
    public HashMap<String ,ArrayList<String>> matchinfo=new HashMap<String, ArrayList<String>>();
    ArrayList<String> info;

    String dbName, collection, docId, jsonStr, match, res, opp1,opp2,score1,score2,
            batsmen11,batsmen12,batsmen21,batsmen22,bowler11,bowler12,bowler21,bowler22,matchno;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_list);
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

        Toast.makeText(getApplicationContext(), "Please wait while Loading!", Toast.LENGTH_LONG).show();

        int network_status = NetworkUtil.getConnectivityStatus(getApplicationContext());
        if (network_status == 0) {
            Snackbar.make(findViewById(R.id.mainView), "Connect to Internet for Updates!", Snackbar.LENGTH_LONG).show();
        }


        mdate = getResources().getStringArray(R.array.matches_date);
        mplay = getResources().getStringArray(R.array.match_play);
        mplace = getResources().getStringArray(R.array.match_venue);
        //  mname1 = getResources().getStringArray(R.array.srt_name1);
        //   mname2 = getResources().getStringArray(R.array.srt_name2);

        date = new ArrayList<>(Arrays.asList(mdate));
        play = new ArrayList<>(Arrays.asList(mplay));
        place = new ArrayList<>(Arrays.asList(mplace));
        //  name = new ArrayList<>(Arrays.asList(mname1));
        //  name2 = new ArrayList<>(Arrays.asList(mname2));

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        dbName = "POINTSTABLE";
        collection = "TeamResults";
        docId = "58ca665ee4b07ed8e34c21b2";

        App42API.initialize(getApplicationContext(), Constants.apiKey,
                Constants.secretKey);
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
          /*  mProgressDialog = new ProgressDialog(Results_Activity.this);
            mProgressDialog.setMessage("Please wait while Loading!");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();*/

        }
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
                        getResult();
                    }
                    else
                    {
                        getResult();
                    }

                    try
                    {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        JSONArray result = jsonObj.getJSONArray("Results");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject c = result.getJSONObject(i);

                          //  match = c.getString(Constants.match);
                          //  res = c.getString(Constants.result);

                            info=new ArrayList<>();

                            res = c.getString(Constants.result);
                            info.add(res);
                            opp1=c.getString(Constants.opp1);
                            info.add(opp1);
                            opp2=c.getString(Constants.opp2);
                            info.add(opp2);
                            score1=c.getString(Constants.Score1);
                            info.add(score1);
                            score2=c.getString(Constants.Score2);
                            info.add(score2);
                            batsmen11=c.getString(Constants.Batsmen11);
                            info.add(batsmen11);
                            batsmen12=c.getString(Constants.Batsmen12);
                            info.add(batsmen12);
                            bowler11=c.getString(Constants.Bowler11);
                            info.add(bowler11);
                            bowler12=c.getString(Constants.Bowler12);
                            info.add(bowler12);
                            batsmen21=c.getString(Constants.Batsmen21);
                            info.add(batsmen21);
                            batsmen22=c.getString(Constants.Batsmen22);
                            info.add(batsmen22);
                            bowler21=c.getString(Constants.Bowler21);
                            info.add(bowler21);
                            bowler22=c.getString(Constants.Bowler22);
                            info.add(bowler22);
                            matchno=c.getString(Constants.matchno);
                            match_no.add(matchno);
                            name.add(opp1);
                            name1.add(opp2);
                            name2.add(res);

                            matchinfo.put(matchno,info);

                           /* name.add(match);
                            name2.add(res);*/
                            System.out.println("RRRR===>"  + res);

                        }
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
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Results_Activity.this,
                                                                    LinearLayoutManager.VERTICAL, false);
            viewH.setLayoutManager(horizontalLayoutManagaer);
            viewH.setHasFixedSize(true);
            viewH.setFocusableInTouchMode(false);
            verticalAdapter = new ResAdapter(Results_Activity.this,date,play,place,name,name1,name2,match_no,matchinfo);
            viewH.setAdapter(verticalAdapter);
            verticalAdapter.notifyDataSetChanged();
            //mProgressDialog.dismiss();
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
