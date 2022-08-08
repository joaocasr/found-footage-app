package com.movies.foundfootage.Interface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.NonNull;

import com.movies.foundfootage.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "moviesDB";
    private static final String TABLE_MOVIES = "movies";
    private static final String KEY_ID = "id";
    private static final String VAL_TITLE = "title";
    private static final String VAL_POSTER_PATH = "poster_path";
    private static final String VAL_RELEASE = "release_date";
    private static final String VAL_GENRES = "genres";
    private static final String VAL_PLOT = "plot";
    private static final String VAL_RATE = "rate";
    private static final String VAL_TIME = "time";
    private static final String VAL_ACTORS = "actors";
    private static final String VAL_DIRECTORS = "directors";


    public DatabaseHelper( Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + VAL_TITLE + " TEXT, "
                    + VAL_POSTER_PATH + " INTEGER, " + VAL_RELEASE + " TEXT, "
                    + VAL_GENRES + " TEXT, " + VAL_PLOT + " TEXT, "
                    + VAL_RATE + " DOUBLE, " + VAL_TIME + " DOUBLE, "
                    + VAL_ACTORS + " TEXT, " + VAL_DIRECTORS + " TEXT " + ");";
            sqLiteDatabase.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(sqLiteDatabase);
    }

    public void addMovie(@NonNull Movie m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VAL_TITLE,m.getTitle());
        contentValues.put(VAL_POSTER_PATH,m.getPoster_path());
        contentValues.put(VAL_RELEASE,m.getRelease());
        contentValues.put(VAL_GENRES,m.getGenres());
        contentValues.put(VAL_PLOT,m.getPlot());
        contentValues.put(VAL_RATE,m.getRate());
        contentValues.put(VAL_TIME,m.getTime());
        contentValues.put(VAL_ACTORS,m.getActors());
        contentValues.put(VAL_DIRECTORS,m.getDirectors());

        db.insert(TABLE_MOVIES,null,contentValues);
        db.close();
    }

    public List<String> getAllTitles(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DATABASE_NAME);

        String [] column = {"title"};
        Cursor cursor = qb.query(db,column,null,null,null,null,null);
        List<String> allNames = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                int index = (cursor.getColumnIndex(VAL_TITLE));
                if(index!=-1) allNames.add(cursor.getString(index));
            }while (cursor.moveToNext());

        }
        return allNames;
    }

    public List<Movie> getMoviesByTitle(String nome){
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String [] sqlSelect ={KEY_ID,VAL_TITLE,VAL_POSTER_PATH,VAL_RELEASE,VAL_GENRES,VAL_PLOT,VAL_RATE,VAL_TIME,VAL_ACTORS,VAL_DIRECTORS};
            qb.setTables(DATABASE_NAME);
            Cursor cursor = qb.query(db,sqlSelect,"title LIKE ?",new String[]{"%"+nome+"%"},null,null,null);
            List<Movie> finalMovies = new ArrayList<>();
            if (cursor.moveToFirst()){
                do{
                    Movie movie = new Movie();
                    int id= cursor.getColumnIndex(KEY_ID);
                    if(id>=0) movie.setId(cursor.getInt(id));
                    int title=  cursor.getColumnIndex(VAL_TITLE);
                    if(title>=0) movie.setTitle(cursor.getString(title));
                    int poster = cursor.getColumnIndex(VAL_POSTER_PATH);
                    if(poster>=0) movie.setPoster_path(cursor.getInt(poster));
                    int release = cursor.getColumnIndex(VAL_RELEASE);
                    if(release>=0) movie.setRelease(cursor.getString(release));
                    int genres = cursor.getColumnIndex(VAL_GENRES);
                    if(genres>=0) movie.setGenres(cursor.getString(genres));
                    int plot = cursor.getColumnIndex(VAL_PLOT);
                    if(plot>=0) movie.setPlot(cursor.getString(plot));
                    int rate = cursor.getColumnIndex(VAL_RATE);
                    if(rate>=0) movie.setRate(cursor.getDouble(rate));
                    int time = cursor.getColumnIndex(VAL_TIME);
                    if(time>=0) movie.setTime(cursor.getDouble(time));

                    int actors = cursor.getColumnIndex(VAL_ACTORS);
                    if(actors>=0) movie.setActors(cursor.getString(actors));
                    int directors = cursor.getColumnIndex(VAL_DIRECTORS);
                    if(directors>=0) movie.setDirectors(cursor.getString(directors));

                    finalMovies.add(movie);
                }while (cursor.moveToNext());
            }
            return finalMovies;

    }

    Movie getMovie(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES,new String[]{KEY_ID,VAL_TITLE,VAL_POSTER_PATH,VAL_RELEASE,VAL_GENRES,VAL_PLOT,VAL_RATE,VAL_TIME,VAL_ACTORS,VAL_DIRECTORS},KEY_ID,
                new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor!=null){
            cursor.moveToFirst();
        }
        return new Movie(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),cursor.getString(3),cursor.getString(4),cursor.getString(5),
                Double.parseDouble(cursor.getString(6)),cursor.getDouble(7),cursor.getString(8),
                cursor.getString(9));
    }

    public List<Movie> getAllMovies(){
        List<Movie> movieList = new ArrayList<>();
        String selectAll = "SELECT *FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setPoster_path(Integer.parseInt(cursor.getString(2)));
                movie.setRelease(cursor.getString(3));
                movie.setGenres(cursor.getString(4));
                movie.setPlot(cursor.getString(5));
                movie.setRate(Double.parseDouble(cursor.getString(6)));
                movie.setTime(cursor.getDouble(7));
                movie.setActors(cursor.getString(8));
                movie.setDirectors(cursor.getString(9));

                movieList.add(movie);
            }while (cursor.moveToNext());
        }
        return movieList;
    }

    public int updateContact(Movie m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VAL_TITLE,m.getTitle());
        contentValues.put(VAL_POSTER_PATH,m.getPoster_path());
        contentValues.put(VAL_RELEASE,m.getRelease());
        contentValues.put(VAL_GENRES,m.getGenres());
        contentValues.put(VAL_PLOT,m.getPlot());
        contentValues.put(VAL_RATE,m.getRate());
        contentValues.put(VAL_TIME,m.getTime());
        contentValues.put(VAL_ACTORS,m.getActors());
        contentValues.put(VAL_DIRECTORS,m.getDirectors());

        return db.update(TABLE_MOVIES,contentValues,KEY_ID+"=?",
                new String[]{String.valueOf(m.getId())});
    }

    public void deleteMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES,KEY_ID+"=?",new String[]{String.valueOf(movie.getId())});
        db.close();
    }

    public int getMoviesCount(){
        String counter = "SELECT *FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(counter,null);
        cursor.close();
        return cursor.getCount();
    }
}
