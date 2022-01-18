package com.example.test_za_bazu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class ImagesDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public ImagesDataSource(Context context){
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

    public void addImagesToDb(int image_ID,
                         int image_episode_ID,
                         String image_link)
    {
        ContentValues values = new ContentValues();
        values.put("image_ID", image_ID);
        values.put("image_episode_ID", image_episode_ID);
        values.put("image_link", image_link);

        database.insert("images", null, values);
    }

    public ArrayList<ImagesEpisodes> getImagesByEpisodeID(int episode_id)
    {
        ArrayList<ImagesEpisodes> imagesToReturn = new ArrayList<ImagesEpisodes>();
        Cursor cursor = database. rawQuery("SELECT * FROM images WHERE image_episode_ID = '" + String.valueOf(episode_id) + "'",null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            ImagesEpisodes imagesEpisode = new ImagesEpisodes();
            imagesEpisode.setImage_ID(cursor.getInt(0));
            imagesEpisode.setImage_episode_ID(cursor.getInt(1));
            imagesEpisode.setImage_path(cursor.getString(2));
            imagesToReturn.add(imagesEpisode);
            cursor.moveToNext();
        }
        cursor.close();
        return imagesToReturn;
    }

}

