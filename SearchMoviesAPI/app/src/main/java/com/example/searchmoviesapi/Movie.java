package com.example.searchmoviesapi;

import java.io.Serializable;

public class Movie implements Serializable {
    String mtitle;
    String moverView;
    String imageURL;
    String poster_path;
    String releaseDate;
    String width;
    int popularity, rating;
    String favourite;

    public Movie() {
        this.mtitle = mtitle;
        this.moverView = moverView;
        this.imageURL = imageURL;
        this.poster_path = poster_path;
        this.releaseDate = releaseDate;
        this.width = width;
        this.popularity = popularity;
        this.rating = rating;
        this.favourite = "False";
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mtitle='" + mtitle + '\'' +
                ", moverView='" + moverView + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", width='" + width + '\'' +
                ", popularity=" + popularity +
                ", rating=" + rating +
                '}';
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMoverView() {
        return moverView;
    }

    public void setMoverView(String moverView) {
        this.moverView = moverView;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
