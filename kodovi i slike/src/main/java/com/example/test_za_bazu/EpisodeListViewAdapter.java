package com.example.test_za_bazu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;

public class EpisodeListViewAdapter extends BaseAdapter {
    Context myContext;
    LayoutInflater myLayoutInflater;

    public EpisodeListViewAdapter(Context myContext) {
        this.myContext = myContext;
        this.myLayoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return DataStorage.episodesList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = myLayoutInflater.inflate(R.layout.episode_list_view,parent,false);
        }
        ImageView episodeTumbImageView = convertView.findViewById(R.id.episodeThumbnail);
        TextView episodeTitleTextView = convertView.findViewById(R.id.episodeTitleItem);
        Episode currentEpisode = DataStorage.episodesList.get(position);

        Ion.with(episodeTumbImageView).load(currentEpisode.getEpisode_tmb());
        episodeTitleTextView.setText(currentEpisode.getEpisode_title());

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
