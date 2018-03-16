package com.krazynutz.iplt20info.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.adapters.Allrounder_Adapter;
import com.krazynutz.iplt20info.adapters.Bowlers_Adapter;
import com.krazynutz.iplt20info.adapters.Players_Adapter;
import com.krazynutz.iplt20info.adapters.ResAdapter;
import com.krazynutz.iplt20info.adapters.Wicketkeeper_Adapter;
import com.krazynutz.iplt20info.utils.Constants;
import com.krazynutz.iplt20info.utils.NetworkUtil;
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

public class PlayersActivity extends AppCompatActivity {

    String[] teams;
    int pageNo;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view2)
    RecyclerView recyclerView2;
    @BindView(R.id.recycler_view3)
    RecyclerView recyclerView3;
    @BindView(R.id.recycler_view4)
    RecyclerView recyclerView4;

    ArrayList<String> playerlist,bowler,allrounder,wicketkeeper;
    Players_Adapter mAdapter;
    Bowlers_Adapter bAdapter;
    Allrounder_Adapter aAdapter;
    Wicketkeeper_Adapter wAdapter;
    ImageView imageView;
    RelativeLayout activity_players;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.captian)
    TextView captianText;
    @BindView(R.id.batsmen)
    TextView batsmen;
    @BindView(R.id.allrounders)
    TextView allrounders;
    @BindView(R.id.bowlers)
    TextView bowlers;
    @BindView(R.id.keepers)
    TextView keepers;
    AdView mAdView;
    private String dbName;
    private String collection;
    private String docId;

    StorageService storageService;

    String jsonStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
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
        collection = "TeamsData";
        docId = "5a9cfc66e4b0a50447c2bf19";

        //Toast.makeText(getApplicationContext(), "Please wait while Loading!", Toast.LENGTH_LONG).show();

        int network_status = NetworkUtil.getConnectivityStatus(getApplicationContext());
        if (network_status == 0) {
            //Snackbar.make(findViewById(R.id.mainView), "Connect to Internet for Updates!", Snackbar.LENGTH_LONG).show();
        }

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // AdRequest adRequest = new AdRequest.Builder().addTestDevice("A13F30D3EC2BADE120367028FB301007").build();
        mAdView.loadAd(adRequest);

        Intent intent = getIntent();
        pageNo = intent.getExtras().getInt("pageno");

        imageView =  findViewById(R.id.image);
        activity_players =  findViewById(R.id.activity_players);

        playerlist = new ArrayList<>();
        bowler=new ArrayList<>();
        allrounder=new ArrayList<>();
        wicketkeeper=new ArrayList<>();
        new RemoteDataTask().execute();

        captianText.setTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Semibold.ttf"));
        if (pageNo == 0) {
            imageView.setBackgroundResource(R.drawable.srh);
            activity_players.setBackgroundResource(R.drawable.srh_bg);
        } else if (pageNo == 1) {
            imageView.setBackgroundResource(R.drawable.mi);
            activity_players.setBackgroundResource(R.drawable.mi_bg);
        } else if (pageNo == 2) {
            imageView.setBackgroundResource(R.drawable.rcb);
            activity_players.setBackgroundResource(R.drawable.rcb_bg);
        } else if (pageNo == 3) {
            imageView.setBackgroundResource(R.drawable.pune);
            activity_players.setBackgroundResource(R.drawable.rp_bg);
        } else if (pageNo == 4) {
            imageView.setBackgroundResource(R.drawable.gujarat);
            activity_players.setBackgroundResource(R.drawable.gl_bg);
        } else if (pageNo == 5) {
            imageView.setBackgroundResource(R.drawable.delhi);
            activity_players.setBackgroundResource(R.drawable.dd_bg);
        } else if (pageNo == 6) {
            imageView.setBackgroundResource(R.drawable.punjab);
            activity_players.setBackgroundResource(R.drawable.kp_bg);
        } else if (pageNo == 7) {
            imageView.setBackgroundResource(R.drawable.kkr);
            activity_players.setBackgroundResource(R.drawable.kkr_bg);
        }


        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(PlayersActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManagaer);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager verticalLayoutManagaer2 = new LinearLayoutManager(PlayersActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(verticalLayoutManagaer2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(aAdapter);


        LinearLayoutManager verticalLayoutManagaer3 = new LinearLayoutManager(PlayersActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(verticalLayoutManagaer3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setAdapter(bAdapter);


        LinearLayoutManager verticalLayoutManagaer4 = new LinearLayoutManager(PlayersActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView4.setLayoutManager(verticalLayoutManagaer4);
        recyclerView4.setItemAnimator(new DefaultItemAnimator());
        recyclerView4.setAdapter(wAdapter);

    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<String, Void, String> {
        Storage storage;
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDoalog = new ProgressDialog(PlayersActivity.this);
            progressDoalog.setMessage("Its Loading....");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            /*progressDoalog.setIndeterminate(true);
            progressDoalog.setCancelable(false);*/
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

                        JSONObject jsonObjectsrh=jsonObj.getJSONObject("srh");

                        JSONObject jsonObjectmi=jsonObj.getJSONObject("mi");

                        JSONObject jsonObjectrcb=jsonObj.getJSONObject("rcb");

                        JSONObject jsonObjectcsk=jsonObj.getJSONObject("csk");

                        JSONObject jsonObjectrr=jsonObj.getJSONObject("rr");

                        JSONObject jsonObjectdd=jsonObj.getJSONObject("dd");

                        JSONObject jsonObjectkix=jsonObj.getJSONObject("kix");

                        JSONObject jsonObjectkkr=jsonObj.getJSONObject("kkr");

                       if(pageNo == 0) {
                           getData(jsonObjectsrh);
                       }
                       else if(pageNo == 1)
                       {
                           getData(jsonObjectmi);
                       }
                       else if(pageNo == 2)
                       {
                           getData(jsonObjectrcb);
                       }
                       else if(pageNo == 3)
                       {
                           getData(jsonObjectcsk);
                       }
                       else if(pageNo == 4)
                       {
                           getData(jsonObjectrr);
                       }
                       else if(pageNo == 5)
                       {
                           getData(jsonObjectdd);
                       }
                       else if(pageNo == 6)
                       {
                           getData(jsonObjectkix);
                       }
                       else if(pageNo == 7)
                       {
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


            mAdapter = new Players_Adapter(getApplicationContext(), playerlist);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            aAdapter = new Allrounder_Adapter(getApplicationContext(), allrounder);
            recyclerView2.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();

            bAdapter = new Bowlers_Adapter(getApplicationContext(), bowler);
            recyclerView3.setAdapter(bAdapter);
            bAdapter.notifyDataSetChanged();

            wAdapter = new Wicketkeeper_Adapter(getApplicationContext(), wicketkeeper);
            recyclerView4.setAdapter(wAdapter);
            wAdapter.notifyDataSetChanged();
        }


        private void getResult()
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

        public void getData(JSONObject jsonObject) {
            JSONArray jsonArraybowlers = null;
            try {
                jsonArraybowlers = jsonObject.getJSONArray("bowler");

                for (int i = 0; i < jsonArraybowlers.length(); i++) {
                    System.out.println("bowlers===" + jsonArraybowlers.get(i));
                    String bowlerstring=jsonArraybowlers.get(i).toString();
                    bowler.add(bowlerstring);
                }
                JSONArray jsonArraybatsmen = jsonObject.getJSONArray("batsmen");
                for (int i = 0; i < jsonArraybatsmen.length(); i++) {
                    System.out.println("batsmen===" + jsonArraybatsmen.get(i));
                    String batsmen=jsonArraybatsmen.get(i).toString();
                    playerlist.add(batsmen);
                }

                JSONArray jsonArrayallrounder = jsonObject.getJSONArray("allrounder");
                for (int i = 0; i < jsonArrayallrounder.length(); i++) {
                    System.out.println("allrounders===" + jsonArrayallrounder.get(i));
                    String allrounderstring=jsonArrayallrounder.get(i).toString();
                    allrounder.add(allrounderstring);
                }

                JSONArray jsonArraywicket = jsonObject.getJSONArray("wicketkeeper");
                for (int i = 0; i < jsonArraywicket.length(); i++) {
                    System.out.println("wicket===" + jsonArraywicket.get(i));
                    String wicketkeeperstring=jsonArraywicket.get(i).toString();
                    wicketkeeper.add(wicketkeeperstring);
                }
                final String captian = jsonObject.getString("captian");
                System.out.println("captian===" + captian);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDoalog.dismiss();
                        captianText.setText(String.format("%s\u00A9", captian));
                        batsmen.setText("\nBatsmen");
                        allrounders.setText("\nAll Rounders");
                        bowlers.setText("\nBowlers");
                        keepers.setText("\nWicket Keepers");
                        mAdapter.notifyDataSetChanged();
                        bAdapter.notifyDataSetChanged();
                        aAdapter.notifyDataSetChanged();
                        wAdapter.notifyDataSetChanged();

                      /*  imageView.setBackgroundResource(R.drawable.srh);
                        activity_players.setBackgroundResource(R.drawable.srh_bg);*/
                    }
                });
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
