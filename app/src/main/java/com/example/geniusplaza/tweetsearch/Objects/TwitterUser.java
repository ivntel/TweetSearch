package com.example.geniusplaza.tweetsearch.Objects;

/**
 * Created by geniusplaza on 6/8/17.
 */

import com.google.gson.annotations.SerializedName;

public class TwitterUser {

    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("name")
    public String name;

    @SerializedName("profile_image_url")
    public String profileImageUrl;

}