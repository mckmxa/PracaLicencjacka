package com.dealalert.webapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alerts")
public class Alert {

    @Id
    String id;

    String username;
    String email;
    String itemId;
    double alertPrice;
    boolean status;

    public Alert() {
    }

    public Alert(String username, String email, String itemId, double alertPrice, boolean status) {
        this.username = username;
        this.email = email;
        this.itemId = itemId;
        this.alertPrice = alertPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAlertPrice() {
        return alertPrice;
    }

    public void setAlertPrice(double alertPrice) {
        this.alertPrice = alertPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "ValueAlert [status=" + status + ", id=" + id + ", alertprice=" + alertPrice + "]";
    }

}