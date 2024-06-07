package com.vannguyenv12.food.modal;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String name;
    private double price;
    private String image;
    private int categoryId;

    public Food() {
    }

    public Food(int id, String name, double price, String image, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
    }
    public Food(String name, double price, String image, int categoryId) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
    }

//    Mới thêm
public Food(String name, double price, int categoryId) {
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
}

    public Food(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int type) {
        this.categoryId = type;
    }

    public int getId() {
        return id;
    }
}
