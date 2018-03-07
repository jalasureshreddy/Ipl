package com.krazynutz.iplt20info.activites;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.astuetz.PagerSlidingTabStrip;
import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.fragments.MainListFragment;

import org.codechimp.apprater.AppRater;
import org.codechimp.apprater.GoogleMarket;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class DashBoard extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    @BindView(R.id.fab_speed)
    FabSpeedDial fabSpeedDial;

    private PagerSlidingTabStrip tabs;

    boolean doubleBackToExitPressedOnce = false;
    FirebaseAnalytics mFirebaseAnalytics;
    String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        AppRater.app_launched(this);

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-5830084677705558~2010434224");
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5830084677705558~5975655423");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

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


        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    default:
                        return null;
                    case 0:
                        return MainListFragment.newInstance();
                    //case 1:
                    //  return DesignListFragment.newInstance("design");

                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    default:
                        return "";
                    case 0:
                        return "Home";
                    //case 1:
                    // return "Designs";
                }
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            public HeaderDesign getHeaderDesign(int position) {
                switch (position) {
                    default:
                        return null;

                    case 0:

                        return HeaderDesign.fromColorAndDrawable(getResources().getColor(R.color.colorPrimaryDark), getResources().getDrawable(R.drawable.banner));

                }

            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(2);
        tabs = mViewPager.getPagerTitleStrip();
        tabs.setViewPager(mViewPager.getViewPager());
        tabs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        tabs.setTypeface(null, Typeface.BOLD);

        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Refreshed token--: " + token);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter()
        {
            @Override
            public boolean onMenuItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_rate) {

                     AppRater.rateNow(DashBoard.this);

                } else if (id == R.id.nav_share) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    String shareBody = "https://play.google.com/store/apps/details?id=" + getPackageName();
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sendIntent, "Share via"));

                } else if (id == R.id.nav_feedback) {

                    new MaterialStyledDialog.Builder(DashBoard.this)
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
                                    if (intent.resolveActivity(DashBoard.this.getPackageManager()) != null) {
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

                    new MaterialStyledDialog.Builder(DashBoard.this)
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
                    new MaterialDialog.Builder(DashBoard.this)
                            .title("About us...")
                            .customView(R.layout.about_us_layout, true)
                            .negativeText("CLOSE").show();
                }

                return super.onMenuItemSelected(item);
            }
        });

    }


    @Override
    protected void onResume() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");

        super.onResume();
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
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.nav_play) {
            String url = "https://play.google.com/store/apps/developer?id=Krazynutz";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}





