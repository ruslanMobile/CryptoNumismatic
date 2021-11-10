package com.example.cryptonumismatic.response;

import com.example.cryptonumismatic.models.ModelTopCoin;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class TopCoinResponse {

    @Expose
    public List<ModelTopCoin> modelTopCoins;

    public List<ModelTopCoin> getModelTopCoins() {
        return modelTopCoins;
    }

    public void setModelTopCoins(List<ModelTopCoin> modelTopCoins) {
        this.modelTopCoins = modelTopCoins;
    }

    public TopCoinResponse(List<ModelTopCoin> modelTopCoins) {
        this.modelTopCoins = modelTopCoins;
    }

    @Override
    public String toString() {
        return "TopCoinResponse{" +
                "modelTopCoins=" + modelTopCoins +
                '}';
    }
}
