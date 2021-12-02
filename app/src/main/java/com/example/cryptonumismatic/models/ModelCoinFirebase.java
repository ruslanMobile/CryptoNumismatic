package com.example.cryptonumismatic.models;

public class ModelCoinFirebase {
    private String idName;
    private String price;
    private String count;
    private String date;
    private String hashId;

    public ModelCoinFirebase(String idName, String price, String count, String date,String hashId) {
        this.idName = idName;
        this.price = price;
        this.count = count;
        this.date = date;
        this.hashId = hashId;
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

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    @Override
    public String toString() {
        return "ModelCoinFirebase{" +
                "idName='" + idName + '\'' +
                ", price='" + price + '\'' +
                ", count='" + count + '\'' +
                ", date='" + date + '\'' +
                ", hashId='" + hashId + '\'' +
                '}';
    }
}
