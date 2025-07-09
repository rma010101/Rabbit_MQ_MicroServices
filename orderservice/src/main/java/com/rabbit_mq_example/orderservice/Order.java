package com.rabbit_mq_example.orderservice;

public class Order {

    //properties of the Order class
    private String orderId;
    private String item;
    private double amount;

    public Order() {
        //TODO
    }
    public Order(String orderId, String item, double amount) {
        this.orderId = orderId;
        this.item = item;
        this.amount = amount;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", item='" + item + '\'' +
                ", amount=" + amount +
                '}';
    }
}
