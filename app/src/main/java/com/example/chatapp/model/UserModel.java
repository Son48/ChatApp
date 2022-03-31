package com.example.chatapp.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    String image;
    String email;
    String name;
    String password;
    //todo Camel
    String userID;
    String token;
    public UserModel() {
    }

    public UserModel(String image, String email, String name, String password,String userID) {
        this.image = image;
        this.email = email;
        this.name = name;
        this.password = password;
        this.userID=userID;
    }

    public String getImage() {
        return image;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
