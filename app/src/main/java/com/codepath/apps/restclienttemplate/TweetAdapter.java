package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    //pass in the Tweets in the constructor

    public ArrayList<Tweet> mTweets;
    Context context;

    public TweetAdapter(ArrayList<Tweet> tweets){
        mTweets = tweets;

    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //invoke when a new row must be made
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(tweetView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Tweet tweet = mTweets.get(i);

        viewHolder.tvUsername.setText(tweet.user.name);
        viewHolder.tvbody.setText(tweet.body);
        viewHolder.tvscreenName.setText("@" + tweet.user.screenName);
        viewHolder.tvRetweet.setText(Integer.toString(tweet.retweet));
        viewHolder.tvFavorite.setText(Integer.toString(tweet.favorite));
        viewHolder.tvCreatedAt.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.ivProfileImage);
        if (tweet.image != null){
            Glide.with(context)
                    .load(tweet.image)
                    .into(viewHolder.tweetImage);
        }else {
            viewHolder.tweetImage.setVisibility(View.GONE);
        }

        viewHolder.replyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v ){
                Intent intent = new Intent(context, replyTweet.class);
                intent.putExtra("username",tweet.user.screenName );
                intent.putExtra("tweet1",tweet );
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //for each row, inflate the layout and chache references into view
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivProfileImage;
        public ImageView tweetImage;
        public TextView tvUsername;
        public TextView tvbody;
        public TextView tvscreenName;
        public TextView tvCreatedAt;
        public ImageButton replyButton;
        public ImageButton reTweetButton;
        public TextView tvRetweet;
        public TextView tvFavorite;
        public ImageButton favButton;
        public ViewHolder(View itemView) {
            super(itemView);
            //find elements
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvbody = itemView.findViewById(R.id.tvBody);
            tvscreenName = itemView.findViewById(R.id.tvScreenName );
            tvCreatedAt = itemView.findViewById(R.id.tvCreateAt);
            replyButton = itemView.findViewById(R.id.replyButton);
            tweetImage = itemView.findViewById(R.id.tweetImage);
            reTweetButton = itemView.findViewById(R.id.retweetIcon);
            favButton = itemView.findViewById(R.id.favButton);
            tvRetweet = itemView.findViewById(R.id.tvRetweet);
            tvFavorite = itemView.findViewById(R.id.tvFavorite);

            itemView.setOnClickListener(this);

            reTweetButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Tweet tweet = mTweets.get(getAdapterPosition());
                    mTweets.add(tweet);
                    tweet.retweet += 1;
                    tvRetweet.setText(Integer.toString(tweet.retweet));
                }
            });

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tweet tweet = mTweets.get(getAdapterPosition());
                    tweet.favorite += 1;
                    tvFavorite.setText(Integer.toString(tweet.favorite));
                }
            });
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION){
                Tweet tweet = mTweets.get(position);


                //creating intent for the new activity
                Intent intent = new Intent(context, DetailView.class);

                // serialize the movie using parceler, use its short name as a key
                intent.putExtra("tweet1", tweet);
                // show the activity
                context.startActivity(intent);
            }

        }



    }

}
