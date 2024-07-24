package com.example.advancepizza_1190083_1190113;

public class Favorites {
    private int pizzaID;
    private String customerEmail;

    public Favorites() {
    }

    public Favorites(int pizzaID, String customerEmail) {
        this.pizzaID = pizzaID;
        this.customerEmail = customerEmail;
    }

    public int getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(int pizzaID) {
        this.pizzaID = pizzaID;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "pizzaID=" + pizzaID +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
