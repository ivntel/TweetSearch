package com.example.geniusplaza.tweetsearch.Retrofit;

/**
 * Created by geniusplaza on 6/8/17.
 */

import com.example.geniusplaza.tweetsearch.ApiConstants;
import com.example.geniusplaza.tweetsearch.Objects.OAuthToken;
import com.example.geniusplaza.tweetsearch.Objects.Tweet;
import com.example.geniusplaza.tweetsearch.Objects.TweetList;
import com.example.geniusplaza.tweetsearch.Objects.UserDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TwitterApi {

    String BASE_URL = "https://api.twitter.com/";

    @FormUrlEncoded
    @POST("oauth2/token")
    Call<OAuthToken> postCredentials(@Field("grant_type") String grantType);

    @GET("/1.1/users/show.json")
    Call<UserDetails> getUserDetails(@Query("screen_name") String name);

    @GET(ApiConstants.TWITTER_HASHTAG_SEARCH_CODE )
    Call<TweetList> callbackgetTweetList(@Header("Authorization") String authorization, @Query("q") String hashtag);
}