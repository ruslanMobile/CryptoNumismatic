package com.example.cryptonumismatic.Fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cryptonumismatic.ApplicationActivity;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelStartApp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class StartAppFragment extends Fragment {
    private ViewModelStartApp viewModelStartApp;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_app,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelStartApp = new ViewModelProvider(this).get(ViewModelStartApp.class);

        //Перевірка,чи користувач ввійшов в систему.Запуск додатку
        viewModelStartApp.getMutableLiveDataFirebaseUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null) {
                    Log.d("MyLog", "START APP LOGIN COMPLETELY " + firebaseUser.getUid());
                    Intent intent = new Intent(getActivity(), ApplicationActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Вхід за допомогою Гугл.Слухач лаунчера і при виборі аккаунту запуск функції входу.
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.d("MyLog","LAUNCHER");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try{
                GoogleSignInAccount acc = task.getResult();
                if(acc!=null) viewModelStartApp.logInWithGoogle(acc.getIdToken());
            }catch (Exception e){
                Log.d("MyLog","API EXCEPTION");
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(view);
        Button logIn = view.findViewById(R.id.buttonLogInStartApp);
        Button signUp = view.findViewById(R.id.buttonSignUpStartApp);
        Button googleSignIn = view.findViewById(R.id.buttonGoogleStartApp);
        //Слухач для входу в додаток
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startAppFragment_to_logInFragment);
            }
        });
        //Слухач для реєстрації в додатоку
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startAppFragment_to_registrationFragment);
            }
        });
        //Слухач для входу через гугл
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch(viewModelStartApp.getClient().getSignInIntent());
            }
        });
    }

}