package com.movies.foundfootage.Interface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.movies.foundfootage.MainActivity;
import com.movies.foundfootage.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "moviesDB";
    private static final String TABLE_MOVIES = "movies";
    private static final String KEY_ID = "id";
    private static final String VAL_TITLE = "title";



    public DatabaseHelper( Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + VAL_TITLE + " TEXT " + ");";
        sqLiteDatabase.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(sqLiteDatabase);

    }


    public boolean checkAlreadyExistLikedMovie(String title){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE " + VAL_TITLE + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{title});
        if (cursor.getCount() > 0)
        {
            return false;
        }
        else
            return true;
    }

    public boolean addRemoveFav(String title){
        boolean r=false;
        if(checkAlreadyExistLikedMovie(title)){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(VAL_TITLE,title);

            sqLiteDatabase.insert(TABLE_MOVIES,null,contentValues);
            sqLiteDatabase.close();
            r=true;
        }else{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_MOVIES, VAL_TITLE+"=?",new String[]{title});
            db.close();
        }
        return r;
    }

    public ArrayList<String> getFavs(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE "+VAL_TITLE+" NOT REGEXP '^#'",null);//tem de se selecionar o conteudo total da tabela
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int title = cursor.getColumnIndex(VAL_TITLE);
            if (title>0) arrayList.add(cursor.getString(title));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList<String> getToWatch(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_MOVIES ,null);//tem de se selecionar o conteudo total da tabela
        while (cursor.moveToNext()){
            int title = cursor.getColumnIndex(VAL_TITLE);
            if (title>0 && cursor.getString(title).charAt(0)=='#') arrayList.add(cursor.getString(title));
        }
        cursor.close();
        return arrayList;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE "+"FROM "+ TABLE_MOVIES); //delete all rows in a table
        db.close();
    }
}
