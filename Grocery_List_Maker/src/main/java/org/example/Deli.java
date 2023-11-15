package org.example;

public class Deli extends Product{

    private String slice;

    public Deli(String productName, int quantity, double cost, String slice) {
        super(productName, quantity, cost);
        this.slice = slice;
    }

    @Override
    public String toString() {
        return "Deli : " + productName + System.lineSeparator() +
                "Quantity: " + quantity + " lbs" + System.lineSeparator() +
                "Cost per pound: " + cost + System.lineSeparator() +
                "Slice type: " + slice + System.lineSeparator() +
                "Total cost: $" + getTotal() + System.lineSeparator() ;
    }

}
