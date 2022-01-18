package com.example.test_za_bazu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDataSource {

    private Context context;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;


    public AuthorDataSource(Context context)	{
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

    public void addAuthorToDb(int author_ID,
                             String author_name) {
        ContentValues values = new ContentValues();
        values.put("author_name", author_name);
        values.put("author_ID", author_ID);
        database.insert("authors", null, values);
    }

    public ArrayList<Author> getAllAuthors()	{
        ArrayList<Author> authors = new ArrayList<Author>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM authors", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Author author = new Author();
            author.setAuthor_ID(cursor.getInt(0));
            author.setAuthor_name(cursor.getString(1));
            authors.add(author);
            cursor.moveToNext();
        }
        cursor.close();
        return	authors;
    }

    public  String getAuthorNameWithID(int author_comic_id)
    {
        String name = new String();
        Cursor cursor =	database.rawQuery("SELECT *	FROM authors WHERE author_ID = '"+String.valueOf(author_comic_id)+"'", null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            Author author = new Author();
            author.setAuthor_ID(cursor.getInt(0));
            author.setAuthor_name(cursor.getString(1));
        }
        return  name;
    }
}
