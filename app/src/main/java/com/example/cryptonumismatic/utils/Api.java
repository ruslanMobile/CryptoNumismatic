package com.example.cryptonumismatic.utils;

import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.ModelTopNfts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    //https://api.nomics.com/v1/currencies/ticker?page=1&convert=USD&key=ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b&interval=1d&per-page=10&sort=rank
   // @GET("currencies/ticker?convert=USD&interval=1d&key=ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b&sort=rank")
    @GET(Credentials.REQUEST_TOP_NFTS)
    Call<ModelTopNfts> getTopNfts();

    @GET(Credentials.REQUEST_ALL_COINS)
    Call<List<ModelCoin>> getAllCoins();

    @GET(Credentials.REQUEST_IDS)
    Call<List<ModelCoin>> getIdsCoins(@Query("ids") String ids);
}
