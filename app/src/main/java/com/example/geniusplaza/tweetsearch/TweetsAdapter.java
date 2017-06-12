package com.example.geniusplaza.tweetsearch;

/**
 * Created by geniusplaza on 6/8/17.
 */

import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geniusplaza.tweetsearch.Objects.Tweet;
import com.example.geniusplaza.tweetsearch.Objects.TweetList;
import com.bumptech.glide.Glide;
import com.example.geniusplaza.tweetsearch.Objects.TwitterUser;

import java.util.List;

/**
 * Created by geniusplaza on 6/6/17.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<Tweet> mTweet;
    private Context mContext;
    //private TwitterUser mTwitterUser;

    public static final String TAG = TweetsAdapter.class.getSimpleName();

    public TweetsAdapter(Context context, List<Tweet> tweet) {
        mContext = context;
        mTweet = tweet;

    }
    public Context getContext(){
        return mContext;
    }

    public void updateData(TweetList tweetList) {
        mTweet = tweetList.tweets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTweet.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_tweets, parent, false);
        return new ViewHolder(binding);

    }
    //Replace contents of a view(invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Tweet tweet = mTweet.get(position);
        Log.d("PostAdapter",tweet.getText().toString() );

        holder.bind(tweet);
        //Glide.with(mContext).load(mTwitterUser.getProfileImageUrl()).into(holder.profile_pic);


    }
    // Replace the contents of a view (invoked by the layout manager)


    // Return the size of your dataset (invoked by the layout manager)
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //LinearLayout rowLayout;
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind (Object obj){
            binding.setVariable(com.example.geniusplaza.tweetsearch.BR.obj, obj);
            binding.executePendingBindings();
        }
    }
}