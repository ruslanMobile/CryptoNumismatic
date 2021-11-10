package com.example.cryptonumismatic.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.models.ModelTopCoin;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinsClient {
    private MutableLiveData<List<ModelTopCoin>> mutableLiveDataTopTenCoins;
    private MutableLiveData<List<ModelTopCoin>> mutableLiveDataAllCoins;
    private MutableLiveData<List<ModelTopCoin>> mutableLiveDataHundredCoins;
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
    }

    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataTopTenCoins() {
        return mutableLiveDataTopTenCoins;
    }

    public void updateMutableLiveDataTopTenCoins() {
        Call<List<ModelTopCoin>> call = RetrofitClient.getInstance().getApi().getTopTenCoins();
        call.enqueue(new Callback<List<ModelTopCoin>>() {
            @Override
            public void onResponse(Call<List<ModelTopCoin>> call, Response<List<ModelTopCoin>> response) {
                if (response.isSuccessful()) {
                    Log.d("MyLog", "CLIENT: " + String.valueOf(response.body().size()));
                    mutableLiveDataTopTenCoins.postValue(response.body());
                } else Log.e("MyLog", "ERROR REQUEST" + response.message());
            }

            @Override
            public void onFailure(Call<List<ModelTopCoin>> call, Throwable t) {
                Log.d("MyLog", "ERROR REQUEST " + " " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataAllCoins() {
        return mutableLiveDataAllCoins;
    }

    public void updateMutableLiveDataAllCoins() {
        Call<List<ModelTopCoin>> call = RetrofitClient.getInstance().getApi().getAllCoins();
        call.enqueue(new Callback<List<ModelTopCoin>>() {
            @Override
            public void onResponse(Call<List<ModelTopCoin>> call, Response<List<ModelTopCoin>> response) {
                if (response.isSuccessful()) {
                    Log.d("MyLog", "CLIENT ALL: " + String.valueOf(response.body().size()));
                    mutableLiveDataAllCoins.postValue(response.body().subList(0, 10000));
                } else Log.e("MyLog", "ERROR REQUEST All");
            }

            @Override
            public void onFailure(Call<List<ModelTopCoin>> call, Throwable t) {
                Log.d("MyLog", "ERROR REQUEST " + " " + t.getMessage());
            }
        });
    }
    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataHundredCoins() {
        return mutableLiveDataHundredCoins;
    }

    public void updateMutableLiveDataHundredCoins() {
        Call<List<ModelTopCoin>> call = RetrofitClient.getInstance().getApi().getHundredCoins();
        call.enqueue(new Callback<List<ModelTopCoin>>() {
            @Override
            public void onResponse(Call<List<ModelTopCoin>> call, Response<List<ModelTopCoin>> response) {
                if (response.isSuccessful()) {
                    Log.d("MyLog", "CLIENT HUNDRED: " + String.valueOf(response.body().size()));
                    try {
                        Collections.sort(response.body(), new Comparator<ModelTopCoin>() {
                            @Override
                            public int compare(ModelTopCoin o1, ModelTopCoin o2) {
                                try {
                                    return Double.valueOf(o2.getModelOneDayChange().getPriceChangePct()).compareTo(Double.valueOf(o1.getModelOneDayChange().getPriceChangePct()));
                                } catch (NullPointerException ee) {
                                    Log.d("MyLog", "NULL EXCEPTION");
                                }
                                return 0;
                            }
                        });
                    }catch (IllegalArgumentException ee){
                        Log.e("MyLog",ee.getMessage());
                    }
                    mutableLiveDataHundredCoins.postValue(response.body().subList(0,10));
                } else Log.e("MyLog", "ERROR REQUEST HUNDRED"+ response.message());
            }

            @Override
            public void onFailure(Call<List<ModelTopCoin>> call, Throwable t) {
                Log.d("MyLog", "ERROR REQUEST " + " " + t.getMessage());
            }
        });
    }
}
