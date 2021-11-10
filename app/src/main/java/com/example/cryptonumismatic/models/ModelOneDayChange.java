package com.example.cryptonumismatic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOneDayChange {
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("price_change")
    @Expose
    private String priceChange;
    @SerializedName("price_change_pct")
    @Expose
    private String priceChangePct;
    @SerializedName("volume_change")
    @Expose
    private String volumeChange;
    @SerializedName("volume_change_pct")
    @Expose
    private String volumeChangePct;
    @SerializedName("market_cap_change")
    @Expose
    private String marketCapChange;
    @SerializedName("market_cap_change_pct")
    @Expose
    private String marketCapChangePct;

    public ModelOneDayChange(String volume, String priceChange, String priceChangePct, String volumeChange, String volumeChangePct, String marketCapChange, String marketCapChangePct) {
        this.volume = volume;
        this.priceChange = priceChange;
        this.priceChangePct = priceChangePct;
        this.volumeChange = volumeChange;
        this.volumeChangePct = volumeChangePct;
        this.marketCapChange = marketCapChange;
        this.marketCapChangePct = marketCapChangePct;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getPriceChangePct() {
        return priceChangePct;
    }

    public void setPriceChangePct(String priceChangePct) {
        this.priceChangePct = priceChangePct;
    }

    public String getVolumeChange() {
        return volumeChange;
    }

    public void setVolumeChange(String volumeChange) {
        this.volumeChange = volumeChange;
    }

    public String getVolumeChangePct() {
        return volumeChangePct;
    }

    public void setVolumeChangePct(String volumeChangePct) {
        this.volumeChangePct = volumeChangePct;
    }

    public String getMarketCapChange() {
        return marketCapChange;
    }

    public void setMarketCapChange(String marketCapChange) {
        this.marketCapChange = marketCapChange;
    }

    public String getMarketCapChangePct() {
        return marketCapChangePct;
    }

    public void setMarketCapChangePct(String marketCapChangePct) {
        this.marketCapChangePct = marketCapChangePct;
    }

    @Override
    public String toString() {
        return "ModelOneDayChange{" +
                "volume='" + volume + '\'' +
                ", priceChange='" + priceChange + '\'' +
                ", priceChangePct='" + priceChangePct + '\'' +
                ", volumeChange='" + volumeChange + '\'' +
                ", volumeChangePct='" + volumeChangePct + '\'' +
                ", marketCapChange='" + marketCapChange + '\'' +
                ", marketCapChangePct='" + marketCapChangePct + '\'' +
                '}';
    }
}
