package com.iti.EShopper.models;

import java.io.Serializable;

public class Order implements Serializable {
    String orderId;
    String userId;
    String addressId;
    String itemCount;
    String itemCategory;
    String itemSubCategory;
    String itemId;
    String data;
    String size;
    String shipmentId;
    String shipmentState;
    boolean orderState;

    public Order(String orderId, String userId, String addressId, String itemCount, String itemCategory, String itemSubCategory, String itemId, String data, String size, String shipmentId, String shipmentState, boolean orderState) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.itemCount = itemCount;
        this.itemCategory = itemCategory;
        this.itemSubCategory = itemSubCategory;
        this.itemId = itemId;
        this.data = data;
        this.size = size;
        this.shipmentId = shipmentId;
        this.shipmentState = shipmentState;
        this.orderState = orderState;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemSubCategory() {
        return itemSubCategory;
    }

    public void setItemSubCategory(String itemSubCategory) {
        this.itemSubCategory = itemSubCategory;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentState() {
        return shipmentState;
    }

    public void setShipmentState(String shipmentState) {
        this.shipmentState = shipmentState;
    }

    public boolean isOrderState() {
        return orderState;
    }

    public void setOrderState(boolean orderState) {
        this.orderState = orderState;
    }
}
