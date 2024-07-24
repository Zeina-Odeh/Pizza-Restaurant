package com.example.advancepizza_1190083_1190113;


public class SpecialOffer {
    private int id;
    private String pizzaType;
    private String pizzaSize;
    private String offerPeriod;
    private double totalPrice;

    public String getStartDateTime() {
        return startDateTime;
    }

    private String startDateTime;

    private String endDateTime;

    public SpecialOffer(int id, String pizzaType, String pizzaSize, String offerPeriod,String startDateTime, String endDateTime, double totalPrice) {
        this.id = id;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.offerPeriod = offerPeriod;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public String getOfferPeriod() {
        return offerPeriod;
    }

    public void setOfferPeriod(String offerPeriod) {
        this.offerPeriod = offerPeriod;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}