package com.xjy.coindata.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Global extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8806321843708397535L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer activeCryptocurrencies;
    private Integer activeMarkets;
    @Column(precision = 5, scale = 2)
    private BigDecimal bitcoinPercentageOfMarketCap;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "global_id", insertable = false, updatable = false)
    private List<Coin> coins;

}
