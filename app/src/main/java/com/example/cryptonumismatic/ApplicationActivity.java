package com.example.cryptonumismatic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.example.cryptonumismatic.ViewModel.ViewModelNetwork;
import com.example.cryptonumismatic.models.ModelTopCoin;
import com.example.cryptonumismatic.request.CoinsClient;
import com.example.cryptonumismatic.request.RetrofitClient;
import com.example.cryptonumismatic.response.TopCoinResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    //ViewModelNetwork viewModelNetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        getSupportActionBar().hide();

        Log.d("MyLog", "======START APP=======");
        //Ініціалізація навігації(BottomNavigationView, NavHostFragment)
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());


       // viewModelNetwork = new ViewModelProvider(this).get(ViewModelNetwork.class);
        /*viewModelNetwork.getMutableLiveDataTopTenCoins().observe(this, new Observer<List<ModelTopCoin>>() {
            @Override
            public void onChanged(List<ModelTopCoin> modelTopCoins) {
                Log.d("MyLog", "VIEW MODEL " + modelTopCoins.size());
                for (ModelTopCoin el : modelTopCoins) {
                    Log.d("MyLog", el.toString());
                }
                Log.d("MyLog", " END VIEW MODEL " + modelTopCoins.size());
            }
        });

        Disposable disposable  = Observable.interval(6000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el->{
                    Log.d("MyLog","GIVE SOME DATA");
                    viewModelNetwork.updatetop();
                },e->{
                    Log.d("MyLog","ERROR");
                });



        viewModelNetwork.getMutableLiveDataAllCoins().observe(this, new Observer<List<ModelTopCoin>>() {
            @Override
            public void onChanged(List<ModelTopCoin> modelTopCoins) {
                Log.d("MyLog", "VIEW MODEL ALL" + modelTopCoins.size());
                List<ModelTopCoin> a = new ArrayList<>();
                for(ModelTopCoin el: modelTopCoins){
                    a.add(el);
                }
                try {
                    Collections.sort(a, new Comparator<ModelTopCoin>() {
                        @Override
                        public int compare(ModelTopCoin o1, ModelTopCoin o2) {
                            try {
                                return Double.valueOf(o2.getModelOneDayChange().getPriceChangePct()).compareTo(Double.valueOf(o1.getModelOneDayChange().getPriceChangePct()));
                            } catch (NullPointerException ee) {
                                Log.d("MyLog", "NULL EXCEPTION");
                            }
                            return 0;
                        }
                    });
                }catch (IllegalArgumentException ee){
                    Log.e("MyLog",ee.getMessage());
                }

                Log.d("MyLog", " END VIEW MODEL ALL" + a.size());

                for(int i =0;i<10;i++){
                    Log.d("MyLog", "ALL: " + a.get(i).toString());
                }
            }
        });

        Disposable disposable2  = Observable.interval(15000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(el->{
                    Log.d("MyLog","GIVE SOME DATA ALL");
                    viewModelNetwork.updateall();
                },e->{
                    Log.d("MyLog","ERROR");
                });*/
    }
}