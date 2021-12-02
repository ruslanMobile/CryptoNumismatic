package com.example.cryptonumismatic.models;


import java.io.Serializable;

public class ModelCoinPortfolio implements Serializable{
    private ModelCoinFirebase modelCoinFirebase;
    private ModelCoin modelCoin;

    public ModelCoinPortfolio(ModelCoinFirebase coinRoom, ModelCoin modelCoin) {
        this.modelCoinFirebase = coinRoom;
        this.modelCoin = modelCoin;
    }

    @Override
    public String toString() {
        return "ModelCoinPortfolio{" +
                "coinRoom=" + modelCoinFirebase +
                ", modelCoin=" + modelCoin +
                '}';
    }

    public ModelCoinFirebase getModelCoinFirebase() {
        return modelCoinFirebase;
    }

    public void setModelCoinFirebase(ModelCoinFirebase modelCoinFirebase) {
        this.modelCoinFirebase = modelCoinFirebase;
    }

    public ModelCoin getModelCoin() {
        return modelCoin;
    }

    public void setModelCoin(ModelCoin modelCoin) {
        this.modelCoin = modelCoin;
    }
}
