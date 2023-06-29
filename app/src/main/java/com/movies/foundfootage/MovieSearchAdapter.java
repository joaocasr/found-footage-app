package com.movies.foundfootage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.foundfootage.Models.Movie;
import com.movies.foundfootage.vInterface.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder> { //implements Filterable {
    public List<Movie> movieList = new ArrayList<>();
    public RecyclerViewInterface recyclerViewInterface;

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
        Picasso.get().load(m.getPoster_path()).placeholder(R.drawable.default_poster).into(holder.
                poster);



        Context context =holder.itemView.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("tgpref1",Context.MODE_PRIVATE);
        boolean tgpref = sharedPreferences.getBoolean(title ,false);

        if (tgpref){
            holder.fav.setChecked(true);
            holder.fav.setBackgroundResource(R.drawable.favourite_red_icon);
        }else {
            holder.fav.setChecked(false);
            holder.fav.setBackgroundResource(R.drawable.favourite_shadow_icon);
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void filteredMovies(List<Movie>filteredList){
        this.movieList= (ArrayList<Movie>) filteredList.stream().map(Movie::clone).collect(Collectors.toList());
        //this.movieList = filteredList;
        notifyDataSetChanged();
    }


    public String getTitle(int pos){
       return this.movieList.get(pos).getTitle();
    }

    public class MovieSearchViewHolder extends RecyclerView.ViewHolder {
        TextView title, rate, duration;
        ImageView poster;
        ToggleButton fav;


        public MovieSearchViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movietitle);
            rate = (TextView) itemView.findViewById(R.id.rate);
            duration = (TextView) itemView.findViewById(R.id.duration);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            fav = (ToggleButton) itemView.findViewById(R.id.favourite_button);


            final Context context = itemView.getContext();

            itemView.findViewById(R.id.favourite_button).setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if(fav.isChecked()) {
                            fav.setBackgroundResource(R.drawable.favourite_red_icon);
                            SharedPreferences sharedPreferences = context.getSharedPreferences("tgpref1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(getTitle(pos),true);
                            editor.commit();

                        }
                        else if(!fav.isChecked()){
                            fav.setBackgroundResource(R.drawable.favourite_shadow_icon);
                            SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("tgpref1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(getTitle(pos), false);
                            editor.commit();
                            }

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onFavClick(pos);
                        }
                    }
                }
            });

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