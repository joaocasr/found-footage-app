package com.movies.foundfootage.Models;

import java.io.Serializable;
import java.util.Comparator;

public class Movie implements Comparable<Movie>, Serializable {

     int id;
     String title;
     String poster_path;
     String release;
     String genres;
     String plot;
     double rate;
     String metascore;
     double time;
     String actors;
     String directors;

    public Movie(){}

    public Movie(int id, String title, String release, String genres, String plot, double rate, double time, String actors, String directors) {
        this.id = id;
        this.title = title;
        //this.poster_path = poster_path;
        this.release = release;
        this.genres = genres;
        this.plot = plot;
        this.rate = rate;
        this.time = time;
        this.actors = actors;
        this.directors = directors;
    }

    public Movie(int id, String title, String poster_path, String release, String genres, String plot, double rate,String metascore, double time, String actors, String directors) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.release = release;
        this.genres = genres;
        this.plot = plot;
        this.rate = rate;
        this.metascore = metascore;
        this.time = time;
        this.actors = actors;
        this.directors = directors;
    }

    public Movie(Movie m){
        this.id=m.getId();
        this.title = m.getTitle();
        this.poster_path = m.getPoster_path();
        this.release = m.getRelease();
        this.genres = m.getGenres();
        this.plot = m.getPlot();
        this.rate = m.getRate();
        this.metascore = m.getMetascore();
        this.time = m.getTime();
        this.actors = m.getActors();
        this.directors = m.getDirectors();

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public Movie clone(){
        return new Movie(this);
    }

    @Override
    public int compareTo(Movie movie) {
        return this.getTitle().compareTo(movie.getTitle());
    }

    public static Comparator<Movie> rateComparator = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie t1) {
            int r=0;
            if(t1.getRate()-movie.getRate()>0) r=1;
            if(t1.getRate()-movie.getRate()<0) r=-1;
            return r;
        }
    };

    public static Comparator<Movie> releaseComparator = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie t1) {
            int r=0;
            if(Integer.parseInt(t1.getRelease())- Integer.parseInt(movie.getRelease())>0) r=1;
            if(Integer.parseInt(t1.getRelease())- Integer.parseInt(movie.getRelease())<0) r=-1;
            return r;
        }
    };
}
