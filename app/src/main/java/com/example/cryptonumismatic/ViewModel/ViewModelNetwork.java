package com.example.cryptonumismatic.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.Repository.RepositoryRetrofit;
import com.example.cryptonumismatic.models.ModelTopCoin;

import java.util.List;

public class ViewModelNetwork extends AndroidViewModel {
    private RepositoryRetrofit repositoryRetrofit;

    public ViewModelNetwork(@NonNull Application application) {
        super(application);
        repositoryRetrofit = RepositoryRetrofit.getInstance();
    }

    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataTopTenCoins() {
        return repositoryRetrofit.getMutableLiveDataTopTenCoins();
    }
    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataAllCoins() {
        return repositoryRetrofit.getMutableLiveDataAllCoins();
    }
    public MutableLiveData<List<ModelTopCoin>> getMutableLiveDataHundredCoins() {
        return repositoryRetrofit.getMutableLiveDataHundredCoins();
    }

    public void updatetop(){
        repositoryRetrofit.updatetop();
    }
    public void updateall(){
        repositoryRetrofit.updateall();
    }
    public void updatehundred(){
        repositoryRetrofit.updatehundred();
    }
}
