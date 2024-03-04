package com.example.cryptonumismatic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataModel implements Serializable {
    @SerializedName("floor_price")
    @Expose
    private String floor_price;

    public String getFloor_price() {
        return floor_price;
    }
}