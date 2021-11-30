package com.example.cryptonumismatic.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "coins")
public class CoinRoom implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String idName;
    public String price;
    public String count;
    public String date;

    public CoinRoom(String idName, String price, String count, String date) {
        this.idName = idName;
        this.price = price;
        this.count = count;
        this.date = date;
    }

    @Override
    public String toString() {
        return "CoinRoom{" +
                "id=" + id +
                ", idName='" + idName + '\'' +
                ", price='" + price + '\'' +
                ", count='" + count + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
