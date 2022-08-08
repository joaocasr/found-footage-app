package com.movies.foundfootage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.foundfootage.Models.Movie;
import com.movies.foundfootage.Interface.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder>{ //implements Filterable {
    private ArrayList<Movie> movieList;
    private final RecyclerViewInterface recyclerViewInterface;

    public MovieSearchAdapter(List<Movie> movieList,RecyclerViewInterface recyclerViewInterface) {
        this.movieList = new ArrayList<>(movieList);
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MovieSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_view,parent,false);
        return new MovieSearchViewHolder(itemView,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchViewHolder holder, int position) {
        Movie m = movieList.get(position);
        String title = m.getTitle();

        holder.title.setText(title);
        StringBuilder rate = new StringBuilder();
        rate.append("Rate: ").append(m.getRate());
        holder.rate.setText(rate.toString());
        StringBuilder duration = new StringBuilder();

        double Decmin = m.getTime() % 1;
        int hour = (int) (m.getTime()-Decmin);
        int minute = (int) (Decmin*60);
        duration.append(hour).append("h ").append(minute).append("m");
        holder.duration.setText(duration);
        holder.poster.setImageResource(m.getPoster_path());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void filteredMovies(ArrayList<Movie>filteredList){
        this.movieList= (ArrayList<Movie>) filteredList.stream().map(Movie::clone).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public static class MovieSearchViewHolder extends RecyclerView.ViewHolder {
         TextView title, rate, duration;
         ImageView poster;


        public MovieSearchViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movietitle);
            rate = (TextView) itemView.findViewById(R.id.rate);
            duration = (TextView) itemView.findViewById(R.id.duration);
            poster = (ImageView) itemView.findViewById(R.id.poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onClick(pos);
                        }
                    }
                }
            });
        }
    }
}
