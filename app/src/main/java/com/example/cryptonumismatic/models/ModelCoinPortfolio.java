package com.example.cryptonumismatic.models;

import com.example.cryptonumismatic.room.CoinRoom;

import java.io.Serializable;

public class ModelCoinPortfolio implements Serializable{
    private CoinRoom coinRoom;
    private ModelCoin modelCoin;

    public ModelCoinPortfolio(CoinRoom coinRoom, ModelCoin modelCoin) {
        this.coinRoom = coinRoom;
        this.modelCoin = modelCoin;
    }

    @Override
    public String toString() {
        return "ModelCoinPortfolio{" +
                "coinRoom=" + coinRoom +
                ", modelCoin=" + modelCoin +
                '}';
    }

    public CoinRoom getCoinRoom() {
        return coinRoom;
    }

    public void setCoinRoom(CoinRoom coinRoom) {
        this.coinRoom = coinRoom;
    }

    public ModelCoin getModelCoin() {
        return modelCoin;
    }

    public void setModelCoin(ModelCoin modelCoin) {
        this.modelCoin = modelCoin;
    }

}
