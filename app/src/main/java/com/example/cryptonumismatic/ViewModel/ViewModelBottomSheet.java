package com.example.cryptonumismatic.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.room.CoinDao;
import com.example.cryptonumismatic.room.CoinRoom;
import com.example.cryptonumismatic.room.Repository;

import java.util.List;

public class ViewModelBottomSheet extends AndroidViewModel {
    private Repository repository;
    public ViewModelBottomSheet(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public void addElement(CoinRoom coinRoom){
        repository.addElement(coinRoom);
    }

    public List<CoinRoom> getAllElements(){ return repository.getAllElements();}

    public void deleteAllElements(){ repository.deleteAllElements();}
}
