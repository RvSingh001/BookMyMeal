package com.example.bookmymeal.models;

import com.google.gson.annotations.SerializedName;

public class Model_Cart {
    @SerializedName("food_image")
    public String food_image;
    @SerializedName("food_name")
    public String food_name;
    @SerializedName("food_price")
    public String food_price;


    public Model_Cart(String food_image, String food_name, String food_price) {
        this.food_image = food_image;
        this.food_name = food_name;
        this.food_price = food_price;
    }

    public String getFood_image() {
        return food_image;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getFood_price() {
        return food_price;
    }
}
