package org.example.Model;

import java.time.LocalDate;

public class ShoppingList {

    private int listId;

    private String listName;

    private LocalDate date;


    public ShoppingList(String listName) {
        this.listName = listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return listName;
    }
}
