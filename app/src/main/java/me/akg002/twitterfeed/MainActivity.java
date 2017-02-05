package me.akg002.twitterfeed;

import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
ListView lv;
    Toolbar toolbar;
    EditText etSearch;
    ImageView ivSearch;
    ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;
    String query;
     Callback<TimelineResult<Tweet>> callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();

    }

    private void setUpViews(){
        lv = (ListView)findViewById(R.id.lv);
        lv.setEmptyView(findViewById(R.id.emptyLv));
      //  toolbar = (Toolbar)findViewById(R.id.toolbar);
etSearch = (EditText)findViewById(R.id.etSearch);
        ivSearch = (ImageView)findViewById(R.id.ivSearch);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        setSupportActionBar(toolbar);
        callback = new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                progressBar.setVisibility(View.GONE);
                ivSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(TwitterException exception) {
                progressBar.setVisibility(View.GONE);
                ivSearch.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Failed to connect.");
                builder.setCancelable(false);
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        };
      ivSearch.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              searchTweet(etSearch.getText().toString());

          }
      });

    }

    public void searchTweet(String hashTag){
        if (hashTag.isEmpty()) {
            Snackbar.make(coordinatorLayout,"Search field should not be empty.",Snackbar.LENGTH_SHORT).show();
            return;
        }
       hideKeyboard();
        ivSearch.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        SearchTimeline searchTimeline = new SearchTimeline.Builder().query("#"+hashTag).build();
        TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(MainActivity.this)
                .setTimeline(searchTimeline)
                .build();
        lv.setAdapter(adapter);
        searchTimeline.next(null,callback);
    }
    void hideKeyboard(){
        View v = this.getCurrentFocus();
        if (v!=null){
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }


}
