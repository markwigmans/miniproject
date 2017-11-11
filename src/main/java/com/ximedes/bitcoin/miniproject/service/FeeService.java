package com.ximedes.bitcoin.miniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Calculate the fee for a given message.
 */
@Component
public class FeeService {

    /**
     * fee per byte
     */
    private final int fee;

    @Autowired
    public FeeService(@Value("${fee.per.byte:50") int fee) {
        this.fee = fee;
    }

    public int calcFee(byte[] message) {
        if (message != null) {
            return message.length * fee;
        }
        return 0;
    }
}
