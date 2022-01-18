package com.example.test_za_bazu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<String> comic_title = new ArrayList<String>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        listView = findViewById(R.id.searchComicList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comic_title);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object comic = parent.getItemAtPosition(position);
                String comic_name = comic.toString();
                Toast.makeText(getApplicationContext(),comic_name,Toast.LENGTH_SHORT).show();

                Comic clickedComic = DataStorage.getComicByTitle(comic_name);
                Toast.makeText(getApplicationContext(),String.valueOf(clickedComic),Toast.LENGTH_SHORT).show();
                Intent openComicEpisodesIntent = new Intent(SearchActivity.this,
                        ComicsEpisodesActivity.class);
                openComicEpisodesIntent.putExtra("opened_comic", clickedComic.getComic_ID());
                startActivity(openComicEpisodesIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.nav_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.comic_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}