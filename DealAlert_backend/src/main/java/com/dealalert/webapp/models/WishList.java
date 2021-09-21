package com.dealalert.webapp.models;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "wishlist")

public class WishList {



    @Id
    private String id;

    private String username; // wlasciciel listy

    private Date createdDate;

    private final Map<String, Boolean> itemIdMap;


    public WishList(String username, Map<String,Boolean> itemIdMap) {
        this.username = username;
        this.itemIdMap = itemIdMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Map<String, Boolean> getItemIdMap() {
        return itemIdMap;
    }

    public void addToMap(String string, boolean bool) {
        this.itemIdMap.put(string,bool);
    }

    public void replace(String string, boolean bool) {
        this.itemIdMap.replace(string,bool);
    }

    public void removeFromMap(String string) {
        this.itemIdMap.remove(string);
    }
}