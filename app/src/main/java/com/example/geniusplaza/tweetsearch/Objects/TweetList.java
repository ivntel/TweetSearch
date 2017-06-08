package com.example.geniusplaza.tweetsearch.Objects;

/**
 * Created by geniusplaza on 6/8/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class TweetList  {

    @SerializedName("statuses")
    public ArrayList<Tweet> tweets;

}