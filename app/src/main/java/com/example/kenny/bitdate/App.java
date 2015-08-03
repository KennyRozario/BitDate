package com.example.kenny.bitdate;

import android.app.Application;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by Kenny on 2015-07-26.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "646pXnyhzr1t9YXA5nn00rPEFEH6GBwvGIHQ4hP7", "t1hysbTpMpiJX2XTFNGCOBH9Qex6sOXHspiv1ZoQ");
        ParseFacebookUtils.initialize(this);
    }
}
