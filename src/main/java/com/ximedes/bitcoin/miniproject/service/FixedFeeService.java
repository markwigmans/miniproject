package com.ximedes.bitcoin.miniproject.service;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * FeeService with a fixed, configurable fee.
 */
@Slf4j
@Component
public class FixedFeeService implements FeeService {
    /**
     * fee per byte
     */
    private final int fee;

    @Autowired
    public FixedFeeService(@Value("${fee.per.byte:100}") int fee) {
        this.fee = fee;
    }

    @PostConstruct
    void init() {
        log.debug("init() : Fee per byte: {}", fee);
    }

    public Coin feePerKb() {
        return Coin.valueOf(fee * 1000);
    }
}
