package com.example.cryptonumismatic.Fragments;

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

import com.example.cryptonumismatic.Adapters.CoinsAdapterFindCoins;
import com.example.cryptonumismatic.Adapters.CoinsAdapterGrowthCoins;
import com.example.cryptonumismatic.Adapters.CoinsAdapterTopCoins;
import com.example.cryptonumismatic.BottomSheets.CustomBottomSheet;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelNetwork;
import com.example.cryptonumismatic.models.ModelCoin;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MarketFragment extends Fragment implements CoinsAdapterTopCoins.ClickAddElementListener, CoinsAdapterGrowthCoins.ClickAddElementListener
        , CoinsAdapterFindCoins.ClickAddElementListener {
    private ViewModelNetwork viewModelNetwork;
    private Disposable disposableFirst, disposableSecond, disposableThird;
    private RecyclerView recyclerViewTopCoins, recyclerViewGrowthLeaders, recyclerViewFindCoins;
    private List<ModelCoin> listFind = new ArrayList<>();
    private List<ModelCoin> listTopCoins = new ArrayList<>();
    private List<ModelCoin> listGrowthCoins = new ArrayList<>();
    private List<ModelCoin> listFindCoins = new ArrayList<>();
    private CoinsAdapterTopCoins adapterTopCoins;
    private CoinsAdapterGrowthCoins adapterGrowthCoins;
    private CoinsAdapterFindCoins adapterFind;
    private TextView textFind;
    private ProgressBar progressBarTopCoins, progressBarGrowthCoins, progressBarFindCoins;
    private TextInputEditText textInputEditText;
    private ScrollView scrollView;
    private CustomBottomSheet customBottomSheet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyLog", "FRAGMENT onCreateView============");
        viewModelNetwork = new ViewModelProvider(requireActivity()).get(ViewModelNetwork.class);
        viewModelNetwork.getMutableLiveDataTopTenCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                Log.d("MyLog", "VIEW MODEL " + modelCoins.size());
                for (ModelCoin el : modelCoins) {
                    listTopCoins.add(el);
                }
                progressBarTopCoins.setVisibility(View.GONE);
                //Оновлення списку топ монет
                adapterTopCoins.findCoins(modelCoins);
            }
        });
        viewModelNetwork.getMutableLiveDataHundredCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                Log.d("MyLog", "VIEW MODEL HUNDRED" + modelCoins.size());
                for (ModelCoin el : modelCoins) {
                    listGrowthCoins.add(el);
                }
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
                for (ModelCoin el : modelCoins) {
                    listFind.add(el);
                }
                textInputEditText.setText(textInputEditText.getText());
                textInputEditText.setSelection(textInputEditText.getText().length());
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
        adapterTopCoins = new CoinsAdapterTopCoins(new ArrayList(), getActivity(), this);
        adapterGrowthCoins = new CoinsAdapterGrowthCoins(new ArrayList(), getActivity(), this);
        adapterFind = new CoinsAdapterFindCoins(new ArrayList(), getActivity(), this);

        recyclerViewFindCoins.setAdapter(adapterFind);
        recyclerViewGrowthLeaders.setAdapter(adapterGrowthCoins);
        recyclerViewTopCoins.setAdapter(adapterTopCoins);

        customBottomSheet = new CustomBottomSheet();

        //Оновлення списків по інтервалах
        Log.d("MyLog", "FRAGMENT onViewCreated============");
        disposableFirst = Observable.interval(100, 7500,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el -> {
                    Log.d("MyLog", "GIVE SOME DATA");
                    viewModelNetwork.updatetop();
                }, e -> {
                    Log.d("MyLog", "ERROR");
                });


        disposableSecond = Observable.interval(3500, 13000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el -> {
                    Log.d("MyLog", "GIVE SOME DATA HUNDRED");
                    viewModelNetwork.updatehundred();
                }, e -> {
                    Log.d("MyLog", "ERROR");
                });


        disposableThird = Observable.interval(5500, 25000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el -> {
                    Log.d("MyLog", "GIVE SOME DATA ALL");
                    viewModelNetwork.updateall();
                }, e -> {
                    Log.d("MyLog", "ERROR");
                });
        //
        search();
    }

    //Очищення disposable від інтервалів
    @Override
    public void onDestroyView() {
        Log.d("MyLog", "FRAGMENT onDestroyView=========");
        disposableFirst.dispose();
        disposableSecond.dispose();
        disposableThird.dispose();
        super.onDestroyView();
    }

    //Пошук елементів.Слухач зміни тексту і пошук.
    public void search() {
        Disposable disposable = (Disposable) Observable.create(emitter -> {
            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override
                public void afterTextChanged(Editable s) {
                    emitter.onNext(s.toString().trim());
                }
            });
        })
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((string) -> {
                    String text = string.toString().trim();
                    Log.e("MyLog", "SEARCH: " + string);
                    if (listFind.size() == 0) {
                        progressBarFindCoins.setVisibility(View.VISIBLE);
                    } else {
                        progressBarFindCoins.setVisibility(View.GONE);
                        listFindCoins = new ArrayList<>();
                        if (text.length() > 0) {
                            for (ModelCoin el : listFind) {
                                if (el.getName().toLowerCase().contains(text.toLowerCase())) {
                                    listFindCoins.add(el);
                                }
                            }
                            Log.d("MyLog", "LIST" + listFindCoins.size());
                            adapterFind.findCoins(listFindCoins);
                        }
                    }
                    if (text.length() > 0) textFind.setVisibility(View.VISIBLE);
                    else {
                        textFind.setVisibility(View.GONE);
                        adapterFind.findCoins(new ArrayList<>());
                        progressBarFindCoins.setVisibility(View.GONE);
                    }
                }, (r) -> {
                    Log.d("MyLog","ERROR RX");
                });
    }

    @Override
    public void onClickAddElementTopCoins(int position) {
        Log.d("MyLog", "========CLICK TopCoins========" + listTopCoins.get(position).toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("coin", listTopCoins.get(position));
        customBottomSheet.setArguments(bundle);
        customBottomSheet.show(getActivity().getSupportFragmentManager(), "TopCoins");
    }

    @Override
    public void onClickAddElementGrowthCoins(int position) {
        Log.d("MyLog", "========CLICK GrowthCoins========" + listGrowthCoins.get(position).toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("coin", listGrowthCoins.get(position));
        customBottomSheet.setArguments(bundle);
        customBottomSheet.show(getActivity().getSupportFragmentManager(), "GrowthCoins");
    }

    @Override
    public void onClickAddElementFindCoins(int position) {
        Log.d("MyLog", "========CLICK FindCoins========" + listFindCoins.get(position).toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("coin", listFindCoins.get(position));
        customBottomSheet.setArguments(bundle);
        customBottomSheet.show(getActivity().getSupportFragmentManager(), "FindCoins");
    }
}