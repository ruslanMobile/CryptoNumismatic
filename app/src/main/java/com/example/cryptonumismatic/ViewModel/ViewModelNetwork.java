package com.example.cryptonumismatic.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.Repository.RepositoryRetrofit;
import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.NftModel;

import java.util.List;

public class ViewModelNetwork extends AndroidViewModel {
    private RepositoryRetrofit repositoryRetrofit;

    public ViewModelNetwork(@NonNull Application application) {
        super(application);
        repositoryRetrofit = RepositoryRetrofit.getInstance();
    }

    //отримання топ nfts
    public MutableLiveData<List<NftModel>> getMutableLiveDataTopNfts() {
        return repositoryRetrofit.getMutableLiveDataTopNfts();
    }
    //отримання всіх монет
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataAllCoins() {
        return repositoryRetrofit.getMutableLiveDataAllCoins();
    }
    //отримання списку по id
    public MutableLiveData<List<ModelCoin>> getMutableLiveDataIdsCoins() {
        return repositoryRetrofit.getMutableLiveDataIdsCoins();
    }

    //оновлення списків
    public void updatetop(){
        repositoryRetrofit.updatetop();
    }
    public void updateall(){
        repositoryRetrofit.updateall();
    }
    public void updateids(String ids){
        repositoryRetrofit.updateids(ids);
    }
    //
}
