package com.example.flixster.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.databinding.PopularmovieActivityBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // inflating a layout from XML and return the Holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 0) {
            ItemMovieBinding movieView = DataBindingUtil.inflate(inflater,R.layout.item_movie, parent, false);
            viewHolder =  new ViewHolder(movieView);
        } else {
            PopularmovieActivityBinding moviepopView = DataBindingUtil.inflate(inflater,R.layout.popularmovie_activity,parent ,false);
            viewHolder = new PopViewHolder(moviepopView);

        }
        return viewHolder;
    }
// Populating data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //Get the movie at the passed position
        Movie movie = movies.get(position);
        // Bind the movie into the ViewHolder
        if(holder.getItemViewType() == 0){

            ViewHolder firstview = (ViewHolder) holder;
            firstview.bind(movie);
        } else {
            PopViewHolder seconview = (PopViewHolder) holder;
            seconview.bind(movie);
        }

    }

//Return the total of item in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getRating() > 5 ){
            return 1;
        } else{
            return 0;
        }
    }



    public  class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        ItemMovieBinding binding;

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull ItemMovieBinding itemView) {
            super(itemView.getRoot());
            tvTitle = itemView.tvTitle;
            tvOverview = itemView.tvOverview;
            ivPoster = itemView.ivPoster;
            container = itemView.container;
            binding =itemView;
        }

        public void bind(Movie movie) {
            binding.setMovie(movie);


//           String pictureUrl;
//            // if phone is in landscape mode
//            if( context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//                // then pictureUrl = back drop image
//                pictureUrl = movie.getBackdropPath();
//            } else {
//                //else pictureUrl = poster image
//                pictureUrl = movie.getPosterPath();
//            }


//            Glide.with(context)
//            .load(pictureUrl).placeholder(R.drawable.loading).into(ivPoster);

            // 1. register the click listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //2.Navigate to new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
//                    i.putExtra("title",movie.getTitle());
                   i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });


        }
    }
    public class PopViewHolder extends RecyclerView.ViewHolder{
        ImageView popMovie;
        RelativeLayout containerpop;
        PopularmovieActivityBinding binding;


        public PopViewHolder(@NonNull PopularmovieActivityBinding itemView) {
            super(itemView.getRoot());
            popMovie = itemView.popMovie;
            containerpop = itemView.containerpop;
            binding = itemView;
        }
        public  void bind(Movie movie){

            binding.setMovie(movie);
//            String pictureUrl;
//            pictureUrl = movie.getBackdropPath();
//            Glide.with(context)
//                    .load(pictureUrl).placeholder(R.drawable.loading) .transform(new RoundedCorners(60 )).into(popMovie);

            containerpop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //2.Navigate to new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
//                    i.putExtra("title",movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptions options = ActivityOptions.
                            makeSceneTransitionAnimation((Activity) context,popMovie,"fade");

                    context.startActivity(i,options.toBundle());

                }
            });
        }
    }
    public static class BindingAdapterUtils {
        @BindingAdapter({"lessPop"})
        public static void loadImage(ImageView view, String url) {
            Glide.with(view )
                    .load(url).placeholder(R.drawable.loading).transform(new RoundedCorners(50)).into(view);

        }
    }

    public static class BindingAdapterUtilspop {
        @BindingAdapter({"morePop"})
        public static void loadImage(ImageView view, String url) {
            Glide.with(view )
                    .load(url).placeholder(R.drawable.loading).transform(new RoundedCorners(50)).into(view);

        }
    }

}
