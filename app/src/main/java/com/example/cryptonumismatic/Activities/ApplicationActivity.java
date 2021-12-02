package com.example.cryptonumismatic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.Repository.RepositoryAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ApplicationActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
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

        /*FirebaseAuth.getInstance().signOut();
        new RepositoryAuth(getApplication()).getClient().signOut();*/
    }
}