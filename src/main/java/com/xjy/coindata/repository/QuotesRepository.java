package com.xjy.coindata.repository;

import com.xjy.coindata.entity.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<Quotes, Integer> {
}
