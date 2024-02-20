package org.example;

import org.example.Model.GroceryList;
import org.example.Model.ListEntry;
import org.example.Model.Product;
import org.example.Services.GroceryListService;
import org.example.Services.ListEntryService;
import org.example.Services.ProductService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GroceryListAdminController {

    private final GroceryListService groceryListService = new GroceryListService();

    private final ListEntryService listEntryService = new ListEntryService();

    private final ProductService productService = new ProductService();

    private static final Scanner sc = new Scanner(System.in);

    public GroceryListAdminController() {

    }

    public void displayMain() {

        System.lineSeparator();
        mainMenu();

    }

    public int menuNumberCheck(int menu) {//returns the menu selection after checking for valid input
        boolean valid = true;
        int menuNumberParsed = 0;
        do {// do loop to make sure inputs are correct
            try {
                String menuNumber = sc.nextLine();
                menuNumberParsed = Integer.parseInt(menuNumber);
                if (menuNumberParsed > menu) {
                    System.out.println("Incorrect menu number. Enter number between 1 and " + menu);
                } else {
                    valid = false;
                }
            } catch (Exception e) {
                System.out.println("Couldn't parse input, please try again");
            }
        } while (valid);
        return menuNumberParsed;

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

            int menuCount = 2;
            int menuNumber = menuNumberCheck(menuCount);

            if (menuNumber == 1) {
                createGroceryList();
            } else if (menuNumber == 2) {
                getLists();
            }
        }
    }

    public void createGroceryList() {

        LocalDate date = LocalDate.now();
        GroceryList groceryList = new GroceryList(date);
        GroceryList createdList = groceryListService.createGrocery(groceryList);

        //once list is created, the menu to add product to list will then populate
        addProductMenu(createdList);
    }

    public void addProductMenu(GroceryList groceryList) {
        //This menu accepts a grocery list to add products too

        System.lineSeparator();
        System.out.println("Please select product category: ");
        System.out.println("1. Add product measured per lb. (produce, deli, meat)");
        System.out.println("2. Add products measured per item (dry goods, toiletries, beverages)");

        int menuCount = 2;
        loadProductData(menuNumberCheck(menuCount), groceryList);//load product data prompts user to add products to a list

        //after products are added the menu below provides the next choices
        System.lineSeparator();
        System.out.println("1. Add another product");
        System.out.println("2. Print grocery list");
        System.out.println("3. Return to Main menu");

        int subMenuCount = 3;
        int menuNumber = menuNumberCheck(subMenuCount);

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

    public void loadProductData(int category, GroceryList groceryList) {
        //these prompts set the values for a list entry and add to list entry list

       // ListEntry listEntry = new ListEntry();
        if (category == 1) {//category one is for products with quantity by lbs.
            System.out.println("What is the product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity in lbs:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per lb.:");
            String cost = sc.nextLine();

            checkForProduct(groceryList, name, quantity, cost, category);

        }
        if (category == 2) {//category 2 is for products with quantity by item
            System.out.println("What is the product name:");
            String name = sc.nextLine();
            System.out.println("What is the quantity:");
            String quantity = sc.nextLine();
            System.out.println("What is the cost per item:");
            String cost = sc.nextLine();

            checkForProduct(groceryList, name, quantity, cost, category);

        }
    }

    public void checkForProduct(GroceryList groceryList, String name, String quantity, String cost, int category) {
        // if products exist, check to see if they match the product being added. Existing product should be used instead of adding another entry to DB

        List<Product> retrievedProducts = productService.getProducts();

        boolean found = false;

        if (retrievedProducts.size() != 0) {
            // if products exist, check to see if they match the product being added. Existing product should be used instead of adding another entry to DB
            for (Product product : retrievedProducts) {
                if (product.getProductName().equalsIgnoreCase(name)) {//if product is found, use that product when mapping to list entries
                    found = true;
                    mapToListEntries(quantity, cost, groceryList, product, category);
                }
            }
        } //if it does not exist add new product
        if (!found) {

            //add new product to product table
            Product newProduct = new Product(name);
            Product createdProduct = productService.createProduct(newProduct);

            //after product has been added it is mapped to a list entry
            mapToListEntries(quantity, cost, groceryList, createdProduct, category);
            // addNewProduct(name, quantity, cost, groceryList, category);
        }
    }


    public void getLists() {
        //print out all lists
        List<GroceryList> groceryLists = groceryListService.getGroceryLists();
        int count = 1;

        for (GroceryList groceryList : groceryLists) {
            System.out.println("ID:" + groceryList.getListId() + ". " + "Grocery List" + groceryList.toString());
            count++;
        }


        //menu count holds the number of menu items
        int menuCount = count - 1;

        System.out.println(System.lineSeparator() + "Select List ID to edit:");
        boolean exists = true;
        int listId = 0;
        do {//loop to make sure proper list entry ids are entered
            try {
                String entryNumber = sc.nextLine();
                listId = Integer.parseInt(entryNumber);

                for (GroceryList groceryList : groceryLists) {
                    if (groceryList.getListId() == listId) {
                        exists = false;
                    }
                    editListMenu(listId);
                }
                if (exists) {
                    System.out.println("ID doesn't exist try again");
                }
            } catch (Exception e) {
                System.out.println("ID doesn't exist try again");
            }
        } while (exists);


    }

    public void listEntryMenu(int listId) {
        //prints list entries for user to select to edit
        List<ListEntry> listEntriesByID = listEntryService.getListEntriesByListId(listId);

        if (listEntriesByID.size() == 0) {
            System.out.println("No entries: please add to the list");//goes to sublist menu
            editListMenu(listId);
        } else {
            for (ListEntry listEntry : listEntriesByID) {//loop through and print entries tied to list id
                System.out.println(listEntry.toString());
            }
        }

        System.out.println("Enter ID to edit:");
        boolean exists = true;
        int entryId = 0;

        do {//loop to make sure proper list entry ids are entered
            try {
                String entryNumber = sc.nextLine();
                entryId = Integer.parseInt(entryNumber);

                for (ListEntry listEntry : listEntriesByID) {
                    if (listEntry.getListEntryId() == entryId) {
                        exists = false;
                    }
                }
                if (exists) {
                    System.out.println("ID doesn't exist try again");
                }
            } catch (Exception e) {
                System.out.println("ID doesn't exist try again");
            }
        } while (exists);


        System.out.println("1. Update List Entry");
        System.out.println("2. Delete");

        int menuCount = 2;
        int menuNumber = menuNumberCheck(menuCount);

        if (menuNumber == 1) {
            //update entry. quantity or cost only
            for (ListEntry listEntry : listEntriesByID) {
                if (listEntry.getListEntryId() == entryId) {
                    System.out.println("Please update product properties for: " + listEntry.getProductName());
                    System.out.println("Enter new quantity and cost, separated by comma ex: 10,9: ");
                    //add check for regex pattern here

                    String str = sc.nextLine();
                    List<String> updates = Arrays.asList(str.split(","));
                    listEntry.setQuantity(Double.parseDouble(updates.get(0)));
                    listEntry.setCost(Double.parseDouble(updates.get(1)));

                    listEntryService.updateListEntry(listEntry);

                    System.out.println("Updated value:" + listEntry.toString());
                    mainOrExitMenu();
                }
            }
        }
        if (menuNumber == 2) {
            for (ListEntry listEntry : listEntriesByID) {
                if (listEntry.getListEntryId() == entryId) {

                    listEntryService.deleteListEntry(entryId);

                    System.out.println("List Entry deleted");//return to entry menu
                    mainMenu();
                }
            }
        }
    }

    public void editListMenu(int listId) {
        //provides menu to edit list
        System.out.println("1. Add product to list");
        System.out.println("2. Update list");
        System.out.println("3. Delete List");
        System.out.println("4. Print List");

        int menuCount = 4;
        int menuNumber = menuNumberCheck(menuCount);


        if (menuNumber == 1) { //if 1  go and add more products to the list
            addProductMenu(groceryListService.getGroceryListById(listId));
            System.out.println(groceryListService.getGroceryListById(listId));
        }
        if (menuNumber == 2) { //print out all list entries for a grocery list
            listEntryMenu(listId);
        }
        if (menuNumber == 3) {//delete list
            System.out.println("" + listId);
            ;
            for (ListEntry listEntry : listEntryService.getListEntriesByListId(listId)) {
                listEntryService.deleteListEntry(listEntry.getListEntryId());
            }
            groceryListService.deleteGroceryList(listId);

            System.out.println("List " + listId + " deleted");
            System.lineSeparator();
            mainOrExitMenu();
        }
        if (menuNumber == 4) {
            printGroceryList(listId);

        }
    }

    public void printGroceryList(int groceryListID) {//prints list and total cost
        double finalCost = 0.00;

        for (ListEntry listEntry : listEntryService.getListEntriesByListId(groceryListID)) {
            // if (listEntry.getListId() == groceryListID) {
            System.lineSeparator();
            System.out.println(listEntry.toString());
            finalCost = finalCost + listEntry.getTotal();

        }
        System.out.println("Your list total is $" + finalCost + System.lineSeparator());

        mainOrExitMenu();
    }

    public void mainOrExitMenu() {
        //menu used at select points to give user option to exit or return to main menu

        System.out.println("1. Return to Main Menu");
        System.out.println("2. Exit");

        int menuCount = 2;
        int menuNumber = menuNumberCheck(menuCount);

        if (menuNumber == 1) {
            mainMenu();
        } else if (menuNumber == 2) {
            System.exit(0);
        }
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


}


