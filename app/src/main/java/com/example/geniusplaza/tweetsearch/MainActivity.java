package com.example.geniusplaza.tweetsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geniusplaza.tweetsearch.Objects.OAuthToken;
import com.example.geniusplaza.tweetsearch.Objects.Tweet;
import com.example.geniusplaza.tweetsearch.Objects.TweetList;
import com.example.geniusplaza.tweetsearch.Objects.TwitterUser;
import com.example.geniusplaza.tweetsearch.Objects.UserDetails;
import com.example.geniusplaza.tweetsearch.Retrofit.TwitterApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String credentials = Credentials.basic(ApiConstants.CONSUMER_KEY, ApiConstants.CONSUMER_SECRET);

    Button requestTokenButton;
    Button requestUserDetailsButton;
    EditText usernameEditText;
    TextView usernameTextView;
    List<Tweet> mTweets = new ArrayList<Tweet>();
    TwitterUser mTwitterUser;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    TweetsAdapter mAdapter;
    ImageView profileImage;
    TextView nameTextView;
    TwitterApi twitterApi;
    OAuthToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestTokenButton = (Button) findViewById(R.id.request_token_button);
        requestUserDetailsButton = (Button) findViewById(R.id.request_user_details_button);
        usernameEditText = (EditText) findViewById(R.id.username_edittext);

        usernameTextView = (TextView) findViewById(R.id.username_textview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        nameTextView = (TextView) findViewById(R.id.name_textview);
        profileImage = (ImageView)findViewById(R.id.profile_img);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new TweetsAdapter(this, mTweets);
        mRecyclerView.setAdapter(mAdapter);


        createTwitterApi();
    }

    private void createTwitterApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        token != null ? token.getAuthorization() : credentials);

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TwitterApi.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        twitterApi = retrofit.create(TwitterApi.class);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_token_button:
                //twitterApi.postCredentials("client_credentials").enqueue(tokenCallback);
                rxJavaPost();
                break;
            case R.id.request_user_details_button:
                String editTextInput = usernameEditText.getText().toString();
                //Glide.with(getApplicationContext()).load(mTwitterUser.getProfileImageUrl()).into(profileImage);
                if (!editTextInput.isEmpty()) {
                    //twitterApi.getUserDetails(editTextInput).enqueue(userDetailsCallback);
                    //twitterApi.callbackgetTweetList(editTextInput, "music").enqueue(tweetListCallback);
                    twitterApi.getUserDetails(editTextInput).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<UserDetails>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserDetails value) {
                            UserDetails userDetails = value;

                            nameTextView.setText(userDetails.getName() == null ? "no value" : userDetails.getName());
                            //Glide.with(getApplicationContext()).load(mTwitterUser.getProfileImageUrl()).into(profileImage);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                    twitterApi.getTwitterUserDetail(editTextInput,editTextInput).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<TwitterUser>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TwitterUser value) {
                            TwitterUser twitterUser = value;
                            Glide.with(getApplicationContext()).load(twitterUser.getProfileImageUrl()).into(profileImage);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                    twitterApi.callbackgetTweetList(editTextInput, "music").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<TweetList>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TweetList value) {
                            mAdapter.updateData(value);
                            Log.d("List Loaded", "Method test");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MainActivity.this, "Failure while requesting user details", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                } else {
                    Toast.makeText(this, "Please provide a username", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

//    Callback<OAuthToken> tokenCallback = new Callback<OAuthToken>() {
//        @Override
//        public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
//            if (response.isSuccessful()) {
//                requestTokenButton.setEnabled(false);
//                requestUserDetailsButton.setEnabled(true);
//                usernameTextView.setEnabled(true);
//                usernameEditText.setEnabled(true);
//                token = response.body();
//            } else {
//                Toast.makeText(MainActivity.this, "Failure while requesting token", Toast.LENGTH_LONG).show();
//                Log.d("RequestTokenCallback", "Code: " + response.code() + "Message: " + response.message());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<OAuthToken> call, Throwable t) {
//            t.printStackTrace();
//        }
//    };

    public void rxJavaPost() {
        twitterApi.postCredentials("client_credentials").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<OAuthToken>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(OAuthToken value) {
                requestTokenButton.setEnabled(false);
                requestUserDetailsButton.setEnabled(true);
                usernameTextView.setEnabled(true);
                usernameEditText.setEnabled(true);
                token = value;
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Failure while requesting token", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
//    Callback<UserDetails> userDetailsCallback = new Callback<UserDetails>() {
//        @Override
//        public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
//            if (response.isSuccessful()) {
//                UserDetails userDetails = response.body();
//
//                nameTextView.setText(userDetails.getName() == null ? "no value" : userDetails.getName());
//
//            } else {
//                Toast.makeText(MainActivity.this, "Failure while requesting user details", Toast.LENGTH_LONG).show();
//                Log.d("UserDetailsCallback", "Code: " + response.code() + "Message: " + response.message());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<UserDetails> call, Throwable t) {
//            t.printStackTrace();
//        }
//    };

//    Callback<TweetList> tweetListCallback = new Callback<TweetList>() {
//        @Override
//        public void onResponse(Call<TweetList> call, Response<TweetList> response) {
//            if (response.isSuccessful()) {
//                mAdapter.updateData(response.body());
//                Log.d("List Loaded", "Method test");
//
//            } else {
//                Toast.makeText(MainActivity.this, "Failure while requesting user details", Toast.LENGTH_LONG).show();
//                Log.d("UserDetailsCallback", "Code: " + response.code() + "Message: " + response.message());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<TweetList> call, Throwable t) {
//            t.printStackTrace();
//        }
//    };
//}