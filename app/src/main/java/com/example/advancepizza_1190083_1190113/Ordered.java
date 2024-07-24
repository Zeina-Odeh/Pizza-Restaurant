package com.example.advancepizza_1190083_1190113;

public class Ordered {
    private String pizzaType;
    private String customerEmail;
    private String orderDate;
    private String orderTime;
    private String size;
    private int quantity;

    private double basePrice;

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    private String reservedBy;
    private double finalPrice; // New field for the final price
    private String customerName;


    public Ordered() {
    }

    public Ordered(String pizzaType, String customerEmail, String date, String time, String size, int quantity,double basePrice, double finalPrice, String reservedBy) {
        this.pizzaType = pizzaType;
        this.customerEmail = customerEmail;
        this.orderDate = date;
        this.orderTime = time;
        this.size = size;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice; // Initialize the finalPrice field
        this.reservedBy = reservedBy;

    }

    public Ordered(String pizzaType, String customerEmail, String date, String time, String size, int quantity,double basePrice, double finalPrice) {
        this.pizzaType = pizzaType;
        this.customerEmail = customerEmail;
        this.orderDate = date;
        this.orderTime = time;
        this.size = size;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice; // Initialize the finalPrice field

    }

    public Ordered(String pizzaType, String orderDate, String orderTime, String size, int quantity, double basePrice, double finalPrice, String customerName) {
        this.pizzaType = pizzaType;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.size = size;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.customerName = customerName;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    public double getBasePrice() {
        return basePrice;
    }


    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Ordered{" +
                "pizzaType=" + pizzaType +
                ", customerEmail='" + customerEmail + '\'' +
                ", date='" + orderDate + '\'' +
                ", time='" + orderTime + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", basePrice=" + basePrice +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
