package com.movies.foundfootage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movies.foundfootage.Models.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ReleaseActivity extends MainActivity {

    public List<Movie> releaseList= new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView2;
    MovieReleaseAdapter movieReleaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);


        this.buildReleaseMovies();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_new_release);
        recyclerView2 = findViewById(R.id.recycler_view2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ReleaseActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        movieReleaseAdapter= new MovieReleaseAdapter(releaseList,ReleaseActivity.this);
        recyclerView2.setAdapter(movieReleaseAdapter);


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_new_release:
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_likes:
                        startActivity(new Intent(getApplicationContext(),Likes.class));
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

    public void buildReleaseMovies(){
        int l=1;
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(R.raw.releasedb);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while((line = br.readLine()) != null) {
                String[] parte = line.split(";");
                releaseList.add(new Movie(l,parte[0],parte[3],parte[1],parte[2],parte[4],0,"-",0,parte[4],parte[5]));
                // read next line
                sb = new StringBuilder();
                l++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}