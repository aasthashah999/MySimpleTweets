package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Tweet implements Serializable {

    //fields
    private static final long serialVersionUID = 5177222050535318633L;
    public String body;
    public long uid; //database ID for the tweet
    public String createdAt;
    public User user;
    public String image;
    public int retweet;
    public int favorite;

    //deserialize the JSON data
    //basically converts JSON data to Tweet
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        //what does static do?
        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.retweet = jsonObject.getInt("retweet_count");
        tweet.favorite = jsonObject.getInt("favorite_count");
        //gets JSON object associated with the string
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        JSONObject obj = jsonObject.getJSONObject("entities");
        if (obj.has("media")) {
            tweet.image = obj.getJSONArray("media").getJSONObject(0).getString("media_url_https");
        }else {
            tweet.image = "";
        }

        return tweet;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
