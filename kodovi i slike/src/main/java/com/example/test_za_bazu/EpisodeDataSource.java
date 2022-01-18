package com.example.test_za_bazu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class EpisodeDataSource {

    private SQLiteDatabase database;
    private	MySQLiteHelper dbHelper;

    public EpisodeDataSource(Context context)
    {
        dbHelper = new MySQLiteHelper(context);
        try{
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

    public void addEpisodeToDb(int episode_comic_ID,
                               int episode_ID,
                               String episode_title,
                               String episode_tmb,
                               String image_path,
                               int image_count) {
        ContentValues values = new ContentValues();
        values.put("episode_comic_ID", episode_comic_ID);
        values.put("episode_ID", episode_ID);
        values.put("episode_title", episode_title);
        values.put("episode_tmb", episode_tmb);
        values.put("image_path", image_path);
        values.put("image_count", image_count);

        database.insert("episodes", null, values);
    }

    public ArrayList<Episode> getEpisodesByEpisodesComicID(int comic_id)
    {
        ArrayList<Episode> episodesToReturn = new ArrayList<Episode>();
        Cursor cursor = database. rawQuery("SELECT * FROM episodes WHERE episode_comic_ID = '" + String.valueOf(comic_id) + "'",null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Episode episode = new Episode();
            episode.setEpisode_ID(cursor.getInt(0));
            episode.setEpisode_comic_ID(cursor.getInt(1));
            episode.setEpisode_title(cursor.getString(2));
            episode.setEpisode_tmb(cursor.getString(3));
            episode.setEpisode_images_path(cursor.getString(4));
            episode.setEpisode_image_count(cursor.getInt(5));
            episodesToReturn.add(episode);
            cursor.moveToNext();
        }
        cursor.close();
        return episodesToReturn;
    }
}
