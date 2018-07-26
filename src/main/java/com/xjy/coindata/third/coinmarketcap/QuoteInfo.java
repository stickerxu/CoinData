package com.xjy.coindata.third.coinmarketcap;

import com.xjy.coindata.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;

public class QuoteInfo extends BaseEntity implements Serializable {

    private Double price;
    private Double volume_24h;
    private Double market_cap;
    private Double percent_change_1h;
    private Double percent_change_24h;
    private Double percent_change_7d;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(Double volume_24h) {
        this.volume_24h = volume_24h;
    }

    public Double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(Double market_cap) {
        this.market_cap = market_cap;
    }

    public Double getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(Double percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public Double getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(Double percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public Double getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(Double percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }
}
