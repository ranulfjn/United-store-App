package com.isi.unitedstore;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Order implements Serializable {
    int id;
    Bitmap image;
    String name;
    int price;
    int qty;

    public Order(int id, Bitmap image, String name, int price, int qty) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
