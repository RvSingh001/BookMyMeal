package com.example.bookmymeal.models;

import com.google.gson.annotations.SerializedName;

public class Model_Main {

    @SerializedName("main_img")
    public String main_img;
    @SerializedName("id")
    public String id;
    @SerializedName("main_name")
    public String main_name;
    @SerializedName("main_des")
    public String main_des;
    @SerializedName("main_price")
    public String main_price;


    public Model_Main(String main_img, String id, String main_name, String main_des, String main_price) {
        this.main_img = main_img;
        this.id = id;
        this.main_name = main_name;
        this.main_des = main_des;
        this.main_price = main_price;


    }

    public String getMain_img() {
        return main_img;
    }

    public String getId() {
        return id;
    }

    public String getMain_name() {
        return main_name;
    }

    public String getMain_des() {
        return main_des;
    }

    public String getMain_price() {
        return main_price;
    }
}
