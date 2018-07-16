package com.example.practica.foodshop;

public class Item {
    private String text;
    private String name;
    private String img;

    public Item(String name, String text, String img) {
        this.text = text;
        this.name = name;
        this.img = img;
    }

    public Item(String text, String name) {
        this.text = text;
        this.name = name;
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
