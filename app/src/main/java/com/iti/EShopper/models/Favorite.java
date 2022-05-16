package com.iti.EShopper.models;

import java.io.Serializable;

public class Favorite implements Serializable {
    String ItemId;
    String Id;
    String date;
    String category;
    String subcategory;

    public Favorite(String itemId, String id, String date, String category, String subcategory) {
        ItemId = itemId;
        Id = id;
        this.date = date;
        this.category = category;
        this.subcategory = subcategory;
    }

    public Favorite(){

    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
}
