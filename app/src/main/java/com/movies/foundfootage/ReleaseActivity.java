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
import java.io.FileReader;
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

        /*textView = findViewById(R.id.movietxt);

        int currentyear = Calendar.getInstance().get(Calendar.YEAR);

        StringBuilder stringBuilder = new StringBuilder();
        for(Movie m : getMovieList()) {
            if (currentyear == Integer.parseInt(m.getRelease())) {
                stringBuilder.append("Movie: ").append(m.getTitle()).append("\n");
            }
        }
        textView.setText(stringBuilder.toString());
*/
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
                String mDrawableName = sb.append("release").append(l).toString();
                int imageID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                releaseList.add(new Movie(l,parte[0],imageID,parte[1],parte[2],parte[3],0,0,parte[4],parte[5]));
                // read next line
                sb = new StringBuilder();
                l++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}