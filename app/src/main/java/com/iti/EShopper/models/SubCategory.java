package com.iti.EShopper.models;

import java.io.Serializable;

public class SubCategory implements Serializable {
    String id;
    String subCategoryName;
    String subCategoryNameAr;
    String subCategoryNameGr;
    String categoryId;
    String subCategoryImage;


    public SubCategory(String id, String subCategoryName, String subCategoryNameAr, String subCategoryNameGr, String categoryId, String subCategoryImage) {
        this.id = id;
        this.subCategoryName = subCategoryName;
        this.subCategoryNameAr = subCategoryNameAr;
        this.subCategoryNameGr = subCategoryNameGr;
        this.categoryId = categoryId;
        this.subCategoryImage = subCategoryImage;
    }

    public SubCategory() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryNameAr() {
        return subCategoryNameAr;
    }

    public void setSubCategoryNameAr(String subCategoryNameAr) {
        this.subCategoryNameAr = subCategoryNameAr;
    }

    public String getSubCategoryNameGr() {
        return subCategoryNameGr;
    }

    public void setSubCategoryNameGr(String subCategoryNameGr) {
        this.subCategoryNameGr = subCategoryNameGr;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryImage() {
        return subCategoryImage;
    }

    public void setSubCategoryImage(String subCategoryImage) {
        this.subCategoryImage = subCategoryImage;
    }
}

