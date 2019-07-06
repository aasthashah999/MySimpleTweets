package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class DetailView extends AppCompatActivity {

    TextView tvName;
    TextView tvUsername;
    TextView tvBody;
    ImageView ivProfile;
    ImageView ivtImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvBody = findViewById(R.id.tvBody);
        ivProfile = findViewById(R.id.ivProfile);
        ivtImage = findViewById(R.id.ivtImage);
        Tweet tweet = (Tweet) getIntent().getSerializableExtra("tweet1");
        tvName.setText(tweet.user.name);
        tvUsername.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile);
        Glide.with(this)
                .load(tweet.image)
                .into(ivtImage);

    }
}
