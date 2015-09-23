package com.example.avjindersinghsekhon.toodle;

import android.app.Application;
import android.content.pm.PackageManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsApplication extends Application {

    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker(){
        if(mTracker==null){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

            /*R.xml.app_tracker contains my Analytics code
            To use this, go to Google Analytics, and get
            your code, create a file under res/xml , and save
            your code as <string name="ga_trackingId">UX-XXXXXXXX-Y</string>
            */

            mTracker = analytics.newTracker(R.xml.app_tracker);
//
            mTracker.setAppName("Minimal");
            mTracker.enableExceptionReporting(true);
            try{
                mTracker.setAppId(getPackageManager().getPackageInfo(getPackageName(),0).versionName);
            }
            catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }

        }
        return mTracker;
    }
}
