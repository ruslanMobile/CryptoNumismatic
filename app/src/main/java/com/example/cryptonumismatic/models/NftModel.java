package com.example.cryptonumismatic.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NftModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("data")
    @Expose
    private DataModel data;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getThumb() {
        return thumb;
    }

    public DataModel getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Nft{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", thumb='" + thumb + '\'' +
                ", data=" + data +
                '}';
    }
}

