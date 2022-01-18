package com.example.test_za_bazu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubscribeDataSource {

    private SQLiteDatabase database;
    private	MySQLiteHelper	dbHelper;

    public SubscribeDataSource(Context context)	{
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

    public void addSubscribeToDb(int subscribe_ID,
                            int subscribe_user_ID,
                            int subscribe_comic_ID)
    {
        ContentValues values = new ContentValues();
        values.put("subscribe_ID",subscribe_ID);
        values.put("subscribe_user_ID",subscribe_user_ID);
        values.put("subscribe_comic_ID",subscribe_comic_ID);
        database.insert("subscribes", null, values);
    }

    public void deleteSubscribeFromDb(int id)
    {
        database.delete("subscribes","subscribe_comic_ID = " + String.valueOf(id),null);
    }

    public ArrayList<Comic> getComicsSubscribesByUserID(int user_ID)
    {
        ArrayList<Comic> comicsToReturn = new ArrayList<Comic>();

        Cursor cursor =	database.rawQuery("SELECT comic_ID,comic_title,comic_author_ID,comic_description,comic_thumbnail FROM comics JOIN subscribes ON comic_ID = subscribe_comic_ID WHERE subscribe_user_ID = '"+String.valueOf(user_ID)+"'", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Comic comic = new Comic();
            comic.setComic_ID(cursor.getInt(0));
            comic.setComic_title(cursor.getString(1));
            comic.setComic_author_ID(cursor.getInt(2));
            comic.setComic_description(cursor.getString(3));
            comic.setComic_thumbnail(cursor.getString(4));
            comicsToReturn.add(comic);
            cursor.moveToNext();
        }
        cursor.close();
        return comicsToReturn;
    }


}
