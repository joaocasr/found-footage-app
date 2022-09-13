package com.movies.foundfootage;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movies.foundfootage.Interface.DatabaseHelper;
import com.movies.foundfootage.Models.Movie;
//import com.movies.foundfootage.Interface.DatabaseHelper;
import com.movies.foundfootage.Interface.RecyclerViewInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private Spinner spinner;
    private EditText editText;
    private BottomNavigationView bottomNavigationView;
    private ImageView close;

    ArrayList<String> arrayList;
    ArrayList<String> watchlistMovies;

    public List<Movie> movieList = new ArrayList<>();
    private ArrayList<Movie> filteredList = new ArrayList<Movie>();
    public List<String> likedMovies = new ArrayList<>();
    public List<String> toWatchMovies = new ArrayList<>();
    RecyclerView recyclerView;
    final MovieSearchAdapter[] movieSearchAdapter = {new MovieSearchAdapter(movieList, this)};
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buildMovies();
        //if(movieList.size()!=0) Toast.makeText(this,"size: "+movieList.size(),Toast.LENGTH_LONG).show();
        movieSearchAdapter[0] = new MovieSearchAdapter(movieList,this);

        editText = (EditText) findViewById(R.id.search_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        close = findViewById(R.id.closeimage);
        close.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recycler_view1);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        spinner = (Spinner) findViewById(R.id.spinner);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieSearchAdapter[0]);

        List<String> opcoes = Arrays.asList("<-none->","Alphabetic","IMDB Rate","Release");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.selected_item,opcoes);
        arrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(arrayAdapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.getText().clear();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    Comparator<Movie> titleComparator = (m1,m2)->m1.compareTo(m2);
                    Collections.sort(movieList,titleComparator);
                    movieSearchAdapter[0] = new MovieSearchAdapter(movieList,MainActivity.this);
                    recyclerView.setAdapter(movieSearchAdapter[0]);
                }
                if(i==2){
                    Collections.sort(movieList,Movie.rateComparator);
                    movieSearchAdapter[0] = new MovieSearchAdapter(movieList,MainActivity.this);
                    recyclerView.setAdapter(movieSearchAdapter[0]);
                }
                if(i==3){
                    Collections.sort(movieList,Movie.releaseComparator);
                    movieSearchAdapter[0] = new MovieSearchAdapter(movieList,MainActivity.this);
                    recyclerView.setAdapter(movieSearchAdapter[0]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        db = new DatabaseHelper(MainActivity.this);
        //db.deleteAll();
        arrayList = db.getFavs();
        watchlistMovies = db.getToWatch();
        this.likedMovies = new ArrayList<>(arrayList);
        this.toWatchMovies = new ArrayList<>(watchlistMovies);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_new_release:
                        startActivity(new Intent(getApplicationContext(),ReleaseActivity.class));
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
                    case R.id.nav_home:
                        return true;
                }
                return false;
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                close.setVisibility(View.GONE);
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                close.setVisibility(View.VISIBLE);
                return;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        /*Adicionar filme à lista de filmes por ver*/
        String watchlistMovie = getIntent().getStringExtra("watchlistTitle");

        if(watchlistMovie!=null){
            Toast.makeText(MainActivity.this,"Watchlist has been updated.",Toast.LENGTH_LONG).show();
            db.addRemoveFav("#"+watchlistMovie);

        }

    }
    private void filter(String s){
        filteredList.clear();
        for (Movie m : movieList){
            if(m.getTitle().toLowerCase().contains(s.toLowerCase()) || m.getDirectors().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(m);
            }
        }
        movieSearchAdapter[0].filteredMovies(filteredList);
        //Toast.makeText(this,filteredList.get(0).getTitle(),Toast.LENGTH_LONG).show();
    }


    public void buildMovies(){
        int l=1;
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(R.raw.moviedb);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
                while((line = br.readLine()) != null) {
                    String[] parte = line.split(";");
                    movieList.add(new Movie(l,parte[0],parte[3],parte[1],parte[2],parte[4],Double.parseDouble(parte[5]),parte[6],Double.parseDouble(parte[7]),parte[8],parte[9]));
                    // read next line
                    sb = new StringBuilder();
                    l++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException n){
            Log.d("error","linha"+l);
        }
    }


    @Override
    public void onClick(int item) {
        Intent intent = new Intent(MainActivity.this,DetailedActivity.class);
        if(filteredList.size()>0){
            intent.putExtra("TITLE", filteredList.get(item).getTitle());
            intent.putExtra("IMAGE", filteredList.get(item).getPoster_path());
            intent.putExtra("RELEASE", filteredList.get(item).getRelease());
            intent.putExtra("GENRES", filteredList.get(item).getGenres());
            intent.putExtra("PLOT", filteredList.get(item).getPlot());
            intent.putExtra("RATE", filteredList.get(item).getRate());
            intent.putExtra("METASCORE", filteredList.get(item).getMetascore());
            intent.putExtra("DURATION", filteredList.get(item).getTime());
            intent.putExtra("ACTORS", filteredList.get(item).getActors());
            intent.putExtra("DIRECTORS", filteredList.get(item).getDirectors());
        }else {
            intent.putExtra("TITLE", movieList.get(item).getTitle());
            intent.putExtra("IMAGE", movieList.get(item).getPoster_path());
            intent.putExtra("RELEASE", movieList.get(item).getRelease());
            intent.putExtra("GENRES", movieList.get(item).getGenres());
            intent.putExtra("PLOT", movieList.get(item).getPlot());
            intent.putExtra("RATE", movieList.get(item).getRate());
            intent.putExtra("METASCORE", movieList.get(item).getMetascore());
            intent.putExtra("DURATION", movieList.get(item).getTime());
            intent.putExtra("ACTORS", movieList.get(item).getActors());
            intent.putExtra("DIRECTORS", movieList.get(item).getDirectors());
        }
        startActivity(intent);

    }

    @Override
    public void onFavClick(int item) {
        String title ="";
        if(filteredList.size()>0) title = filteredList.get(item).getTitle();
        else title = movieList.get(item).getTitle();
        db.addRemoveFav(title);
        //Toast.makeText(MainActivity.this,db.getFavs().get(0),Toast.LENGTH_LONG).show();

    }

    public List<Movie> getMovieList(){
        List<Movie> l = new ArrayList<>();
        for(String title :likedMovies){
            for(Movie m : movieList){
                if(m.getTitle().equals(title)){
                    l.add(m.clone());
                }
            }
        }
        return l;
    }

    public List<Movie> getWatchList(){
        List<Movie> l = new ArrayList<>();
        for(String title :toWatchMovies){
            for(Movie m : movieList){
                if(m.getTitle().equals(title.substring(1))){
                    l.add(m.clone());
                }
            }
        }
        return l;
    }
}
