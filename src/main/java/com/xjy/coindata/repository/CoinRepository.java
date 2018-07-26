package com.xjy.coindata.repository;

import com.xjy.coindata.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoinRepository extends JpaRepository<Coin, Integer>, JpaSpecificationExecutor<Coin> {
}
