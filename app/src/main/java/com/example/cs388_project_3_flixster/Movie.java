package com.example.cs388_project_3_flixster;

public class Movie {
    private final String title, poster, desc;
    private final Double rating;

    public Movie(String title, String posterPath, String desc, Double rating) {
        this.title = title;
        this.poster = posterPath;
        this.desc = desc;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Double getRating() {return rating;
    }

    public String getPosterPath() { return poster;
    }

}
