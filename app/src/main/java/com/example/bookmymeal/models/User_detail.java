package com.example.bookmymeal.models;

import com.google.gson.annotations.SerializedName;

public class User_detail {
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("address")
    public String address;
    @SerializedName("contact")
    public String contact;
    @SerializedName("image")
    public String image;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getImage() {
        return image;
    }

    public User_detail(String name, String email, String password, String address, String contact, String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.contact = contact;
        this.image=image;
    }


}
