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

import java.util.ArrayList;
import java.util.List;

public class WatchlistMoviesAdapter extends RecyclerView.Adapter<WatchlistMoviesAdapter.WatchlistMoviesViewHolder>{
    private ArrayList<Movie> watchlist;
    Context context;

    public WatchlistMoviesAdapter(List<Movie> watchlist, Context context){
        this.watchlist = new ArrayList<>(watchlist);
        this.context = context;
    }

    @NonNull
    @Override
    public WatchlistMoviesAdapter.WatchlistMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlistmovie_view, parent, false);
        return new WatchlistMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistMoviesAdapter.WatchlistMoviesViewHolder holder, int position) {
        Movie m = watchlist.get(position);
        holder.textView.setText(m.getTitle());
        holder.imageView.setImageResource(m.getPoster_path());

        holder.textView.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return watchlist.size();
    }

    public class WatchlistMoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public WatchlistMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageView_poster);
            textView =(TextView) itemView.findViewById(R.id.textView_movie_name);

        }
    }
}
