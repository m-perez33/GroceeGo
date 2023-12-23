package org.example.Model;

import java.time.LocalDate;

public class GroceryList {

    private int listId;

   // private String listName;

    private LocalDate date;


    public GroceryList(LocalDate date){
        //this.listName = listName;
        this.date = date;
    }

    public GroceryList(){

    }

   // public void setListName(String listName) {
   //     this.listName = listName;
   // }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getListId() {
        return listId;
    }

  //  public String getListName() {
   //     return listName;
   // }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return   " " + listId + " " + date;
    }
}
