package com.ximedes.bitcoin.miniproject.service;

import org.bitcoinj.core.Coin;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * unit test class for {@link FeeService}
 */
public class FeeServiceTest {

    @Test
    public void testFee() {
        int fee = 20;
        FeeService service = new FeeService(fee);
        byte[] message = new byte[]{1, 2, 3, 4,};
        assertThat(service.fee(message.length), is(Coin.valueOf(80)));
    }

    @Test
    public void testFeeNull() {
        int fee = 30;
        FeeService service = new FeeService(fee);
        assertThat(service.fee(0), is(Coin.valueOf(0)));
    }

    @Test
    public void testFeePerKb() {
        int fee = 30;
        FeeService service = new FeeService(fee);
        assertThat(service.feePerKb(), is(Coin.valueOf(30 * 1000)));
    }
}