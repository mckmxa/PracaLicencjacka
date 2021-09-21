package com.dealalert.resource_adapter.models;

import java.io.Serializable;

public class Price implements Serializable {
    private double value;
    private String symbol;

    public Price(){

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
