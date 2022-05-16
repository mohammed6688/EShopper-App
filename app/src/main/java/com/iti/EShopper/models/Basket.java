package com.iti.EShopper.models;

public class Basket {

    String Id;
    String itemId;
    String date;
    String category;
    String subcategory;
    String size;


    public Basket(String id, String itemId, String date, String category, String subcategory, String size) {
        Id = id;
        this.itemId = itemId;
        this.date = date;
        this.category = category;
        this.subcategory = subcategory;
        this.size = size;
    }

    public Basket(){

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
