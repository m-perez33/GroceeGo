package org.example.DAO;

import org.example.Model.GroceryList;

import java.util.List;

public interface GroceryListDao {
    List<GroceryList> getGroceryLists();

    GroceryList getGroceryListById(int id);

    GroceryList createGrocery(GroceryList groceryList);

    int deleteGroceryList(int id);

}
