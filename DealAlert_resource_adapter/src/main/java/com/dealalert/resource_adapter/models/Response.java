package com.dealalert.resource_adapter.models;

import java.io.Serializable;

public class Response implements Serializable {
    private Bestseller[] bestsellers;

    public Bestseller[] getBestsellers() {
        return bestsellers;
    }

    public void setBestsellers(Bestseller[] bestsellers) {
        this.bestsellers = bestsellers;
    }
}
