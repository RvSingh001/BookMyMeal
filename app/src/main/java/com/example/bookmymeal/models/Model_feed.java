package com.example.bookmymeal.models;

public class Model_feed {

    public String feed;
    public String rate;
    public String name;
    public String email;

    public Model_feed(String feed, String rate, String name, String email) {
        this.feed = feed;
        this.rate = rate;
        this.name = name;
        this.email = email;
    }

    public String getFeed() {
        return feed;
    }

    public String getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
