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
import android.widget.TextView;

import com.example.cryptonumismatic.Adapters.CoinsAdapter;
import com.example.cryptonumismatic.ViewModel.ViewModelNetwork;
import com.example.cryptonumismatic.models.ModelTopCoin;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MarketFragment extends Fragment {
    ViewModelNetwork viewModelNetwork;
    Disposable disposableFirst,disposableSecond,disposableThird;
    RecyclerView recyclerViewTopCoins,recyclerViewGrowthLeaders,recyclerViewFindCoins;
    List<ModelTopCoin> listFind = new ArrayList<>();
    CoinsAdapter adapterFind;
    TextView textFind;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyLog","FRAGMENT onCreateView============");
        viewModelNetwork = new ViewModelProvider(requireActivity()).get(ViewModelNetwork.class);
        viewModelNetwork.getMutableLiveDataTopTenCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelTopCoin>>() {
            @Override
            public void onChanged(List<ModelTopCoin> modelTopCoins) {
                Log.d("MyLog", "VIEW MODEL " + modelTopCoins.size());
                upDateListTopCoins(modelTopCoins);
            }
        });
        viewModelNetwork.getMutableLiveDataHundredCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelTopCoin>>() {
            @Override
            public void onChanged(List<ModelTopCoin> modelTopCoins) {
                Log.d("MyLog", "VIEW MODEL HUNDRED" + modelTopCoins.size());
                upDateListGrowthLeaders(modelTopCoins);
            }
        });
        viewModelNetwork.getMutableLiveDataAllCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelTopCoin>>() {
            @Override
            public void onChanged(List<ModelTopCoin> modelTopCoins) {
                Log.d("MyLog", "VIEW MODEL ALL" + modelTopCoins.size());
                for(ModelTopCoin el: modelTopCoins){
                    listFind.add(el);
                }
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

        TextInputEditText textInputEditText = view.findViewById(R.id.textInputEditTextFind);
        adapterFind = new CoinsAdapter(new ArrayList(),getActivity());
        recyclerViewFindCoins.setAdapter(adapterFind);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()>0) {
                    textFind.setVisibility(View.VISIBLE);
                    filterEditText(s.toString().trim());
                }
                else {
                    textFind.setVisibility(View.GONE);
                    adapterFind.findCoins(new ArrayList<>());
                }
            }
        });

        Log.d("MyLog","FRAGMENT onViewCreated============");
        viewModelNetwork.updatetop();
        disposableFirst  = Observable.interval(7500,
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
    }

    @Override
    public void onDestroyView() {
        Log.d("MyLog","FRAGMENT onDestroyView=========");
        disposableFirst.dispose();
        disposableSecond.dispose();
        disposableThird.dispose();
        super.onDestroyView();
    }
    public void upDateListTopCoins(List list){
        Log.d("MyLog","FRAGMENT UPDATE=========");
        CoinsAdapter adapter = new CoinsAdapter(list,getActivity());
        recyclerViewTopCoins.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void upDateListGrowthLeaders(List list){
        Log.d("MyLog","FRAGMENT UPDATE=========");
        CoinsAdapter adapter = new CoinsAdapter(list,getActivity());
        recyclerViewGrowthLeaders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void filterEditText(String string){
        List<ModelTopCoin> list = new ArrayList<>();
        for(ModelTopCoin el: listFind){
            if(el.getName().toLowerCase().contains(string.toLowerCase())){
                list.add(el);
            }
        }
        Log.d("MyLog","LIST" + list.size());
        adapterFind.findCoins(list);
    }
}