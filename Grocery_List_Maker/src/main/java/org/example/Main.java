package org.example;

import java.sql.SQLOutput;
import java.util.*;

public class Main {

    private static final List<Product> shoppingList = new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //List<Product> products = new ArrayList<>();

      /* Produce produce = new Produce("Bannna", 10, 3.50);
        Meat meat = new Meat("Chicken thighs", 5, 5.50);
        Deli deli = new Deli("American Cheese", 6, 3.5, "Thick");


        produce.setQuantity(23);

        products.add(meat);
        products.add(produce);
        products.add(deli);


        displayList(products);*/


        //need to loop to add more to the list


        //Scanner sc = new Scanner(System.in);
        String prompt = "";

        System.out.println("1. Produce" + System.lineSeparator() +
                "2. Meat" + System.lineSeparator() +
                "3. Deli" + System.lineSeparator());

        while(!prompt.equalsIgnoreCase("n")) {
            System.out.println("Please enter product number: ");
            String number = sc.nextLine();

            loadProduct(Integer.parseInt(number));

            System.out.println("Enter another product (Y/N): ");
            prompt = sc.nextLine().toLowerCase(Locale.ROOT);
        }


        //loop and add products as needed

        displayList(shoppingList);


    }

    public static List<Product> shoppingList(Product product) {
        List<Product> shoppingList = new ArrayList<>();

        shoppingList.add(product);

        return shoppingList;
    }

    public static void loadProduct(int productNumber) {


        if (productNumber == 1) {
            System.out.println("What is the produce name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            Produce produce = new Produce(name, Integer.parseInt(quantity),
                    Double.parseDouble(cost));

            shoppingList.add(produce);
        }


        // System.out.println(produce.toString());


    }

    public static void loadQuantity(int productNumber) {
        Produce produce = new Produce();
        if (productNumber == 1) {
            // System.out.println();sc
            System.out.println("What is the quantity name:");
            int q = sc.nextInt();

        }


    }

    /*public static double finalListTotal(List<Product> products){
        double finalCost = 0.0;
        for (Product product : products){
            //System.out.println(product.getTotal());
            finalCost = finalCost + product.getTotal();
        };
        return finalCost;

    }*/

    public static void displayList(List<Product> products) {
        System.out.println("Your items!: " + System.lineSeparator());

        double finalCost = 0.0;
        for (Product product : products) {
            System.out.println(product.toString());
            finalCost = finalCost + product.getTotal();

        }
        ;

        System.out.println("Your final total is $" + finalCost);
    }


}
