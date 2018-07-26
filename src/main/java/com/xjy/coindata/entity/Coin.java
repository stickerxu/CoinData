package com.xjy.coindata.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import java.util.Date;
import java.util.List;

@Data
@Entity
//@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler", "user", "userId"})
public class Coin extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 79474029991816977L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int(5)", nullable = false)
    private Integer apiId;
    @Column(length = 50)
    private String name;
    @Column(length = 10)
    private String symbol;
    @Column(length = 50)
    private String websiteSlug;
    @Column(columnDefinition = "int(5)")
    private Integer rank;
    @Column(precision = 15, scale = 2)
    private BigDecimal circulatingSupply;
    @Column(precision = 15, scale = 2)
    private BigDecimal totalSupply;
    @Column(precision = 15, scale = 2)
    private BigDecimal maxSupply;
    @Column(precision = 15, scale = 2)
    private BigDecimal totalMarketCap; //全球数据
    @Column(precision = 15, scale = 2)
    private BigDecimal totalVolumeOneDay; //全球数据
    @UpdateTimestamp
    private Date updateTime;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "coin_id", insertable = false, updatable = false)
    private List<Quotes> quotes;

    //关联表
    @Column(name = "global_id")
    private Integer globalId;
}
