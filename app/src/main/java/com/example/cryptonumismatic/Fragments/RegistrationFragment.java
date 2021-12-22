package com.example.cryptonumismatic.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cryptonumismatic.Activities.ApplicationActivity;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationFragment extends Fragment {
    private ViewModelLogin viewModelLogin;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private boolean letRegister = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelLogin = new ViewModelProvider(this).get(ViewModelLogin.class);
        //Перевірка,чи користувач ввійшов в систему.Запуск додатку
        viewModelLogin.getMutableLiveDataFirebaseUser().observe(getActivity(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!= null){
                    Intent intent = new Intent(getActivity(), ApplicationActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Вхід за допомогою Гугл.Слухач лаунчера і при виборі аккаунту запуск функції входу.
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try{
                GoogleSignInAccount acc = task.getResult();
                if(acc!=null) viewModelLogin.logInWithGoogle(acc.getIdToken());
            }catch (Exception e){}
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextInputEditText editTextEmail = view.findViewById(R.id.editTextEmailRegistrationInput);
        TextInputLayout editTextEmailOutput = view.findViewById(R.id.editTextEmailRegistration);

        TextInputEditText editTextPassword = view.findViewById(R.id.editTextPasswordRegistrationInput);
        TextInputLayout editTextPasswordOutput = view.findViewById(R.id.editTextPasswordRegistration);

        TextInputEditText editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPasswordRegistrationInput);
        TextInputLayout editTextRepeatPasswordOutput = view.findViewById(R.id.editTextRepeatPasswordRegistration);

        Button button = view.findViewById(R.id.buttonRegistration);
        Button googleLogIn = view.findViewById(R.id.buttonGoogleRegistration);

        //Слухач набору тексту E-mail
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if(!viewModelLogin.validationEmail(editTextEmail.getText().toString())) {
                    editTextEmailOutput.setHelperText("Invalid E-mail");
                    editTextEmailOutput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.redText)));
                    letRegister = false;
                }else {
                    editTextEmailOutput.setHelperTextEnabled(false);
                    letRegister = true;
                }
            }
        });

        //Слухач набору тексту Password
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!viewModelLogin.validationPassword(editTextPassword.getText().toString())){
                    editTextPasswordOutput.setHelperText("Invalid Password");
                    editTextPasswordOutput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.redText)));
                    letRegister = false;
                }else {
                    editTextPasswordOutput.setHelperTextEnabled(false);
                    letRegister = true;
                }
            }
        });
        //Слухач набору тексту Password Repeat
        editTextRepeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!editTextRepeatPassword.getText().toString().trim().equals(editTextPassword.getText().toString().trim())) {
                    editTextRepeatPasswordOutput.setHelperText("Invalid Password");
                    editTextRepeatPasswordOutput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.redText)));
                    letRegister = false;
                }else {
                    editTextRepeatPasswordOutput.setHelperTextEnabled(false);
                    letRegister = true;
                }
            }
        });
        //Слухач натиску на кнопку для реєстрації
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(letRegister) {
                    viewModelLogin.signUp(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
                }
                else Toast.makeText(getActivity(),"Invalid date",Toast.LENGTH_LONG).show();
            }
        });

        //Слухач входу через гугл аккаунт
        googleLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch(viewModelLogin.getClient().getSignInIntent());
            }
        });
    }
}