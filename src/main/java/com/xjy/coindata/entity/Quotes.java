package com.xjy.coindata.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Quotes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 493628033319442090L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 10)
    private String symbol;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;
    @Column(precision = 15, scale = 2)
    private BigDecimal volumeOneDay;
    @Column(precision = 15, scale = 2)
    private BigDecimal marketCap;
    @Column(precision = 5, scale = 2)
    private BigDecimal percentChangeOneHour;
    @Column(precision = 5, scale = 2)
    private BigDecimal percentChangeOneDay;
    @Column(precision = 5, scale = 2)
    private BigDecimal percentChangeOneWeek;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;

    //关系表
    @Column(name = "coin_id")
    private Integer coinId;
}
