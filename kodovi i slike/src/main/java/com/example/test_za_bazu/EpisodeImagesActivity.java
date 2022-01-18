package com.example.test_za_bazu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EpisodeImagesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        final ListView imagesLV = findViewById(R.id.comicEpisodesImages);
        final ImagesEpisodeListViewAdapter imagesEpisodeListViewAdapter = new ImagesEpisodeListViewAdapter(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        int sentEpisodeID = extras.getInt("opened_episode");
        String image_path_string = null;
        Episode episode = DataStorage.getEpisodeById(sentEpisodeID);
        ImagesEpisodes imagesEpisodes = new ImagesEpisodes();
        final int image_count = episode.getEpisode_image_count();
        ImagesDataSource imagesDataSource = new ImagesDataSource(getApplicationContext());
        for(int i = 1; i <= image_count; i++) {

            image_path_string = episode.getEpisode_images_path() + String.valueOf(i) + ".jpg";
            imagesEpisodes.setImage_path(image_path_string);
            imagesEpisodes.setImage_episode_ID(episode.getEpisode_ID());
            imagesEpisodes.setImage_ID(Integer.valueOf(String.valueOf(episode.getEpisode_comic_ID())+String.valueOf(episode.getEpisode_ID())+String.valueOf(i)));
            imagesDataSource.addImagesToDb(imagesEpisodes.getImage_ID(),
                                        imagesEpisodes.getImage_episode_ID(),
                                        imagesEpisodes.getImage_path());
        }
        DataStorage.imagesEpisodesList = imagesDataSource.getImagesByEpisodeID(sentEpisodeID);

        //Log.d("Davor", DataStorage.imagesEpisodesList.get(i).getImage_path());

        imagesLV.setAdapter(imagesEpisodeListViewAdapter);
    }
}
