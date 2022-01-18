package com.example.test_za_bazu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDataSource {
    private SQLiteDatabase database;
    private	MySQLiteHelper	dbHelper;

    public UserDataSource(Context context)	{
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

    public void addUserToDb(int user_ID,
                             String user_name,
                             String user_password)
    {
        ContentValues values = new ContentValues();
        values.put("user_ID",user_ID);
        values.put("user_name",user_name);
        values.put("user_password",user_password);
        database.insert("users", null, values);
    }

    public ArrayList<Users> getAllUsers()
    {
        ArrayList<Users> usersToReturn = new ArrayList<Users>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM users", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Users user = new Users();
            user.setUser_ID(cursor.getInt(0));
            user.setUser_name(cursor.getString(1));
            user.setUser_password(cursor.getString(2));
            usersToReturn.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return	usersToReturn;
    }

    public Users getUserByName(String name)
    {
        Users userToReturn = new Users();
        Cursor cursor = database.rawQuery("SELECT *	FROM users WHERE user_name = '"+name+"'",null);

        cursor.moveToFirst();

        if (! cursor.isAfterLast()) {
            userToReturn.setUser_ID(cursor.getInt(0));
            userToReturn.setUser_name(cursor.getString(1));
            userToReturn.setUser_password(cursor.getString(2));
        }
        return userToReturn;
    }
}
