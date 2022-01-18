package com.example.test_za_bazu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    String loggedInUserName = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView comicLV = findViewById(R.id.comicList);

        AsyncHttpClient myClient = new AsyncHttpClient();
        final Gson myGson = new Gson();

        myClient.get("https://raw.githubusercontent.com/warbossy/CoSe-seminarski/main/authors.txt", new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Nesto nevalja s API-em",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type collectionType = new TypeToken<Collection<Author>>(){}.getType();
                Collection<Author> authors = myGson.fromJson(responseString, collectionType);
                AuthorDataSource authorDataSource = new AuthorDataSource(getApplicationContext());
                for(Author a : authors) {
                    authorDataSource.addAuthorToDb(
                            a.getAuthor_ID(),
                            a.getAuthor_name()
                    );
                }
                DataStorage.authorsList = authorDataSource.getAllAuthors();
            }
        });

        myClient.get("https://raw.githubusercontent.com/warbossy/CoSe-seminarski/main/comics_no_tags.txt", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Nesto nevalja s API-em",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type collectionType = new TypeToken<Collection<Comic>>(){}.getType();
                Collection<Comic> comics = myGson.fromJson(responseString, collectionType);
                ComicsDataSource comicsDataSource = new ComicsDataSource(getApplicationContext());
                for(Comic c : comics) {
                    comicsDataSource.addComicToDb(
                            c.getComic_thumbnail().toString(),
                            c.getComic_title().toString(),
                            c.getComic_ID(),
                            c.getComic_author_ID(),
                            c.getComic_description().toString());
                }
                DataStorage.comicsList = comicsDataSource.getAllComics();

                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                Users loggedInUser = userDataSource.getUserByName(loggedInUserName);
                final ComicListViewAdapter comicListViewAdapter = new ComicListViewAdapter(getApplicationContext());
                final ComicListViewAdapterLoggedIn comicListViewAdapterLoggedIn = new ComicListViewAdapterLoggedIn(getApplicationContext(),loggedInUser);
                final Button recentButton = (Button) findViewById(R.id.recentButton);
                if(!loggedInUserName.isEmpty())
                {

                    comicLV.setAdapter(comicListViewAdapterLoggedIn);
                    recentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataStorage.comicsList = comicsDataSource.getAllComics();
                            comicLV.setAdapter(comicListViewAdapterLoggedIn);
                        }
                    });
                }
                else
                {
                    comicLV.setAdapter(comicListViewAdapter);
                }
            }
        });
        final Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(switchActivityIntent); }
        });
        final Button subscribeButton = (Button) findViewById(R.id.subscribeButton);

        final ImageButton signInButton = (ImageButton) findViewById(R.id.signInButton);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        loggedInUserName = sharedPreferences.getString("value","");
        UserDataSource userDataSource = new UserDataSource(getApplicationContext());
        Users loggedInUser = userDataSource.getUserByName(loggedInUserName);
        SubscribeDataSource subscribeDataSource = new SubscribeDataSource(getApplicationContext());

        if(!loggedInUserName.isEmpty())
        {
            signInButton.setImageResource(R.drawable.ic_log_out_pic);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartMainActivity();
                }
            });

            subscribeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataStorage.comicsList = subscribeDataSource.getComicsSubscribesByUserID(loggedInUser.getUser_ID());
                    final ComicListViewAdapterLoggedIn comicListViewAdapterLoggedIn = new ComicListViewAdapterLoggedIn(getApplicationContext(),loggedInUser);
                    comicLV.setAdapter(comicListViewAdapterLoggedIn);
                }
            });

        }
        else
        {
            signInButton.setImageResource(R.drawable.user1);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivitiesLogin(); }
            });

        }

        comicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comic clickedComic = DataStorage.comicsList.get(position);
                Intent openComicEpisodesIntent = new Intent(MainActivity.this,ComicsEpisodesActivity.class);
                openComicEpisodesIntent.putExtra("opened_comic",clickedComic.getComic_ID());
                startActivity(openComicEpisodesIntent);
            }
        });




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_search_bar);
            }
        });

    }


    private void switchActivitiesLogin() {
        Intent switchActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }
    private void restartMainActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String emptyString = new String();
        editor.putString("value",emptyString);
        editor.apply();

        Intent switchActivityIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void restartMainActivityLoggedIn() {
        Intent switchActivityIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(switchActivityIntent);
    }


}