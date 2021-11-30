package com.example.cryptonumismatic.room;

import android.content.Context;
import android.util.Log;

import com.example.cryptonumismatic.models.ModelCoin;

import java.util.List;

public class Repository {
    Database database;
    public Repository(Context context) {
        database = Database.getDatabase(context);
    }

    public void addElement(CoinRoom coinRoom) {
        database.getDao().addElement(coinRoom);
    }

    public List<CoinRoom> getAllElements() { return database.getDao().getAllElements();}

    public void deleteAllElements(){database.getDao().deleteAllElements();}
}
