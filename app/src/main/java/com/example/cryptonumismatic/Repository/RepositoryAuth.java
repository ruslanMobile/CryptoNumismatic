package com.example.cryptonumismatic.Repository;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.models.ModelCoinFirebase;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryAuth {
    private MutableLiveData<Boolean> mutableLiveDataIsLogIn;
    private MutableLiveData<FirebaseUser> mutableLiveDataFirebaseUser;
    private MutableLiveData<List<ModelCoinFirebase>> mutableLiveDataModelFirebase;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public RepositoryAuth(Application application) {
        this.context = application;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mutableLiveDataIsLogIn = new MutableLiveData<>(false);
        mutableLiveDataFirebaseUser = new MutableLiveData<>();
        mutableLiveDataModelFirebase = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
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

    public MutableLiveData<List<ModelCoinFirebase>> getMutableLiveDataModelFirebase() {
        return mutableLiveDataModelFirebase;
    }

    //Вхід в аккаунт firebase
    public void logIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mutableLiveDataFirebaseUser.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show();
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
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Registration complete", Toast.LENGTH_LONG).show();
                            DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                            Map<String, Object> map = new HashMap<>();
                            map.put("email", email);
                            map.put("password", password);
                            documentReference.set(map);
                            mutableLiveDataFirebaseUser.postValue(firebaseAuth.getCurrentUser());
                        } else
                            Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //Отримання клієнта нашого гугл аккаунта
    public GoogleSignInClient getClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("context.getString(R.string.default_web_client_id)")
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(context, gso);
    }

    //Вхід за допомогою гугл і нашого ключа
    public void logInWithGoogle(String token) {
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Success Log In", Toast.LENGTH_LONG).show();
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

    //Додавання нової монети в портфоліо
    public void addElementToFirebase(String id, String price, String count, String date) {
        DocumentReference documentReference = firebaseFirestore.collection(firebaseAuth.getCurrentUser().getUid())
                .document();
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("price", price);
        map.put("count", count);
        map.put("date", date);
        map.put("hashId",documentReference.getId());
        documentReference.set(map);
    }

    //Отримання всіх монет з портфоліо
    public void getAllElementsFromFirebase() {
        List<ModelCoinFirebase> listMyCoins = new ArrayList<>();
        Task<QuerySnapshot> documentReference = firebaseFirestore.collection(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listMyCoins.add(new ModelCoinFirebase(document.getData().get("id").toString(),
                                        document.getData().get("price").toString(), document.getData().get("count").toString(),
                                        document.getData().get("date").toString(),document.getData().get("hashId").toString()));
                            }
                        } else {}
                        mutableLiveDataModelFirebase.postValue(listMyCoins);
                    }
                });
    }
    //Видалення елементу з портфоліо
    public void deleteElementFromFirebase(String name){
        DocumentReference documentReference = firebaseFirestore.collection(firebaseAuth.getCurrentUser().getUid())
                .document(name);
        documentReference.delete();
    }
}