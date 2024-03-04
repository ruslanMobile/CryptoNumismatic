package com.example.cryptonumismatic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelCoin implements Serializable {

    @SerializedName("id")
    @Expose
    private String full_id;
    @SerializedName("symbol")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("current_price")
    @Expose
    private double current_price;
    @SerializedName("price_change_24h")
    @Expose
    private double price_change_24h;
    @SerializedName("price_change_percentage_24h")
    @Expose
    private double price_change_percentage_24h;

    public ModelCoin(String id, String name, String logoUrl, double price, double price_change_24h, double price_change_percentage_24h) {
        this.id = id;
        this.name = name;
        this.image = logoUrl;
        this.current_price = price;
        this.price_change_24h = price_change_24h;
        this.price_change_percentage_24h = price_change_percentage_24h;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return image;
    }

    public String getPrice() {
        return String.valueOf(current_price);
    }

    public double getPrice_change_24h() {
        return price_change_24h;
    }

    public double getPrice_change_percentage_24h() {
        return price_change_percentage_24h;
    }

    @Override
    public String toString() {
        return "ModelCoin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", current_price=" + current_price +
                ", price_change_24h=" + price_change_24h +
                ", price_change_percentage_24h=" + price_change_percentage_24h +
                '}';
    }

    public String getFull_id() {
        return full_id;
    }
}
