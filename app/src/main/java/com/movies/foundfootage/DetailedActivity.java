package com.movies.foundfootage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    Button backbutton;
    ToggleButton toggleButton;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-1810794261274785/6574157107", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        displayAD();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Toast.makeText(DetailedActivity.this,"Something went wrong.\nCheck your internet connection.",Toast.LENGTH_LONG).show();
                        mInterstitialAd = null;
                    }
                });


        backbutton = findViewById(R.id.gobackbutton);
        toggleButton = findViewById(R.id.watchlist_button);

        TextView titleView = (TextView) findViewById(R.id.title_movie);
        TextView releaseView = (TextView) findViewById(R.id.release_movie);
        ImageView imageView = (ImageView) findViewById(R.id.image_movie);
        TextView rateView = (TextView) findViewById(R.id.rate_movie);
        TextView metascoreView = (TextView) findViewById(R.id.metascore_movie);

        TextView plotView = (TextView) findViewById(R.id.synopsis);
        TextView durationView = (TextView) findViewById(R.id.duration_movie);
        TextView genresView = (TextView) findViewById(R.id.genres_movie);
        TextView actorsView = (TextView) findViewById(R.id.cast_movie);
        TextView directorsView = (TextView) findViewById(R.id.directors_movie);

            String title = getIntent().getStringExtra("TITLE");
            String image = getIntent().getStringExtra("IMAGE");
            String release = getIntent().getStringExtra("RELEASE");
            String genres = getIntent().getStringExtra("GENRES");
            String plot = getIntent().getStringExtra("PLOT");
            double rate = getIntent().getDoubleExtra("RATE", 0);
            String metascore = getIntent().getStringExtra("METASCORE");
            double duration = getIntent().getDoubleExtra("DURATION", 0);
            String actors = getIntent().getStringExtra("ACTORS");
            String directors = getIntent().getStringExtra("DIRECTORS");



            titleView.setText(title);
            StringBuilder movierelease = new StringBuilder();
            movierelease.append("(").append(release).append(")");
            releaseView.setText(movierelease.toString());
            Picasso.get().load(image).into(imageView);
            StringBuilder rateText = new StringBuilder();
            rateText.append(rate).append("/10");
            rateView.setText(rateText);
            try {
                double score = Double.parseDouble(metascore);
                if (score >= 0 && score <= 3.9)
                    metascoreView.setTextColor(Color.parseColor("#ff0000"));
                if (score >= 4.0 && score <= 6.0)
                    metascoreView.setTextColor(Color.parseColor("#ffcc33"));
                if (score >= 6.1 && score <= 10.0)
                    metascoreView.setTextColor(Color.parseColor("#66cc33"));

            } catch (NumberFormatException numberFormatException) {
                numberFormatException.getMessage();
            }
            metascoreView.setText(metascore);

            plotView.setText(plot);
            /**MOVIE DURATION */
            StringBuilder time = new StringBuilder();
            double Decmin = duration % 1;
            int hour = (int) (duration - Decmin);
            int minute = (int) (Decmin * 60);
            time.append(hour).append("h ").append(minute).append("m");
            durationView.setText(time);
            genresView.setText(genres);
            actorsView.setText(actors);
            directorsView.setText(directors);

            SharedPreferences sharedPreferences = getSharedPreferences("watchlistbutton", MODE_PRIVATE);
            boolean tgwl = sharedPreferences.getBoolean("wlbutton"+title ,false);
            if(tgwl){
                toggleButton.setChecked(true);
                toggleButton.setBackgroundResource(R.drawable.watchlist_added_icon);
            }else{
                toggleButton.setChecked(false);
                toggleButton.setBackgroundResource(R.drawable.watchlist_add_icon);
            }


        backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailedActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });


            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailedActivity.this,MainActivity.class);
                    if(toggleButton.isChecked()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("watchlistbutton", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("wlbutton"+title,true);
                        editor.commit();
                    }else{
                        SharedPreferences sharedPreferences = getSharedPreferences("watchlistbutton", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("wlbutton"+title,false);
                        editor.commit();
                    }
                    intent.putExtra("watchlistTitle",title);
                    startActivity(intent);
                }
            });
        }

        private void displayAD(){
            if (mInterstitialAd != null) {
                mInterstitialAd.show(this);
            }else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
       }
}
