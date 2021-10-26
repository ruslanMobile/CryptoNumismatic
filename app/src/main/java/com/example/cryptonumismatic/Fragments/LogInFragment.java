package com.example.cryptonumismatic.Fragments;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.cryptonumismatic.ApplicationActivity;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.ViewModel.ViewModelLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment {
    private ViewModelLogin viewModelLogin;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private boolean letRegister = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelLogin = new ViewModelProvider(this).get(ViewModelLogin.class);

        viewModelLogin.getMutableLiveDataFirebaseUser().observe(getActivity(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!= null){
                    Log.d("MyLog","NOT NULL AND LOG IN");
                    Intent intent = new Intent(getActivity(), ApplicationActivity.class);
                    startActivity(intent);
                }
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.d("MyLog","LAUNCHER");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try{
                GoogleSignInAccount acc = task.getResult();
                if(acc!=null) viewModelLogin.logInWithGoogle(acc.getIdToken());
            }catch (Exception e){
                Log.d("MyLog","API EXCEPTION");
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextInputEditText editTextEmail = view.findViewById(R.id.editTextEmailInput);
        TextInputLayout editTextEmailOutput = view.findViewById(R.id.editTextEmail);

        TextInputEditText editTextPassword = view.findViewById(R.id.editTextPasswordInput);
        TextInputLayout editTextPasswordOutput = view.findViewById(R.id.editTextPassword);

        Button buttonLogIn = view.findViewById(R.id.buttonLogIn);
        Button googleLogIn = view.findViewById(R.id.buttonGoogleLogIn);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!viewModelLogin.validationEmail(s.toString().trim())){
                    editTextEmailOutput.setHelperText("Invalid E-mail");
                    editTextEmailOutput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.redText)));
                    letRegister = false;
                }else {
                    editTextEmailOutput.setHelperTextEnabled(false);
                    letRegister = true;
                }
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!viewModelLogin.validationPassword(s.toString().trim())){
                    editTextPasswordOutput.setHelperText("Invalid Password");
                    editTextPasswordOutput.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.redText)));
                    letRegister = false;
                }else {
                    editTextPasswordOutput.setHelperTextEnabled(false);
                    letRegister = true;
                }
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(letRegister) {
                    viewModelLogin.logIn(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
                }
                else Toast.makeText(getActivity(),"Invalid date",Toast.LENGTH_LONG).show();
            }
        });

        googleLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch(viewModelLogin.getClient().getSignInIntent());
            }
        });
    }
}