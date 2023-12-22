package org.example;

import org.example.Model.ListEntry;
import org.example.Model.Product;
import org.example.Model.ShoppingList;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    private static final List<ShoppingList> SHOPPING_LISTS = new ArrayList<>();

    private static final List<Product> PRODUCTS = new CopyOnWriteArrayList<>();

    private static final List<ListEntry> LIST_ENTRIES = new CopyOnWriteArrayList<>();

    private static ShoppingList shoppinglist;


    private static final Scanner sc = new Scanner(System.in);

    //private static String inputNumber = sc.nextLine();

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

            String menuNumber = sc.nextLine();

            menuSelection(Integer.parseInt(menuNumber));
        }


    }

    public static void editExistingListSubMenu() {

        System.out.println("1. Add product to list");
        System.out.println("2. Update list");
        System.out.println("3. Delete List");
        ;

        String menuNumber = sc.nextLine();
        int number = Integer.parseInt(menuNumber);
    }

    public static void updateProduct(int number) {
        if (number == 1) {

        }
    }

    public static void createShoppingList() {

        //create list
        // System.out.println("Please name your list: ");
        // String listname = sc.nextLine();

        LocalDate date = LocalDate.now();

        ShoppingList shoppingList = new ShoppingList(date);

        SHOPPING_LISTS.add(shoppingList);

        //temp to set lists. can pull from dao later
        shoppingList.setListId(SHOPPING_LISTS.indexOf(shoppingList) + 1);

        addProductMenu(shoppingList);

    }

    public static void addProductMenu(ShoppingList shoppingList) {

        System.lineSeparator();
        System.out.println("Please select product category: ");
        System.out.println("1. Add product measured per lb. (produce, deli, meat)");
        System.out.println("2. Add products measured per item (dry goods, toiletries, beverages)");

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
            addProductMenu(shoppingList);
        }
        if (menuNumber == 2) {
            printShoppingList(shoppingList.getListId());
        }
        if (menuNumber == 3) {
            mainMenu();
        }

    }

    public static void getLists() {

        int count = 1;
        for (ShoppingList shoppingList : SHOPPING_LISTS) {
            System.out.println("" + count + ". " + "Shopping List " + count + shoppingList.toString());
            count++;
        }
        System.out.println(System.lineSeparator() + "Select list to edit:");

        String menuNumber = sc.nextLine();
        int listId = Integer.parseInt(menuNumber);


        System.out.println("1. Add product to list" + System.lineSeparator() +
                "2. Update list" + System.lineSeparator() +
                "3. Delete List");

        String nextMenu = sc.nextLine();
        int nextMenuChoice = Integer.parseInt(nextMenu);

        if (nextMenuChoice == 1) {
            System.out.println("tough");

            addProductMenu(SHOPPING_LISTS.get(listId - 1));
        }
        if (nextMenuChoice == 2) {
            //
            //print out all list entries for a shopping list

            List<ListEntry> myList = new CopyOnWriteArrayList<>();

            for (ListEntry listEntry : LIST_ENTRIES) {
                System.out.println(listEntry.getListId());
                ;
                System.out.println(listId);
                if (listEntry.getListId() == listId) {
                    //System.lineSeparator();
                    System.out.println("" + "." + listEntry.toString());
                    //add to lsit
                    myList.add(listEntry);
                }
            }


            // ListEntry entryToUpdate = ListEntry.get(listId-1);
            //printShoppingList(listId);

            System.out.println("Enter ID to edit:");
            String entryNumber = sc.nextLine();
            int entryId = Integer.parseInt(entryNumber);

            System.out.println("1. Update List Entry");
            System.out.println("2. Delete");

            String listMenuNumber = sc.nextLine();
            int menuChoice = Integer.parseInt(listMenuNumber);

            if (menuChoice == 1) {
                for (ListEntry listEntry : myList) {
                    if (listEntry.getListEntryId() == entryId) {
                        System.out.println("Please update item properties for: " + listEntry.getProductName());
                        System.out.println("Enter new quantity and cost, separated by comma ex: 10,9: ");
                        String str = sc.nextLine();
                        List<String> updates = Arrays.asList(str.split(","));
                        listEntry.setQuantity(Double.parseDouble(updates.get(0)));
                        listEntry.setCost(Double.parseDouble(updates.get(1)));

                        System.out.println("Updated value:" + listEntry.toString());

                    }


                }
            }
            if (menuChoice == 2) {
                for (ListEntry listEntry : myList) {
                    if (listEntry.getListEntryId() == entryId) {

                        LIST_ENTRIES.remove(listEntry);
                        System.out.println("List Entry deleted");
                        //return to entry menu
                        mainMenu();
                        // }
                    }
                }

            }


           // System.out.println("Select entry to edit:");
           // int entryID = Integer.parseInt(sc.nextLine());

        }


        if (nextMenuChoice == 3) {
            System.out.println("3?");
            SHOPPING_LISTS.remove(listId - 1);
            System.out.println("List deleted");
            mainMenu();
            System.out.println();
            //  "2. Update Product Properties" + System.lineSeparator());
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
            shoppingList, Product product, int category) {

        listEntry.setListId(shoppingList.getListId());
        listEntry.setProductId(product.getProductId());
        listEntry.setQuantity(Double.parseDouble(quantity));
        listEntry.setCost(Double.parseDouble(cost));
        listEntry.setProductName(product.getProductName());
        listEntry.setCategory(category);

        LIST_ENTRIES.add(listEntry);
        listEntry.setListEntryId(LIST_ENTRIES.size());
        System.out.println("its working");
    }

    public static void addNewProduct(String name, String quantity, String cost, ListEntry listEntry,
                                     ShoppingList shoppingList, int productNumber) {

        Product newProduct = new Product(name);
        newProduct.setProductId(PRODUCTS.size() + 1);
        PRODUCTS.add(newProduct);
        mapToListEntries(quantity, cost, listEntry, shoppingList, newProduct, productNumber);
    }

    public static void loadProductData(int productNumber, ShoppingList shoppingList) {
        //these promps set the values for a list entry and add to list entry list

        ListEntry listEntry = new ListEntry();
        if (productNumber == 1) {
            System.out.println("What is the product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            if (!PRODUCTS.isEmpty()) {

                //Product newProduct = null;
                for (Product product : PRODUCTS) {
                    if (product.getProductName().equalsIgnoreCase(name)) {
                        mapToListEntries(quantity, cost, listEntry, shoppingList, product, productNumber);
                    } else {
                        addNewProduct(name, quantity, cost, listEntry, shoppingList, productNumber);
                    }
                }
            } else {
                addNewProduct(name, quantity, cost, listEntry, shoppingList, productNumber);

            }
        }
        if (productNumber == 2) {
            System.out.println("What is the product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per item:");
            String cost = sc.nextLine();

            //Product newProduct = new Product();
            if (!PRODUCTS.isEmpty()) {

                for (Product product : PRODUCTS) {
                    if (product.getProductName().equalsIgnoreCase(name)) {
                        System.out.println(product.getProductName());
                        mapToListEntries(quantity, cost, listEntry, shoppingList, product, productNumber);

                    } else {

                        addNewProduct(name, quantity, cost, listEntry, shoppingList, productNumber);
                    }
                }
            } else {
                addNewProduct(name, quantity, cost, listEntry, shoppingList, productNumber);

            }
            // Product product = new org.example.Model.Product(name);
            //PRODUCTS.add(newProduct);

            //set to size to emultate serial

            // newProduct.setProductId(PRODUCTS.size());


        }


    }
}

  /*  public static void displayList(List<Product> products) {
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
*/