package com.example.cryptonumismatic.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.Repository.RepositoryRetrofit;
import com.example.cryptonumismatic.models.ModelCoin;

import java.util.List;

public class ViewModelNetwork extends AndroidViewModel {
    private RepositoryRetrofit repositoryRetrofit;

    public ViewModelNetwork(@NonNull Application application) {
        super(application);
        repositoryRetrofit = RepositoryRetrofit.getInstance();
    }

    //отримання топ монет
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataTopTenCoins() {
        return repositoryRetrofit.getMutableLiveDataTopTenCoins();
    }
    //отримання всіх монет
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataAllCoins() {
        return repositoryRetrofit.getMutableLiveDataAllCoins();
    }
    //отримання лідерів росту
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataHundredCoins() {
        return repositoryRetrofit.getMutableLiveDataHundredCoins();
    }

    //оновлення списків
    public void updatetop(){
        repositoryRetrofit.updatetop();
    }
    public void updateall(){
        repositoryRetrofit.updateall();
    }
    public void updatehundred(){
        repositoryRetrofit.updatehundred();
    }
    //
}
