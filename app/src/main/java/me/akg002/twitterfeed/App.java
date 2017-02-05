package me.akg002.twitterfeed;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by AKG002 on 04-02-2017.
 */

public class App extends Application {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    @Override
    public void onCreate() {
        super.onCreate();
         String TWITTER_KEY = getResources().getString(R.string.twitter_key);
         final String TWITTER_SECRET = getResources().getString(R.string.twitter_secret);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }
}
