package com.movies.foundfootage;

import androidx.annotation.NonNull;
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

public class Likes extends MainActivity {

    public List<Movie> likedMovies = new ArrayList<>();
    RecyclerView recyclerView3;
    LikeMoviesAdapter likeMoviesAdapter;
    BottomNavigationView bottomNavigationView;
    TextView textView_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_likes);
        recyclerView3 = findViewById(R.id.recycler_view3);
        textView_empty = findViewById(R.id.likes_empty_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Likes.this,LinearLayoutManager.VERTICAL,false);
        recyclerView3.setLayoutManager(layoutManager);

        likedMovies = getMovieList();
        likeMoviesAdapter = new LikeMoviesAdapter(likedMovies,Likes.this);
        if(likeMoviesAdapter.getItemCount()==0) textView_empty.setVisibility(View.VISIBLE);
        else textView_empty.setVisibility(View.GONE);

        recyclerView3.setAdapter(likeMoviesAdapter);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_likes:
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
                        startActivity(new Intent(getApplicationContext(),Watchlist.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}