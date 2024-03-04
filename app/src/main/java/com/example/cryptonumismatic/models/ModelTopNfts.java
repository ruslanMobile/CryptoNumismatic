package com.example.cryptonumismatic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelTopNfts  implements Serializable {
    @SerializedName("nfts")
    @Expose
    private List<NftModel> nfts;

    public List<NftModel> getNfts() {
        return nfts;
    }
}