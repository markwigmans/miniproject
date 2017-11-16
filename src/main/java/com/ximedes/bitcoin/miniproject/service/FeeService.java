package com.ximedes.bitcoin.miniproject.service;

import org.bitcoinj.core.Coin;

/**
 * Service to calculate the fee
 */
public interface FeeService {

    /**
     * Fee per Kb
     */
    Coin feePerKb();
}
