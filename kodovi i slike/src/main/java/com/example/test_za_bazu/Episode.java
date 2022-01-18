package com.example.test_za_bazu;

import java.sql.Date;

public class Episode {
    private int episode_comic_ID;
    private int episode_ID;
    private String episode_title;
    private String episode_tmb;
    private String image_path;
    private int image_count;

    /*public Episode(int episode_comic_ID, int episode_ID, String episode_title, String episode_tmb, String episode_images_path)
    {
        this.episode_comic_ID = episode_comic_ID;
        this.episode_images_path = episode_images_path;
        this.episode_title = episode_title;
        this.episode_tmb = episode_tmb;
        this.episode_images_path = episode_images_path;
    }*/

    public int getEpisode_comic_ID() {
        return episode_comic_ID;
    }

    public void setEpisode_comic_ID(int episode_comic_ID) {
        this.episode_comic_ID = episode_comic_ID;
    }

    public int getEpisode_ID() {
        return episode_ID;
    }

    public void setEpisode_ID(int episode_ID) {
        this.episode_ID = episode_ID;
    }

    public String getEpisode_title() {
        return episode_title;
    }

    public void setEpisode_title(String episode_title) {
        this.episode_title = episode_title;
    }

    public String getEpisode_tmb() {
        return episode_tmb;
    }

    public void setEpisode_tmb(String episode_tmb) {
        this.episode_tmb = episode_tmb;
    }

    public String getEpisode_images_path() {
        return image_path;
    }

    public void setEpisode_images_path(String image_path) {
        this.image_path = image_path;
    }

    public int getEpisode_image_count() {
        return image_count;
    }

    public void setEpisode_image_count(int image_count) {
        this.image_count = image_count;
    }
}
