package com.example.cryptonumismatic.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.models.ModelTopCoin;
import com.example.cryptonumismatic.request.CoinsClient;

import java.util.List;

public class RepositoryRetrofit {
    private static RepositoryRetrofit instance;
    private CoinsClient coinsClient;

    public static RepositoryRetrofit getInstance() {
        if(instance == null){
            instance = new RepositoryRetrofit();
        }
        return instance;
    }

    private RepositoryRetrofit(){
        coinsClient = CoinsClient.getInstance();
    }
    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataTopTenCoins(){
        return coinsClient.getMutableLiveDataTopTenCoins();
    }
    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataAllCoins(){
        return coinsClient.getMutableLiveDataAllCoins();
    }
    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataHundredCoins() {
        return coinsClient.getMutableLiveDataHundredCoins();
    }
    public void updatetop() {
        coinsClient.updateMutableLiveDataTopTenCoins();
    }

    public void updateall() {
        coinsClient.updateMutableLiveDataAllCoins();
    }

    public void updatehundred() {
        coinsClient.updateMutableLiveDataHundredCoins();
    }
}
