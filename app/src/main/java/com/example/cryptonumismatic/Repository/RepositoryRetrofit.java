package com.example.cryptonumismatic.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.models.ModelCoin;
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
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataTopTenCoins(){
        return coinsClient.getMutableLiveDataTopTenCoins();
    }
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataAllCoins(){
        return coinsClient.getMutableLiveDataAllCoins();
    }
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataHundredCoins() {
        return coinsClient.getMutableLiveDataHundredCoins();
    }
    //оновлення списку топ монет
    public void updatetop() {
        coinsClient.updateMutableLiveDataTopTenCoins();
    }

    //оновлення списку всіх монет
    public void updateall() {
        coinsClient.updateMutableLiveDataAllCoins();
    }

    //оновлення списку лідерів росту
    public void updatehundred() {
        coinsClient.updateMutableLiveDataHundredCoins();
    }
}
