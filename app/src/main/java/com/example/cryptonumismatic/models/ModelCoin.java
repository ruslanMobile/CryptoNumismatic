package com.example.cryptonumismatic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelCoin implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_date")
    @Expose
    private String priceDate;
    @SerializedName("price_timestamp")
    @Expose
    private String priceTimestamp;
    @SerializedName("market_cap")
    @Expose
    private String marketCap;
    @SerializedName("market_cap_dominance")
    @Expose
    private String marketCapDominance;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("high")
    @Expose
    private String high;
    @SerializedName("high_timestamp")
    @Expose
    private String highTimestamp;
    @SerializedName("1d")
    @Expose
    private ModelOneDayChange modelOneDayChange;

    public ModelCoin(String id, String name, String logoUrl, String price, String priceDate, String priceTimestamp, String marketCap, String marketCapDominance, String rank, String high, String highTimestamp, ModelOneDayChange modelOneDayChange) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.price = price;
        this.priceDate = priceDate;
        this.priceTimestamp = priceTimestamp;
        this.marketCap = marketCap;
        this.marketCapDominance = marketCapDominance;
        this.rank = rank;
        this.high = high;
        this.highTimestamp = highTimestamp;
        this.modelOneDayChange = modelOneDayChange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    public String getPriceTimestamp() {
        return priceTimestamp;
    }

    public void setPriceTimestamp(String priceTimestamp) {
        this.priceTimestamp = priceTimestamp;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getMarketCapDominance() {
        return marketCapDominance;
    }

    public void setMarketCapDominance(String marketCapDominance) {
        this.marketCapDominance = marketCapDominance;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getHighTimestamp() {
        return highTimestamp;
    }

    public void setHighTimestamp(String highTimestamp) {
        this.highTimestamp = highTimestamp;
    }

    @Override
    public String toString() {
        return "ModelTopCoin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", price='" + price + '\'' +
                ", priceDate='" + priceDate + '\'' +
                ", priceTimestamp='" + priceTimestamp + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", marketCapDominance='" + marketCapDominance + '\'' +
                ", rank='" + rank + '\'' +
                ", high='" + high + '\'' +
                ", highTimestamp='" + highTimestamp + '\'' +
                ", modelOneDayChange=" + modelOneDayChange +
                '}';
    }

    public ModelOneDayChange getModelOneDayChange() {
        return modelOneDayChange;
    }

    public void set1d(ModelOneDayChange modelOneDayChange) {
        this.modelOneDayChange = modelOneDayChange;
    }

}
