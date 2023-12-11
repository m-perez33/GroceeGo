package org.example;

import org.example.Model.ListEntry;
import org.example.Model.ShoppingList;

import java.sql.SQLOutput;
import java.util.*;

public class Main {

    private static final List<ShoppingList> SHOPPING_LISTS = new ArrayList<>();

    private static final List<org.example.Model.Product> PRODUCTS = new ArrayList<>();

    private static final List<ListEntry> LIST_ENTRIES = new ArrayList<>();

    private static ShoppingList shoppinglist;


    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("");
        System.lineSeparator();

        mainMenu();

    }


    public static void mainMenu() {

        if (SHOPPING_LISTS.size() == 0) {
            System.out.println("Create new shopping list" + System.lineSeparator());
            //if no shopping lists immediately load create list product menu
            createShoppingList();

        } else {
            //otherwise give options
            System.out.println("1. Create new shopping list");
            System.out.println("2. Edit existing shopping lists" + System.lineSeparator());

            System.out.println("Please make your selection: ");
            String number = sc.nextLine();

            menuSelection(Integer.parseInt(number));
        }


    }

    public static void editExistingSubMenu() {

        System.out.println("1. Add product to list" + System.lineSeparator() +
                "2. Update list" + System.lineSeparator() +
                "3. Delete List");
    }

    public static void createShoppingList() {

        //create list
        System.out.println("Please name your list: ");
        String listname = sc.nextLine();

        ShoppingList shoppingList = new ShoppingList(listname);

        SHOPPING_LISTS.add(shoppingList);

        shoppingList.setListId(SHOPPING_LISTS.indexOf(shoppingList) + 1);

        selectProductCategoryMenu(shoppingList);

    }

    public static void selectProductCategoryMenu(ShoppingList shoppingList) {

        System.lineSeparator();
        System.out.println("Please select product category: ");
        System.out.println("1. Produce" + System.lineSeparator() +
                "2. Meat" + System.lineSeparator() +
                "3. Deli" + System.lineSeparator());
        String menuNumber = sc.nextLine();
        int number = Integer.parseInt(menuNumber);

        loadProductData(number, shoppingList);

        subMenu1(shoppingList);


    }

    public static void subMenu1(ShoppingList shoppingList) {

        System.lineSeparator();
        System.out.println("1. Add another product" + System.lineSeparator() +
                "2. Print shopping List" + System.lineSeparator() +
                "3. Return to Main menu" + System.lineSeparator());

        String menuNumber = sc.nextLine();
        int number = Integer.parseInt(menuNumber);
        subMenuSelection(number, shoppingList);

        mainMenu();

    }

    public static void subMenuSelection(int menuNumber, ShoppingList shoppingList) {

        if (menuNumber == 1) {
            selectProductCategoryMenu(shoppingList);
        }
        if (menuNumber == 2) {
            printShoppingList(shoppingList.getListId());
        }
        if (menuNumber == 3) {
            mainMenu();
        }

    }

    public static void getLists() {


        for (ShoppingList shoppingList : SHOPPING_LISTS) {
            System.out.println("" + shoppingList.getListId() + "." + shoppingList.toString());

        }
        System.out.println(System.lineSeparator() + "Select list to edit:");

        String menuNumber = sc.nextLine();
        int listId = Integer.parseInt(menuNumber);

        //printShoppingList(number);

        //System.out.println("Select entry to edit:");

        // String entryId = sc.nextLine();

        System.out.println("1. Add product to list" + System.lineSeparator() +
                "2. Update list" + System.lineSeparator() +
                "3. Delete List");

        String nextMenu = sc.nextLine();
        int nextMenuChoice = Integer.parseInt(nextMenu);

        if (nextMenuChoice == 1) {
            selectProductCategoryMenu(SHOPPING_LISTS.get(listId - 1));
        }
        if (nextMenuChoice == 2) {
            printShoppingList(listId);
            System.out.println("Select entry to edit:");
            String entryNumber = sc.nextLine();
            int entryId = Integer.parseInt(menuNumber);
            System.out.println("1. Update List Name" + System.lineSeparator() +
                    "2. Update Product Name" + System.lineSeparator() +
                    "3. Delete list Entry " + System.lineSeparator());

            if (nextMenuChoice == 3) {
                System.out.println("1. Delete List" + System.lineSeparator() +
                        "2. Update Product Properties" + System.lineSeparator());
            }

            System.out.println("Select entry to edit:");
            int entryID = Integer.parseInt(sc.nextLine());
        }

    }

    public static void menuSelection(int menuNumber) {

        if (menuNumber == 1) {
            createShoppingList();
        }
        if (menuNumber == 2) {
            getLists();
        }


    }

    public static void printShoppingList(int shoppingListID) {
        double finalCost = 0.00;

        for (ListEntry listEntry : LIST_ENTRIES) {
            if (listEntry.getListId() == shoppingListID) {
                System.lineSeparator();
                System.out.println(listEntry.toString());
                finalCost = finalCost + listEntry.getTotal();
            }
        }
        System.out.println("Your list total is $" + finalCost + System.lineSeparator());

    }


    public static void mapToListEntries(String quantity, String cost, ListEntry listEntry, ShoppingList
            shoppingList, org.example.Model.Product product) {

        listEntry.setListId(shoppingList.getListId());
        listEntry.setProductId(product.getProductId());
        listEntry.setQuantity(Integer.parseInt(quantity));
        listEntry.setCost(Double.parseDouble(cost));
        listEntry.setProductName(product.getProductName());
        listEntry.setCategory("Produce");
        LIST_ENTRIES.add(listEntry);
        listEntry.setListEntryId(LIST_ENTRIES.size());

    }

    public static void loadProductData(int productNumber, ShoppingList shoppingList) {
        ListEntry listEntry = new ListEntry();
        if (productNumber == 1) {
            System.out.println("What is the produce name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            org.example.Model.Product product = new org.example.Model.Product(name);
            PRODUCTS.add(product);
            //set to size to emultate serial

            product.setProductId(PRODUCTS.size());
            mapToListEntries(quantity, cost, listEntry, shoppingList, product);

        }
        if (productNumber == 2) {
            System.out.println("What is the meat name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            org.example.Model.Product product = new org.example.Model.Product(name);
            PRODUCTS.add(product);
            //set to size to emultate serial

            product.setProductId(PRODUCTS.size());
            mapToListEntries(quantity, cost, listEntry, shoppingList, product);


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

            org.example.Model.Product product = new org.example.Model.Product(name);
            PRODUCTS.add(product);
            //set to size to emultate serial
            listEntry.setSlice(slice);

            product.setProductId(PRODUCTS.size());
            mapToListEntries(quantity, cost, listEntry, shoppingList, product);

        }


    }


    public static void displayList(List<Product> products) {
        System.out.println("Your shopping list!: " + System.lineSeparator());

        double finalCost = 0.00;
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
            System.out.println("" + product.getProductName() + " | "
                    + product.getQuantity() + "lbs"
                    + " | $" + product.getCost());
            // System.out.println(product.toString());

        }
        ;

    }
}
