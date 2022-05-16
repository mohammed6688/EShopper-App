package com.iti.EShopper.models;

public class Installment {

    String id;
    String phoneNumber;
    String name;
    String userName;
    String image;
    String phoneModel;
    String initial;


    public Installment() {
    }

    public Installment(String id, String phoneNumber, String name, String userName, String image, String phoneModel, String initial) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.userName = userName;
        this.image = image;
        this.phoneModel = phoneModel;
        this.initial = initial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }
}
