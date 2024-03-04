package com.example.cryptonumismatic.request;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.ModelTopNfts;
import com.example.cryptonumismatic.models.NftModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinsClient {
    private MutableLiveData<List<NftModel>> mutableLiveDataTopNfts;
    private MutableLiveData<List<ModelCoin>> mutableLiveDataAllCoins;
    private MutableLiveData<List<ModelCoin>> mutableLiveDataIdsCoins;
    private static CoinsClient instance;

    public static CoinsClient getInstance() {
        if (instance == null) {
            instance = new CoinsClient();
        }
        return instance;
    }

    private CoinsClient() {
        mutableLiveDataTopNfts = new MutableLiveData<>();
        mutableLiveDataAllCoins = new MutableLiveData<>();
        mutableLiveDataIdsCoins = new MutableLiveData<>();
    }

    //отримати списку топ nft
    public MutableLiveData<List<NftModel>> getMutableLiveDataTopNfts() {
        return mutableLiveDataTopNfts;
    }

    //запрос топ nft
    public void updateMutableLiveDataTopNfts() {
        Call<ModelTopNfts> call = RetrofitClient.getInstance().getApi().getTopNfts();
        call.enqueue(new Callback<ModelTopNfts>() {
            @Override
            public void onResponse(Call<ModelTopNfts> call, Response<ModelTopNfts> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataTopNfts.postValue(response.body().getNfts());
                    Call l = call;
                } else {}
            }

            @Override
            public void onFailure(Call<ModelTopNfts> call, Throwable t) {}
        });
    }

    //отримання списку всіх монет
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataAllCoins() {
        return mutableLiveDataAllCoins;
    }

    //отримання всіх монет
    public void updateMutableLiveDataAllCoins() {
        Call<List<ModelCoin>> call = RetrofitClient.getInstance().getApi().getAllCoins();
        call.enqueue(new Callback<List<ModelCoin>>() {
            @Override
            public void onResponse(Call<List<ModelCoin>> call, Response<List<ModelCoin>> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataAllCoins.postValue(response.body());
                } else {}
            }

            @Override
            public void onFailure(Call<List<ModelCoin>> call, Throwable t) {}
        });
    }

    //отримання списку по id
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataIdsCoins() {
        return mutableLiveDataIdsCoins;
    }

    //оновлення списку по id
    public void updateMutableLiveDataIdsCoins(String ids) {
        Call<List<ModelCoin>> call = RetrofitClient.getInstance().getApi().getIdsCoins(ids);
        call.enqueue(new Callback<List<ModelCoin>>() {
            @Override
            public void onResponse(Call<List<ModelCoin>> call, Response<List<ModelCoin>> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataIdsCoins.postValue(response.body());
                } else {}
            }

            @Override
            public void onFailure(Call<List<ModelCoin>> call, Throwable t) {}
        });
    }

}
