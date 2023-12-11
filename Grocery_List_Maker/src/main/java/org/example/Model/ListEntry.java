package org.example.Model;

public class ListEntry {

    private int listEntryId;

    private int listId;

    private int productId;

    private int quantity;

    private double cost;

    private  String slice;

    private String category;

    private String productName;
    //pull from join



    public ListEntry() {
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setListEntryId(int listEntryId) {
        this.listEntryId = listEntryId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setSlice(String slice) {
        this.slice = slice;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }
    public int getListEntryId() {
        return listEntryId;
    }

    public int getListId() {
        return listId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public double getTotal(){
        return cost * quantity;
    }

    public String getSlice() {
        return slice;
    }

    @Override
    public String toString() {

        return listEntryId + "." +"Product: " + productName + System.lineSeparator() +
                "Quantity: " + quantity + " lbs" + System.lineSeparator() +
                "Cost per pound: " + cost + System.lineSeparator() +
                "Total cost: $" + getTotal() + System.lineSeparator()  ;
    }
}
