package org.example;

import org.example.DAO.GroceryListDao;
import org.example.DAO.ListEntryDao;
import org.example.DAO.ProductDao;
import org.example.Model.GroceryList;
import org.example.Model.ListEntry;
import org.example.Model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class GroceryListAdminController {

    private GroceryListDao groceryListDao;

    private ListEntryDao listEntryDao;

    private ProductDao productDao;
    private static final List<GroceryList> GROCERY_LISTS = new ArrayList<>();
    private static final List<Product> PRODUCTS = new CopyOnWriteArrayList<>();
    private static final List<ListEntry> LIST_ENTRIES = new CopyOnWriteArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public GroceryListAdminController(GroceryListDao groceryListDao, ListEntryDao listEntryDao, ProductDao productDao) {
        this.groceryListDao = groceryListDao;
        this.listEntryDao = listEntryDao;
        this.productDao = productDao;
    }


    // private GroceryListDao groceryListDao;

    public void displayMain() {


        System.lineSeparator();
        mainMenu();

    }

    public void mainMenu() {

        if (GROCERY_LISTS.size() == 0) {//if no grocery lists immediately load create list product menu
            System.out.println("Create new grocery list" + System.lineSeparator());
            createGroceryList();

        } else {//otherwise give options
            System.out.println("1. Create new grocery list");
            System.out.println("2. Edit existing grocery lists" + System.lineSeparator());
            System.out.println("Please make your selection: ");

            String menuNumber = sc.nextLine();
            mainMenuSelection(Integer.parseInt(menuNumber));
        }
    }

    public void mainMenuSelection(int menuNumber) {
        if (menuNumber == 1) {
            createGroceryList();
        }
        if (menuNumber == 2) {
            getLists();
        }
    }

    public void createGroceryList() {
        //create list

        //groceryListDao.createGrocery();
        LocalDate date = LocalDate.now();

        GroceryList groceryList = new GroceryList(date);
        GroceryList createdList = groceryListDao.createGrocery(groceryList);


        GROCERY_LISTS.add(createdList);

        //temp to set list ids. can pull from dao later
        groceryList.setListId(GROCERY_LISTS.indexOf(groceryList) + 1);
        addProductMenu(createdList);
    }

    public void addProductMenu(GroceryList groceryList) {

        System.lineSeparator();
        System.out.println("Please select product category: ");
        System.out.println("1. Add product measured per lb. (produce, deli, meat)");
        System.out.println("2. Add products measured per item (dry goods, toiletries, beverages)");

        String menuNumber = sc.nextLine();
        loadProductData(Integer.parseInt(menuNumber), groceryList);

        subAddProductMenu(groceryList);
    }

    public void subAddProductMenu(GroceryList groceryList) {
        //This menu accepts a grocery list to edit
        System.lineSeparator();
        System.out.println("1. Add another product");
        System.out.println("2. Print grocery list");
        System.out.println("3. Return to Main menu");

        String menuNumber = sc.nextLine();
        subMenuSelection(Integer.parseInt(menuNumber), groceryList);

        mainMenu();
    }

    public void subMenuSelection(int menuNumber, GroceryList groceryList) {

        if (menuNumber == 1) {
            addProductMenu(groceryList);
        }
        if (menuNumber == 2) {
            printGroceryList(groceryList.getListId());
        }
        if (menuNumber == 3) {
            mainMenu();
        }
    }

    public void getLists() {

        int count = 1;
        //print out all of users lists
        for (GroceryList groceryList : GROCERY_LISTS) {
            System.out.println("" + count + ". " + "Grocery List " + count + groceryList.toString());
            count++;
        }

        System.out.println(System.lineSeparator() + "Select list to edit:");

        String menuNumber = sc.nextLine();
        int listId = Integer.parseInt(menuNumber);
        subListMenu(listId);
    }

    public void listEntryMenu(int listId) {

        List<ListEntry> myList = new CopyOnWriteArrayList<>();

        for (ListEntry listEntry : LIST_ENTRIES) {//loop through and print entries tied to list id
            if (listEntry.getListId() == listId) {
                System.out.println("" + "." + listEntry.toString());
                myList.add(listEntry);
            }
        }

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
                    System.out.println("Please update product properties for: " + listEntry.getProductName());
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
                    System.out.println("List Entry deleted");//return to entry menu
                    mainMenu();
                }
            }
        }
    }

    public void subListMenu(int listId) {
        System.out.println("1. Add product to list");
        System.out.println("2. Update list");
        System.out.println("3. Delete List");

        String nextMenu = sc.nextLine();
        int nextMenuChoice = Integer.parseInt(nextMenu);

        if (nextMenuChoice == 1) { //if 1 go and add more products to the list
            addProductMenu(GROCERY_LISTS.get(listId - 1));
        }
        if (nextMenuChoice == 2) { //print out all list entries for a grocery list
            listEntryMenu(listId);
        }
        if (nextMenuChoice == 3) {
            System.out.println("3?");
            GROCERY_LISTS.remove(listId - 1);
            System.out.println("List deleted");
            mainMenu();
            System.out.println();
        }
    }

    public void printGroceryList(int groceryListID) {
        double finalCost = 0.00;

        for (ListEntry listEntry : LIST_ENTRIES) {
            if (listEntry.getListId() == groceryListID) {
                System.lineSeparator();
                System.out.println(listEntry.toString());
                finalCost = finalCost + listEntry.getTotal();
            }
        }
        System.out.println("Your list total is $" + finalCost + System.lineSeparator());
    }

    public void mapToListEntries(String quantity, String cost, ListEntry listEntry, GroceryList
            groceryList, Product product, int category) {

        listEntry.setListId(groceryList.getListId());
        listEntry.setProductId(product.getProductId());
        listEntry.setQuantity(Double.parseDouble(quantity));
        listEntry.setCost(Double.parseDouble(cost));
        listEntry.setProductName(product.getProductName());
        //listEntry.setCategory(category);

        LIST_ENTRIES.add(listEntry);
        listEntry.setListEntryId(LIST_ENTRIES.size());
        System.out.println("its working");
    }

    public void addNewProduct(String name, String quantity, String cost, ListEntry listEntry,
                              GroceryList groceryList, int productNumber) {

        Product newProduct = new Product(name);
        Product createdProduct = productDao.createProduct(newProduct);
        System.out.println(createdProduct.getProductName() + " " +createdProduct.getProductId());
        newProduct.setProductId(PRODUCTS.size() + 1);
        PRODUCTS.add(newProduct);

        ListEntry newEntry = new ListEntry();
        newEntry.setProductId(createdProduct.getProductId());
        newEntry.setListId(groceryList.getListId());
        newEntry.setCost(Double.parseDouble(cost));
        newEntry.setQuantity(Double.parseDouble(quantity));
        listEntryDao.createListEntry(newEntry);

        mapToListEntries(quantity, cost, listEntry, groceryList, newProduct, productNumber);
    }

    public void loadProductData(int productNumber, GroceryList groceryList) {
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

                        mapToListEntries(quantity, cost, listEntry, groceryList, product, productNumber);
                    } else {

                        addNewProduct(name, quantity, cost, listEntry, groceryList, productNumber);
                    }
                }
            } else {
                addNewProduct(name, quantity, cost, listEntry, groceryList, productNumber);
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
                        mapToListEntries(quantity, cost, listEntry, groceryList, product, productNumber);

                    } else {

                        addNewProduct(name, quantity, cost, listEntry, groceryList, productNumber);
                    }
                }
            } else {
                addNewProduct(name, quantity, cost, listEntry, groceryList, productNumber);
            }
        }
    }
}


