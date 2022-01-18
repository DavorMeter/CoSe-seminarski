package com.example.test_za_bazu;

import java.util.ArrayList;

public class DataStorage {

    public static ArrayList<Comic> comicsList = new ArrayList<Comic>();
    public static ArrayList<Author> authorsList = new ArrayList<Author>();
    public static ArrayList<Episode> episodesList = new ArrayList<Episode>();
    public static ArrayList<ImagesEpisodes> imagesEpisodesList = new ArrayList<ImagesEpisodes>();
    public static ArrayList<Users> usersList = new ArrayList<Users>();

    public static Comic getComicById(Integer id) {
        for(int i = 0; i < comicsList.size(); i++) {
            if (comicsList.get(i).getComic_ID() == id) {
                return comicsList.get(i);
            }
        }
        return null;
    }

    public static Author getAuthorById(Integer id) {
        for(int i = 0; i < authorsList.size(); i++) {
            if (authorsList.get(i).getAuthor_ID() == id) {
                return authorsList.get(i);
            }
        }
        return null;
    }

    public static Episode getEpisodeById(Integer id) {
        for(int i = 0; i < episodesList.size(); i++) {
            if (episodesList.get(i).getEpisode_ID() == id) {
                return episodesList.get(i);
            }
        }
        return null;
    }
    public static boolean checkIfImageEpisodeExists(Integer id) {
        if(imagesEpisodesList.size() < 0) {
            for (int i = 0; i < imagesEpisodesList.size(); i++) {
                if (imagesEpisodesList.get(i).getImage_ID() == id) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public static Comic getComicByTitle(String title) {
        for (int i = 0; i < comicsList.size(); i++) {
            if(comicsList.get(i).getComic_title().equals(title)) {
                return comicsList.get(i);
            }
        }
        return null;
    }


}