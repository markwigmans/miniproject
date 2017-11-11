package com.ximedes.bitcoin.miniproject.service;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Service to calculate the fee
 */
@Slf4j
@Component
public class FeeService {

    /**
     * fee per byte
     */
    private final int fee;

    @Autowired
    public FeeService(@Value("${fee.per.byte:100}") int fee) {
        this.fee = fee;
    }

    @PostConstruct
    void init() {
        log.info("Fee per byte: {}", fee);
    }

    public Coin feePerKb() {
        return Coin.valueOf(fee * 1000);
    }

    public Coin fee(int messageSize) {
        if (messageSize > 0) {
            return Coin.valueOf(fee * messageSize);
        }
        return Coin.valueOf(0);
    }
}
