package com.example.test_za_bazu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "comicDatabase.db";
    private	static final int DATABASE_VERSION =	1;

    //	Database	creation	sql	statement
    private	static final String	DATABASE_CREATE_Comics	= "CREATE TABLE	comics"
            + "(comic_ID INTEGER NOT NULL PRIMARY KEY,"
            + " comic_title TEXT NOT NULL,"
            + " comic_author_ID INTEGER NOT NULL,"
            + " comic_description TEXT NOT NULL,"
            + " comic_thumbnail TEXT NOT NULL);";

    private	static final String	DATABASE_CREATE_Episodes = "CREATE TABLE episodes"
            + "(episode_ID INTEGER NOT NULL PRIMARY KEY,"
            + " episode_comic_ID INTEGER NOT NULL,"
            + " episode_title TEXT NOT NULL,"
            + " episode_tmb TEXT NOT NULL,"
            + " image_path TEXT NOT NULL,"
            + " image_count INTEGER NOT NULL);";

    private	static final String	DATABASE_CREATE_Author = "CREATE TABLE authors"
            + "(author_ID INTEGER NOT NULL PRIMARY KEY,"
            + " author_name TEXT NOT NULL);";

    private	static final String	DATABASE_CREATE_Images = "CREATE TABLE images"
            + "(image_ID INTEGER NOT NULL PRIMARY KEY,"
            + " image_episode_ID INTEGER NOT NULL,"
            + " image_link TEXT NOT NULL);";

    private	static final String	DATABASE_CREATE_Users = "CREATE TABLE users"
            + "(user_ID INTEGER NOT NULL PRIMARY KEY,"
            + " user_name TEXT NOT NULL UNIQUE,"
            + " user_password TEXT NOT NULL UNIQUE);";

    private	static final String	DATABASE_CREATE_Subscribes = "CREATE TABLE subscribes"
            + "(subscribe_ID INTEGER NOT NULL PRIMARY KEY,"
            + " subscribe_user_ID INTEGER NOT NULL,"
            + " subscribe_comic_ID INTEGER NOT NULL );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_Comics);
        database.execSQL(DATABASE_CREATE_Episodes);
        database.execSQL(DATABASE_CREATE_Author);
        database.execSQL(DATABASE_CREATE_Images);
        database.execSQL(DATABASE_CREATE_Users);
        database.execSQL(DATABASE_CREATE_Subscribes);
    }

    @Override
    public void	onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS comics");
        db.execSQL("DROP TABLE IF EXISTS episodes");
        db.execSQL("DROP TABLE IF EXISTS authors");
        db.execSQL("DROP TABLE IF EXISTS images");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS subscribes");

        onCreate(db);
    }
}