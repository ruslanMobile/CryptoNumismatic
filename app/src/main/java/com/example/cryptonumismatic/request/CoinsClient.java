package com.example.cryptonumismatic.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.models.ModelCoin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinsClient {
    private MutableLiveData<List<ModelCoin>> mutableLiveDataTopTenCoins;
    private MutableLiveData<List<ModelCoin>> mutableLiveDataAllCoins;
    private MutableLiveData<List<ModelCoin>> mutableLiveDataHundredCoins;
    private MutableLiveData<List<ModelCoin>> mutableLiveDataIdsCoins;
    private static CoinsClient instance;

    public static CoinsClient getInstance() {
        if (instance == null) {
            instance = new CoinsClient();
        }
        return instance;
    }

    private CoinsClient() {
        mutableLiveDataTopTenCoins = new MutableLiveData<>();
        mutableLiveDataAllCoins = new MutableLiveData<>();
        mutableLiveDataHundredCoins = new MutableLiveData<>();
        mutableLiveDataIdsCoins = new MutableLiveData<>();
    }

    //отримати списку топ монет
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataTopTenCoins() {
        return mutableLiveDataTopTenCoins;
    }

    //запрос топ монет
    public void updateMutableLiveDataTopTenCoins() {
        Call<List<ModelCoin>> call = RetrofitClient.getInstance().getApi().getTopTenCoins();
        call.enqueue(new Callback<List<ModelCoin>>() {
            @Override
            public void onResponse(Call<List<ModelCoin>> call, Response<List<ModelCoin>> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataTopTenCoins.postValue(response.body());
                } else {}
            }

            @Override
            public void onFailure(Call<List<ModelCoin>> call, Throwable t) {}
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
                    mutableLiveDataAllCoins.postValue(response.body().subList(0, 10000));
                } else {}
            }

            @Override
            public void onFailure(Call<List<ModelCoin>> call, Throwable t) { }
        });
    }
    //отримання списку лідерів росту
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataHundredCoins() {
        return mutableLiveDataHundredCoins;
    }

    //оновлення списку лідерів росту
    public void updateMutableLiveDataHundredCoins() {
        Call<List<ModelCoin>> call = RetrofitClient.getInstance().getApi().getHundredCoins();
        call.enqueue(new Callback<List<ModelCoin>>() {
            @Override
            public void onResponse(Call<List<ModelCoin>> call, Response<List<ModelCoin>> response) {
                if (response.isSuccessful()) {
                    try {
                        Collections.sort(response.body(), new Comparator<ModelCoin>() {
                            @Override
                            public int compare(ModelCoin o1, ModelCoin o2) {
                                try {
                                    return Double.valueOf(o2.getModelOneDayChange().getPriceChangePct()).compareTo(Double.valueOf(o1.getModelOneDayChange().getPriceChangePct()));
                                } catch (NullPointerException ee) {}
                                return 0;
                            }
                        });
                    }catch (IllegalArgumentException ee){}
                    mutableLiveDataHundredCoins.postValue(response.body().subList(0,10));
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
