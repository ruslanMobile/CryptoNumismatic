package com.example.cryptonumismatic.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.Repository.RepositoryAuth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;


public class ViewModelLogin extends AndroidViewModel {
    private RepositoryAuth repositoryAuth;
    private MutableLiveData<FirebaseUser> mutableLiveDataFirebaseUser;

    public ViewModelLogin(@NonNull Application application) {
        super(application);
        repositoryAuth = new RepositoryAuth(application);
        mutableLiveDataFirebaseUser = repositoryAuth.getMutableLiveDataFirebaseUser();
    }

    //Вхід
    public void logIn(String email, String password) {
        repositoryAuth.logIn(email, password);
    }

    //Реєстрація
    public void signUp(String email, String password) {
        repositoryAuth.signUp(email, password);
    }

    //Отримання клієнта гугл аккаунта
    public GoogleSignInClient getClient() {
        return repositoryAuth.getClient();
    }

    //Вхід з гугл
    public void logInWithGoogle(String idToken) {
        repositoryAuth.logInWithGoogle(idToken);
    }

    public MutableLiveData<FirebaseUser> getMutableLiveDataFirebaseUser() {
        return mutableLiveDataFirebaseUser;
    }

    //Валідація поля вводу Email
    public boolean validationEmail(String text) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (text.isEmpty()) {
            return false;
        } else {
            if (text.trim().matches(emailPattern)) {
                return true;
            } else {
                return false;
            }
        }
    }

    //Валідація поля вводу Password
    public boolean validationPassword(String text){
        if(text.trim().length()<8) return false;
        return true;
    }
}
