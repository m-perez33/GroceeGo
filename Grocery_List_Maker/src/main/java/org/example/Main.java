package org.example;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        Produce produce = new Produce("Bannna", 10, 3.50);
        Meat meat = new Meat("Chicken thighs", 5, 5.50);
        Deli deli = new Deli("American Cheese", 6, 3.5, "Thick");
        //System.out.println(produce.toString());
        produce.setQuantity(23);
        //System.out.println(produce.toString());
        //System.out.println(deli.toString());

        products.add(meat);
        products.add(produce);
        products.add(deli);
        //System.out.println(products.get(0).toString());


        //System.out.println(finalListTotal(products));
        displayList(products);
       /* double finalCost = 0.0;
        for (Product product : products){
            System.out.println(product.getTotal());
            finalCost = finalCost + product.getTotal();

        };
        System.out.println(finalCost);*/




    }

    /*public static double finalListTotal(List<Product> products){
        double finalCost = 0.0;
        for (Product product : products){
            //System.out.println(product.getTotal());
            finalCost = finalCost + product.getTotal();
        };
        return finalCost;

    }*/

    public static void displayList(List<Product> products){
        System.out.println("Your items!: " + System.lineSeparator());

        double finalCost = 0.0;
        for (Product product : products){
            System.out.println(product.toString());
            finalCost = finalCost + product.getTotal();

        };

        System.out.println("Your final total is $" + finalCost);
    }



}
