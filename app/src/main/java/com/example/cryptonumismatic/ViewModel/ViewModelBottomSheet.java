package com.example.cryptonumismatic.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.Repository.RepositoryAuth;

import com.example.cryptonumismatic.models.ModelCoinFirebase;


import java.util.List;

public class ViewModelBottomSheet extends AndroidViewModel {
    private RepositoryAuth repositoryAuth;
    private MutableLiveData mutableLiveDataModelFirebase;
    public ViewModelBottomSheet(@NonNull Application application) {
        super(application);
        this.repositoryAuth = new RepositoryAuth(application);
        this.mutableLiveDataModelFirebase = repositoryAuth.getMutableLiveDataModelFirebase();
    }
    //Додавання нової монети
    public void addElementToFirebase(String id, String price, String count, String date) {
        repositoryAuth.addElementToFirebase(id,price,count,date);
    }
    //Отримання всіх монет
    public void getAllElementsFromFirebase(){
         repositoryAuth.getAllElementsFromFirebase();
    }

    //Наблюдатель за списком всіх елементів
    public MutableLiveData<List<ModelCoinFirebase>> getMutableLiveDataModelFirebase(){
        return mutableLiveDataModelFirebase;
    }
    //Видалення монети
    public void deleteElementFromFirebase(String name){
        repositoryAuth.deleteElementFromFirebase(name);
    }
}
