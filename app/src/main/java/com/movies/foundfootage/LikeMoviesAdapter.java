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
import java.util.stream.Collectors;

public class LikeMoviesAdapter extends RecyclerView.Adapter<LikeMoviesAdapter.LikeMoviesViewHolder> {
    private ArrayList<Movie> likedMovies;
    Context context;


    public LikeMoviesAdapter(List<Movie> likedMovies, Context context){
        this.likedMovies = new ArrayList<>(likedMovies);
        this.context = context;
    }

    @NonNull
    @Override
    public LikeMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.likemovie_view, parent, false);
        return new LikeMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeMoviesViewHolder holder, int position) {
        Movie m = likedMovies.get(position);
        String title = m.getTitle();
        holder.titulo.setText(title);
        holder.data.setText(m.getRelease());
        holder.poster.setImageResource(m.getPoster_path());

    }

    @Override
    public int getItemCount() {
        return likedMovies.size();
    }

    public void addData(List<Movie> likedMovies) {
        this.likedMovies = new ArrayList<>();
        this.likedMovies = (ArrayList<Movie>) likedMovies.stream().map(Movie::clone).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public class LikeMoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView titulo,data;

        public LikeMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster =(ImageView) itemView.findViewById(R.id.likedPoster);
            titulo =(TextView) itemView.findViewById(R.id.likedTitle);
            data = (TextView) itemView.findViewById(R.id.likedRelease);

        }
    }
}
