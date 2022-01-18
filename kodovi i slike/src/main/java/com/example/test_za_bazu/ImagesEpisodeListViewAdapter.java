package com.example.test_za_bazu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.koushikdutta.ion.Ion;

public class ImagesEpisodeListViewAdapter extends BaseAdapter {
    Context myContext;
    LayoutInflater myLayoutInflater;

    public ImagesEpisodeListViewAdapter(Context myContext) {
        this.myContext = myContext;
        this.myLayoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return DataStorage.imagesEpisodesList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = myLayoutInflater.inflate(R.layout.episode_image_list_view,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.episodeImagesView);
        ImagesEpisodes currentImagesEpisodes = DataStorage.imagesEpisodesList.get(position);
        Ion.with(imageView).load(currentImagesEpisodes.getImage_path());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
