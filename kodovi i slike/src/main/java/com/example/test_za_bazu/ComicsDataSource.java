package com.example.test_za_bazu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;


public class ComicsDataSource {

    private SQLiteDatabase database;
    private	MySQLiteHelper	dbHelper;

    public ComicsDataSource(Context context)	{
        dbHelper = new MySQLiteHelper(context);

        try {
            this.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void	open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void	close()	{
        database.close();
    }

    public void addComicToDb(String comic_thumbnail,
                             String comic_title,
                             int comic_id,
                             int comic_author_ID,
                             String comic_description) {
        ContentValues values = new ContentValues();
        values.put("comic_thumbnail", comic_thumbnail);
        values.put("comic_title", comic_title);
        values.put("comic_ID", comic_id);
        values.put("comic_author_ID", comic_author_ID);
        values.put("comic_description", comic_description);
        database.insert("comics", null, values);
    }

    public Comic getComicById(int id) {
        Comic comicToReturn = new Comic();

        Cursor cursor = database.rawQuery("SELECT * FROM comics WHERE comic_ID = '" + String.valueOf(id) + "'", null);

        cursor.moveToFirst();

        if (! cursor.isAfterLast()) {
            comicToReturn.setComic_ID(cursor.getInt(0));
            comicToReturn.setComic_title(cursor.getString(1));
            comicToReturn.setComic_author_ID(cursor.getInt(2));
            comicToReturn.setComic_description(cursor.getString(3));
            comicToReturn.setComic_thumbnail(cursor.getString(4));
        }

        return comicToReturn;
    }

    public ArrayList<Comic> getAllComics()	{
        ArrayList<Comic> comics = new ArrayList<Comic>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM comics", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Comic comic = new Comic();
            comic.setComic_ID(cursor.getInt(0));
            comic.setComic_title(cursor.getString(1));
            comic.setComic_author_ID(cursor.getInt(2));
            comic.setComic_description(cursor.getString(3));
            comic.setComic_thumbnail(cursor.getString(4));
            comics.add(comic);
            cursor.moveToNext();
        }

        cursor.close();
        return	comics;
    }

    public ArrayList<String> getAllComicsTitles()	{
        ArrayList<String> titlesToReturn = new ArrayList<String>();

        Cursor cursor =	database.rawQuery("SELECT comic_title	FROM comics", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Comic comic = new Comic();
            comic.setComic_ID(cursor.getInt(0));
            comic.setComic_title(cursor.getString(1));
            comic.setComic_author_ID(cursor.getInt(2));
            comic.setComic_description(cursor.getString(3));
            comic.setComic_thumbnail(cursor.getString(4));
            titlesToReturn.add(comic.getComic_title());
            cursor.moveToNext();
        }

        cursor.close();
        return	titlesToReturn;
    }

}