package com.example.cryptonumismatic;

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
import com.example.cryptonumismatic.ViewModel.ViewModelBottomSheet;
import com.example.cryptonumismatic.ViewModel.ViewModelNetwork;
import com.example.cryptonumismatic.models.ModelCoin;
import com.example.cryptonumismatic.models.ModelCoinPortfolio;
import com.example.cryptonumismatic.room.CoinDao;
import com.example.cryptonumismatic.room.CoinRoom;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class PortfolioFragment extends Fragment implements CoinsAdapterPortfolio.ClickMoreInfoListener {
    private ViewModelBottomSheet viewModelBottomSheet;
    private ViewModelNetwork viewModelNetwork;
    private List<CoinRoom> listMyCoins;
    private List<ModelCoin> listRetrofit;
    private List<ModelCoinPortfolio> listPortfolio;
    private RecyclerView recyclerView;
    private Disposable disposable;
    private String ids = "";
    private TextView textAllMoney, textGrowth24;
    private ProgressBar progressBarPortfolio;
    private CoinsAdapterPortfolio coinsAdapterPortfolio;
    private PortfolioBottomSheet portfolioBottomSheet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModelBottomSheet = new ViewModelProvider(requireActivity()).get(ViewModelBottomSheet.class);
        viewModelNetwork = new ViewModelProvider(requireActivity()).get(ViewModelNetwork.class);
        viewModelNetwork.getMutableLiveDataIdsCoins().observe(getViewLifecycleOwner(), new Observer<List<ModelCoin>>() {
            @Override
            public void onChanged(List<ModelCoin> modelCoins) {
                Log.d("MyLog", "VIEW MODEL IDS " + modelCoins.size());
                for (ModelCoin el : modelCoins) {
                    Log.d("MyLog", el.toString());
                }
                Disposable disposable = Observable.fromIterable(listMyCoins)
                        .map(o -> {
                            for (ModelCoin el : modelCoins) {
                                if (el.getId().equals(o.getIdName())) {
                                    return new ModelCoinPortfolio(o, el);
                                }
                            }
                            return 0;
                        })
                        .toList()
                        .subscribe(objects -> {
                            Log.d("MyLog", "RX " + objects.size());
                            for (Object el : objects) {
                                Log.d("MyLog", "RX " + el.toString());
                            }
                            coinsAdapterPortfolio.updateList(objects);
                            countAllMoneyAndGrowth(objects);
                        }, (throwable) -> {});
                progressBarPortfolio.setVisibility(View.GONE);
            }
        });
        return inflater.inflate(R.layout.fragment_portfolio, container, false);
    }

    public void countAllMoneyAndGrowth(List<Serializable> objects) {
        double allMoney = 0.0;
        double usdBefore = 0.0;

        listPortfolio = new ArrayList<>();
        for (Object el : objects) {
            listPortfolio.add((ModelCoinPortfolio) el);
        }

        for (ModelCoinPortfolio el : listPortfolio) {
            allMoney += Double.valueOf(el.getCoinRoom().getCount()) * Double.valueOf(el.getModelCoin().getPrice());
            try {
                usdBefore += Double.valueOf(el.getCoinRoom().getCount()) *
                        (Double.valueOf(el.getModelCoin().getPrice()) - Double.valueOf(el.getModelCoin().getModelOneDayChange().getPriceChange()));
            } catch (NullPointerException ex) {
                usdBefore += Double.valueOf(el.getCoinRoom().getCount()) * Double.valueOf(el.getModelCoin().getPrice());
            }
        }
        textAllMoney.setText(String.valueOf(new DecimalFormat("0.0").format(allMoney)) + " USD");
        textGrowth24.setText("USD" + String.valueOf(new DecimalFormat("0.00").format(allMoney - usdBefore)) + "(24h)");
        if (allMoney - usdBefore < 0)
            textGrowth24.setTextColor(getResources().getColor(R.color.percentMinus));
        else textGrowth24.setTextColor(getResources().getColor(R.color.percentPlus));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // viewModelBottomSheet.deleteAllElements();
        listPortfolio = new ArrayList<>();
        portfolioBottomSheet = new PortfolioBottomSheet();
        progressBarPortfolio = view.findViewById(R.id.progressBarPortfolio);
        progressBarPortfolio.setVisibility(View.VISIBLE);
        textAllMoney = view.findViewById(R.id.textAllMoney);
        textGrowth24 = view.findViewById(R.id.textGrowth24Portfolio);
        recyclerView = view.findViewById(R.id.recyclerPortfolio);

        coinsAdapterPortfolio = new CoinsAdapterPortfolio(new ArrayList(), getActivity(), this);
        recyclerView.setAdapter(coinsAdapterPortfolio);

        listMyCoins = viewModelBottomSheet.getAllElements();
        for (CoinRoom el : listMyCoins) {
            Log.d("MyLog", "ROOM " + el.toString());
        }

        for (CoinRoom el : listMyCoins) {
            ids += el.getIdName() + ",";
        }
        if (ids.trim().length() > 0) {
            disposable = Observable.interval(1000, 7000,
                    TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(el -> {
                        Log.d("MyLog", "GIVE SOME DATA PORTFOLIO");
                        viewModelNetwork.updateids(ids);
                    }, e -> {
                        Log.d("MyLog", "ERROR");
                    });
        } else Log.e("MyLog", "EMPTY IDS");
    }

    //Очищення disposable від інтервалів
    @Override
    public void onDestroyView() {
        Log.d("MyLog", "FRAGMENT onDestroyView=========");
        if (disposable != null)
            disposable.dispose();
        super.onDestroyView();
    }

    //Натиск на кнопку додаткової інформації
    @Override
    public void onClickMoreInfoPortfolio(int position) {
        Log.e("MyLog", "ClICL");
        Bundle bundle = new Bundle();
        bundle.putSerializable("coin", listPortfolio.get(position));
        portfolioBottomSheet.setArguments(bundle);
        portfolioBottomSheet.show(getActivity().getSupportFragmentManager(), "Portfolio");
    }
}