package com.example.geniusplaza.tweetsearch;

/**
 * Created by geniusplaza on 6/8/17.
 */

import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geniusplaza.tweetsearch.Objects.Tweet;
import com.example.geniusplaza.tweetsearch.Objects.TweetList;

import java.util.List;

/**
 * Created by geniusplaza on 6/6/17.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<Tweet> mTweet;
    private Context mContext;

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
        Context context = parent.getContext();

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tweets, parent, false);
        //ViewHolder vh = new ViewHolder(v);
        //return new ViewHolder(v, binding);
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_tweets, parent, false);
//        return new ViewHolder(binding);
        return new ViewHolder(v);
    }
    //Replace contents of a view(invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Tweet tweet = mTweet.get(position);
        Log.d("PostAdapter",tweet.getText().toString() );
        holder.rowId.setText(tweet.getText().toString());
        //Log.d("PostAdapter", "Bind success!");
        //holder.bind(tweet);
        //holder.rowSong.setText(song.getSongName());
        //Glide.with(mContext).load(song.getArtistUrl()).into(holder.rowImage);
    }
    // Replace the contents of a view (invoked by the layout manager)


    // Return the size of your dataset (invoked by the layout manager)
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //LinearLayout rowLayout;
        //private final ViewDataBinding binding;
        public TextView rowId;

        public ViewHolder(View v) {
            super(v);
            //super(binding.getRoot());
            //rowLayout = (LinearLayout) v.findViewById(R.id.row_layout);
            rowId = (TextView) v.findViewById(R.id.row_id);
            //binding = DataBindingUtil.bind(post_contents_row);
            //this.binding = binding;
        }
//        public void bind (Object obj){
//            //binding.setVariable(com.example.geniusplaza.sample.BR.obj, obj);
//            //binding.executePendingBindings();
//        }
    }
}