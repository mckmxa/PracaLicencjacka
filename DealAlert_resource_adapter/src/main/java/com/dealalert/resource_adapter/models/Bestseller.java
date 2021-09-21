package com.dealalert.resource_adapter.models;

import java.io.Serializable;

public class Bestseller implements Serializable {
    private String asin;
    private String title;
    private String image;
    private Price price;

    public Bestseller() {

    }


    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
