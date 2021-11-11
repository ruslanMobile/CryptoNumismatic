package com.example.cryptonumismatic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cryptonumismatic.Adapters.CoinsAdapter;
import com.example.cryptonumismatic.ViewModel.ViewModelNetwork;
import com.example.cryptonumismatic.models.ModelCoin;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MarketFragment extends Fragment {
    private ViewModelNetwork viewModelNetwork;
    private Disposable disposableFirst,disposableSecond,disposableThird;
    private RecyclerView recyclerViewTopCoins,recyclerViewGrowthLeaders,recyclerViewFindCoins;
    private List<ModelCoin> listFind = new ArrayList<>();
    private CoinsAdapter adapterFind,adapterTopCoins,adapterGrowthCoins;
    private TextView textFind;
    private ProgressBar progressBarTopCoins,progressBarGrowthCoins,progressBarFindCoins;
    private TextInputEditText textInputEditText;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyLog","FRAGMENT onCreateView============");
        viewModelNetwork = new ViewModelProvider(requireActivity()).get(ViewModelNetwork.class);
        viewModelNetwork.getMutableLiveDataTopTenCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                Log.d("MyLog", "VIEW MODEL " + modelCoins.size());
                progressBarTopCoins.setVisibility(View.GONE);
                //Оновлення списку топ монет
                adapterTopCoins.findCoins(modelCoins);
            }
        });
        viewModelNetwork.getMutableLiveDataHundredCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                Log.d("MyLog", "VIEW MODEL HUNDRED" + modelCoins.size());
                progressBarGrowthCoins.setVisibility(View.GONE);
                //Оновлення списку Лідерів росту
                adapterGrowthCoins.findCoins(modelCoins);
            }
        });
        viewModelNetwork.getMutableLiveDataAllCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                Log.d("MyLog", "VIEW MODEL ALL" + modelCoins.size());
                listFind = new ArrayList<>();
                for(ModelCoin el: modelCoins){
                    listFind.add(el);
                }
                filterEditText(textInputEditText.getText().toString());
            }
        });
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewTopCoins = view.findViewById(R.id.recyclerViewTopCoins);
        recyclerViewGrowthLeaders = view.findViewById(R.id.recyclerViewGrowthLeaders);
        recyclerViewFindCoins = view.findViewById(R.id.recyclerViewFindCoins);
        textFind = view.findViewById(R.id.textFindCoinsMarket);
        progressBarTopCoins = view.findViewById(R.id.progressBarTopCoins);
        progressBarGrowthCoins = view.findViewById(R.id.progressBarGrowthCoins);
        progressBarFindCoins = view.findViewById(R.id.progressBarFindCoins);
        scrollView = view.findViewById(R.id.scrollView);

        progressBarTopCoins.setVisibility(View.VISIBLE);
        progressBarGrowthCoins.setVisibility(View.VISIBLE);
        textInputEditText = view.findViewById(R.id.textInputEditTextFind);
        //Адаптери для списків
        adapterGrowthCoins = new CoinsAdapter(new ArrayList(),getActivity());
        adapterTopCoins = new CoinsAdapter(new ArrayList(),getActivity());
        adapterFind = new CoinsAdapter(new ArrayList(),getActivity());

        recyclerViewFindCoins.setAdapter(adapterFind);
        recyclerViewGrowthLeaders.setAdapter(adapterGrowthCoins);
        recyclerViewTopCoins.setAdapter(adapterTopCoins);

        //При наборі тексту відбувається пошук елементів зі списку
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                scrollView.scrollTo(0,0);
                filterEditText(s.toString().trim());
            }
        });

        //Оновлення списків по інтервалах
        Log.d("MyLog","FRAGMENT onViewCreated============");
        disposableFirst  = Observable.interval(100,7500,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el->{
                    Log.d("MyLog","GIVE SOME DATA");
                    viewModelNetwork.updatetop();
                },e->{
                    Log.d("MyLog","ERROR");
                });


        disposableSecond  = Observable.interval(3500,13000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el->{
                    Log.d("MyLog","GIVE SOME DATA HUNDRED");
                    viewModelNetwork.updatehundred();
                },e->{
                    Log.d("MyLog","ERROR");
                });


        disposableThird = Observable.interval(5500,25000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el->{
                    Log.d("MyLog","GIVE SOME DATA ALL");
                    viewModelNetwork.updateall();
                },e->{
                    Log.d("MyLog","ERROR");
                });
        //

    }

    //Очищення disposable від інтервалів
    @Override
    public void onDestroyView() {
        Log.d("MyLog","FRAGMENT onDestroyView=========");
        disposableFirst.dispose();
        disposableSecond.dispose();
        disposableThird.dispose();
        super.onDestroyView();
    }

    //Фільтер пошуку елементів
    public void filterEditText(String string) {
        if (listFind.size() == 0) {
            progressBarFindCoins.setVisibility(View.VISIBLE);
        } else {
            progressBarFindCoins.setVisibility(View.GONE);
            if (string.length() > 0) {
                List<ModelCoin> list = new ArrayList<>();
                for (ModelCoin el : listFind) {
                    if (el.getName().toLowerCase().contains(string.toLowerCase())) {
                        list.add(el);
                    }
                }
                Log.d("MyLog", "LIST" + list.size());
                adapterFind.findCoins(list);
            }
        }
        if(string.length()>0) textFind.setVisibility(View.VISIBLE);
        else {
            textFind.setVisibility(View.GONE);
            adapterFind.findCoins(new ArrayList<>());
            progressBarFindCoins.setVisibility(View.GONE);
        }
    }
}