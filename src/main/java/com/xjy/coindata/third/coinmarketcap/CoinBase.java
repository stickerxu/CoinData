package com.xjy.coindata.third.coinmarketcap;

import com.xjy.coindata.entity.BaseEntity;

import java.io.Serializable;
import java.util.Map;

public class CoinBase extends BaseEntity implements Serializable {


    private Integer id;
    private String name;
    private String symbol;
    private String website_slug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWebsite_slug() {
        return website_slug;
    }

    public void setWebsite_slug(String website_slug) {
        this.website_slug = website_slug;
    }

}
