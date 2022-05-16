package com.iti.EShopper.models;

import java.io.Serializable;

public class Category implements Serializable {
    String id;
    String categoryName;
    String categoryNameAr;
    String categoryNameGr;
    String ItemId;
    String categoryImage;

    public Category(String id, String categoryName, String categoryNameAr, String categoryNameGr, String itemId, String categoryImage) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryNameAr = categoryNameAr;
        this.categoryNameGr = categoryNameGr;
        ItemId = itemId;
        this.categoryImage = categoryImage;
    }

    public Category(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNameAr() {
        return categoryNameAr;
    }

    public void setCategoryNameAr(String categoryNameAr) {
        this.categoryNameAr = categoryNameAr;
    }

    public String getCategoryNameGr() {
        return categoryNameGr;
    }

    public void setCategoryNameGr(String categoryNameGr) {
        this.categoryNameGr = categoryNameGr;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
