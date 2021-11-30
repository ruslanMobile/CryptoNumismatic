package com.example.cryptonumismatic.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cryptonumismatic.models.ModelCoin;

import java.util.List;

import retrofit2.http.GET;

@Dao
public interface CoinDao {
    @Insert
    void addElement(CoinRoom coinRoom);

    @Query("SELECT * FROM coins")
    List<CoinRoom> getAllElements();

    @Query("DELETE FROM coins")
    void deleteAllElements();
}
