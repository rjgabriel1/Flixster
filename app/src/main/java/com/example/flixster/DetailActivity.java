package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    private static  final String YOUTUBE_API_KEY = "AIzaSyAkVTjJGH4_o5cy5PGYD5E_LWqbq8yOu2c";
    public static final String VIDEOS_URL ="https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    Movie movie;
ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        ratingBar = binding.ratingBar;
        youTubePlayerView = binding.player;

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        binding.setMovie(movie);
//        tvTitle.setText(movie.getTitle());
//       tvOverview.setText(movie.getOverView());
//       ratingBar.setRating((float) movie.getRating());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL,movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                  JSONArray results = json.jsonObject.getJSONArray("results");
                  if (results.length()==0){
                      return;
                  }
                  String youtubeKey=results.getJSONObject(0).getString("key");

                  initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity","failed",e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });


    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                if (movie.getRating()> 5){
                    youTubePlayer.loadVideo(youtubeKey);
                }else {  youTubePlayer.cueVideo(youtubeKey);}
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


            }
        });
    }
}