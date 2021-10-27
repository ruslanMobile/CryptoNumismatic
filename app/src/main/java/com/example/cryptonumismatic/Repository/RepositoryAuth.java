package com.example.cryptonumismatic.Repository;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RepositoryAuth {
    private MutableLiveData<Boolean> mutableLiveDataIsLogIn;
    private MutableLiveData<FirebaseUser> mutableLiveDataFirebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public RepositoryAuth(Application application) {
        this.context = application;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mutableLiveDataIsLogIn = new MutableLiveData<>(false);
        mutableLiveDataFirebaseUser = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null){
            mutableLiveDataIsLogIn.postValue(true);
            mutableLiveDataFirebaseUser.postValue(firebaseAuth.getCurrentUser());
        }
    }


    public MutableLiveData<Boolean> getMutableLiveDataIsLogIn() {
        return mutableLiveDataIsLogIn;
    }

    public MutableLiveData<FirebaseUser> getMutableLiveDataFirebaseUser() {
        return mutableLiveDataFirebaseUser;
    }

    //Вхід в аккаунт firebase
    public void logIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("MyLog", "COMPLETE LOGIN");
                        if (task.isSuccessful()) {
                            Log.d("MyLog", "SECCESS LOGIN " + task.getResult().getUser().getUid());
                            mutableLiveDataFirebaseUser.postValue(firebaseAuth.getCurrentUser());
                        }else{
                            Toast.makeText(context,"User not found",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Реєстрація аккаунту в firebase
    public void signUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("MyLog", "COMPLETE REGISTER");
                        if (task.isSuccessful()) {
                            Toast.makeText(context,"Registration complete",Toast.LENGTH_LONG).show();
                            Log.d("MyLog", "SECCESS REGISTER " + task.getResult().getUser().getUid());
                            DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                            Map<String, Object> map = new HashMap<>();
                            map.put("email", email);
                            map.put("password", password);
                            documentReference.set(map);
                            mutableLiveDataFirebaseUser.postValue(firebaseAuth.getCurrentUser());
                        }else Toast.makeText(context,"Registration failed",Toast.LENGTH_LONG).show();
                    }
                });
    }

    //Отримання клієнта нашого гугл аккаунта
    public GoogleSignInClient getClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(context,gso);
    }

    //Вхід за допомогою гугл і нашого ключа
    public void logInWithGoogle(String token) {
        AuthCredential credential = GoogleAuthProvider.getCredential(token,null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context,"Success Log In",Toast.LENGTH_LONG).show();
                    Log.d("MyLog", "SECCESS REG WITH GOOGLE " + task.getResult().getUser().getUid());
                    DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", task.getResult().getUser().getEmail());
                    map.put("name", task.getResult().getUser().getDisplayName());
                    documentReference.set(map);
                    mutableLiveDataFirebaseUser.postValue(firebaseAuth.getCurrentUser());
                }
            }
        });
        /*FirebaseAuth.getInstance().signOut();
        getClient().signOut();*/
    }
}
