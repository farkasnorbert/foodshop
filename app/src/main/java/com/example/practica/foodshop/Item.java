package com.example.practica.foodshop;

import java.io.Serializable;

public class Item implements Serializable {
    private String text;
    private String name;
    private String img;
    private double price;
    private int id;

    public Item(String name, String text, String img, double price, int id) {
        this.text = text;
        this.name = name;
        this.img = img;
        this.price = price;
        this.id = id;
    }

    public int getId() { return id; }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public double getPrice() { return price; }
}
