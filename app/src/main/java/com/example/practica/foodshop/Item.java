package com.example.practica.foodshop;

import java.io.Serializable;

public class Item implements Serializable {
    private String text;
    private String name;
    private String img;
    private double price;

    public Item(String name, String text, String img, double price) {
        this.text = text;
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public Item(String text, String name, double price) {
        this.text = text;
        this.name = name;
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public String toString() {
        return "Item{" +
                "text='" + text + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public void setImg(String img) {
        this.img = img;
    }
}
