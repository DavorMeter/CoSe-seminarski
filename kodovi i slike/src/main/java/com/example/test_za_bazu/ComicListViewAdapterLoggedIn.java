package com.example.test_za_bazu;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.koushikdutta.ion.Ion;

public class ComicListViewAdapterLoggedIn extends BaseAdapter {
    Context myContext;
    LayoutInflater myLayoutInflater;
    Users loggedInUser;

    public ComicListViewAdapterLoggedIn(Context myContext, Users loggedInUser) {
        this.myContext = myContext;
        this.loggedInUser = loggedInUser;
        this.myLayoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return DataStorage.comicsList.size();
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
        Comic currentComic = DataStorage.comicsList.get(position);
        Author author_name = null;

        author_name = DataStorage.getAuthorById(currentComic.getComic_author_ID());
        Ion.with(comicThumbImageView).load(currentComic.getComic_thumbnail());
        comicTitleTextView.setText(currentComic.getComic_title());
        comicAuthorTextView.setText(author_name.getAuthor_name());

        ImageButton subscribeButtonComic = convertView.findViewById(R.id.buttonSubscribeCommic);
        subscribeButtonComic.setImageResource(R.drawable.check_box_off);

        subscribeButtonComic.setOnClickListener(new View.OnClickListener() {

            int a = 0;

            @Override
            public void onClick(View v) {
                SubscribeDataSource subscribeDataSource = new SubscribeDataSource(myContext.getApplicationContext());
                if(a == 0) {
                    subscribeButtonComic.setImageResource(R.drawable.check_box_on);

                    subscribeDataSource.addSubscribeToDb(Integer.valueOf(String.valueOf(loggedInUser.getUser_ID())+String.valueOf(currentComic.getComic_ID())),loggedInUser.getUser_ID(),currentComic.getComic_ID());
                    a = 1;
                }
                else{
                    subscribeButtonComic.setImageResource(R.drawable.check_box_off);
                    subscribeDataSource.deleteSubscribeFromDb(currentComic.getComic_ID());
                    a = 0;
                }
            }
        });
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