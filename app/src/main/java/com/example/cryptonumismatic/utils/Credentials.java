package com.example.cryptonumismatic.utils;

public class Credentials {
    public static final String BASE_URL = "https://api.nomics.com/v1/";
    public static final String API_KEY = "ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b";
    public static final String REQUEST_TOP_10_COINS = "currencies/ticker?page=1&convert=USD&key=ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b&interval=1d&per-page=10&sort=rank";
    public static final String REQUEST_ALL_COINS = "currencies/ticker?convert=USD&interval=1d&key=ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b&sort=rank";
    public static final String REQUEST_100_COINS = "currencies/ticker?page=1&convert=USD&key=ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b&interval=1d&per-page=100&sort=rank";
    public static final String REQUEST_IDS = "https://api.nomics.com/v1/currencies/ticker?convert=USD&interval=1d&key=ae3a5fe528b4d90893e3d36013b1a88a78e3ea2b";
}
