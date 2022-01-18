package com.example.test_za_bazu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import java.lang.reflect.Type;
import java.util.Collection;

public class ComicsEpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        DataStorage.imagesEpisodesList.clear();

        final ListView episodesLV = findViewById(R.id.episodesList);
        final EpisodeListViewAdapter episodeListViewAdapter = new EpisodeListViewAdapter(getApplicationContext());

        AsyncHttpClient myClient=new AsyncHttpClient();
        final Gson myGson = new Gson();

        Bundle extras = getIntent().getExtras();
        final int sentComicID=extras.getInt("opened_comic");



        myClient.get("https://raw.githubusercontent.com/warbossy/CoSe-seminarski/main/episodes.txt", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Nesto nevalja s API-em",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type collectionType = new TypeToken<Collection<Episode>>(){}.getType();
                Collection<Episode> episodes = myGson.fromJson(responseString, collectionType);
                EpisodeDataSource episodeDataSource = new EpisodeDataSource(getApplicationContext());
                for(Episode e : episodes){
                    episodeDataSource.addEpisodeToDb(
                            e.getEpisode_comic_ID(),
                            e.getEpisode_ID(),
                            e.getEpisode_title(),
                            e.getEpisode_tmb(),
                            e.getEpisode_images_path(),
                            e.getEpisode_image_count());
                }
                DataStorage.episodesList = episodeDataSource.getEpisodesByEpisodesComicID(sentComicID);
                episodesLV.setAdapter(episodeListViewAdapter);

                episodesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Episode clickedEpisode = DataStorage.episodesList.get(position);
                        Intent openEpisodeImagesIntent = new Intent(ComicsEpisodesActivity.this,EpisodeImagesActivity.class);
                        openEpisodeImagesIntent.putExtra("opened_episode",clickedEpisode.getEpisode_ID());
                        startActivity(openEpisodeImagesIntent);
                    }
                });
            }
        });
    }
}
