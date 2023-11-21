package org.example;

abstract class Product {

    protected String productName;

    protected int quantity;

    protected double cost;

   // protected double weight;

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public double getTotal(){
        return cost * quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }



    public Product(String productName, int quantity, double cost) {
        this.productName = productName;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Product() {

    }



}
