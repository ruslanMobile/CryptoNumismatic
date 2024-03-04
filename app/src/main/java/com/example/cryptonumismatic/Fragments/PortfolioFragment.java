package com.example.cryptonumismatic.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cryptonumismatic.Adapters.CoinsAdapterPortfolio;
import com.example.cryptonumismatic.BottomSheets.PortfolioBottomSheet;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelBottomSheet;
import com.example.cryptonumismatic.ViewModel.ViewModelNetwork;
import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.ModelCoinFirebase;
import com.example.cryptonumismatic.models.ModelCoinPortfolio;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;

public class PortfolioFragment extends Fragment implements CoinsAdapterPortfolio.ClickMoreInfoListener {
    private ViewModelBottomSheet viewModelBottomSheet;
    private ViewModelNetwork viewModelNetwork;
    private List<ModelCoinFirebase> listMyCoins = new ArrayList<>();
    private List<ModelCoinPortfolio> listPortfolio;
    private RecyclerView recyclerView;
    private Disposable disposable,disposableRetrofit;
    private String ids = "";
    private TextView textAllMoney,textEmpty;
    private ProgressBar progressBarPortfolio;
    private CoinsAdapterPortfolio coinsAdapterPortfolio;
    private PortfolioBottomSheet portfolioBottomSheet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxJavaPlugins.setErrorHandler(th->{});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelBottomSheet = new ViewModelProvider(requireActivity()).get(ViewModelBottomSheet.class);
        //Наблюдач за списком всіх своїх монет в Firebase
        viewModelBottomSheet.getMutableLiveDataModelFirebase().observe(getViewLifecycleOwner(), new Observer<List<ModelCoinFirebase>>() {
            @Override
            public void onChanged(List<ModelCoinFirebase> modelCoinFirebases) {
                if (disposable != null)
                    disposable.dispose();

                listMyCoins = modelCoinFirebases;
                for (ModelCoinFirebase el : listMyCoins) {
                    ids += el.getIdName() + ",";
                }
                if (ids.trim().length() > 0) {
                    disposable = Observable.interval(1000, 7000,
                            TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(el -> {
                                viewModelNetwork.updateids(ids);
                            }, e -> {});
                    textEmpty.setVisibility(View.GONE);
                } else {
                    progressBarPortfolio.setVisibility(View.GONE);
                    textEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModelNetwork = new ViewModelProvider(requireActivity()).get(ViewModelNetwork.class);
        //Наблюдач за зміною списку з Retrofit
        viewModelNetwork.getMutableLiveDataIdsCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                 //Отримання списку з Firebase, і після отримання зписку з Retrofit конвертувати в список з кастомною Model
                 disposableRetrofit = Observable.fromIterable(listMyCoins)
                        .map(o -> {
                            for (ModelCoin el : modelCoins) {
                                if (el.getFull_id().equals(o.getIdName())) {
                                    return new ModelCoinPortfolio(o, el);
                                }
                            }
                            return 0;
                        })
                        .toList()
                        .subscribe(objects -> {
                            if(!objects.contains(0)) {
                                coinsAdapterPortfolio.updateList(objects);
                                countAllMoneyAndGrowth(objects);
                            }
                        }, (throwable) -> {
                        });

                progressBarPortfolio.setVisibility(View.GONE);
            }
        });
        return inflater.inflate(R.layout.fragment_portfolio, container, false);
    }

    //Підрахунок загальної кількості грошей
    public void countAllMoneyAndGrowth(List<Serializable> objects) {
        double allMoney = 0.0;

        listPortfolio = new ArrayList<>();
        for (Object el : objects) {
            listPortfolio.add((ModelCoinPortfolio) el);
        }

        for (ModelCoinPortfolio el : listPortfolio) {
            allMoney += Double.valueOf(el.getModelCoinFirebase().getCount()) * Double.valueOf(el.getModelCoin().getPrice());
        }
        textAllMoney.setText(String.valueOf(new DecimalFormat("0.0").format(allMoney)) + " USD");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listPortfolio = new ArrayList<>();
        portfolioBottomSheet = new PortfolioBottomSheet();
        progressBarPortfolio = view.findViewById(R.id.progressBarPortfolio);
        progressBarPortfolio.setVisibility(View.VISIBLE);
        textAllMoney = view.findViewById(R.id.textAllMoney);
        textEmpty = view.findViewById(R.id.textEmptyPortfolio);
        recyclerView = view.findViewById(R.id.recyclerPortfolio);

        coinsAdapterPortfolio = new CoinsAdapterPortfolio(new ArrayList(), getActivity(), this);
        recyclerView.setAdapter(coinsAdapterPortfolio);

        viewModelBottomSheet.getAllElementsFromFirebase();


    }

    //Очищення disposable від інтервалів
    @Override
    public void onDestroyView() {
        if (disposable != null)
            disposable.dispose();
        super.onDestroyView();
    }

    //Натиск на кнопку додаткової інформації
    @Override
    public void onClickMoreInfoPortfolio(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("coin", listPortfolio.get(position));
        portfolioBottomSheet.setArguments(bundle);
        portfolioBottomSheet.show(getActivity().getSupportFragmentManager(), "Portfolio");
    }
}