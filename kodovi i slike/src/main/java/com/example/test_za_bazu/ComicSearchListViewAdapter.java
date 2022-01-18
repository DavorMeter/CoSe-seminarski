package com.example.test_za_bazu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ComicSearchListViewAdapter extends BaseAdapter {
    Context myContext;
    LayoutInflater myLayoutInflater;
    ArrayList<Comic> searchedComicsList;

    public ComicSearchListViewAdapter(Context myContext, ArrayList<Comic> searchedComicsList) {
        this.myContext = myContext;
        this.searchedComicsList = searchedComicsList;
        this.myLayoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return searchedComicsList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = myLayoutInflater.inflate(R.layout.comic_list_view,parent,false);
        }
        ImageView comicThumbImageView = convertView.findViewById(R.id.comicThumbnail);
        TextView comicTitleTextView = convertView.findViewById(R.id.comicTitleItem);
        TextView comicAuthorTextView = convertView.findViewById(R.id.comicAuthorItem);
        Comic currentComic = searchedComicsList.get(position);
        Author author_name = new Author();

        author_name = DataStorage.getAuthorById(currentComic.getComic_author_ID());
        Ion.with(comicThumbImageView).load(currentComic.getComic_thumbnail());
        comicTitleTextView.setText(currentComic.getComic_title());
        comicAuthorTextView.setText(author_name.getAuthor_name());

        ImageButton subscribeButtonComic = convertView.findViewById(R.id.buttonSubscribeCommic);
        subscribeButtonComic.setVisibility(View.INVISIBLE);

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

    /*public interface  comicListViewListener{
        void onClick(View v, int position);
    }*/
}

