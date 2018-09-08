package com.app.tuskan.moviestage.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.tuskan.moviestage.DetailActivity;
import com.app.tuskan.moviestage.R;
import com.app.tuskan.moviestage.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yakup on 25.5.2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public static class movieHolder extends RecyclerView.ViewHolder {

        ImageView imageView ;
        public movieHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.thumbnail_image_view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new movieHolder(layoutInflater.inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Movie item = movies.get(position);

        final movieHolder container = (movieHolder) holder;

        Picasso.with(context)
                .load(item.getMoviePoster())
                .error(R.mipmap.ic_launcher)
                .into(container.imageView);

        container.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("release_date", item.getReleaseDate());
                intent.putExtra("poster", item.getBackdrop_path());
                intent.putExtra("vote", item.getVoteAvarage());
                intent.putExtra("overview", item.getPlotSynopsis());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
