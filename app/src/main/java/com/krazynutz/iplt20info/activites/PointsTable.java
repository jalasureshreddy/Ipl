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
    @BindView(R.id.teamssrh) TextView teams_srh;
    @BindView(R.id.srhmatch) TextView srh_match;
    @BindView(R.id.srhwon) TextView srh_won;
    @BindView(R.id.srhlost) TextView srh_lost;
    @BindView(R.id.srhdraw) TextView srh_draw;
    @BindView(R.id.srhpoints) TextView srh_points;
    @BindView(R.id.srhnrr) TextView srh_nrr;

    @BindView(R.id.table2) TableRow tableRow2;
    @BindView(R.id.teamsrcb) TextView teams_rcb;
    @BindView(R.id.rcbmatch) TextView rcb_match;
    @BindView(R.id.rcbwon) TextView rcb_won;
    @BindView(R.id.rcblost) TextView rcb_lost;
    @BindView(R.id.rcbdraw) TextView rcb_draw;
    @BindView(R.id.rcbpoints) TextView rcb_points;
    @BindView(R.id.rcbnrr) TextView rcb_nrr;

    @BindView(R.id.table3) TableRow tableRow3;
    @BindView(R.id.teamsmi) TextView teams_mi;
    @BindView(R.id.mimatch) TextView mi_match;
    @BindView(R.id.miwon) TextView mi_won;
    @BindView(R.id.milost) TextView mi_lost;
    @BindView(R.id.midraw) TextView mi_draw;
    @BindView(R.id.mipoints) TextView mi_points;
    @BindView(R.id.minrr) TextView mi_nrr;

    @BindView(R.id.table4) TableRow tableRow4;
    @BindView(R.id.teamsrps) TextView teams_rps;
    @BindView(R.id.rpsmatch) TextView rps_match;
    @BindView(R.id.rpswon) TextView rps_won;
    @BindView(R.id.rpslost) TextView rps_lost;
    @BindView(R.id.rpsdraw) TextView rps_draw;
    @BindView(R.id.rpspoints) TextView rps_points;
    @BindView(R.id.rpsnrr) TextView rps_nrr;

    @BindView(R.id.table5) TableRow tableRow5;
    @BindView(R.id.teamsgl) TextView teams_gl;
    @BindView(R.id.glmatch) TextView gl_match;
    @BindView(R.id.glwon) TextView gl_won;
    @BindView(R.id.gllost) TextView gl_lost;
    @BindView(R.id.gldraw) TextView gl_draw;
    @BindView(R.id.glpoints) TextView gl_points;
    @BindView(R.id.glnrr) TextView gl_nrr;

    @BindView(R.id.table6) TableRow tableRow6;
    @BindView(R.id.teamskkr) TextView teams_kkr;
    @BindView(R.id.kkrmatch) TextView kkr_match;
    @BindView(R.id.kkrwon) TextView kkr_won;
    @BindView(R.id.kkrlost) TextView kkr_lost;
    @BindView(R.id.kkrdraw) TextView kkr_draw;
    @BindView(R.id.kkrpoints) TextView kkr_points;
    @BindView(R.id.kkrnrr) TextView kkr_nrr;

    @BindView(R.id.table7) TableRow tableRow7;
    @BindView(R.id.teamsdd) TextView teams_dd;
    @BindView(R.id.ddmatch) TextView dd_match;
    @BindView(R.id.ddrwon) TextView dd_won;
    @BindView(R.id.ddlost) TextView dd_lost;
    @BindView(R.id.dddraw) TextView dd_draw;
    @BindView(R.id.ddpoints) TextView dd_points;
    @BindView(R.id.ddnrr) TextView dd_nrr;

    @BindView(R.id.table8) TableRow tableRow8;
    @BindView(R.id.teamskxip) TextView teams_kxip;
    @BindView(R.id.kxipmatch) TextView kxip_match;
    @BindView(R.id.kxiprwon) TextView kxip_won;
    @BindView(R.id.kxiplost) TextView kxip_lost;
    @BindView(R.id.kxipdraw) TextView kxip_draw;
    @BindView(R.id.kxippoints) TextView kxip_points;
    @BindView(R.id.kxipnrr) TextView kxip_nrr;

    @BindView(R.id.match) TextView match_played;
    @BindView(R.id.won) TextView match_won;
    @BindView(R.id.lost) TextView match_lost;
    @BindView(R.id.tied) TextView match_draw;
    @BindView(R.id.point) TextView match_points;
    @BindView(R.id.nrr) TextView match_nrr;

    @BindView(R.id.adView) AdView mAdView;

     String teammi,mimatches,miwins,milost,midraw,mipoints,minrr,
            teamsrh,srhmatches,srhwins,srhlost,srhdraw,srhpoints,srhnrr,
            teamrcb,rcbmatches,rcbwins,rcblost,rcbdraw,rcbpoints,rcbnrr,
            teamrps,rpsmatches,rpswins,rpslost,rpsdraw,rpspoints,rpsnrr,
            teamgl,glmatches,glwins,gllost,gldraw,glpoints,glnrr,
            teamkkr,kkrmatches,kkrwins,kkrlost,kkrdraw,kkrpoints,kkrnrr,
            teampunjab,punjabmatches,punjabwins,punjablost,punjabdraw,punjabpoints,punjabnrr,
            teamdd,ddmatches,ddwins,ddlost,dddraw,ddpoints,ddnrr;

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
        collection = "Points";
        docId = "58c7d0f2e4b0d7f86c675a0b";

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
                                JSONObject jsonObjDD= jsonObj.getJSONObject("DD");
                                dddraw = jsonObjDD.get("draw").toString();
                                ddlost = jsonObjDD.get("lost").toString();
                                ddmatches = jsonObjDD.get("matches").toString();
                                ddnrr = jsonObjDD.get("nrr").toString();
                                ddpoints = jsonObjDD.get("points").toString();
                                ddwins = jsonObjDD.get("wins").toString();

                                //GL
                                JSONObject jsonObjGL= jsonObj.getJSONObject("GL");
                                gldraw = jsonObjGL.get("draw").toString();
                                gllost = jsonObjGL.get("lost").toString();
                                glmatches = jsonObjGL.get("matches").toString();
                                glnrr = jsonObjGL.get("nrr").toString();
                                glpoints = jsonObjGL.get("points").toString();
                                glwins = jsonObjGL.get("wins").toString();

                                //KXIP
                                JSONObject jsonObjKIXP= jsonObj.getJSONObject("KIXP");
                                punjabdraw = jsonObjKIXP.get("draw").toString();
                                punjablost = jsonObjKIXP.get("lost").toString();
                                punjabmatches = jsonObjKIXP.get("matches").toString();
                                punjabnrr = jsonObjKIXP.get("nrr").toString();
                                punjabpoints = jsonObjKIXP.get("points").toString();
                                punjabwins = jsonObjKIXP.get("wins").toString();

                                //KKR
                                JSONObject jsonObjKKR= jsonObj.getJSONObject("KKR");
                                kkrdraw = jsonObjKKR.get("draw").toString();
                                kkrlost = jsonObjKKR.get("lost").toString();
                                kkrmatches = jsonObjKKR.get("matches").toString();
                                kkrnrr = jsonObjKKR.get("nrr").toString();
                                kkrpoints = jsonObjKKR.get("points").toString();
                                kkrwins = jsonObjKKR.get("wins").toString();

                                //MI
                                JSONObject jsonObjMI= jsonObj.getJSONObject("MI");
                                midraw = jsonObjMI.get("draw").toString();
                                milost = jsonObjMI.get("lost").toString();
                                mimatches = jsonObjMI.get("matches").toString();
                                minrr = jsonObjMI.get("nrr").toString();
                                mipoints = jsonObjMI.get("points").toString();
                                miwins = jsonObjMI.get("wins").toString();

                                //RCB
                                JSONObject jsonObjRCB= jsonObj.getJSONObject("RCB");
                                rcbdraw = jsonObjRCB.get("draw").toString();
                                rcblost = jsonObjRCB.get("lost").toString();
                                rcbmatches = jsonObjRCB.get("matches").toString();
                                rcbnrr = jsonObjRCB.get("nrr").toString();
                                rcbpoints = jsonObjRCB.get("points").toString();
                                rcbwins = jsonObjRCB.get("wins").toString();

                                //RPS
                                JSONObject jsonObjRPS= jsonObj.getJSONObject("RPS");
                                rpsdraw = jsonObjRPS.get("draw").toString();
                                rpslost = jsonObjRPS.get("lost").toString();
                                rpsmatches = jsonObjRPS.get("matches").toString();
                                rpsnrr = jsonObjRPS.get("nrr").toString();
                                rpspoints = jsonObjRPS.get("points").toString();
                                rpswins = jsonObjRPS.get("wins").toString();

                                //SRH
                                JSONObject jsonObjSRH= jsonObj.getJSONObject("SRH");
                                srhdraw = jsonObjSRH.get("draw").toString();
                                srhlost = jsonObjSRH.get("lost").toString();
                                srhmatches = jsonObjSRH.get("matches").toString();
                                srhnrr = jsonObjSRH.get("nrr").toString();
                                srhpoints = jsonObjSRH.get("points").toString();
                                srhwins = jsonObjSRH.get("wins").toString();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //setValues
                                        teams_dd.setText("DD");
                                        dd_match.setText(ddmatches);
                                        dd_won.setText(ddwins);
                                        dd_lost.setText(ddlost);
                                        dd_draw.setText(dddraw);
                                        dd_points.setText(ddpoints);
                                        dd_nrr.setText(ddnrr);

                                        //setValues
                                        teams_mi.setText("MI");
                                        mi_match.setText(mimatches);
                                        mi_won.setText(miwins);
                                        mi_lost.setText(milost);
                                        mi_draw.setText(midraw);
                                        mi_points.setText(mipoints);
                                        mi_nrr.setText(minrr);

                                        //setValues
                                        teams_srh.setText("SRH");
                                        srh_match.setText(srhmatches);
                                        srh_won.setText(srhwins);
                                        srh_lost.setText(srhlost);
                                        srh_draw.setText(srhdraw);
                                        srh_points.setText(srhpoints);
                                        srh_nrr.setText(srhnrr);

                                        //setValues
                                        teams_kxip.setText("KXIP");
                                        kxip_match.setText(punjabmatches);
                                        kxip_won.setText(punjabwins);
                                        kxip_lost.setText(punjablost);
                                        kxip_draw.setText(punjabdraw);
                                        kxip_points.setText(punjabpoints);
                                        kxip_nrr.setText(punjabnrr);

                                        //setValues
                                        teams_kkr.setText("KKR");
                                        kkr_match.setText(kkrmatches);
                                        kkr_won.setText(kkrwins);
                                        kkr_lost.setText(kkrlost);
                                        kkr_draw.setText(kkrdraw);
                                        kkr_points.setText(kkrpoints);
                                        kkr_nrr.setText(kkrnrr);

                                        //setValues
                                        teams_gl.setText("GL");
                                        gl_match.setText(glmatches);
                                        gl_won.setText(glwins);
                                        gl_lost.setText(gllost);
                                        gl_draw.setText(gldraw);
                                        gl_points.setText(glpoints);
                                        gl_nrr.setText(glnrr);

                                        //setValues
                                        teams_rcb.setText("RCB");
                                        rcb_match.setText(rcbmatches);
                                        rcb_won.setText(rcbwins);
                                        rcb_lost.setText(rcblost);
                                        rcb_draw.setText(rcbdraw);
                                        rcb_points.setText(rcbpoints);
                                        rcb_nrr.setText(rcbnrr);

                                        //setValues
                                        teams_rps.setText("RPS");
                                        rps_match.setText(rpsmatches);
                                        rps_won.setText(rpswins);
                                        rps_lost.setText(rpslost);
                                        rps_draw.setText(rpsdraw);
                                        rps_points.setText(rpspoints);
                                        rps_nrr.setText(rpsnrr);
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
                mProgressDialog.dismiss();
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

        teams_srh.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_dd.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_gl.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_kkr.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_kxip.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_rps.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_rcb.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));
        teams_mi.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Oxygen.otf"));

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
