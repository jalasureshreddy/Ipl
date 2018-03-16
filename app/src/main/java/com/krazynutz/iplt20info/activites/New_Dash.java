package com.krazynutz.iplt20info.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.adapters.MainList_Adapter;
import com.krazynutz.iplt20info.adapters.MatchList_Adaptor;
import com.krazynutz.iplt20info.utils.NetworkUtil;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CacheManager;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

import org.codechimp.apprater.AppRater;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class New_Dash extends AppCompatActivity implements View.OnClickListener
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_speed)
    FabSpeedDial fabSpeedDial;

    @BindView(R.id.button_one)
    Button btn_Teams;
    @BindView(R.id.button_two)
    Button btn_Shcd;
    @BindView(R.id.button_thre)
    Button btn_stad;
    @BindView(R.id.button_four)
    Button btn_res;
    @BindView(R.id.button_five)
    Button btn_point;
    @BindView(R.id.button_six)
    Button btn_stats;
    @BindView(R.id.live)
    TextView live;
    @BindView(R.id.desc)
    TextView descc;
    @BindView(R.id.des)
    TextView dess;
   //@BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    boolean doubleBackToExitPressedOnce = false;
    FirebaseAnalytics mFirebaseAnalytics;
    String dbName, collection, docId, jsonStr, url, live_data, desc, des;
    StorageService storageService;
    ProgressDialog mProgressDialog;

    private Timer timer;
    TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dash);
        ButterKnife.bind(this);

        Fabric.with(this, new Crashlytics());
        AppRater.app_launched(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5830084677705558~5975655423");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        if (toolbar != null) {
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


        int network_status = NetworkUtil.getConnectivityStatus(getApplicationContext());
        if (network_status == 0) {
            Snackbar.make(findViewById(R.id.content_main), "Connect to Internet for Updates!", Snackbar.LENGTH_LONG).show();
        }

        btn_Teams.setOnClickListener(this);
        btn_Teams.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        btn_Shcd.setOnClickListener(this);
        btn_Shcd.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        btn_stad.setOnClickListener(this);
        btn_stad.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        btn_res.setOnClickListener(this);
        btn_res.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        btn_point.setOnClickListener(this);
        btn_point.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        btn_stats.setOnClickListener(this);
        btn_stats.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter()
        {
            @Override
            public boolean onMenuItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_rate) {

                    AppRater.rateNow(New_Dash.this);

                } else if (id == R.id.nav_share) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    String shareBody = "https://play.google.com/store/apps/details?id=" + getPackageName();
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sendIntent, "Share via"));

                } else if (id == R.id.nav_feedback) {

                    new MaterialStyledDialog.Builder(New_Dash.this)
                            .setTitle("FeedBack!")
                            .setDescription("What can we improve? Your feedback is always welcome.")
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.mipmap.ic_feedback_black_48dp)
                            .setHeaderColor(R.color.colorblue)
                            .withDialogAnimation(true)
                            .setPositiveText("Feedback")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                                {

                                    Intent intent = new Intent("android.intent.action.SENDTO");
                                    intent.setData(Uri.parse("mailto:"));
                                    intent.putExtra("android.intent.extra.EMAIL", new String[]{"developers.krazynutz@gmail.com"});
                                    intent.putExtra("android.intent.extra.SUBJECT", "Feedback");
                                    if (intent.resolveActivity(New_Dash.this.getPackageManager()) != null) {
                                        startActivity(intent);
                                    }
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.content_main), "No Email Client Found", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                            })
                            .setNegativeText("Not Now")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();


                } else if (id == R.id.nav_play) {

                    new MaterialStyledDialog.Builder(New_Dash.this)
                            .setTitle("Developer's Apps!")
                            .setDescription("Hey Checkout the more apps from this Developer")
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.mipmap.ic_shop_black_48dp)
                            .setHeaderColor(R.color.colorblue)
                            .withDialogAnimation(true)
                            .setPositiveText("Play Store")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    String url = "https://play.google.com/store/apps/developer?id=Krazynutz";
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);

                                }
                            })
                            .show();

                } else if (id == R.id.nav_about) {
                    new MaterialDialog.Builder(New_Dash.this)
                            .title("About us...")
                            .customView(R.layout.about_us_layout, true)
                            .negativeText("CLOSE").show();
                }

                return super.onMenuItemSelected(item);
            }
        });

        live.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));
        descc.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));
        dess.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "JosefinSans-SemiBold.ttf"));

        dbName = "POINTSTABLE";
        collection = "LiveURL";
        docId = "58e78f51e4b0be582861c32e";

        App42API.initialize(getApplicationContext(),"dd210bf91797594f6aed3dccd464bc5a493ba418f1763cb083e1929a6faa09b8",
                "8677f049dd2b14686e05d27a83152b43cc2c1b4aa80e3ae97ca82f0163abeb23");
        App42CacheManager.setPolicy(App42CacheManager.Policy.NETWORK_FIRST);

        new RemoteDataTask().execute();

        /*mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
                        {
                            @Override
                            public void onRefresh()
                            {
                                try {
                                    timer = new Timer();
                                    timerTask = new TimerTask()
                                    {
                                        @Override
                                        public void run() {

                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run() {
                                                    mSwipeRefreshLayout.setRefreshing(false);
                                                    // getData(url);
                                                    new RemoteDataTask().execute();
                                                    live.setText(live_data);
                                                    descc.setText(desc);
                                                    dess.setText(des);
                                                }
                                            });

                                        }
                                    };
                                    timer.schedule(timerTask, 5000, 5000);
                                } catch (IllegalStateException e){
                                    android.util.Log.i("Damn", "resume error");
                                }
                                *//*new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run() {


                                    }
                                }, 3000);*//*
                            }
                        });*/
    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<String, Void, String>
    {
        Storage storage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mProgressDialog = new ProgressDialog(New_Dash.this);
          //  mProgressDialog.setCanceledOnTouchOutside(true);
          //  mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings)
        {
            storageService = App42API.buildStorageService();
            storageService.findDocumentById(dbName, collection, docId, new App42CallBack() {
                public void onSuccess(Object response) {
                    storage = (Storage) response;
                    if (storage.isFromCache()) {

                        networkCall();
                    } else {
                        networkCall();
                    }
                    try {

                        JSONObject jsonObj = new JSONObject(jsonStr);

                        url = jsonObj.getString("url");

                        getData(url);

                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        if(live_data.isEmpty() || live_data == null)
                                        {
                                            live.setText("Connect to Internet for Live Score Updates");
                                            live.setTextSize(20);
                                            descc.setText("--");
                                            dess.setText("--");
                                        }
                                        else {
                                            live.setText(live_data);
                                            descc.setText(desc);
                                            dess.setText(des);
                                        }

                                    }
                                });

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
           // mProgressDialog.dismiss();
        }

        public void networkCall() {
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

    }

    public void getData(String url)
    {
        StringBuffer buffer = new StringBuffer();
        try {
            //  Log.d("JSwa", "Connecting to ["+url[0]+"]");
            Document doc  = Jsoup.connect(url).get();
            //Log.d("JSwa", "Connected to ["+url[0]+"]");
            // Get document (HTML page) title
            String title = doc.title();
            Log.d("JSwA", "Title ["+title+"]");


            String filename = title;     // full file name
            int iend = filename.indexOf("|");
            if (iend != -1) {
                String subString = filename.substring(0, iend);
                buffer.append(subString + "\r\n");
            }

            //Elements ele = doc.select("meta[name=keywords]");
           // desc = ele.attr("content");
           // System.out.println("DATA--->" +desc );

            Elements ele1 = doc.select("div[class=match-information-strip");
            desc = ele1.text();
            System.out.println("DATA11--->" +desc );

            Elements ele11 = doc.select("div[class=innings-requirement");
            des = ele11.text();
            System.out.println("DATA11--->" +des );

        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        live_data = buffer.toString();
    }




    @Override
    public void onBackPressed()
    {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.content_main), "Press back again to exit", Snackbar.LENGTH_SHORT);
        snackbar.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view)
    {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button_one:
                intent = new Intent(this, TeamsActivity.class);
                break;
            case R.id.button_two:
                intent = new Intent(this, MatchesList.class);
                break;
            case R.id.button_thre:
                intent = new Intent(this, Stadium_List.class);
                break;
            case R.id.button_four:
                intent = new Intent(this, Results_Activity.class);
                break;
            case R.id.button_five:
                intent = new Intent(this, PointsTable.class);
                break;
            case R.id.button_six:
                intent = new Intent(this, Stats_Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
