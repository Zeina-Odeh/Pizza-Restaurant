package com.example.advancepizza_1190083_1190113;

import java.io.Serializable;

public class Pizza implements Serializable {
    private int id;
    private String type;
    private String category;
    private double price;
    private SpecialOffer specialOffer;

    private String orderedTime;

    private String orderedDate;

    private String orderedBy;
    private boolean favorite;


    public Pizza() {

    }

    public Pizza(int id, String type, String category, double price, String orderedBy, String orderedTime, String orderedDate) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.price = price;
        this.orderedTime = orderedTime;
        this.orderedDate = orderedDate;
        this.orderedBy = orderedBy;
    }

    public Pizza(int id, String type, String category, double price, String orderedTime, String orderedDate) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.price = price;
        this.orderedTime = orderedTime;
        this.orderedDate = orderedDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

    public String getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(String orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getFormattedPrice() {
        return String.format("$%.2f", price);
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
