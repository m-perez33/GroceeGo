package org.example;

public class Meat extends Product{

    //Produce class for veggies and fruites, salads, herbs,


    public Meat (String productName, int quantity, double cost) {
        super(productName, quantity, cost);

    }

    @Override
    public String toString() {
        return "Meat: " + productName + System.lineSeparator() +
                "Quantity: " + quantity + " lbs" + System.lineSeparator() +
                "Cost per pound: " + cost + System.lineSeparator() +
                "Total cost: $" + getTotal() + System.lineSeparator()  ;
    }

}
