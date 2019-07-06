package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class replyTweet extends AppCompatActivity {

    TextView screenName;
    Button tweetButton;
    EditText tiText;
    ImageButton closeButton;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replytweet);
        screenName = findViewById(R.id. tvReplyTo);
        tweetButton = findViewById(R.id.tweetButton1);
        tiText = findViewById(R.id.tiText1);
        closeButton = findViewById(R.id.closeButton);
        final Tweet tweet = (Tweet) getIntent().getSerializableExtra("tweet1");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
                startActivity(i);
            }
        });
        String name = getIntent().getStringExtra("username");
        screenName.setText("Replying to @" + name);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = tiText.getText().toString();
                TwitterClient client = new TwitterClient(getApplicationContext());
                client.sendTweet(message, tweet,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TwitterClient", response.toString());
                        try {
                            Tweet tweet = Tweet.fromJSON(response);
                            Intent i = new Intent();
                            i.putExtra("tweet1", tweet);
                            setResult(RESULT_OK, i);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("TwitterClient", responseString);
                        throwable.printStackTrace();
                    }
                });


            }
        });
    }
}
