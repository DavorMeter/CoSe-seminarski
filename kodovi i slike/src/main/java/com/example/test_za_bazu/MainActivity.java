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

    //private ComicListViewAdapter.comicListViewListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView comicLV = findViewById(R.id.comicList);
        final ComicListViewAdapter comicListViewAdapter = new ComicListViewAdapter(getApplicationContext());

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
                comicLV.setAdapter(comicListViewAdapter);

                comicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Comic clickedComic = DataStorage.comicsList.get(position);
                        Intent openComicEpisodesIntent = new Intent(MainActivity.this,ComicsEpisodesActivity.class);
                        openComicEpisodesIntent.putExtra("opened_comic",clickedComic.getComic_ID());
                        startActivity(openComicEpisodesIntent);
                    }
                });
            }
        });
        final Button searchButton = (Button) findViewById(R.id.searchButton);
        final ImageButton signInButton = (ImageButton) findViewById(R.id.signInButton);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        String loggedInUser = sharedPreferences.getString("value","");
        if(!loggedInUser.isEmpty())
        {
            signInButton.setImageResource(R.drawable.ic_log_out_pic);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartMainActivity(); }
            });

        }
        else
        {
            signInButton.setImageResource(R.drawable.user1);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { switchActivitiesLogin(); }
            });

        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { switchActivitiesSearch(); }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_search_bar);
            }
        });

    }

    private void switchActivitiesSearch() {
        Intent switchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchActivitiesLogin() {
        Intent switchActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }
    private void restartMainActivity() {
        Intent switchActivityIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}