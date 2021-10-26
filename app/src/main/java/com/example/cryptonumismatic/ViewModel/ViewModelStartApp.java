package com.example.cryptonumismatic.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.Repository.RepositoryAuth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseUser;

public class ViewModelStartApp extends AndroidViewModel {
    private MutableLiveData<Boolean> mutableLiveDataIsLogIn;
    private MutableLiveData<FirebaseUser> mutableLiveDataFirebaseUser;
    private RepositoryAuth repositoryAuth;
    public ViewModelStartApp(@NonNull Application application) {
        super(application);
        repositoryAuth = new RepositoryAuth(getApplication());
        mutableLiveDataIsLogIn = repositoryAuth.getMutableLiveDataIsLogIn();
        mutableLiveDataFirebaseUser = repositoryAuth.getMutableLiveDataFirebaseUser();
    }

    public MutableLiveData<Boolean> getMutableLiveDataIsLogIn() {
        return mutableLiveDataIsLogIn;
    }

    public MutableLiveData<FirebaseUser> getMutableLiveDataFirebaseUser() {
        return mutableLiveDataFirebaseUser;
    }

    public GoogleSignInClient getClient() {
        return repositoryAuth.getClient();
    }

    public void logInWithGoogle(String idToken) {
        repositoryAuth.logInWithGoogle(idToken);
    }
}
