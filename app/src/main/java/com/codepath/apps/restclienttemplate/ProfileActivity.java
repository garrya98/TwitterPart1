package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
TwitterClient twitterClient;
    Tweet tweet;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        twitterClient = TwitterApp.getRestClient();
        if (user != null) {
            // create the user fragment
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(user.screenName);
            //display the user timeline fragment inside the container
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // make change
            ft.replace(R.id.flContainer, userTimelineFragment);
            // commit
            ft.commit();

            getSupportActionBar().setTitle(user.screenName);
            populateUserHeadline(user);
        } else {
            // create the user fragment
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(null);
            //display the user timeline fragment inside the container
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // make change
            ft.replace(R.id.flContainer, userTimelineFragment);
            // commit
            ft.commit();

            twitterClient.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
                    // deserialize the User object
                    try {
                        User user = User.fromJSON(response);
                        // set the title of the ActionBar based on the user information
                        getSupportActionBar().setTitle(user.screenName);
                        // populate the user headline

                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
    public void populateUserHeadline(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");

        // load profile image with Glide
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);

    }
}
