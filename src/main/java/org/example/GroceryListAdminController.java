package org.example;

import org.example.Model.GroceryList;
import org.example.Model.ListEntry;
import org.example.Model.Product;
import org.example.Services.GroceryListService;
import org.example.Services.ListEntryService;
import org.example.Services.ProductService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class GroceryListAdminController {

    private GroceryListService groceryListService = new GroceryListService();

    private ListEntryService listEntryService = new ListEntryService();

    private ProductService productService = new ProductService();

    private static final Scanner sc = new Scanner(System.in);

    public GroceryListAdminController() {

    }

    private void handleGroceryLists() {
        groceryListService.getGroceryLists();

    }

    public void displayMain() {

        System.lineSeparator();
        mainMenu();

    }

    public void mainMenu() {

        List<GroceryList> groceryLists = groceryListService.getGroceryLists();

        if (groceryLists.size() == 0) {//if no grocery lists immediately load create list product menu
            System.out.println("Create new grocery list" + System.lineSeparator());
            createGroceryList();

        } else {//otherwise give options
            System.out.println("1. Create new grocery list");
            System.out.println("2. Edit existing grocery lists" + System.lineSeparator());
            System.out.println("Please make your selection: ");
            do {// do loop to make sure inputs are correct
                try {
                    String menuNumber = sc.nextLine();
                    int menuNumberParsed = Integer.parseInt(menuNumber);
                    if (menuNumberParsed > 2) {
                        System.out.println("Incorrect menu number. Enter either 1 or 2");
                    } else {
                        mainMenuSelection(Integer.parseInt(menuNumber));
                    }
                } catch (Exception e) {
                    System.out.println("Couldn't parse input, please try again");
                }
            } while (true);
        }

    }

    public void mainMenuSelection(int menuNumber) {
        if (menuNumber == 1) {
            createGroceryList();
        } else if (menuNumber == 2) {
            getLists();
        } else {
            mainMenu();
        }
    }

    public void createGroceryList() {
        //create list

        LocalDate date = LocalDate.now();
        GroceryList groceryList = new GroceryList(date);
        GroceryList createdList = groceryListService.createGrocery(groceryList);

        addProductMenu(createdList);
    }

    public void addProductMenu(GroceryList groceryList) {

        System.lineSeparator();
        System.out.println("Please select product category: ");
        System.out.println("1. Add product measured per lb. (produce, deli, meat)");
        System.out.println("2. Add products measured per item (dry goods, toiletries, beverages)");
        do {// do loop to make sure inputs are correct
            try {
                String menuNumber = sc.nextLine();
                int menuNumberParsed = Integer.parseInt(menuNumber);
                if (menuNumberParsed > 2) {
                    System.out.println("Incorrect menu number. Enter either 1 or 2");
                } else {
                    loadProductData(Integer.parseInt(menuNumber), groceryList);
                    subAddProductMenu(groceryList);
                }
            } catch (Exception e) {
                System.out.println("Couldn't parse input, please try again bro");
            }

        } while (true);
//


    }

    public void subAddProductMenu(GroceryList groceryList) {
        //This menu accepts a grocery list to edit
        System.lineSeparator();
        System.out.println("1. Add another product");
        System.out.println("2. Print grocery list");
        System.out.println("3. Return to Main menu");
        do {// do loop to make sure inputs are correct
            try {
                String menuNumber = sc.nextLine();
                int menuNumberParsed = Integer.parseInt(menuNumber);
                if (menuNumberParsed > 2) {
                    System.out.println("Incorrect menu number. Try again");
                } else {
                    subMenuSelection(Integer.parseInt(menuNumber), groceryList);
                    mainMenu();

                }
            } catch (Exception e) {
                System.out.println("Couldn't parse input, please try again bro");
            }

            // String menuNumber = sc.nextLine();
        } while (true) ;
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

        List<GroceryList> groceryLists = groceryListService.getGroceryLists();
        int count = 1;
        //print out all of users lists
        for (GroceryList groceryList : groceryLists) {
            System.out.println("" + count + ". " + "Grocery List " + groceryList.toString());
            count++;
        }

        System.out.println(System.lineSeparator() + "Select list to edit:");

        String menuNumber = sc.nextLine();
        int listId = Integer.parseInt(menuNumber);
        subListMenu(listId);
    }

    public void listEntryMenu(int listId) {

        List<ListEntry> myList = new CopyOnWriteArrayList<>();

        //List<ListEntry> listEntries = listEntryService.getListEntries();

        List<ListEntry> listEntriesByID = listEntryService.getListEntriesByListId(listId);

        if(listEntriesByID.size() == 0){
            System.out.println("no entries: please add to the list");
            subListMenu(listId);
        }else{
        for (ListEntry listEntry : listEntriesByID) {//loop through and print entries tied to list id
            // if (listEntry.getListId() == listId) {

            System.out.println(listEntry.toString());}
            //myList.add(listEntry);
            //  }
        }

        System.out.println("Enter ID to edit:");
        String entryNumber = sc.nextLine();
        int entryId = Integer.parseInt(entryNumber);

        System.out.println("1. Update List Entry");
        System.out.println("2. Delete");

        String listMenuNumber = sc.nextLine();
        int menuChoice = Integer.parseInt(listMenuNumber);

        if (menuChoice == 1) {
            for (ListEntry listEntry : listEntriesByID) {
                if (listEntry.getListEntryId() == entryId) {
                    System.out.println("Please update product properties for: " + listEntry.getProductName());
                    System.out.println("Enter new quantity and cost, separated by comma ex: 10,9: ");

                    String str = sc.nextLine();
                    List<String> updates = Arrays.asList(str.split(","));
                    listEntry.setQuantity(Double.parseDouble(updates.get(0)));
                    listEntry.setCost(Double.parseDouble(updates.get(1)));

                    listEntryService.updateListEntry(listEntry);

                    //since its a put request, dont need to retrieve data
                   // ListEntry updatedEntry = listEntryService.getListEntryById(listEntry.getListEntryId());

                    System.out.println("Updated value:" + listEntry.toString());
                    mainOrExitMenu();
                }
            }
        }
        if (menuChoice == 2) {
            for (ListEntry listEntry : listEntriesByID) {
                if (listEntry.getListEntryId() == entryId) {

                    listEntryService.deleteListEntry(entryId);

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
        System.out.println("4. Print List");

        int nextMenuChoice = 0;
        do {//do loop to make sure inputs are correct
            try {
                String nextMenu = sc.nextLine();
                nextMenuChoice = Integer.parseInt(nextMenu);

            } catch (Exception e) {
                System.out.println("Couldn't parse input, please try again");
            }
            if (nextMenuChoice > 4) {
                System.out.println("Input must be between 1 and 4, try again");
            }
            if (nextMenuChoice == 1) { //if 1  go and add more products to the list
                addProductMenu(groceryListService.getGroceryListById(listId));
                System.out.println(groceryListService.getGroceryListById(listId));
            }
            if (nextMenuChoice == 2) { //print out all list entries for a grocery list
                listEntryMenu(listId);
            }
            if (nextMenuChoice == 3) {
                System.out.println("" + listId);
                ;
                for (ListEntry listEntry : listEntryService.getListEntriesByListId(listId)) {
                    listEntryService.deleteListEntry(listEntry.getListEntryId());
                }
                groceryListService.deleteGroceryList(listId);

                System.out.println("List " + "7" + " deleted");
                mainMenu();
                System.out.println();
            }
            if (nextMenuChoice == 4) {
                printGroceryList(listId);

            }
        } while (true);

    }

    public void printGroceryList(int groceryListID) {
        double finalCost = 0.00;

        for (ListEntry listEntry : listEntryService.getListEntriesByListId(groceryListID)) {
            // if (listEntry.getListId() == groceryListID) {
            System.lineSeparator();
            System.out.println(listEntry.toString());
            finalCost = finalCost + listEntry.getTotal();
            // }
        }
        System.out.println("Your list total is $" + finalCost + System.lineSeparator());

        mainOrExitMenu();

    }

    public void mainOrExitMenu() {

        System.out.println("1. Return to Main Menu");
        System.out.println("2. Exit");

        do {
            //input = false;
            try {
                String menuNumber = sc.nextLine();
                int menuNumberParsed = Integer.parseInt(menuNumber);
                if (menuNumberParsed == 1) {
                    mainMenu();
                } else if (menuNumberParsed == 2) {
                    System.exit(0);
                }
                if (menuNumberParsed > 2) {
                    System.out.println("Incorrect menu number. Enter either 1 or 2");
                } else {
                    mainMenuSelection(Integer.parseInt(menuNumber));
                }
            } catch (Exception e) {
                System.out.println("Couldn't parse input, please try again");
            }

        } while (true);


    }

    public void mapToListEntries(String quantity, String cost, GroceryList
            groceryList, Product product, int category) {


        ListEntry newEntry = new ListEntry();
        newEntry.setProductId(product.getProductId());
        newEntry.setListId(groceryList.getListId());
        newEntry.setCost(Double.parseDouble(cost));
        newEntry.setQuantity(Double.parseDouble(quantity));
        newEntry.setCategory(category);
        listEntryService.createListEntry(newEntry);


    }

    public void addNewProduct(String name, String quantity, String cost,
                              GroceryList groceryList, int productNumber) {

        Product newProduct = new Product(name);
        Product createdProduct = productService.createProduct(newProduct);

        mapToListEntries(quantity, cost, groceryList, createdProduct, productNumber);
    }

    public void loadProductData(int category, GroceryList groceryList) {
        //these promps set the values for a list entry and add to list entry list
        List<Product> retrievedProducts = productService.getProducts();

        ListEntry listEntry = new ListEntry();
        if (category == 1) {
            System.out.println("What is the product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            boolean found = false;

            if (retrievedProducts.size() != 0) {
                // if products exist, check to see if they match the product being added.
                //existing product should be used instead of adding another entry to DB
                for (Product product : retrievedProducts) {

                    if (product.getProductName().equalsIgnoreCase(name)) {
                        found = true;
                        System.out.println("true");
                        mapToListEntries(quantity, cost, groceryList, product, category);
                    }
                }
            } //if empty just add new product
            if (found == false) {
                addNewProduct(name, quantity, cost, groceryList, category);
            }


        }
        if (category == 2) {
            System.out.println("What is the product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per item:");
            String cost = sc.nextLine();

            //Product newProduct = new Product();
            boolean found = false;

            if (retrievedProducts.size() != 0) {
                // if products exist, check to see if they match the product being added.
                //existing product should be used instead of adding another entry to DB
                for (Product product : retrievedProducts) {

                    if (product.getProductName().equalsIgnoreCase(name)) {
                        found = true;
                        System.out.println("true");
                        mapToListEntries(quantity, cost, groceryList, product, category);
                    }
                }
            } //if empty just add new product
            if (found == false) {
                addNewProduct(name, quantity, cost, groceryList, category);
            }
        }
    }
}


