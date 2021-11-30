package com.example.cryptonumismatic.room;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {CoinRoom.class},version = 1)
public abstract class Database extends RoomDatabase {
    public abstract CoinDao getDao();
    public static Database INSTANCE;

    public static Database getDatabase(Context context){
        if(INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "Database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
