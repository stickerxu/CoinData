package com.xjy.coindata.third.coinmarketcap;

import com.xjy.coindata.entity.BaseEntity;

import java.io.Serializable;
import java.util.Map;

public class CoinGlobal extends BaseEntity implements Serializable {

    private Integer active_cryptocurrencies;
    private Integer active_markets;
    private Double bitcoin_percentage_of_market_cap;
    private Map<String,GlobalQuote> quotes;
    private Integer last_updated;

    public Integer getActive_cryptocurrencies() {
        return active_cryptocurrencies;
    }

    public void setActive_cryptocurrencies(Integer active_cryptocurrencies) {
        this.active_cryptocurrencies = active_cryptocurrencies;
    }

    public Integer getActive_markets() {
        return active_markets;
    }

    public void setActive_markets(Integer active_markets) {
        this.active_markets = active_markets;
    }

    public Double getBitcoin_percentage_of_market_cap() {
        return bitcoin_percentage_of_market_cap;
    }

    public void setBitcoin_percentage_of_market_cap(Double bitcoin_percentage_of_market_cap) {
        this.bitcoin_percentage_of_market_cap = bitcoin_percentage_of_market_cap;
    }

    public Map<String, GlobalQuote> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, GlobalQuote> quotes) {
        this.quotes = quotes;
    }

    public Integer getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Integer last_updated) {
        this.last_updated = last_updated;
    }
}
