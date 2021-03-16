package com.example.osos_assignement;

import java.util.List;

public class Model1 {
    String title;
    int image;
    List<String> strImage;

    public Model1(String title, List<String> strImage) {
        this.title = title;
        this.strImage = strImage;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public void setStrImage(List<String> strImage) {
        this.strImage = strImage;
    }

    public List<String> getStrImage() {
        return strImage;
    }
}
