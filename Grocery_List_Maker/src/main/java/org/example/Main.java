package org.example;

import java.sql.SQLOutput;
import java.util.*;

public class Main {

    private static final List<Product> shoppingList = new ArrayList<>();

    private static final List<Product> favorites = new ArrayList<>();

    private static final List<Product> items = new ArrayList<>();

    private static final List<List<Product>> shoppingLists = new ArrayList<>();


    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        mainMenu();



    }


    public static void mainMenu() {

        System.out.println(
                "1. List All Items" + System.lineSeparator() +
                        "2. List Favorite Grocery Items" + System.lineSeparator() +
                        "3. List previous shopping lists" + System.lineSeparator() +
                        "4. Create new shopping list" + System.lineSeparator());


        System.out.println("Please make your selection: ");
        String number = sc.nextLine();

        menuSelection(Integer.parseInt(number));


    }

    public static void menuSelection(int menuNumber) {
        if (menuNumber == 1) {
            System.out.println("List of all grocery items: " + System.lineSeparator());
            displayItems(items);
            mainMenu();
        }
        if (menuNumber == 2) {
            System.out.println("List of your favorites: " + System.lineSeparator());
            displayItems(favorites);
            mainMenu();
        }
        if (menuNumber == 3) {
            displayAllLists(shoppingLists);
            mainMenu();
        }
        if (menuNumber == 4) {
            loadAddProductMenu();
        }


    }

    public static void newShoppingList() {
        List<Product> newList = new ArrayList<>();


    }

    public static void loadAddProductMenu() {
        List<Product> newList = new ArrayList<>();
        Product returnedProduct = null;

        String prompt = "";

        while (prompt == "") {
            System.out.println("Add to shopping list: ");
            System.out.println(
                    "1. Choose from all products" + System.lineSeparator() +
                            "2. Choose from favorite products" + System.lineSeparator() +
                            "3. Add new products");
            if (newList.size() > 0) {
                System.out.println("4. Print shopping list. List contains " + newList.size() + " items." + System.lineSeparator());

            }

            String menuNumber = sc.nextLine();

            if (Integer.parseInt(menuNumber) == 3) {
                // String prompt = "";
                //while (!prompt.equalsIgnoreCase("n")) {
                System.out.println("Please enter product number: ");
                System.out.println(
                        "1. Produce" + System.lineSeparator() +
                                "2. Meat" + System.lineSeparator() +
                                "3. Deli" + System.lineSeparator());
                String number = sc.nextLine();

                returnedProduct = loadProduct(Integer.parseInt(number));
                //add to current shopping list
                newList.add(returnedProduct);
                //add to all items
                items.add(returnedProduct);

            }
            if (Integer.parseInt(menuNumber) == 4) {
                displayList(newList);
                prompt = "done";

            }
        }

        shoppingLists.add(newList);



        System.out.println("Select 0 to return to main menu");

        String next = sc.nextLine();

        int nextInt = Integer.parseInt(next);

        if (nextInt == 0) {
            mainMenu();

        }


    }

    public static void loadFav(Product favProduct) {

        System.out.println("Add item to your favorites list? Y/N");

        String addToFavorites = sc.nextLine().toLowerCase();
        if (addToFavorites.equalsIgnoreCase("y")) {
            favorites.add(favProduct);
        }


    }

    public static Product loadProduct(int productNumber) {
        Product product = null;

        if (productNumber == 1) {
            System.out.println("What is the produce name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            Produce produce = new Produce(name, Integer.parseInt(quantity),
                    Double.parseDouble(cost));

            product = produce;
            loadFav(produce);
            //shoppingList.add(produce);
        }
        if (productNumber == 2) {
            System.out.println("What is the meat name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            Meat meat = new Meat(name, Integer.parseInt(quantity),
                    Double.parseDouble(cost));

            product = meat;
            //ask to add to favs
            loadFav(meat);

            shoppingList.add(meat);
        }
        if (productNumber == 3) {
            System.out.println("What is the deli product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();
            System.out.println("What is slice thick, thin, or normal?: ");


            String slice = sc.nextLine();

            Deli deli = new Deli(name, Integer.parseInt(quantity),
                    Double.parseDouble(cost), slice);

            product = deli;

            loadFav(deli);

            shoppingList.add(deli);
        }

        return product;


    }

    public static void loadQuantity(int productNumber) {
        Produce produce = new Produce();
        if (productNumber == 1) {
            System.out.println("What is the quantity name:");
            int q = sc.nextInt();
        }


    }


    public static void displayList(List<Product> products) {
        System.out.println("Your shopping list!: " + System.lineSeparator());

        double finalCost = 0.0;
        for (Product product : products) {

            System.out.println(product.toString());
            finalCost = finalCost + product.getTotal();

        }
        ;

        System.out.println("Your list total is $" + finalCost);
    }

    public static void displayAllLists(List<List<Product>> lists) {
        //System.out.println("Your shopping list!: " + System.lineSeparator());

        int count = 1;

        for (List list : lists) {
            System.out.println("List #" + count);
            displayList(list);
            displayItems(list);
            count++;
            System.lineSeparator();
            //System.out.println(product.toString());
            //finalCost = finalCost + product.getTotal();

        }
        ;

        System.out.println("Happy Shopping!");
    }

    public static void displayItems(List<Product> products) {
//

        double finalCost = 0.0;
        for (Product product : products) {
            System.out.println("" + product.getProductName() + " | " + product.getQuantity() +
                    " | " + product.getCost());
            // System.out.println(product.toString());

        }
        ;

    }
}
