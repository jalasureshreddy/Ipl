package com.krazynutz.iplt20info.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScrn_Activity extends AppCompatActivity
{
 /*   // Set Duration of the splash_screen
    long Delay = 2000;
    Timer RunSplash;
    TimerTask ShowSplash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.splash_screen);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        // Get the view from splash_screen.xml
      //  setContentView(R.layout.activity_splash);

        // Create a Timer
        RunSplash = new Timer();

        // Task to do when the timer ends
        ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class
                finish();

                // Start DashBoard_Activity.class
                Intent myIntent = new Intent(SplashScrn_Activity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }*/

    private static int SPLASH_TIME_OUT = 3000;  //2 Seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());

      /*  Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // close this activity
                finish();
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScrn_Activity.this, New_Dash.class);
                startActivity(i);


            }
        }, SPLASH_TIME_OUT);
    }
}
