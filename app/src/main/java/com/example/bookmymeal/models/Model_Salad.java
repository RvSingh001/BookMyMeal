package com.example.bookmymeal.models;

import com.google.gson.annotations.SerializedName;

public class Model_Salad {

    @SerializedName("salad_img")
    public String salad_img;
    @SerializedName("id")
    public String id;
    @SerializedName("salad_name")
    public String salad_name;
    @SerializedName("salad_des")
    public String salad_des;
    @SerializedName("salad_price")
    public String salad_price;

    public Model_Salad(String salad_img, String id, String salad_name, String salad_des, String salad_price) {
        this.salad_img = salad_img;
        this.id = id;
        this.salad_name = salad_name;
        this.salad_des = salad_des;
        this.salad_price = salad_price;
    }

    public String getSalad_img() {
        return salad_img;
    }

    public String getId() {
        return id;
    }

    public String getSalad_name() {
        return salad_name;
    }

    public String getSalad_des() {
        return salad_des;
    }

    public String getSalad_price() {
        return salad_price;
    }
}
