package com.iti.EShopper.models;

import java.io.Serializable;

public class Address implements Serializable {


    String id;
    String AddressTitle;
    String Address;
    String country;
    String city;
    String area;
    String date;
    int postalCode;


    public Address(String id, String addressTitle, String address, String country, String city, String area, String date, int postalCode) {
        this.id = id;
        AddressTitle = addressTitle;
        Address = address;
        this.country = country;
        this.city = city;
        this.area = area;
        this.date = date;
        this.postalCode = postalCode;
    }

    public Address(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddressTitle() {
        return AddressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        AddressTitle = addressTitle;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
