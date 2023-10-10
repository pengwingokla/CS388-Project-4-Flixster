package com.example.cs388_project_3_flixster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>{
    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieList = movies;

    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_banner, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.rating.setText(movie.getRating().toString());
        holder.title.setText(movie.getTitle());
        holder.desc.setText(movie.getDesc());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath())
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateData(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title, desc, rating;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextView);
            desc = itemView.findViewById(R.id.descTextView);
            rating = itemView.findViewById(R.id.ratingTextView);

            poster = itemView.findViewById(R.id.posterImageView); // Add ImageView for poster image


        }
        



    }
}
