package com.iti.EShopper.models;

import java.io.Serializable;

public class Item implements Serializable {
    String id;
    String category;
    String subCategory;
    String Price;
    String Title;
    String CreationDate;
    String LastMod;
    String Discount;
    String Ram;
    String Rom;
    String Battery;
    String Cpu;
    String Camera;
    String FrontCamera;
    String Images;
    String More;
    int AvailableUnits;
    int ProductCode;


    public Item(){

    }

    public Item(String id, String category, String subCategory, String price, String title, String creationDate, String lastMod, String discount, String ram, String rom, String battery, String cpu, String camera, String frontCamera, String images, String more, int availableUnits, int productCode) {
        this.id = id;
        this.category = category;
        this.subCategory = subCategory;
        Price = price;
        Title = title;
        CreationDate = creationDate;
        LastMod = lastMod;
        Discount = discount;
        Ram = ram;
        Rom = rom;
        Battery = battery;
        Cpu = cpu;
        Camera = camera;
        FrontCamera = frontCamera;
        Images = images;
        More = more;
        AvailableUnits = availableUnits;
        ProductCode = productCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getLastMod() {
        return LastMod;
    }

    public void setLastMod(String lastMod) {
        LastMod = lastMod;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getRam() {
        return Ram;
    }

    public void setRam(String ram) {
        Ram = ram;
    }

    public String getRom() {
        return Rom;
    }

    public void setRom(String rom) {
        Rom = rom;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getCpu() {
        return Cpu;
    }

    public void setCpu(String cpu) {
        Cpu = cpu;
    }

    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        Camera = camera;
    }

    public String getFrontCamera() {
        return FrontCamera;
    }

    public void setFrontCamera(String frontCamera) {
        FrontCamera = frontCamera;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getMore() {
        return More;
    }

    public void setMore(String more) {
        More = more;
    }

    public int getAvailableUnits() {
        return AvailableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        AvailableUnits = availableUnits;
    }

    public int getProductCode() {
        return ProductCode;
    }

    public void setProductCode(int productCode) {
        ProductCode = productCode;
    }
}
