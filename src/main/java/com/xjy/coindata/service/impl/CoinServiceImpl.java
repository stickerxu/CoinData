package com.xjy.coindata.service.impl;

import com.xjy.coindata.repository.CoinRepository;
import com.xjy.coindata.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinRepository coinRepository;
}
