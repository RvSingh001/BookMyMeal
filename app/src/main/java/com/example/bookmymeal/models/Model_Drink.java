package com.example.bookmymeal.models;

import com.google.gson.annotations.SerializedName;

public class Model_Drink {
    @SerializedName("drink_img")
    public String drink_img;
    @SerializedName("id")
    public String id;
    @SerializedName("drink_name")
    public String drink_name;
    @SerializedName("drink_des")
    public String drink_des;
    @SerializedName("drink_price")
    public String drink_price;

    public Model_Drink(String drink_img,String id, String drink_name, String drink_des, String drink_price) {
        this.drink_img = drink_img;
        this.id=id;
        this.drink_name = drink_name;
        this.drink_des = drink_des;
        this.drink_price = drink_price;
    }

    public String getDrink_img() {
        return drink_img;
    }
    public String getId()
    {
        return id;
    }
    public String getDrink_name() {
        return drink_name;
    }

    public String getDrink_des() {
        return drink_des;
    }

    public String getDrink_price() {
        return drink_price;
    }
}
