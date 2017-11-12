package com.ximedes.bitcoin.miniproject.service;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.script.ScriptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service to store given value on to the blockchain.
 */
@Slf4j
@Component
public class StoreService {

    private final WalletService walletService;

    @Autowired
    public StoreService(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Store the given value on thr blockchain
     *
     * @param value  the value to be stored
     * @param hashed hash the value before storing or not.
     * @throws InsufficientMoneyException of not enough money is available in the wallet.
     */
    public void store(final String value, boolean hashed) throws InsufficientMoneyException {
        final byte[] bytes;
        if (hashed) {
            bytes = Sha256Hash.of(value.getBytes()).getBytes();
            log.debug("store() : value is SHA-256 hashed");
        } else {
            log.debug("store() : value is plain");
            bytes = value.getBytes();
        }

        log.debug("store({}) : bytes: '{}'", value, toHexString(bytes));

        // Create a tx with an OP_RETURN output
        Transaction tx = walletService.createTransaction();
        tx.addOutput(Coin.ZERO, ScriptBuilder.createOpReturnScript(bytes));

        // BitcoinJ takes care about the fee and the the change is send back to the wallet.
        walletService.send(tx);
    }

    String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}

