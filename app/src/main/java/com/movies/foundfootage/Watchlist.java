package com.movies.foundfootage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movies.foundfootage.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class Watchlist extends MainActivity {
    BottomNavigationView bottomNavigationView;
    public List<Movie> watchMovies = new ArrayList<>();
    WatchlistMoviesAdapter watchlistMoviesAdapter;
    RecyclerView recyclerView4;
    TextView txtwatchlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        recyclerView4 = findViewById(R.id.recycler_view4);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_watchlist);
        txtwatchlist = findViewById(R.id.watchlist_empty_text);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Watchlist.this,2,LinearLayoutManager.VERTICAL,false);
        recyclerView4.setLayoutManager(gridLayoutManager);

        watchMovies = getWatchList();
        watchlistMoviesAdapter = new WatchlistMoviesAdapter(watchMovies,Watchlist.this);
        if(watchlistMoviesAdapter.getItemCount()==0) txtwatchlist.setVisibility(View.VISIBLE);
        else txtwatchlist.setVisibility(View.GONE);

        recyclerView4.setAdapter(watchlistMoviesAdapter);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_likes:
                        startActivity(new Intent(getApplicationContext(),Likes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_new_release:
                        startActivity(new Intent(getApplicationContext(),ReleaseActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_watchlist:
                        return true;
                }
                return false;
            }
        });
    }
}