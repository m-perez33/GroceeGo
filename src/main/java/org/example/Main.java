package org.example;

import org.example.DAO.*;
import org.example.Model.ListEntry;
import org.example.Model.Product;
import org.example.Model.GroceryList;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

public class Main {


   /* private static final List<GroceryList> GROCERY_LISTS = new ArrayList<>();
    private static final List<Product> PRODUCTS = new CopyOnWriteArrayList<>();
    private static final List<ListEntry> LIST_ENTRIES = new CopyOnWriteArrayList<>();
    private static final Scanner sc = new Scanner(System.in);*/

    // private GroceryListDao groceryListDao;

    public static void main(String[] args) throws URISyntaxException {

        // private static GroceryListDao groceryListDao;


        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/GroceryListDB");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        GroceryListDao groceryListDao = new JdbcGroceryListDao(dataSource);
        ListEntryDao listEntryDao = new JdbcListEntryDao(dataSource);
        ProductDao productDao = new JdbcProductDao(dataSource);

        System.lineSeparator();
        // mainMenu(groceryListDao);
        GroceryListAdminController controller = new GroceryListAdminController();
        controller.displayMain();
    }

}


