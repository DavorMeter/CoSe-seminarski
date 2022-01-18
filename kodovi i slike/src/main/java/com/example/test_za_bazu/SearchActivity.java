package com.example.test_za_bazu;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        Log.d("comic_listSize",String.valueOf(DataStorage.comicsList.size()));

        SearchView searchView=(SearchView) findViewById(R.id.searchBar);
        ListView comicSearchedLV=(ListView) findViewById(R.id.searchComicList) ;
        ComicsDataSource comicsDataSource = new ComicsDataSource(getApplicationContext());
        DataStorage.comicsList = comicsDataSource.getAllComics();
        ComicSearchListViewAdapter comicSearchListViewAdapter = new ComicSearchListViewAdapter(getApplicationContext(),DataStorage.comicsList);
        comicSearchedLV.setAdapter(comicSearchListViewAdapter);
        Log.d("comic_listSize",String.valueOf(DataStorage.comicsList.size()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Comic> comics = new ArrayList<Comic>();

                for(Comic c:DataStorage.comicsList)
                {
                    if(c.getComic_title().toLowerCase().contains(newText.toLowerCase()))
                    {
                        comics.add(c);
                        Log.d("COMIC_ID",String.valueOf(comics.size()));
                    }
                }

                comicSearchedLV.setAdapter(comicSearchListViewAdapter);
                return false;
            }
        });

    }
}