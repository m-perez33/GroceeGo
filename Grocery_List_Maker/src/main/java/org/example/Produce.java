package org.example;

public class Produce extends Product {
 //Produce class for veggies and fruites, salads, herbs,


    public Produce(String productName, int quantity, double cost) {
        super(productName, quantity, cost);

    }
    public Produce(){

    }

    @Override
    public String toString() {
        return "Produce: " + productName + System.lineSeparator() +
                "Quantity: " + quantity + " lbs" + System.lineSeparator() +
                "Cost per pound: " + cost + System.lineSeparator() +
                "Total cost: $" + getTotal() + System.lineSeparator()  ;
    }


}
