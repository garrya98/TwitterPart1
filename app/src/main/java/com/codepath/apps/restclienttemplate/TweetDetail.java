package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetail extends AppCompatActivity {
    Tweet tweet;
    TextView tvName;
    TextView tvBody;
    TextView tvUsername;
    TextView tvTimeStamp;
    ImageButton ibReply;
    ImageButton ibRetweet;
    ImageView ivProfilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        ibReply = (ImageButton) findViewById(R.id.ibReply);
        ibRetweet = (ImageButton) findViewById(R.id.ibRetweet);
        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);

        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvTimeStamp.setText(TweetAdapter.getRelativeTimeAgo(tweet.createdAt));

        // load profile image with Glide
        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfilePic);

    }
    }



