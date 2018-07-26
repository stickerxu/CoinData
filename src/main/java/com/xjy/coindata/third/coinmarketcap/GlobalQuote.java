package com.xjy.coindata.third.coinmarketcap;

import com.xjy.coindata.entity.BaseEntity;

import java.io.Serializable;
import java.util.Map;

public class GlobalQuote extends BaseEntity implements Serializable {

    private Double total_market_cap;
    private Double total_volume_24h;

    public Double getTotal_market_cap() {
        return total_market_cap;
    }

    public void setTotal_market_cap(Double total_market_cap) {
        this.total_market_cap = total_market_cap;
    }

    public Double getTotal_volume_24h() {
        return total_volume_24h;
    }

    public void setTotal_volume_24h(Double total_volume_24h) {
        this.total_volume_24h = total_volume_24h;
    }
}
