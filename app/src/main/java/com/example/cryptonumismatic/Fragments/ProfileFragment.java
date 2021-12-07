package com.example.cryptonumismatic.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cryptonumismatic.Activities.AboutAppActivity;
import com.example.cryptonumismatic.Activities.MainActivity;
import com.example.cryptonumismatic.R;
import com.example.cryptonumismatic.Repository.RepositoryAuth;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private Button buttonSignOut, buttonAboutApp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        buttonAboutApp = view.findViewById(R.id.buttonAboutAppProfile);
        buttonSignOut = view.findViewById(R.id.buttonSignOutProfile);
        buttonSignOut.setOnClickListener((a)-> {
            Log.e("MyLog","========");
            FirebaseAuth.getInstance().signOut();
            new RepositoryAuth(getActivity().getApplication()).getClient().signOut();
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        buttonAboutApp.setOnClickListener(a->{
            Intent intent = new Intent(getContext(), AboutAppActivity.class);
            startActivity(intent);
        });
    }
}