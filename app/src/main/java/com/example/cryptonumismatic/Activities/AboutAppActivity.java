package com.example.cryptonumismatic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cryptonumismatic.R;
import android.os.Bundle;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getSupportActionBar().hide();
    }
}