package com.movies.foundfootage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.foundfootage.Models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieReleaseAdapter extends RecyclerView.Adapter<MovieReleaseAdapter.MovieReleaseViewHolder>{
    private ArrayList<Movie> releaseList;
    Context context;

    public MovieReleaseAdapter(List<Movie> l,Context context){
        this.releaseList =new ArrayList<>(l);
        this.context = context;
    }

    @NonNull
    @Override
    public MovieReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.release_view,parent,false);
        return new MovieReleaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReleaseViewHolder holder, int position) {
        Movie m = releaseList.get(position);
        String title = m.getTitle();
        holder.titulo.setText(title);
        holder.status.setText(m.getRelease());
        holder.plotrelease.setText(m.getPlot());
        Picasso.get().load(m.getPoster_path()).placeholder(R.drawable.default_poster).into(holder.
                poster);


    }

    @Override
    public int getItemCount() {
        return releaseList.size();
    }

    public static class MovieReleaseViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView titulo,status,plotrelease;
        public MovieReleaseViewHolder(@NonNull View itemView) {
            super(itemView);
            poster =(ImageView) itemView.findViewById(R.id.poster_release);
            titulo =(TextView) itemView.findViewById(R.id.release_movie_title);
            status = (TextView) itemView.findViewById(R.id.release_movie_status);
            plotrelease = (TextView) itemView.findViewById(R.id.release_movie_plot);
        }
    }
}

