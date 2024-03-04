package com.example.cryptonumismatic.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.NftModel;
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
    public MutableLiveData<List<NftModel>> getMutableLiveDataTopNfts(){
        return coinsClient.getMutableLiveDataTopNfts();
    }
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataAllCoins(){
        return coinsClient.getMutableLiveDataAllCoins();
    }
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataIdsCoins() {
        return coinsClient.getMutableLiveDataIdsCoins();
    }
    //оновлення списку топ монет
    public void updatetop() {
        coinsClient.updateMutableLiveDataTopNfts();
    }

    //оновлення списку всіх монет
    public void updateall() {
        coinsClient.updateMutableLiveDataAllCoins();
    }
    //оновлення списку по id
    public void updateids(String ids) {
        coinsClient.updateMutableLiveDataIdsCoins(ids);
    }
}
