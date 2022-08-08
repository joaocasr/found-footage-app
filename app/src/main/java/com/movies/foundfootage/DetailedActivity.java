package com.movies.foundfootage;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        String title = getIntent().getStringExtra("TITLE");
        int image = getIntent().getIntExtra("IMAGE",0);
        String release = getIntent().getStringExtra("RELEASE");
        String genres = getIntent().getStringExtra("GENRES");
        String plot = getIntent().getStringExtra("PLOT");
        double rate = getIntent().getDoubleExtra("RATE",0);
        double duration = getIntent().getDoubleExtra("DURATION",0);
        String actors = getIntent().getStringExtra("ACTORS");
        String directors = getIntent().getStringExtra("DIRECTORS");

        TextView titleView = (TextView) findViewById(R.id.title_movie);
        TextView releaseView = (TextView) findViewById(R.id.release_movie);
        ImageView imageView = (ImageView) findViewById(R.id.image_movie);
        TextView rateView = (TextView) findViewById(R.id.rate_movie);
        TextView plotView = (TextView) findViewById(R.id.synopsis);
        TextView durationView = (TextView) findViewById(R.id.duration_movie);
        TextView genresView = (TextView) findViewById(R.id.genres_movie);
        TextView actorsView = (TextView) findViewById(R.id.cast_movie);
        TextView directorsView = (TextView) findViewById(R.id.directors_movie);


        titleView.setText(title);
        StringBuilder movierelease = new StringBuilder();
        movierelease.append("(").append(release).append(")");
        releaseView.setText(movierelease.toString());
        imageView.setImageResource(image);
        StringBuilder rateText = new StringBuilder();
        rateText.append(rate).append("/10");
        rateView.setText(rateText);
        plotView.setText(plot);
        /**MOVIE DURATION */
        StringBuilder time = new StringBuilder();
        double Decmin = duration % 1;
        int hour = (int) (duration-Decmin);
        int minute = (int) (Decmin*60);
        time.append(hour).append("h ").append(minute).append("m");
        durationView.setText(time);
        genresView.setText(genres);
        actorsView.setText(actors);
        directorsView.setText(directors);
    }
}
