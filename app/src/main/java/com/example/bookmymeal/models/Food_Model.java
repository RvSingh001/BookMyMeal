package com.example.bookmymeal.models;

public class Food_Model {
    private int id;
    private String food_name;
    private String food_des;
    private String food_img;
    private String food_price;
    private String category;

    public Food_Model(int id,String food_name, String food_des, String food_img, String food_price, String category) {
        this.id=id;
        this.food_name = food_name;
        this.food_des = food_des;
        this.food_img = food_img;
        this.food_price = food_price;
        this.category = category;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public void setFood_des(String food_des) {
        this.food_des = food_des;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

    public void setFood_price(String food_price) {
        this.food_price = food_price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getFood_des() {
        return food_des;
    }

    public String getFood_img() {
        return food_img;
    }

    public String getFood_price() {
        return food_price;
    }

    public String getCategory() {
        return category;
    }
}
