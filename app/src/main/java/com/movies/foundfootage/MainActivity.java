package com.movies.foundfootage;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movies.foundfootage.Models.Movie;
import com.movies.foundfootage.Interface.DatabaseHelper;
import com.movies.foundfootage.Interface.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private Spinner spinner;
    private EditText editText;
    private BottomNavigationView bottomNavigationView;


    public List<Movie> movieList = new ArrayList<>();
    RecyclerView recyclerView;
    final MovieSearchAdapter[] movieSearchAdapter = {new MovieSearchAdapter(movieList, this)};
    DatabaseHelper db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            this.buildMovies();
            movieSearchAdapter[0] = new MovieSearchAdapter(movieList,this);

            editText = (EditText) findViewById(R.id.search_view);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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

            //db = new DatabaseHelper(this);
            //db.addMovie(R.drawable.poster1//);
            //movieSearchAdapter = new MovieSearchAdapter(this,db.getAllMovies());


            bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_new_release:
                            startActivity(new Intent(getApplicationContext(),ReleaseActivity.class));
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
                    return;
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    return;

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    filter(editable.toString(),movieSearchAdapter);
                }
            });

        }
        private void filter(String s,MovieSearchAdapter [] adapter){
            ArrayList<Movie> filteredList = new ArrayList<>();
            for (Movie m : movieList){
                if(m.getTitle().toLowerCase().contains(s.toLowerCase())){
                    filteredList.add(m);
                }
            }
            movieSearchAdapter[0].filteredMovies(filteredList);
            //Toast.makeText(this,filteredList.get(0).getTitle(),Toast.LENGTH_LONG).show();
        }


        public void buildMovies(){
            Movie m1 = new Movie(1,"Incantation",R.drawable.poster1,"2022","Horror","Six years ago, Li Ronan was cursed after breaking a religious taboo. Now, she must protect her daughter from the consequences of her actions.",6.2,1.50," Actors: Hsuan-yen Tsai, Mohamed Elgendy, Ying-Hsuan Kao, Sean Lin","Director: Kevin Ko");
            Movie m2 = new Movie(2,"Paranormal Activity: Next of Kin",R.drawable.poster2,"2021","Horror, Mystery, Thriller","Margot, a young woman who was abandoned by her mother as a baby, travels to a secluded Amish community with a documentary film crew seeking answers about her mother and extended family.",5.2,1.38,"Actors: Emily Bader, Roland Buck III, Dan Lippert, Jaye Ayres-Brown","Director: William Eubank");
            Movie m3 = new Movie(3,"The Visit",R.drawable.poster3,"2015","Horror, Mystery, Thriller","Two siblings become increasingly frightened by their grandparents' disturbing behavior while visiting them on vacation.",6.2,1.34," Actors: Olivia DeJonge, Ed Oxenbould, Deanna Dunagan, Peter McRobbie","Director: M. Night Shyamalan");
            Movie m4 = new Movie(4,"Cannibal Holocaust",R.drawable.poster4,"1980","Adventure, Horror","During a rescue mission into the Amazon rainforest, a professor stumbles across lost film shot by a missing documentary crew.",5.8,1.35," Actors: Robert Kerman, Francesca Ciardi, Perry Pirkanen, Luca Barbareschi","Director: Ruggero Deodato");

            movieList.add(m1);
            movieList.add(m2);
            movieList.add(m3);
            movieList.add(m4);
        }


    @Override
    public void onClick(int item) {
        Intent intent = new Intent(MainActivity.this,DetailedActivity.class);
        intent.putExtra("TITLE",movieList.get(item).getTitle());
        intent.putExtra("IMAGE",movieList.get(item).getPoster_path());
        intent.putExtra("RELEASE",movieList.get(item).getRelease());
        intent.putExtra("GENRES",movieList.get(item).getGenres());
        intent.putExtra("PLOT",movieList.get(item).getPlot());
        intent.putExtra("RATE",movieList.get(item).getRate());
        intent.putExtra("DURATION",movieList.get(item).getTime());
        intent.putExtra("ACTORS",movieList.get(item).getActors());
        intent.putExtra("DIRECTOR",movieList.get(item).getDirectors());

        startActivity(intent);

    }

    //public List<Movie> getMovieList(){
    //        return this.movieList.stream().map(Movie::clone).collect(Collectors.toList());
   //}

}