package com.dealalert.webapp.models;

import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "history")
public class History {
    @Id
    private String id;

    private String itemId;
    private Map<LocalDate, Double> datePrice = new HashMap<>();

    public History(String itemId) {
        this.itemId = itemId;
    }

    public History(String itemId, Map<LocalDate, Double> datePrice) {
        this.itemId = itemId;
        this.datePrice = datePrice;
    }


    public History(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Map<LocalDate, Double> getDatePrice() {
        return datePrice;
    }

    public void setDatePrice(Map<LocalDate, Double> datePrice) {
        this.datePrice = datePrice;
    }

    public void addToMap(LocalDate date, double price) {
        this.datePrice.put(date, price);
    }
}
